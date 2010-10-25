/*
 * Created on 9/Fev/2006
 */
package codebase;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * TODO: Implement the error reporting. Implementation of the GNU GetOpt
 * command-line parser.
 * <p>
 * Nowadays, GetOpt is a part of the GNU C library as part of POSIX 2 standard.
 * Unfortunately Java does not offer a call as powerfull as GNU's GetOpt.
 * <p>
 * This class implements a parser guided by two important parameters specified
 * in the constructor: <i>(i)</i> the string with short options and <i>(ii)</i>
 * the array of long options. The array of arguments given to the application
 * (known as <i>argv</i>) should also be passed in contructor. Then the method
 * {@link #getOpt()} should be called repeatedly to get the options. If an
 * option needs an argument the argument can be retrieved by calling
 * {@link #getOptArg()}.
 * <p>
 * <b>Specifying options strings.</b> An <i>options string</i> is the string
 * containing the legitimate option characters for short options. This string is
 * specified according to the following rules:
 * <ol>
 * <li>If an option character is seen that is not listed in the options string,
 * {@link #getOpt()} returns '?' after setting error message.</li>
 * <li> If a char specified in the options string is followed by a colon, that
 * means it that an argument should follow that option. An argument can appear
 * in the same argv-element, or the text of the following argv-element.</li>
 * <li> Two colons mean an option that wants an optional argument; if there is
 * text in the current argv-element, it is returned in {@link #getOptArg()}.</li>
 * <li>If the options string starts with '-' or '+', it requests different
 * methods of handling the non-option argv-elements. See the comments about
 * RETURN_IN_ORDER and REQUIRE_ORDER, below.</li>
 * </ol>
 * <b>Working with long options</b> Long-named options begin with '--' instead
 * of '-'. Their names may be abbreviated as long as the abbreviation is unique
 * or is an exact match for some defined option. Arguments of long options
 * should follow the option name in the same argv-element, separated from the
 * option name by a '=', or else the in next argv-element.
 * <p>
 * When {@link #getOpt()} finds a long-named option, it returns <code>0</code>
 * if that option's `flag' field is non-null, else return the value of the
 * option's 'val' field if the 'flag' field is null.
 * <p>
 * <b>Dealing with options that follow non-option a-elements.</b> If the caller
 * did not specify anything, the default is REQUIRE_ORDER if the environment
 * variable POSIXLY_CORRECT is defined, PERMUTE otherwise.
 * <ol>
 * <li>REQUIRE_ORDER means don't recognize them as options; stop option
 * processing when the first non-option is seen. This is what Unix does. This
 * mode of operation is selected by either setting the environment variable
 * POSIXLY_CORRECT, or using '+' as the first character of the list of option
 * characters.</li>
 * <li> PERMUTE is the default. We permute the contents of argv as we scan, so
 * that eventually all the non-options are at the end. This allows options to be
 * given in any order, even with programs that were not written to expect this.</li>
 * <li> RETURN_IN_ORDER is an option available to programs that were written to
 * expect options and other ARGV-elements in any order and that care about the
 * ordering of the two. We describe each non-option ARGV-element as if it were
 * the argument of an option with character code 1. Using '-' as the first
 * character of the list of option characters selects this mode of operation.
 * </li>
 * </ol>
 * <p>
 * <b>Note:</b> The special argument '--' forces an end of option-scanning
 * regardless of the value of 'ordering'. In the case of RETURN_IN_ORDER, only
 * '--' can cause {@link #getOpt()} to return {@link #EOF} with
 * <code>{@link #optInd} != argv.length</code>.
 * <p>
 * <b>Example:</b>
 * 
 * <pre>
 * public static void main(String[] argv) {
 *     
 *     StringBuffer sbDelete = new StringBuffer();
 *     StringBuffer sbCreate = new StringBuffer();
 *     StringBuffer sbFile = new StringBuffer();
 *     
 *     LongOption[] longOptions = new LongOption[] {
 *             new GetOpt.LongOption(&quot;verbose&quot;, GetOpt.LongOption.NO_ARGUMENT,
 *                 null, 'v'),
 *             new GetOpt.LongOption(&quot;brief&quot;, GetOpt.LongOption.NO_ARGUMENT, null,
 *                 (char) 1),
 *             
 *             new GetOpt.LongOption(&quot;add&quot;, GetOpt.LongOption.NO_ARGUMENT, null,
 *                 'a'),
 *             new GetOpt.LongOption(&quot;append&quot;, GetOpt.LongOption.NO_ARGUMENT,
 *                 null, 'b'),
 *             new GetOpt.LongOption(&quot;delete&quot;,
 *                 GetOpt.LongOption.REQUIRED_ARGUMENT, sbDelete, 'd'),
 *             new GetOpt.LongOption(&quot;create&quot;,
 *                 GetOpt.LongOption.REQUIRED_ARGUMENT, sbCreate, 'c'),
 *             new GetOpt.LongOption(&quot;file&quot;, GetOpt.LongOption.REQUIRED_ARGUMENT,
 *                 sbFile, 'f') };
 *     
 *     GetOpt getOpt = new GetOpt(&quot;main&quot;, argv, &quot;abc:d:f:&quot;, longOptions);
 *     
 *     // Flag set by --verbose
 *     
 *     boolean verboseFlag = false;
 *     
 *     while (true) {
 *         final int c = getOpt.getOpt();
 *         
 *         // Detect the end of the options.
 *         if (c == GetOpt.EOF) {
 *             break;
 *         }
 *         
 *         // Treat options
 *         
 *         final int optionIndex = getOpt.getOptInd();
 *         switch (c) {
 *         case 0:
 *             // If this option set a flag, do nothing else now.
 *             if (longOptions[optionIndex].flag != null) {
 *                 break;
 *             }
 *             
 *             System.out.print(&quot;option '&quot; + longOptions[optionIndex].name + &quot;'&quot;);
 *             
 *             if (!Strings.isEmpty(getOpt.getOptArg())) {
 *                 System.out.println(&quot; with arg &quot; + getOpt.getOptArg());
 *             }
 *             break;
 *         
 *         case 'a':
 *             System.out.println(&quot;option -a\n&quot;);
 *             break;
 *         
 *         case 'b':
 *             System.out.println(&quot;option -b\n&quot;);
 *             break;
 *         
 *         case 'c':
 *             System.out.println(&quot;option -c with value&quot; + getOpt.getOptArg());
 *             break;
 *         
 *         case 'd':
 *             System.out.println(&quot;option -d with value &quot; + getOpt.getOptArg());
 *             break;
 *         
 *         case 'f':
 *             System.out.println(&quot;option -f with value:&quot; + getOpt.getOptArg());
 *             break;
 *         
 *         case 1:
 *             verboseFlag = false;
 *             System.out.println(&quot;brief mode&quot;);
 *             break;
 *         
 *         case 'v':
 *             verboseFlag = true;
 *             System.out.println(&quot;verbose flag is set&quot;);
 *             break;
 *         
 *         case '?':
 *             // getopt_long already printed an error message.
 *             break;
 *         
 *         default:
 *             System.out.println(&quot;bad option: &quot;
 *                     + longOptions[getOpt.getOptInd()].getName());
 *         }
 *     }
 *     
 *     if (verboseFlag) {
 *         // Print any remaining command line arguments (not options).
 *         if (getOpt.getOptInd() &lt; argv.length) {
 *             System.out.print(&quot;non-option ARGV-elements: &quot;);
 *             int idx = getOpt.getOptInd();
 *             while (idx &lt; argv.length) {
 *                 System.out.print(argv[idx++]);
 *             }
 *             System.out.println();
 *         }
 *     }
 * }
 * </pre>
 */
public class GetOpt {
    
    /**
     * A long option in the Java port of GNU GetOpt.
     * <p>
     * An array of {@link LongOption} objects is passed to the constructor
     * object to define the list of valid long options for a given parsing
     * session.
     */
    public static final class LongOption
            extends Object {
        
        /**
         * Constant for {@link #hasArg} constructor argument. Indicates that the
         * option takes no argument.
         */
        public static final int NO_ARGUMENT = 0;
        
        /**
         * Constant for {@link #hasArg} constructor argument. TIndicates that
         * the option takes an argument that is required.
         */
        public static final int REQUIRED_ARGUMENT = 1;
        
        /**
         * Constant for {@link #hasArg} constructor argument. Indicates that the
         * option takes an argument that is optional.
         */
        public static final int OPTIONAL_ARGUMENT = 2;
        
        /**
         * The name of the long option.
         */
        protected String optionName;
        
        /**
         * Indicates whether the option has no argument, a required argument, or
         * an optional argument. This should be named <code>argumentKind</code>
         * but it was not modified to maintain the legacy name in order not to
         * obscure the port.
         */
        protected int hasArg;
        
        /**
         * If this variable is not null, then the value stored in "val" is
         * stored here when this long option is encountered. If this is null,
         * the value stored in "val" is treated as the name of an equivalent
         * short option.
         */
        protected StringBuffer flag;
        
        /**
         * The value to store in "flag" if flag is not null, to indicate that
         * the option was seen. Otherwise the equivalent short option character
         * for this long option.
         */
        protected char val;
        
        /**
         * Create a new Long Option object with the given parameter values. If
         * the value passed as has_arg is not valid, then an exception is
         * thrown.
         * 
         * @param name The long option String.
         * @param argKind Indicates whether the option has no argument
         *            (NO_ARGUMENT), a required argument (REQUIRED_ARGUMENT) or
         *            an optional argument (OPTIONAL_ARGUMENT).
         * @param argumentBuffer If non-null, this is a location to store the
         *            value of "val" when this option is encountered, otherwise
         *            "val" is treated as the equivalent short option character.
         * @param valueChar The value to return for this long option, or the
         *            equivalent single letter option to emulate if the argument
         *            buffer is null.
         * @exception IllegalArgumentException If the has_arg param is not one
         *                of NO_ARGUMENT, REQUIRED_ARGUMENT or
         *                OPTIONAL_ARGUMENT.
         */
        public LongOption(final String name, final int argKind,
                final StringBuffer argumentBuffer, final char valueChar)
                throws IllegalArgumentException {
            
            if ((argKind != NO_ARGUMENT) && (argKind != REQUIRED_ARGUMENT)
                    && (argKind != OPTIONAL_ARGUMENT)) {
                final Object[] msgArgs = { new Integer(argKind).toString() };
                throw new IllegalArgumentException(MessageFormat.format(
                    MESSAGES.getString("getopt.invalidValue"), msgArgs));
            }
            
            this.optionName = name;
            this.hasArg = argKind;
            this.flag = argumentBuffer;
            this.val = valueChar;
        }
        
        /**
         * Returns the name of this Long Option as a String.
         * 
         * @return Then name of the long option
         */
        public String getOptionName() {
            return (optionName);
        }
        
        /**
         * Returns the value set for the 'has_arg' field for this long option.
         * 
         * @return The value of 'has_arg'
         */
        public int getHasArg() {
            return (hasArg);
        }
        
        /**
         * Returns the value of the 'flag' field for this long option.
         * 
         * @return The value of 'flag'
         */
        public StringBuffer getFlag() {
            return (flag);
        }
        
        /**
         * Returns the value of the 'val' field for this long option.
         * 
         * @return The value of 'val'
         */
        public int getVal() {
            return (val);
        }
    }
    
    /*
     * Variables that characterise the option parser
     */

    /*
     * Odering constants
     */

    /**
     * Constant for {@link #ordering} that indicates that options following
     * non-option ARGV-elements should not be treated as options.
     */
    public static final int REQUIRE_ORDER = 1;
    
    /**
     * Constant for {@link #ordering} that indicates that options after
     * non-options are permuted to appear as if they were given upfront.
     */
    public static final int PERMUTE = 2;
    
    /**
     * Constant for {@link #ordering} that indicates the ordering of options and
     * non-options is relevant.
     */
    public static final int RETURN_IN_ORDER = 3;
    
    /**
     * Value returned by getOpt when no more options are available.
     */
    public static final char EOF = '\uFFFF';
    
    /**
     * The localized strings are kept in a separate file.
     */
    private static final ResourceBundle MESSAGES = PropertyResourceBundle
        .getBundle("codebase/MessagesBundle", Locale.getDefault());
    
    /*
     * Argument handeling
     */

    /**
     * Saved argument list passed to the program.
     */
    protected final String[] argv;
    
    /**
     * Determines whether we permute arguments or not.
     */
    protected final int ordering;
    
    /**
     * Name to print as the program name in error messages. This is necessary
     * since Java does not place the program name in argv[0].
     */
    protected final String progName;
    
    /**
     * A flag which communicates whether or not checkLongOption() did all
     * necessary processing for the current option.
     */
    protected boolean longOptHandled;
    
    /**
     * The flag determines whether or not we operate in strict POSIX compliance.
     */
    private final boolean posixlyCorrect;
    
    /**
     * This flag determines whether or not we are parsing only long args.
     */
    private final boolean longOnly;
    
    /**
     * Array of LongOpt describing the valid long options.
     */
    private final LongOption[] longOptions;
    
    /**
     * Contains the valid short options.
     */
    private String optString;
    
    /*
     * Variables that handle the permutation of arguments
     */

    /**
     * Flag to tell {@link #getOpt()} to immediately return {@link #EOF} the
     * next time it is called.
     */
    private boolean endParse;
    
    /**
     * The index of the first non-option in argv[].
     */
    private int firstNonOpt = 1;
    
    /**
     * Stores the index into the {@link #longOptions} array of the long option
     * found.
     */
    private int longIndex;
    
    /**
     * The index of the last non-option in argv[].
     */
    private int lastNonOpt = 1;
    
    /**
     * For communication from `getopt' to the caller. When `getopt' finds an
     * option that takes an argument, the argument value is returned here. Also,
     * when `ordering' is RETURN_IN_ORDER, each non-option ARGV-element is
     * returned here.
     */
    private String optArg = "";
    
    /**
     * Callers store false here to inhibit the error message for unrecognized
     * options.
     */
    private boolean optErr = true;
    
    /**
     * Index in argv of the next element to be scanned. This is used for
     * communication to and from the caller and for communication between
     * successive calls to `getopt'. On entry to `GetOpt', zero means this is
     * the first call; initialize. When `GetOpt' returns EOF, this is the index
     * of the first of the non-option elements that the caller should itself
     * scan. Otherwise, `optind' communicates from one call to the next how much
     * of argv has been scanned so far.
     */
    private int optInd;
    
    /**
     * When an unrecognized option is encountered, getopt will return a '?' and
     * store the value of the invalid option here.
     */
    private int optOpt = '?';
    
    /**
     * The next char to be scanned in the option-element in which the last
     * option character we returned was found. This allows us to pick up the
     * scan where we left off. If this is zero, or a null string, it means
     * resume the scan by advancing to the next ARGV-element.
     */
    private String nextChar;
    
    /**
     * This function is called to report the command line parsing errors.
     * 
     * @param error
     */
    private void reportError(final String error) {
        // Should we throw exceptions?
    }
    
    /**
     * Check to see if an option is a valid long option.
     * <p>
     * Called by {@link #getOpt()}. Put in a separate method because this needs
     * to be done twice.
     * 
     * @return Various things depending on circumstances
     */
    private char checkLongOption() {
        LongOption pfound = null;
        int nameend;
        boolean ambig;
        boolean exact;
        
        longOptHandled = true;
        ambig = false;
        exact = false;
        longIndex = -1;
        
        nameend = nextChar.indexOf("=");
        if (nameend == -1) {
            nameend = nextChar.length();
        }
        // Test all lnog options for either exact match or abbreviated matches
        for (int i = 0; i < longOptions.length; i++) {
            if (longOptions[i].getOptionName().startsWith(
                nextChar.substring(0, nameend))) {
                if (longOptions[i].getOptionName().equals(
                    nextChar.substring(0, nameend))) {
                    // Exact match found
                    pfound = longOptions[i];
                    longIndex = i;
                    exact = true;
                    break;
                } else if (pfound == null) {
                    // First nonexact match found
                    pfound = longOptions[i];
                    longIndex = i;
                } else {
                    // Second or later nonexact match found
                    ambig = true;
                }
            }
        }
        
        // Print out an error if the option specified was ambiguous
        if (ambig && !exact) {
            if (optErr) {
                Object[] msgArgs = { progName, argv[optInd] };
                System.err.println(MessageFormat.format(MESSAGES
                    .getString("getopt.ambigious"), msgArgs));
            }
            
            nextChar = "";
            optOpt = 0;
            ++optInd;
            
            return ('?');
        }
        
        if (pfound != null) {
            ++optInd;
            
            if (nameend != nextChar.length()) {
                if (pfound.hasArg != LongOption.NO_ARGUMENT) {
                    if (nextChar.substring(nameend).length() > 1) {
                        optArg = nextChar.substring(nameend + 1);
                    } else {
                        optArg = "";
                    }
                } else {
                    if (optErr) {
                        // -- option
                        if (argv[optInd - 1].startsWith("--")) {
                            Object[] msgArgs = { progName, pfound.optionName };
                            System.err.println(MessageFormat.format(MESSAGES
                                .getString("getopt.arguments1"), msgArgs));
                        } else {
                            /*
                             * +option or -option
                             */
                            Object[] msgArgs = {
                                    progName,
                                    new Character(argv[optInd - 1].charAt(0))
                                        .toString(), pfound.optionName };
                            
                            reportError(MessageFormat.format(MESSAGES
                                .getString("getopt.arguments2"), msgArgs));
                        }
                    }
                    
                    nextChar = "";
                    optOpt = pfound.val;
                    
                    return ('?');
                }
            } else if (pfound.hasArg == LongOption.REQUIRED_ARGUMENT) {
                if (optInd < argv.length) {
                    optArg = argv[optInd];
                    ++optInd;
                } else {
                    if (optErr) {
                        Object[] msgArgs = { progName, argv[optInd - 1] };
                        reportError(MessageFormat.format(MESSAGES
                            .getString("getopt.requires"), msgArgs));
                    }
                    
                    nextChar = "";
                    optOpt = pfound.val;
                    if (optString.charAt(0) == ':') {
                        return (':');
                    } else {
                        return ('?');
                    }
                }
            } // else if (pfound)
            
            nextChar = "";
            
            if (pfound.flag != null) {
                pfound.flag.setLength(0);
                pfound.flag.append(pfound.val);
                
                return (0);
            }
            
            return pfound.val;
        }
        
        longOptHandled = false;
        
        return (0);
    }
    
    /**
     * Exchange two adjacent subsequences of ARGV.
     * <p>
     * One subsequence is elements [FirstNonOpt,LastNonOpt) which contains all
     * the non-options that have been skipped so far. The other is elements
     * [LastNonOpt,OptInd), which contains all the options processed since those
     * non-options were skipped. `FirstNonOpt' and `LastNonOpt' are relocated so
     * that they describe the new indices of the non-options in ARGV after they
     * are moved. *
     * 
     * @param argv an array of arguments
     */
    private void exchange(final String[] argv) {
        int bottom = firstNonOpt;
        int middle = lastNonOpt;
        int top = optInd;
        String tem;
        
        while (top > middle && middle > bottom) {
            if (top - middle > middle - bottom) {
                // Bottom segment is the short one.
                int len = middle - bottom;
                int i;
                
                // Swap it with the top part of the top segment.
                for (i = 0; i < len; i++) {
                    tem = argv[bottom + i];
                    argv[bottom + i] = argv[top - (middle - bottom) + i];
                    argv[top - (middle - bottom) + i] = tem;
                }
                // Exclude the moved bottom segment from further swapping.
                top -= len;
            } else {
                // Top segment is the short one.
                int len = top - middle;
                int i;
                
                // Swap it with the bottom part of the bottom segment.
                for (i = 0; i < len; i++) {
                    tem = argv[bottom + i];
                    argv[bottom + i] = argv[middle + i];
                    argv[middle + i] = tem;
                }
                // Exclude the moved top segment from further swapping.
                bottom += len;
            }
        }
        
        // Update records for the slots the non-options now occupy.
        
        firstNonOpt += (optInd - lastNonOpt);
        lastNonOpt = optInd;
    }
    
    /**
     * Internal only. Should not call this directly. Scan elements of argv
     * (whose length is argc) for option characters given in opt_string. if an
     * element of argv starts with '-', and is not exactly "-" or "--", then it
     * is an option element. The characters of this element (aside from the
     * initial '-') are option characters. If there are no more option
     * characters, `get_opt' returns `EOF'. Then `OptInd' is the index in argv
     * of the first argv-element that is not an option. (the argv-elements have
     * been permuted so that those that are not options now come last.) The
     * elements of argv aren't really final, because we permute them. But we
     * pretend they're final in the prototype to be compatible with other
     * systems.
     * 
     * @param argc the number of arguments
     * @param argv the array of arguments
     * @param short_opts
     * @param long_opts is a vector for long options made of `struct option'
     *            terminated by an element containing a name which is zero.
     * @param long_ind returns the index in GetLongOpt of the long-named option
     *            found. It is only valid when a long-named option has been
     *            found by the most recent call
     * @param long_only if nonzero, '-' as well as '--' can introduce long-named
     *            options.
     * @return <code></code>
     */
    
    /**
     * Construct a basic Getopt instance with the given input data. Note that
     * this handles "short" options only.
     * 
     * @param progname The name to display as the program name when printing
     *            errors
     * @param argv The String array passed as the command line to the program.
     * @param optstring A String containing a description of the valid args for
     *            this program
     */
    public GetOpt(final String progname, final String[] argv,
            final String optstring) {
        this(progname, argv, optstring, null, false);
    }
    
    /**
     * Construct a Getopt instance with given input data that is capable of
     * parsing long options as well as short.
     * 
     * @param progname The name to display as the program name when printing
     *            errors
     * @param argv The String array passed as the command ilne to the program
     * @param optstring A String containing a description of the valid short
     *            args for this program
     * @param long_options An array of LongOpt objects that describes the valid
     *            long args for this program
     */
    public GetOpt(final String progname, final String[] argv, String optstring,
            final LongOption[] long_options) {
        this(progname, argv, optstring, long_options, false);
    }
    
    /**
     * Construct a Getopt instance with given input data that is capable of
     * parsing long options and short options. Contrary to what you might think,
     * the flag 'long_only' does not determine whether or not we scan for only
     * long arguments. Instead, a value of true here allows long arguments to
     * start with a '-' instead of '--' unless there is a conflict with a short
     * option name.
     * 
     * @param progname The name to display as the program name when printing
     *            errors
     * @param args The String array passed as the command line to the program
     * @param optstring A String containing a description of the valid short
     *            args for this program
     * @param long_options An array of LongOpt objects that describes the valid
     *            long args for this program
     * @param long_only true if long options that do not conflict with short
     *            options can start with a '-' as well as '--'
     */
    public GetOpt(final String progname, final String[] args,
            final String optstring, final LongOption[] long_options,
            final boolean long_only) {
        
        // _getopt_initialize from GNU getopt
        progName = progname;
        argv = args;
        if (optstring.length() == 0) {
            optString = " ";
        } else {
            optString = optstring;
        }
        longOptions = long_options;
        longOnly = long_only;
        
        // Check for property "POSIXLY_CORRECT" to determine whether to
        // strictly follow the POSIX standard.
        if (System.getProperty("POSIXLY_CORRECT", null) == null) {
            posixlyCorrect = false;
        } else {
            posixlyCorrect = true;
        }
        
        // Determine how to handle the ordering of options and non-options
        if (optstring.charAt(0) == '-') {
            ordering = RETURN_IN_ORDER;
            if (optString.length() > 1) {
                optString = optString.substring(1);
            }
        } else if (optstring.charAt(0) == '+') {
            ordering = REQUIRE_ORDER;
            if (optString.length() > 1) {
                optString = optString.substring(1);
            }
        } else if (posixlyCorrect) {
            ordering = REQUIRE_ORDER;
        } else {
            /*
             * normal default case
             */
            ordering = PERMUTE;
        }
    }
    
    /**
     * Parses the next option and returns its corresponding char.
     * <p>
     * When this method is called repeatedly, it returns successively each of
     * the option characters from each of the option elements. If another option
     * character is found, it returns that character, updating {@link #optInd}
     * and {@link #nextChar} so that the next call to {@link #getOpt()} can
     * resume the scan with the following option character or argv-element.
     * <p>
     * If the option takes an argument, then the internal variable
     * {@link #optArg} is set with the argument. This value can be retrieved by
     * the caller using the {@link #getOptarg()}.
     * <p>
     * If an invalid option is found, an error message is printed and a '?' is
     * returned. The name of the invalid option character can be retrieved by
     * calling the {@link getOptOpt() method}.
     * <p>
     * When there are no more options to be scanned, this method returns
     * {@link #EOF}.
     * <p>
     * The index of first non-option element in argv can be retrieved with the
     * {@link #getOptInd()} method.
     * <p>
     * 
     * @return a char representing the current option that has been parsed from
     *         the command line
     */
    public final char getOpt() {
        optArg = null;
        
        if (endParse) {
            return EOF;
        }
        
        if ((nextChar == null) || (nextChar.equals(""))) {
            // If we have just processed some options following some
            // non-options,
            // exchange them so that the options come first.
            if (lastNonOpt > optInd) {
                lastNonOpt = optInd;
            }
            if (firstNonOpt > optInd) {
                firstNonOpt = optInd;
            }
            
            if (ordering == PERMUTE) {
                // If we have just processed some options following some
                // non-options,
                // exchange them so that the options come first.
                if ((firstNonOpt != lastNonOpt) && (lastNonOpt != optInd)) {
                    exchange(argv);
                } else if (lastNonOpt != optInd) {
                    firstNonOpt = optInd;
                }
                // Skip any additional non-options
                // and extend the range of non-options previously skipped.
                while ((optInd < argv.length)
                        && (argv[optInd].equals("")
                                || (argv[optInd].charAt(0) != '-') || argv[optInd]
                            .equals("-"))) {
                    optInd++;
                }
                
                lastNonOpt = optInd;
            }
            
            // The special ARGV-element `--' means premature end of options.
            // Skip it like a null option,
            // then exchange with previous non-options as if it were an
            // option,
            // then skip everything else like a non-option.
            if ((optInd != argv.length) && argv[optInd].equals("--")) {
                optInd++;
                
                if ((firstNonOpt != lastNonOpt) && (lastNonOpt != optInd)) {
                    exchange(argv);
                } else if (firstNonOpt == lastNonOpt) {
                    firstNonOpt = optInd;
                }
                lastNonOpt = argv.length;
                
                optInd = argv.length;
            }
            
            // If we have done all the ARGV-elements, stop the scan
            // and back over any non-options that we skipped and permuted.
            if (optInd == argv.length) {
                // Set the next-arg-index to point at the non-options
                // that we previously skipped, so the caller will digest them.
                if (firstNonOpt != lastNonOpt) {
                    optInd = firstNonOpt;
                }
                return EOF;
            }
            
            // If we have come to a non-option and did not permute it,
            // either stop the scan or describe it to the caller and pass it
            // by.
            if (argv[optInd].equals("") || (argv[optInd].charAt(0) != '-')
                    || argv[optInd].equals("-")) {
                if (ordering == REQUIRE_ORDER) {
                    return EOF;
                }
                optArg = argv[optInd++];
                return 1;
            }
            
            // We have found another option-ARGV-element.
            // Skip the initial punctuation.
            if (argv[optInd].startsWith("--")) {
                nextChar = argv[optInd].substring(2);
            } else {
                nextChar = argv[optInd].substring(1);
            }
        }
        
        // Decode the current option-ARGV-element.
        
        /*
         * Check whether the ARGV-element is a long option. If long_only and the
         * ARGV-element has the form "-f", where f is a valid short option,
         * don't consider it an abbreviated form of a long option that starts
         * with f. Otherwise there would be no way to give the -f short option.
         * On the other hand, if there's a long option "fubar" and the
         * ARGV-element is "-fu", do consider that an abbreviation of the long
         * option, just like "--fu", and not "-f" with arg "u". This distinction
         * seems to be the most useful approach.
         */
        if ((longOptions != null)
                && (argv[optInd].startsWith("--") || (longOnly && ((argv[optInd]
                    .length() > 2) || (optString
                    .indexOf(argv[optInd].charAt(1)) == -1))))) {
            char c = checkLongOption();
            
            if (longOptHandled) {
                return c;
            }
            // Can't find it as a long option. If this is not
            // getopt_long_only,
            // or the option starts with '--' or is not a valid short
            // option, then it's an error.
            // Otherwise interpret it as a short option.
            if (!longOnly || argv[optInd].startsWith("--")
                    || (optString.indexOf(nextChar.charAt(0)) == -1)) {
                if (optErr) {
                    if (argv[optInd].startsWith("--")) {
                        Object[] msgArgs = { progName, nextChar };
                        reportError(MessageFormat.format(MESSAGES
                            .getString("getopt.unrecognized"), msgArgs));
                    } else {
                        Object[] msgArgs = {
                                progName,
                                new Character(argv[optInd].charAt(0))
                                    .toString(), nextChar };
                        reportError(MessageFormat.format(MESSAGES
                            .getString("getopt.unrecognized2"), msgArgs));
                    }
                }
                
                nextChar = "";
                ++optInd;
                optOpt = 0;
                
                return '?';
            }
        } // if (longopts)
        
        // Look at and handle the next short option-character */
        char c = nextChar.charAt(0); // **** Do we need to check for empty
        // str?
        if (nextChar.length() > 1) {
            nextChar = nextChar.substring(1);
        } else {
            nextChar = "";
        }
        
        String temp = null;
        if (optString.indexOf(c) != -1) {
            temp = optString.substring(optString.indexOf(c));
        }
        if (nextChar.equals("")) {
            ++optInd;
        }
        if ((temp == null) || (c == ':')) {
            if (optErr) {
                if (posixlyCorrect) {
                    // 1003.2 specifies the format of this message
                    Object[] msgArgs = { progName,
                            new Character((char) c).toString() };
                    reportError(MessageFormat.format(MESSAGES
                        .getString("getopt.illegal"), msgArgs));
                } else {
                    Object[] msgArgs = { progName,
                            new Character((char) c).toString() };
                    reportError(MessageFormat.format(MESSAGES
                        .getString("getopt.invalid"), msgArgs));
                }
            }
            
            optOpt = c;
            
            return ('?');
        }
        
        // Convenience. Treat POSIX -W foo same as long option --foo
        if ((temp.charAt(0) == 'W') && (temp.length() > 1)
                && (temp.charAt(1) == ';')) {
            if (!nextChar.equals("")) {
                optArg = nextChar;
            } else if (optInd == argv.length) {
                /*
                 * No further cars in this argv element and no more argv
                 * elements
                 */
                if (optErr) {
                    // 1003.2 specifies the format of this message.
                    Object[] msgArgs = { progName,
                            new Character((char) c).toString() };
                    reportError(MessageFormat.format(MESSAGES
                        .getString("getopt.requires2"), msgArgs));
                }
                
                optOpt = c;
                if (optString.charAt(0) == ':') {
                    return (':');
                } else {
                    return ('?');
                }
            } else {
                // We already incremented `optind' once;
                // increment it again when taking next ARGV-elt as argument.
                nextChar = argv[optInd];
                optArg = argv[optInd];
            }
            
            c = checkLongOption();
            
            if (longOptHandled) {
                return c;
            } else {
                /*
                 * Let the application handle it
                 */
                nextChar = null;
                ++optInd;
                return ('W');
            }
        }
        
        if ((temp.length() > 1) && (temp.charAt(1) == ':')) {
            if ((temp.length() > 2) && (temp.charAt(2) == ':')) {
                /*
                 * This is an option that accepts and argument optionally
                 */
                if (!nextChar.equals("")) {
                    optArg = nextChar;
                    ++optInd;
                } else {
                    optArg = null;
                }
                
                nextChar = null;
            } else {
                if (!nextChar.equals("")) {
                    optArg = nextChar;
                    ++optInd;
                } else if (optInd == argv.length) {
                    if (optErr) {
                        // 1003.2 specifies the format of this message
                        Object[] msgArgs = { progName,
                                new Character((char) c).toString() };
                        reportError(MessageFormat.format(MESSAGES
                            .getString("getopt.requires2"), msgArgs));
                    }
                    
                    optOpt = c;
                    
                    if (optString.charAt(0) == ':') {
                        return (':');
                    } else {
                        return ('?');
                    }
                } else {
                    optArg = argv[optInd];
                    ++optInd;
                    
                    // Ok, here's an obscure Posix case. If we have o:, and
                    // we get -o -- foo, then we're supposed to skip the --,
                    // end parsing of options, and make foo an operand to -o.
                    // Only do this in Posix mode.
                    if ((posixlyCorrect) && optArg.equals("--")) {
                        // If end of argv, error out
                        if (optInd == argv.length) {
                            if (optErr) {
                                // 1003.2 specifies the format of this message
                                Object[] msgArgs = { progName,
                                        new Character((char) c).toString() };
                                reportError(MessageFormat.format(MESSAGES
                                    .getString("getopt.requires2"), msgArgs));
                            }
                            
                            optOpt = c;
                            
                            if (optString.charAt(0) == ':') {
                                return (':');
                            } else {
                                return ('?');
                            }
                        }
                        
                        // Set new optarg and set to end
                        // Don't permute as we do on -- up above since we
                        // know we aren't in permute mode because of Posix.
                        optArg = argv[optInd];
                        ++optInd;
                        firstNonOpt = optInd;
                        lastNonOpt = argv.length;
                        endParse = true;
                    }
                }
                
                nextChar = null;
            }
        }
        
        return c;
    }
    
    /**
     * Returns the index into the array of long options of the current option.
     * <p>
     * The index is not into argv.
     * 
     * @return an index into the array of long options that represents the long
     *         option that was found.
     */
    public final int getLongInd() {
        return (longIndex);
    }
    
    /**
     * Returns the value of the argument of an option.
     * <p>
     * When {@link #ordering} is set to {@link #RETURN_IN_ORDER}, each
     * non-option ARGV-element is returned here.
     * 
     * @return whenever {@link #getOpt()} finds an option that takes an
     *         argument, the argument value is returned here.
     */
    public final String getOptArg() {
        return (optArg);
    }
    
    /**
     * Returns the index in ARGV of the next element to be scanned.
     * <p>
     * Whenever {@link #getOpt()} returns {@link #EOF()} , this is the index of
     * the first of the non-option elements that the caller should itself scan.
     * Otherwise, the value returned communicates from one call to the next how
     * much of ARGV has been scanned so far.
     * 
     * @return the index in ARGV of the next element to be scanned or
     *         <code>-1</code> if no more options exist.
     */
    public final int getOptInd() {
        return (optInd);
    }
    
    /**
     * Returns the code of an invalid option.
     * <p>
     * When
     * {@link #getOpt() encounters an invalid option, it stores the value of that
     * option in {@link #optOpt} which can be retrieved with this method.
     * 
     * @return the code of the invalid option
     */
    public final int getOptOpt() {
        return (optOpt);
    }
}
