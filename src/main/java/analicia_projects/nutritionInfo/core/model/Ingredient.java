package analicia_projects.nutritionInfo.core.model;

import analicia_projects.nutritionInfo.api.controller.resource.IngredientResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Ingredient {
    
    String id;
    String name;
    Double quantityInGrams;
    boolean isPlantBased;
    NutritionInfo nutritionInfo;
    
    public static Ingredient of(IngredientResource ingredient) {
        return new Ingredient(
                UUID.randomUUID().toString(),
                ingredient.getName(),
                ingredient.getQuantityInGrams(),
                ingredient.isPlantBased(),
                ingredient.getNutritionInfo()
        );
    }
}
