package com.restaurant.platform.service;

import com.restaurant.platform.dto.LocalizedCategoryResponse;
import com.restaurant.platform.dto.LocalizedProductResponse;
import com.restaurant.platform.model.RestaurantSettings;
import com.restaurant.platform.repository.CategoryRepository;
import com.restaurant.platform.repository.ModifierRepository;
import com.restaurant.platform.repository.OfferRepository;
import com.restaurant.platform.repository.ProductRepository;
import com.restaurant.platform.repository.SettingsRepository;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final CategoryRepository categories;
    private final ProductRepository products;
    private final ModifierRepository modifiers;
    private final OfferRepository offers;
    private final SettingsRepository settings;
    private final LanguageService languageService;

    public MenuService(
            CategoryRepository categories,
            ProductRepository products,
            ModifierRepository modifiers,
            OfferRepository offers,
            SettingsRepository settings,
            LanguageService languageService
    ) {
        this.categories = categories;
        this.products = products;
        this.modifiers = modifiers;
        this.offers = offers;
        this.settings = settings;
        this.languageService = languageService;
    }

    public Map<String, Object> menu(String language) {

        String selectedLanguage =
                languageService.resolve(language);

        Map<String, Object> response =
                new LinkedHashMap<>();

        response.put(
                "language",
                selectedLanguage
        );

        response.put(
                "supportedLanguages",
                List.of(
                        "en",
                        "hi",
                        "ta",
                        "te",
                        "kn"
                )
        );

        RestaurantSettings restaurant =
                settings.findById("restaurant")
                        .orElseGet(() ->
                                settings.findAll()
                                        .stream()
                                        .findFirst()
                                        .orElse(
                                                new RestaurantSettings()
                                        )
                        );

        response.put(
                "restaurant",
                restaurant
        );

        List<LocalizedCategoryResponse>
                localizedCategories =
                categories
                        .findAllByActiveTrueOrderBySortOrderAsc()
                        .stream()
                        .map(category ->
                                new LocalizedCategoryResponse(
                                        category.getId(),

                                        languageService.categoryName(
                                                category,
                                                selectedLanguage
                                        ),

                                        category.getImage(),

                                        category.getSortOrder(),

                                        category.getActive()
                                )
                        )
                        .collect(Collectors.toList());

        List<LocalizedProductResponse>
                localizedProducts =
                products
                        .findAllByOrderBySortOrderAsc()
                        .stream()
                        .map(product ->
                                new LocalizedProductResponse(
                                        product.getId(),

                                        product.getCategoryId(),

                                        languageService.productName(
                                                product,
                                                selectedLanguage
                                        ),

                                        languageService.productDescription(
                                                product,
                                                selectedLanguage
                                        ),

                                        product.getImage(),

                                        product.getPrice(),

                                        product.getTaxRate(),

                                        product.getAvailable(),

                                        product.getFeatured(),

                                        product.getSortOrder(),

                                        product.getModifierIds()
                                )
                        )
                        .collect(Collectors.toList());

        response.put(
                "categories",
                localizedCategories
        );

        response.put(
                "products",
                localizedProducts
        );

        response.put(
                "modifiers",
                modifiers.findAll()
        );

        response.put(
                "offers",
                offers.findAllByActiveTrue()
        );

        return response;
    }

    public Map<String, Object> menu() {
        return menu("en");
    }
}