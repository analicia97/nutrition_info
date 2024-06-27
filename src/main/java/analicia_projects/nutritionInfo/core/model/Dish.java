package analicia_projects.nutritionInfo.core.model;

import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

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
                UUID.randomUUID().toString(),
                dishResource.getName(),
                dishResource.getDescription(),
                dishResource.getPrice(),
                dishResource.getIngredients(),
                dishResource.isVegan(),
                dishResource.isNutritionallyBalanced()
                );
    }
}
