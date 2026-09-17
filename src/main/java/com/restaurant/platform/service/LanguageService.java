package com.restaurant.platform.service;

import com.restaurant.platform.config.SupportedLanguages;
import com.restaurant.platform.model.Category;
import com.restaurant.platform.model.Product;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LanguageService {

    /**
     * Resolve and normalize the requested language.
     */
    public String resolve(String language) {
        return SupportedLanguages.normalize(language);
    }

    /**
     * Get localized category name.
     */
    public String categoryName(
            Category category,
            String language
    ) {

        if (category == null) {
            return "";
        }

        String lang = resolve(language);

        String translated = getTranslation(
                category.getNameTranslations(),
                lang
        );

        if (hasText(translated)) {
            return translated;
        }

        return safe(category.getName());
    }

    /**
     * Get localized product name.
     */
    public String productName(
            Product product,
            String language
    ) {

        if (product == null) {
            return "";
        }

        String lang = resolve(language);

        String translated = getTranslation(
                product.getNameTranslations(),
                lang
        );

        if (hasText(translated)) {
            return translated;
        }

        return safe(product.getName());
    }

    /**
     * Get localized product description.
     */
    public String productDescription(
            Product product,
            String language
    ) {

        if (product == null) {
            return "";
        }

        String lang = resolve(language);

        String translated = getTranslation(
                product.getDescriptionTranslations(),
                lang
        );

        if (hasText(translated)) {
            return translated;
        }

        return safe(product.getDescription());
    }

    /**
     * Generic translation lookup.
     *
     * Example:
     *
     * {
     *   "en": "Dosa",
     *   "te": "దోస",
     *   "ta": "தோசை",
     *   "kn": "ದೋಸೆ",
     *   "hi": "डोसा"
     * }
     */
    private String getTranslation(
            Map<String, String> translations,
            String language
    ) {

        if (translations == null || translations.isEmpty()) {
            return null;
        }

        // Requested language
        String value = translations.get(language);

        if (hasText(value)) {
            return value.trim();
        }

        // English fallback
        value = translations.get(SupportedLanguages.ENGLISH);

        if (hasText(value)) {
            return value.trim();
        }

        return null;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}