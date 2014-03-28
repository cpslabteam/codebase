package codebase;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Basic message mechanism for i18n.
 * 
 * @author Fernando Martins
 * @version 1.0, 2009-02-18
 */
public class MessagesBase {

	/**
	 * Constructor.
	 */
	protected MessagesBase() {

	}

	/**
	 * Returns the resource bundle.
	 * 
	 * @param bundleName
	 *            bundle identifier
	 * @return resource bundle
	 */
	protected static final ResourceBundle getResourceBundle(
			final String bundleName) {
		return ResourceBundle.getBundle(bundleName);
	}

	/**
	 * Gets the specified key string.
	 * 
	 * @param bundle
	 *            the resource bundle to use
	 * @param key
	 *            key string to return
	 * @return requested message
	 */
	protected static final String getString(final ResourceBundle bundle,
			final String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * Gets the specified key string binded with the specified values.
	 * 
	 * @param bundle
	 *            the resource bundle to use
	 * @param key
	 *            key string to return
	 * @param messageArguments
	 *            array of arguments to bind
	 * @return message composed with values binded
	 */
	protected static final String getString(final ResourceBundle bundle,
			final String key, final Object... messageArguments) {
		MessageFormat messageForm = new MessageFormat("");
		messageForm.setLocale(bundle.getLocale());
		messageForm.applyPattern(getString(bundle, key));
		return messageForm.format(messageArguments);
	}
}
