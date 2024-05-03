package analicia_projects.nutritionInfo.core.service.nutritionInfo;

import analicia_projects.nutritionInfo.core.model.Dish;
import reactor.core.publisher.Mono;

public interface DishService {
    
    public Mono<Dish> addDish(Dish dish);
}
