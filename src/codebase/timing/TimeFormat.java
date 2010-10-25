package codebase.timing;

/**
 * Time utility functions.
 */
public final class TimeFormat {
    
    /**
     * The amount of seconds that corresponds to one day.
     */
    private static final long DAY_SECONDS = 86400;
    
    /**
     * The amount of seconds that corresponds to one hour.
     */
    private static final long HOUR_SECONDS = 3600;
    
    /**
     * The amount of seconds that corresponds to one minute.
     */
    private static final long MINUTE_SECONDS = 60;
    
    /**
     * Prevents the instantiation.
     */
    private TimeFormat() {
    }
    
    /**
     * @param millis the time elapsed in milliseconds
     * @return human readable string for time.
     */
    public static String formatTime(final long millis) {
        long days = 0;
        long hours = 0;
        long mins = 0;
        double secs = 0;
        String time = "";
        
        secs = millis / 1000;
        
        while (secs >= DAY_SECONDS) {
            days++;
            secs -= DAY_SECONDS;
        }
        
        while (secs >= HOUR_SECONDS) {
            hours++;
            secs -= HOUR_SECONDS;
        }
        
        while (secs >= MINUTE_SECONDS) {
            mins++;
            secs -= MINUTE_SECONDS;
        }
        if (days != 0) {
            time = days + "days ";
        }
        if (hours != 0) {
            time = hours + "h ";
        }
        if (mins != 0) {
            time += mins + "m ";
        }
        time += secs + "s";
        
        return time;
    }
    
    /**
     * 
     * @param s
     * @return
     */
    public static long parseTime(String s) {
        s = s.toLowerCase();
        if (s.endsWith("ms")) {
            return Long.parseLong(s.substring(0, s.length() - 2));
        }
        if (s.endsWith("s")) {
            return Long.parseLong(s.substring(0, s.length() - 1)) * 1000;
        }
        if (s.endsWith("m")) {
            return Long.parseLong(s.substring(0, s.length() - 1)) * 60000;
        }
        return Long.parseLong(s);
    }
}
