package analicia_projects.nutritionInfo.core.model;

import reactor.core.publisher.Mono;

public class Ingredient {
    String id;
    
    String name;
    Double quantityInGrams;
    boolean isPlantBased;
    Mono<NutritionInfo> nutritionInfo;
}
