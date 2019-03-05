package de.esports.aeq.admins.common.i18n;

import java.io.Serializable;
import java.util.Locale;

/**
 * Represents a text which may be available for different locales.
 *
 * @see Locale
 */
public interface LocalizedMessage extends Serializable {

    /**
     * Obtains the locale.
     *
     * @return the locale, not <code>null</code>
     */
    Locale getLocale();

    /**
     * Obtains the text for the related locale.
     *
     * @return the text, not <code>null</code>
     */
    String getText();
}
