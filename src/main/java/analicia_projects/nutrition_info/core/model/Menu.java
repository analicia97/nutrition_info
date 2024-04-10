package analicia_projects.nutrition_info.core.model;

import reactor.core.publisher.Flux;

public class Menu {
    
    String id;
    String name;
    String description;
    String price;
    Flux<Dish> dishes;

}
