package analicia_projects.nutritionInfo.core.model;

import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.api.controller.resource.IngredientResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    
    String id;
    String name;
    String description;
    String price;
    List<Ingredient> ingredients;
    boolean isVegan;
    boolean isNutritionallyBalanced;
    
    public static Dish of(DishResource dishResource) {
        return new Dish(
                dishResource.getId(),
                dishResource.getName(),
                dishResource.getDescription(),
                dishResource.getPrice(),
                ofIngredientResourceList(dishResource.getIngredients()),
                dishResource.isVegan(),
                dishResource.isNutritionallyBalanced()
                );
    }
    
    public static List<Ingredient> ofIngredientResourceList(List<IngredientResource> ingredients) {
        return Flux.fromIterable(ingredients)
                       .map(Ingredient::of)
                       .collectList()
                       .block();
    }
    
    public static boolean isVegan(Dish dish) {
        return dish.getIngredients().stream().allMatch(Ingredient::isPlantBased);
    }
    
    public static boolean isNutritionallyBalanced(Dish dish) {
        
        List<Ingredient> ingredients = dish.getIngredients();
        
        double totalCarbohydrates = 0.0;
        double totalSimpleCarbohydrates = 0.0;
        double totalComplexCarbohydrates = 0.0;
        double totalFats = 0.0;
        double totalSaturatedFats = 0.0;
        double totalUnsaturatedFats = 0.0;
        double totalProteins = 0.0;
        
        for (Ingredient ingredient : ingredients) {
            NutritionInfo info = ingredient.getNutritionInfo();
            double quantityRatio = ingredient.getQuantityInGrams();
            
            totalCarbohydrates += info.getCarbohydrates() * quantityRatio;
            totalSimpleCarbohydrates += info.getSimpleCarbohydrates() * quantityRatio;
            totalComplexCarbohydrates += info.getComplexCarbohydrates() * quantityRatio;
            totalFats += info.getFats() * quantityRatio;
            totalSaturatedFats += info.getSaturatedFats() * quantityRatio;
            totalUnsaturatedFats += info.getUnsaturatedFats() * quantityRatio;
            totalProteins += info.getProteins() * quantityRatio;
        }
        
        double totalMacronutrients = totalCarbohydrates + totalFats + totalProteins;
        
        double proteinRatio = totalProteins / totalMacronutrients;
        double carbsRatio = totalCarbohydrates / totalMacronutrients;
        double fatsRatio = totalFats / totalMacronutrients;
        
        double simpleCarbsRatio = totalSimpleCarbohydrates / totalMacronutrients;
        double complexCarbsRatio = totalComplexCarbohydrates / totalMacronutrients;
        
        double saturatedFatsRatio = totalSaturatedFats / totalMacronutrients;
        double unsaturatedFatsRatio = totalUnsaturatedFats / totalMacronutrients;
        
        final double MIN_PROTEIN_RATIO = 0.25;
        final double MIN_CARBS_RATIO = 0.5;
        final double MAX_SIMPLE_CARBS_RATIO = 0.1;
        final double MIN_COMPLEX_CARBS_RATIO = 0.4;
        final double MIN_FATS_RATIO = 0.25;
        final double MAX_SATURATED_FATS_RATIO = 0.1;
        final double MIN_UNSATURATED_FATS_RATIO = 0.15;
        
        boolean hasEnoughProtein = proteinRatio >= MIN_PROTEIN_RATIO;
        boolean hasEnoughCarbs = carbsRatio >= MIN_CARBS_RATIO;
        boolean hasBalancedCarbs = simpleCarbsRatio <= MAX_SIMPLE_CARBS_RATIO &&
                                           complexCarbsRatio >= MIN_COMPLEX_CARBS_RATIO;
        boolean hasEnoughFats = fatsRatio >= MIN_FATS_RATIO;
        boolean hasBalancedFats = saturatedFatsRatio <= MAX_SATURATED_FATS_RATIO &&
                                          unsaturatedFatsRatio >= MIN_UNSATURATED_FATS_RATIO;
        
        return hasEnoughProtein && hasEnoughCarbs && hasBalancedCarbs &&
                       hasEnoughFats && hasBalancedFats;
    }
}
