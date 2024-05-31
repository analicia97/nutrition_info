package analicia_projects.nutritionInfo.core.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import reactor.core.publisher.Mono;

@Data
public class Ingredient {
    
    String id;
    String name;
    Double quantityInGrams;
    boolean isPlantBased;
    NutritionInfo nutritionInfo;
}
