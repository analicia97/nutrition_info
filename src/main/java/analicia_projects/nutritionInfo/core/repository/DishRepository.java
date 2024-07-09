package analicia_projects.nutritionInfo.core.repository;

import analicia_projects.nutritionInfo.core.model.Dish;
import reactor.core.publisher.Mono;


public interface DishRepository {
    
    Mono<Dish> getDishById(String id);
    Mono<Dish> save(Dish dish);
    Mono<Void> delete(Dish dish);
    
}
