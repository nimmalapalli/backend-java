package com.restaurant.platform.model;

import java.util.HashMap;
import java.util.Map;

public class LocalizedText {

    private String defaultValue;

    private Map<String, String> translations = new HashMap<>();

    public LocalizedText() {
    }

    public LocalizedText(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Map<String, String> getTranslations() {
        return translations;
    }

    public void setTranslations(Map<String, String> translations) {
        this.translations = translations;
    }

    public String get(String language) {

        if (translations != null) {
            String translated = translations.get(language);

            if (translated != null && !translated.isBlank()) {
                return translated;
            }
        }

        return defaultValue;
    }
}
