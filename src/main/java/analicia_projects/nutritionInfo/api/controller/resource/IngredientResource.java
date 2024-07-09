package analicia_projects.nutritionInfo.api.controller.resource;

import analicia_projects.nutritionInfo.core.model.Ingredient;
import analicia_projects.nutritionInfo.core.model.NutritionInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

import java.util.UUID;


@AllArgsConstructor
@Data
public class IngredientResource {
    
    String id;
    @NotEmpty (message = "Name is required.")
    String name;
    @NotEmpty @NotNull(message = "Quantity is required.")
    Double quantityInGrams;
    
    boolean isPlantBased;
    
    @NotNull (message = "Nutrition info is required.")
    NutritionInfo nutritionInfo;
    
    public static IngredientResource of(Ingredient ingredient) {
        return new IngredientResource(
                ingredient.getId() == null ? UUID.randomUUID().toString() : ingredient.getId(),
                ingredient.getName(),
                ingredient.getQuantityInGrams(),
                ingredient.isPlantBased(),
                ingredient.getNutritionInfo()
        );
    }
    
}
