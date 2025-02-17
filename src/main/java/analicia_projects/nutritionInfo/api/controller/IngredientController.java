package analicia_projects.nutritionInfo.api.controller;

import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.api.controller.resource.IngredientResource;
import analicia_projects.nutritionInfo.api.controller.swagger.IngredientInterface;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import analicia_projects.nutritionInfo.core.service.ingredient.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController implements IngredientInterface {
    
    private final IngredientService ingredientService;
    
    @GetMapping("/{dishId}")
    public Mono<ResponseEntity<List<IngredientResource>>> getIngredients(@PathVariable String dishId) {
        return ingredientService.getIngredients(dishId)
                       .map(IngredientResource::of)
                       .collectList()
                       .flatMap(ingredients -> {
                           if (ingredients.isEmpty()) {
                               log.info("No ingredients found for dish with id {}", dishId);
                               return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).<List<IngredientResource>>build());
                           } else {
                               log.info("Ingredients for dish with id {} retrieved", dishId);
                               return Mono.just(ResponseEntity.ok(ingredients));
                           }
                       })
                       .onErrorResume(Exception.class, e -> {
                           log.error("Error retrieving the ingredients for dish with id {}", dishId, e);
                           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<List<IngredientResource>>build());
                       });
    }
    
    
    @PostMapping("/{dishId}")
    public Mono<ResponseEntity<DishResource>> addNewIngredientToDish(
            @PathVariable String dishId,
            @RequestBody IngredientResource ingredientResource) {
        
        return ingredientService.addIngredient(dishId, Ingredient.of(ingredientResource))
                       .map(DishResource::of)
                       .flatMap(ingredient -> {
                           log.info("Ingredient with id {} added to the dish with id {}", ingredient.getId(), dishId);
                           return Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(ingredient));
                       })
                       .onErrorResume(NoSuchElementException.class, e -> {
                           log.info("Dish with id {} not found", dishId);
                           return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).<DishResource>build());
                       })
                       .onErrorResume(RuntimeException.class, e -> {
                           log.error("Error adding ingredient", e);
                           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<DishResource>build());
                       });
    }
    
}
