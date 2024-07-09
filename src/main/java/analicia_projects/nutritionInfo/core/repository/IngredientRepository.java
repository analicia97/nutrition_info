package analicia_projects.nutritionInfo.core.repository;

import analicia_projects.nutritionInfo.core.model.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientRepository {
    
    Mono<Ingredient> save(Ingredient dish);
    
}
