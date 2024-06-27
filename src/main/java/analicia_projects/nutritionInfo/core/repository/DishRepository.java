package analicia_projects.nutritionInfo.core.repository;

import analicia_projects.nutritionInfo.core.model.Dish;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


public interface DishRepository {
    
    Mono<Dish> getDishById(String id);
    Mono<Dish> addDish(Dish dish);
}
