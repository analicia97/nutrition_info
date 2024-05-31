package analicia_projects.nutritionInfo.data.dish;

import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;

@Getter
@NoArgsConstructor(staticName = "empty")
@AllArgsConstructor(staticName = "of")
@Document("Dish")
public class DishDocument {
    
    String id;
    String name;
    String description;
    String price;
    Flux<Ingredient> ingredients;
    
    public static DishDocument of(Dish dish) {
        return new DishDocument(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                dish.getIngredients()
        );
    }
    
    public static Dish toModel(DishDocument dishDocument) {
        
        return new Dish(
                dishDocument.getId(),
                dishDocument.getName(),
                dishDocument.getDescription(),
                dishDocument.getPrice(),
                dishDocument.getIngredients(),
                //TODO añadir los métodos para comprobar si es vegetariano, vegano y equilibrado
                false,
                false,
                false
        );
    }
}
