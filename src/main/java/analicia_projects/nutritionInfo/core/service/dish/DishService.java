package analicia_projects.nutritionInfo.core.service.dish;

import analicia_projects.nutritionInfo.core.model.Dish;
import reactor.core.publisher.Mono;

public interface DishService {
    
    Mono<Dish> getDishById(String id);
    Mono<Dish> addDish(Dish dish);
    Mono<Dish> updateDish(Dish dish);
    Mono<Void> deleteDish(String id);
}
