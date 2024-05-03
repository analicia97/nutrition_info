package analicia_projects.nutritionInfo.api.controller.resource;

import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishResource {
    
    @NotEmpty(message = "Name is required.")
    String name;
    
    @NotEmpty(message = "Description is required.")
    String description;
    
    @NotBlank(message = "Price is required.")
    String price;
    
    @NotEmpty(message = "Ingredients are required.")
    Flux<Ingredient> ingredients;
    
    boolean isVegetarian;
    boolean isVegan;
    boolean isNutritionallyBalanced;
    
    
    public static DishResource of(Dish dish) {
        return new DishResource(
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getIngredients(),
                dish.isVegetarian(),
                dish.isVegan(),
                dish.isNutritionallyBalanced()
        );
    }
}
