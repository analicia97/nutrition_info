package analicia_projects.nutritionInfo.api.controller.resource;

import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishResource {
    
    String id;
    
    @NotEmpty(message = "Dish name is required.")
    String name;
    
    @NotEmpty(message = "Dish description is required.")
    String description;
    
    @NotBlank(message = "Dish price is required.")
    String price;
    
    @NotEmpty(message = "Ingredients are required.")
    List<IngredientResource> ingredients;
    
    boolean isVegan;
    boolean isNutritionallyBalanced;
    
    
    public static DishResource of(Dish dish) {
        return new DishResource(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                ofIngredientList(dish.getIngredients()),
                dish.isVegan(),
                dish.isNutritionallyBalanced()
        );
    }
    
    public static List<IngredientResource> ofIngredientList(List<Ingredient> ingredients) {
        return Flux.fromIterable(ingredients)
                .map(IngredientResource::of)
                .collectList()
                .block();
    }
}
