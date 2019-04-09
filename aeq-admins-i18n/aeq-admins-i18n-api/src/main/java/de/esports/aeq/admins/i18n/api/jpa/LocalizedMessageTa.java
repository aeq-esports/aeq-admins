package de.esports.aeq.admins.i18n.api.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "localized_message")
public class LocalizedMessageTa implements Serializable {

    @Id
    @Column(name = "localized_message_id")
    private String key;

    @Column
    private Locale locale;

    @Lob
    private String text;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
