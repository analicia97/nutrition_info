package analicia_projects.nutrition_info.core.model;

import reactor.core.publisher.Mono;

public class Ingredient {
    String id;
    
    String name;
    Double quantityInGrams;
    boolean isPlantBased;
    Mono<NutritionInfo> nutritionInfo;
}
