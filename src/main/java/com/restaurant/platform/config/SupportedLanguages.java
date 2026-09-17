package com.restaurant.platform.config;

import java.util.List;
import java.util.Locale;

public final class SupportedLanguages {

    public static final String ENGLISH = "en";
    public static final String HINDI = "hi";
    public static final String TAMIL = "ta";
    public static final String TELUGU = "te";
    public static final String KANNADA = "kn";

    public static final List<String> ALL = List.of(
            ENGLISH,
            HINDI,
            TAMIL,
            TELUGU,
            KANNADA
    );

    private SupportedLanguages() {
    }

    public static String normalize(String language) {

        if (language == null || language.isBlank()) {
            return ENGLISH;
        }

        String code = language
                .trim()
                .toLowerCase(Locale.ROOT)
                .split("[-_]")[0];

        return ALL.contains(code)
                ? code
                : ENGLISH;
    }
}