package de.esports.aeq.admins.i18n.api;

import java.io.Serializable;
import java.util.Locale;

/**
 * Represents a text which may be available for different locales.
 *
 * @see Locale
 */
public class LocalizedMessage implements Serializable {

    private String key;
    private Locale locale;
    private String text;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Obtains the locale.
     *
     * @return the locale, not <code>null</code>
     */
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Obtains the text for the related locale.
     *
     * @return the text, not <code>null</code>
     */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
