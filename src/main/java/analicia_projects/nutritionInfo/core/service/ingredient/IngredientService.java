package analicia_projects.nutritionInfo.core.service.ingredient;

import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IngredientService {
    
    Flux<Ingredient> getIngredients(String dishId);
    
    Mono<Dish> addIngredient(String dishId, Ingredient ingredient);
    
}
