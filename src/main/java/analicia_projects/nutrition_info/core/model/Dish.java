package analicia_projects.nutrition_info.core.model;

import reactor.core.publisher.Flux;

public class Dish {
    String id;
    String name;
    String description;
    String price;
    Flux<Ingredient> ingredients;
    boolean isVegetarian;
    boolean isVegan;
}
