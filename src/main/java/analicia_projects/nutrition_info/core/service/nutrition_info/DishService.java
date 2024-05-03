package analicia_projects.nutrition_info.core.service.nutrition_info;

import analicia_projects.nutrition_info.core.model.Dish;
import reactor.core.publisher.Mono;

public interface DishService {
    
    public Mono<Dish> addDish(Dish dish);
}
