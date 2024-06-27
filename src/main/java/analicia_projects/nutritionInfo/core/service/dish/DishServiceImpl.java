package analicia_projects.nutritionInfo.core.service.dish;

import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import analicia_projects.nutritionInfo.core.model.NutritionInfo;
import analicia_projects.nutritionInfo.core.repository.DishRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    
    private final DishRepository dishRepository;
    
    @Override
    public Mono<Dish> addDish(Dish dish) {
        dish.setVegan(isVegan(dish));
        dish.setNutritionallyBalanced(isNutritionallyBalanced(dish));
        return dishRepository.addDish(dish);
    }
    
    
    private boolean isVegan(Dish dish) {
        return dish.getIngredients().stream()
                .allMatch(Ingredient::isPlantBased);
    }
    
    private boolean isNutritionallyBalanced(Dish dish) {
        
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
