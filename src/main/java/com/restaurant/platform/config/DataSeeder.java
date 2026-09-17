package com.restaurant.platform.config;

import com.restaurant.platform.model.*;
import com.restaurant.platform.repository.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataSeeder {

    private final CategoryRepository cats;
    private final ProductRepository products;
    private final SettingsRepository settings;
    private final KioskRepository kiosks;
    private final KitchenRepository kitchens;

    public DataSeeder(
            CategoryRepository cats,
            ProductRepository products,
            SettingsRepository settings,
            KioskRepository kiosks,
            KitchenRepository kitchens
    ) {
        this.cats = cats;
        this.products = products;
        this.settings = settings;
        this.kiosks = kiosks;
        this.kitchens = kitchens;
    }

    @Bean
    CommandLineRunner seed(
            @Value("${app.seed.enabled:true}") boolean enabled
    ) {

        return args -> {

            if (!enabled) {
                return;
            }

            /*
             * ============================================================
             * REMOVE OLD MENU DATA
             * ============================================================
             *
             * Products are deleted first because they reference categories.
             */
            products.deleteAll();
            cats.deleteAll();


            /*
             * ============================================================
             * RESTAURANT SETTINGS
             * ============================================================
             */

            if (settings.count() == 0) {

                settings.save(
                        RestaurantSettings.builder()
                                .id("restaurant")
                                .name("Udupi Restaurant")
                                .currency("₹")
                                .taxEnabled(true)
                                .taxRate(new BigDecimal("5"))
                                .idleTimeout(120)
                                .build()
                );
            }


            /*
             * ============================================================
             * CATEGORIES
             * ============================================================
             */

            Category breakfast =
                    Category.builder()
                            .name("South Indian Breakfast")
                            .sortOrder(1)
                            .active(true)
                            .build();

            Category dosa =
                    Category.builder()
                            .name("Dosa Specials")
                            .sortOrder(2)
                            .active(true)
                            .build();

            Category meals =
                    Category.builder()
                            .name("Udupi Meals & Thali")
                            .sortOrder(3)
                            .active(true)
                            .build();

            Category rice =
                    Category.builder()
                            .name("Rice & Tiffin")
                            .sortOrder(4)
                            .active(true)
                            .build();

            Category northIndian =
                    Category.builder()
                            .name("North Indian")
                            .sortOrder(5)
                            .active(true)
                            .build();

            Category breads =
                    Category.builder()
                            .name("Indian Breads")
                            .sortOrder(6)
                            .active(true)
                            .build();

            Category snacks =
                    Category.builder()
                            .name("Snacks & Starters")
                            .sortOrder(7)
                            .active(true)
                            .build();

            Category chaats =
                    Category.builder()
                            .name("Chaats")
                            .sortOrder(8)
                            .active(true)
                            .build();

            Category beverages =
                    Category.builder()
                            .name("Beverages")
                            .sortOrder(9)
                            .active(true)
                            .build();

            Category juices =
                    Category.builder()
                            .name("Juices & Shakes")
                            .sortOrder(10)
                            .active(true)
                            .build();

            Category sweets =
                    Category.builder()
                            .name("Sweets & Desserts")
                            .sortOrder(11)
                            .active(true)
                            .build();

            Category combos =
                    Category.builder()
                            .name("Combos")
                            .sortOrder(12)
                            .active(true)
                            .build();


            List<Category> savedCategories =
                    cats.saveAll(
                            List.of(
                                    breakfast,
                                    dosa,
                                    meals,
                                    rice,
                                    northIndian,
                                    breads,
                                    snacks,
                                    chaats,
                                    beverages,
                                    juices,
                                    sweets,
                                    combos
                            )
                    );


            /*
             * ============================================================
             * PRODUCTS
             * ============================================================
             */

            List<Product> menu = new ArrayList<>();

            int sort = 1;


            // ============================================================
            // SOUTH INDIAN BREAKFAST
            // ============================================================

            menu.add(product(
                    breakfast,
                    "Idli",
                    "Soft steamed South Indian rice cakes served with sambar and chutney",
                    "40",
                    sort++
            ));

            menu.add(product(
                    breakfast,
                    "Vada",
                    "Crispy South Indian urad dal vada served with sambar and chutney",
                    "45",
                    sort++
            ));

            menu.add(product(
                    breakfast,
                    "Idli Vada",
                    "Two idlis and one crispy vada served with sambar and chutney",
                    "65",
                    sort++
            ));

            menu.add(product(
                    breakfast,
                    "Sambar Idli",
                    "Soft idlis soaked in hot flavorful sambar",
                    "55",
                    sort++
            ));

            menu.add(product(
                    breakfast,
                    "Masala Idli",
                    "Idli tossed with South Indian spices and vegetables",
                    "60",
                    sort++
            ));

            menu.add(product(
                    breakfast,
                    "Pongal",
                    "Traditional South Indian rice and moong dal pongal",
                    "65",
                    sort++
            ));

            menu.add(product(
                    breakfast,
                    "Upma",
                    "Traditional semolina upma with vegetables and South Indian seasoning",
                    "50",
                    sort++
            ));

            menu.add(product(
                    breakfast,
                    "Poori Sagu",
                    "Fluffy puris served with Karnataka-style vegetable sagu",
                    "75",
                    sort++
            ));

            menu.add(product(
                    breakfast,
                    "Mangalore Buns",
                    "Soft sweet banana buns served with coconut chutney",
                    "60",
                    sort++
            ));


            // ============================================================
            // DOSA SPECIALS
            // ============================================================

            menu.add(product(
                    dosa,
                    "Plain Dosa",
                    "Crispy traditional South Indian dosa served with sambar and chutney",
                    "55",
                    sort++
            ));

            menu.add(product(
                    dosa,
                    "Masala Dosa",
                    "Crispy dosa filled with seasoned potato masala",
                    "80",
                    sort++
            ));

            menu.add(product(
                    dosa,
                    "Mysore Masala Dosa",
                    "Crispy dosa spread with spicy Mysore chutney and potato masala",
                    "95",
                    sort++
            ));

            menu.add(product(
                    dosa,
                    "Set Dosa",
                    "Soft and fluffy set of dosas served with vegetable sagu",
                    "75",
                    sort++
            ));

            menu.add(product(
                    dosa,
                    "Rava Dosa",
                    "Thin crispy semolina dosa with aromatic spices",
                    "85",
                    sort++
            ));

            menu.add(product(
                    dosa,
                    "Onion Rava Dosa",
                    "Crispy rava dosa topped with fresh onions",
                    "95",
                    sort++
            ));

            menu.add(product(
                    dosa,
                    "Ghee Roast Dosa",
                    "Extra crispy dosa roasted generously with ghee",
                    "95",
                    sort++
            ));

            menu.add(product(
                    dosa,
                    "Podi Dosa",
                    "Dosa topped with traditional South Indian spicy podi",
                    "85",
                    sort++
            ));

            menu.add(product(
                    dosa,
                    "Butter Masala Dosa",
                    "Masala dosa roasted with butter and potato masala",
                    "100",
                    sort++
            ));

            menu.add(product(
                    dosa,
                    "Paneer Masala Dosa",
                    "Dosa filled with spiced paneer and potato masala",
                    "120",
                    sort++
            ));


            // ============================================================
            // UDUPI MEALS & THALI
            // ============================================================

            menu.add(product(
                    meals,
                    "South Indian Meals",
                    "Traditional South Indian meal with rice, sambar, rasam, vegetables, curd and accompaniments",
                    "140",
                    sort++
            ));

            menu.add(product(
                    meals,
                    "Mini Meals",
                    "Compact South Indian meal with rice, sambar, vegetables and curd",
                    "110",
                    sort++
            ));

            menu.add(product(
                    meals,
                    "Udupi Thali",
                    "Traditional Udupi-style thali with rice, curries, sambar, rasam, curd and sweet",
                    "180",
                    sort++
            ));

            menu.add(product(
                    meals,
                    "Karnataka Thali",
                    "Authentic Karnataka-style vegetarian thali",
                    "190",
                    sort++
            ));

            menu.add(product(
                    meals,
                    "Curd Rice",
                    "Creamy South Indian curd rice tempered with spices",
                    "60",
                    sort++
            ));

            menu.add(product(
                    meals,
                    "Bisibele Bath",
                    "Karnataka-style rice, lentils and vegetables cooked with aromatic spices",
                    "80",
                    sort++
            ));


            // ============================================================
            // RICE & TIFFIN
            // ============================================================

            menu.add(product(
                    rice,
                    "Lemon Rice",
                    "South Indian lemon rice tempered with mustard, curry leaves and peanuts",
                    "65",
                    sort++
            ));

            menu.add(product(
                    rice,
                    "Coconut Rice",
                    "Fragrant rice prepared with fresh coconut and South Indian tempering",
                    "70",
                    sort++
            ));

            menu.add(product(
                    rice,
                    "Tomato Rice",
                    "Spicy and tangy South Indian tomato rice",
                    "70",
                    sort++
            ));

            menu.add(product(
                    rice,
                    "Vangi Bath",
                    "Karnataka-style brinjal rice with aromatic spice powder",
                    "75",
                    sort++
            ));

            menu.add(product(
                    rice,
                    "Veg Pulao",
                    "Fragrant basmati rice cooked with fresh vegetables and spices",
                    "100",
                    sort++
            ));

            menu.add(product(
                    rice,
                    "Vegetable Biryani",
                    "Aromatic vegetarian biryani served with raita",
                    "120",
                    sort++
            ));


            // ============================================================
            // NORTH INDIAN
            // ============================================================

            menu.add(product(
                    northIndian,
                    "Paneer Butter Masala",
                    "Paneer cooked in a rich tomato and butter gravy",
                    "150",
                    sort++
            ));

            menu.add(product(
                    northIndian,
                    "Kadai Paneer",
                    "Paneer cooked with capsicum, onion and kadai spices",
                    "150",
                    sort++
            ));

            menu.add(product(
                    northIndian,
                    "Palak Paneer",
                    "Paneer cooked in a creamy spinach gravy",
                    "145",
                    sort++
            ));

            menu.add(product(
                    northIndian,
                    "Chana Masala",
                    "Chickpeas cooked in a flavorful Indian masala",
                    "110",
                    sort++
            ));

            menu.add(product(
                    northIndian,
                    "Dal Tadka",
                    "Yellow dal tempered with ghee, garlic and Indian spices",
                    "100",
                    sort++
            ));

            menu.add(product(
                    northIndian,
                    "Mix Veg Curry",
                    "Seasonal vegetables cooked in a flavorful Indian gravy",
                    "120",
                    sort++
            ));


            // ============================================================
            // INDIAN BREADS
            // ============================================================

            menu.add(product(
                    breads,
                    "Chapati",
                    "Soft whole wheat Indian flatbread",
                    "25",
                    sort++
            ));

            menu.add(product(
                    breads,
                    "Butter Roti",
                    "Whole wheat tandoori roti finished with butter",
                    "35",
                    sort++
            ));

            menu.add(product(
                    breads,
                    "Butter Naan",
                    "Soft naan brushed with butter",
                    "55",
                    sort++
            ));

            menu.add(product(
                    breads,
                    "Garlic Naan",
                    "Naan topped with garlic and coriander",
                    "65",
                    sort++
            ));

            menu.add(product(
                    breads,
                    "Aloo Paratha",
                    "Whole wheat paratha stuffed with spiced potato",
                    "75",
                    sort++
            ));


            // ============================================================
            // SNACKS & STARTERS
            // ============================================================

            menu.add(product(
                    snacks,
                    "Gobi Manchurian",
                    "Crispy cauliflower tossed in Indo-Chinese Manchurian sauce",
                    "110",
                    sort++
            ));

            menu.add(product(
                    snacks,
                    "Gobi 65",
                    "Crispy fried cauliflower tossed with South Indian spices",
                    "105",
                    sort++
            ));

            menu.add(product(
                    snacks,
                    "Paneer 65",
                    "Crispy paneer tossed with spicy South Indian seasoning",
                    "130",
                    sort++
            ));

            menu.add(product(
                    snacks,
                    "Veg Cutlet",
                    "Crispy vegetable cutlets served with chutney",
                    "70",
                    sort++
            ));

            menu.add(product(
                    snacks,
                    "Onion Pakoda",
                    "Crispy onion fritters with Indian spices",
                    "60",
                    sort++
            ));

            menu.add(product(
                    snacks,
                    "Mangalore Bajji",
                    "Traditional Mangalore-style fritters",
                    "65",
                    sort++
            ));


            // ============================================================
            // CHAATS
            // ============================================================

            menu.add(product(
                    chaats,
                    "Pani Puri",
                    "Crispy puris filled with spiced potato and tangy pani",
                    "60",
                    sort++
            ));

            menu.add(product(
                    chaats,
                    "Masala Puri",
                    "Karnataka-style crushed puris topped with spicy peas masala",
                    "65",
                    sort++
            ));

            menu.add(product(
                    chaats,
                    "Bhel Puri",
                    "Puffed rice mixed with chutneys, vegetables and sev",
                    "65",
                    sort++
            ));

            menu.add(product(
                    chaats,
                    "Dahi Puri",
                    "Crispy puris topped with yogurt, chutneys and sev",
                    "75",
                    sort++
            ));


            // ============================================================
            // BEVERAGES
            // ============================================================

            menu.add(product(
                    beverages,
                    "South Indian Filter Coffee",
                    "Traditional strong South Indian filter coffee",
                    "35",
                    sort++
            ));

            menu.add(product(
                    beverages,
                    "Masala Tea",
                    "Indian tea brewed with aromatic spices",
                    "30",
                    sort++
            ));

            menu.add(product(
                    beverages,
                    "Ginger Tea",
                    "Hot tea infused with fresh ginger",
                    "30",
                    sort++
            ));

            menu.add(product(
                    beverages,
                    "Buttermilk",
                    "Refreshing spiced South Indian buttermilk",
                    "30",
                    sort++
            ));

            menu.add(product(
                    beverages,
                    "Badam Milk",
                    "Creamy milk flavored with almonds and saffron",
                    "50",
                    sort++
            ));

            menu.add(product(
                    beverages,
                    "Fresh Lime Soda",
                    "Refreshing fresh lime soda",
                    "45",
                    sort++
            ));


            // ============================================================
            // JUICES & SHAKES
            // ============================================================

            menu.add(product(
                    juices,
                    "Fresh Orange Juice",
                    "Freshly prepared orange juice",
                    "70",
                    sort++
            ));

            menu.add(product(
                    juices,
                    "Watermelon Juice",
                    "Fresh chilled watermelon juice",
                    "60",
                    sort++
            ));

            menu.add(product(
                    juices,
                    "Pineapple Juice",
                    "Fresh pineapple juice",
                    "70",
                    sort++
            ));

            menu.add(product(
                    juices,
                    "Mango Shake",
                    "Creamy fresh mango milkshake",
                    "90",
                    sort++
            ));

            menu.add(product(
                    juices,
                    "Banana Shake",
                    "Fresh banana milkshake",
                    "80",
                    sort++
            ));

            menu.add(product(
                    juices,
                    "Chocolate Shake",
                    "Rich chocolate milkshake",
                    "100",
                    sort++
            ));


            // ============================================================
            // SWEETS & DESSERTS
            // ============================================================

            menu.add(product(
                    sweets,
                    "Gulab Jamun",
                    "Soft milk-solid dumplings soaked in sugar syrup",
                    "60",
                    sort++
            ));

            menu.add(product(
                    sweets,
                    "Mysore Pak",
                    "Traditional Karnataka sweet made with gram flour and ghee",
                    "70",
                    sort++
            ));

            menu.add(product(
                    sweets,
                    "Kesari Bath",
                    "Traditional semolina sweet flavored with saffron and ghee",
                    "55",
                    sort++
            ));

            menu.add(product(
                    sweets,
                    "Pineapple Kesari",
                    "Kesari prepared with pineapple and aromatic spices",
                    "60",
                    sort++
            ));

            menu.add(product(
                    sweets,
                    "Payasam",
                    "Traditional South Indian milk-based dessert",
                    "60",
                    sort++
            ));

            menu.add(product(
                    sweets,
                    "Ice Cream",
                    "Choice of classic ice cream flavors",
                    "70",
                    sort++
            ));


            // ============================================================
            // COMBOS
            // ============================================================

            menu.add(product(
                    combos,
                    "Idli Vada + Coffee",
                    "Idli, vada, sambar, chutney and South Indian filter coffee",
                    "95",
                    sort++
            ));

            menu.add(product(
                    combos,
                    "Masala Dosa + Coffee",
                    "Masala dosa with sambar, chutney and filter coffee",
                    "110",
                    sort++
            ));

            menu.add(product(
                    combos,
                    "Poori Sagu + Coffee",
                    "Poori with vegetable sagu and filter coffee",
                    "105",
                    sort++
            ));

            menu.add(product(
                    combos,
                    "Mini Meals Combo",
                    "Mini South Indian meal with curd and beverage",
                    "135",
                    sort++
            ));

            menu.add(product(
                    combos,
                    "Veg Biryani Combo",
                    "Vegetable biryani with raita and beverage",
                    "145",
                    sort++
            ));


            /*
             * ============================================================
             * SAVE ALL PRODUCTS
             * ============================================================
             */

            products.saveAll(menu);


            /*
             * ============================================================
             * KIOSK
             * ============================================================
             */

            if (kiosks.count() == 0) {

                kiosks.save(
                        Kiosk.builder()
                                .id("kiosk-1")
                                .name("Kiosk 1")
                                .location("Front Counter")
                                .status("ONLINE")
                                .active(true)
                                .build()
                );
            }


            /*
             * ============================================================
             * KITCHEN
             * ============================================================
             */

            if (kitchens.count() == 0) {

                kitchens.save(
                        Kitchen.builder()
                                .id("kitchen-1")
                                .name("Main Kitchen")
                                .status("ONLINE")
                                .autoRefreshSeconds(5)
                                .soundEnabled(true)
                                .stations(new ArrayList<>())
                                .build()
                );
            }

        };
    }


    /*
     * ================================================================
     * PRODUCT BUILDER
     * ================================================================
     */

private Product product(
        Category category,
        String name,
        String description,
        String price,
        int sortOrder
) {

    return Product.builder()
            .categoryId(category.getId())
            .name(name)
            .description(description)
            .price(new BigDecimal(price))
            .taxRate(new BigDecimal("5"))
            .available(true)
            .sortOrder(sortOrder)
            .image(imageFor(name))
            .build();
}

private String imageFor(String name) {

    String key = name.toLowerCase()
            .replace(" ", "-")
            .replace("+", "plus")
            .replace("&", "and");

    return switch (key) {

        // =========================
        // BREAKFAST
        // =========================

        case "idli" ->
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=800&q=85";

        case "vada" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "idli-vada" ->
                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?auto=format&fit=crop&w=800&q=85";

        case "sambar-idli" ->
                "https://images.unsplash.com/photo-1630383249896-424e482df921?auto=format&fit=crop&w=800&q=85";

        case "masala-idli" ->
                "https://images.unsplash.com/photo-1589308078059-be1415eab4c3?auto=format&fit=crop&w=800&q=85";

        case "pongal" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "upma" ->
                "https://images.unsplash.com/photo-1630383249896-424e482df921?auto=format&fit=crop&w=800&q=85";

        case "poori-sagu" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "mangalore-buns" ->
                "https://images.unsplash.com/photo-1555507036-ab1f4038808a?auto=format&fit=crop&w=800&q=85";


        // =========================
        // DOSA
        // =========================

        case "plain-dosa" ->
                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=800&q=85";

        case "masala-dosa" ->
                "https://images.unsplash.com/photo-1630383249896-424e482df921?auto=format&fit=crop&w=800&q=85";

        case "mysore-masala-dosa" ->
                "https://images.unsplash.com/photo-1694849789329-7f8c1e0f8d98?auto=format&fit=crop&w=800&q=85";

        case "set-dosa" ->
                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=800&q=85";

        case "rava-dosa" ->
                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=800&q=85";

        case "onion-rava-dosa" ->
                "https://images.unsplash.com/photo-1694849789329-7f8c1e0f8d98?auto=format&fit=crop&w=800&q=85";

        case "ghee-roast-dosa" ->
                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=800&q=85";

        case "podi-dosa" ->
                "https://images.unsplash.com/photo-1694849789329-7f8c1e0f8d98?auto=format&fit=crop&w=800&q=85";

        case "butter-masala-dosa" ->
                "https://images.unsplash.com/photo-1630383249896-424e482df921?auto=format&fit=crop&w=800&q=85";

        case "paneer-masala-dosa" ->
                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=800&q=85";


        // =========================
        // MEALS
        // =========================

        case "south-indian-meals" ->
                "https://images.unsplash.com/photo-1626509653291-18d7a3c4d3e5?auto=format&fit=crop&w=800&q=85";

        case "mini-meals" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "udupi-thali" ->
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=800&q=85";

        case "karnataka-thali" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "curd-rice" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "bisibele-bath" ->
                "https://images.unsplash.com/photo-1630383249896-424e482df921?auto=format&fit=crop&w=800&q=85";


        // =========================
        // RICE
        // =========================

        case "lemon-rice" ->
                "https://images.unsplash.com/photo-1596797038530-2c107229654b?auto=format&fit=crop&w=800&q=85";

        case "coconut-rice" ->
                "https://images.unsplash.com/photo-1512058564366-18510be2db19?auto=format&fit=crop&w=800&q=85";

        case "tomato-rice" ->
                "https://images.unsplash.com/photo-1596797038530-2c107229654b?auto=format&fit=crop&w=800&q=85";

        case "vangi-bath" ->
                "https://images.unsplash.com/photo-1512058564366-18510be2db19?auto=format&fit=crop&w=800&q=85";

        case "veg-pulao" ->
                "https://images.unsplash.com/photo-1512058564366-18510be2db19?auto=format&fit=crop&w=800&q=85";

        case "vegetable-biryani" ->
                "https://images.unsplash.com/photo-1563379091339-03246963d96c?auto=format&fit=crop&w=800&q=85";


        // =========================
        // NORTH INDIAN
        // =========================

        case "paneer-butter-masala" ->
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?auto=format&fit=crop&w=800&q=85";

        case "kadai-paneer" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "palak-paneer" ->
                "https://images.unsplash.com/photo-1613292443284-8d10ef9383fe?auto=format&fit=crop&w=800&q=85";

        case "chana-masala" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "dal-tadka" ->
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=800&q=85";

        case "mix-veg-curry" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";


        // =========================
        // BREADS
        // =========================

        case "chapati" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "butter-roti" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "butter-naan" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "garlic-naan" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "aloo-paratha" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";


        // =========================
        // SNACKS
        // =========================

        case "gobi-manchurian" ->
                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?auto=format&fit=crop&w=800&q=85";

        case "gobi-65" ->
                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?auto=format&fit=crop&w=800&q=85";

        case "paneer-65" ->
                "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?auto=format&fit=crop&w=800&q=85";

        case "veg-cutlet" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "onion-pakoda" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "mangalore-bajji" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";


        // =========================
        // CHAATS
        // =========================

        case "pani-puri" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "masala-puri" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "bhel-puri" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "dahi-puri" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";


        // =========================
        // BEVERAGES
        // =========================

        case "south-indian-filter-coffee" ->
                "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?auto=format&fit=crop&w=800&q=85";

        case "masala-tea" ->
                "https://images.unsplash.com/photo-1597318181409-cf64d0f5e4b1?auto=format&fit=crop&w=800&q=85";

        case "ginger-tea" ->
                "https://images.unsplash.com/photo-1597318181409-cf64d0f5e4b1?auto=format&fit=crop&w=800&q=85";

        case "buttermilk" ->
                "https://images.unsplash.com/photo-1553530666-ba11a7da3888?auto=format&fit=crop&w=800&q=85";

        case "badam-milk" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "fresh-lime-soda" ->
                "https://images.unsplash.com/photo-1556679343-c7306c1976bc?auto=format&fit=crop&w=800&q=85";


        // =========================
        // JUICES / SHAKES
        // =========================

        case "fresh-orange-juice" ->
                "https://images.unsplash.com/photo-1600271886742-f049cd451bba?auto=format&fit=crop&w=800&q=85";

        case "watermelon-juice" ->
                "https://images.unsplash.com/photo-1587049352846-4a222e784d38?auto=format&fit=crop&w=800&q=85";

        case "pineapple-juice" ->
                "https://images.unsplash.com/photo-1550258987-190a2d41a8ba?auto=format&fit=crop&w=800&q=85";

        case "mango-shake" ->
                "https://images.unsplash.com/photo-1546173159-315724a31696?auto=format&fit=crop&w=800&q=85";

        case "banana-shake" ->
                "https://images.unsplash.com/photo-1577805947697-89e18249d767?auto=format&fit=crop&w=800&q=85";

        case "chocolate-shake" ->
                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?auto=format&fit=crop&w=800&q=85";


        // =========================
        // DESSERTS
        // =========================

        case "gulab-jamun" ->
                "https://images.unsplash.com/photo-1666190094766-6f9f4f1e9f4d?auto=format&fit=crop&w=800&q=85";

        case "mysore-pak" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "kesari-bath" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "pineapple-kesari" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "payasam" ->
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?auto=format&fit=crop&w=800&q=85";

        case "ice-cream" ->
                "https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=800&q=85";


        // =========================
        // COMBOS
        // =========================

        case "idli-vada-plus-coffee" ->
                "https://images.unsplash.com/photo-1589302168068-964664d93dc0?auto=format&fit=crop&w=800&q=85";

        case "masala-dosa-plus-coffee" ->
                "https://images.unsplash.com/photo-1668236543090-82eba5ee5976?auto=format&fit=crop&w=800&q=85";

        case "poori-sagu-plus-coffee" ->
                "https://images.unsplash.com/photo-1601050690117-94f5f6fa8bd7?auto=format&fit=crop&w=800&q=85";

        case "mini-meals-combo" ->
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=800&q=85";

        case "veg-biryani-combo" ->
                "https://images.unsplash.com/photo-1563379091339-03246963d96c?auto=format&fit=crop&w=800&q=85";

        default ->
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?auto=format&fit=crop&w=800&q=85";
    };
}
}
