package analicia_projects.nutrition_info.api.controller.resource;

import analicia_projects.nutrition_info.core.model.NutritionInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

public class IngredientResource {

    @NotEmpty (message = "Name is required.")
    String name;
    @NotEmpty @NotNull(message = "Quantity is required.")
    Double quantityInGrams;
    
    boolean isPlantBased;
    
    @NotNull (message = "Nutrition info is required.")
    Mono<NutritionInfo> nutritionInfo;
}
