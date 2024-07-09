package analicia_projects.nutritionInfo.api.controller;

import analicia_projects.nutritionInfo.api.controller.resource.IngredientResource;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import analicia_projects.nutritionInfo.core.service.ingredient.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {
    
    private final IngredientService ingredientService;
    
    @GetMapping("/{dishId}")
    public Mono<ResponseEntity<List<IngredientResource>>> getIngredients(@PathVariable String dishId) {
        return ingredientService.getIngredients(dishId)
                       .map(IngredientResource::of)
                       .collectList()
                       .map(ingredients -> {
                           if (ingredients.isEmpty()) {
                               log.info("No ingredients found for dish with id {}", dishId);
                               return ResponseEntity.status(HttpStatus.NOT_FOUND).<List<IngredientResource>>build();
                           } else {
                               log.info("Ingredients for dish with id {} retrieved", dishId);
                               return ResponseEntity.ok(ingredients);
                           }
                       })
                       .onErrorResume(Exception.class, e -> {
                           log.error("Error retrieving the ingredients for dish with id {}", dishId, e);
                           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<List<IngredientResource>>build());
                       });
    }
    
    @PostMapping("/{dishId}")
    public Mono<ResponseEntity<IngredientResource>> addNewIngredientToDish(
            @PathVariable String dishId,
            @RequestBody Ingredient ingredient) {
        
        return ingredientService.addIngredient(dishId, ingredient)
                       .map(IngredientResource::of)
                       .map(ingredient1 -> {
                           log.info("Ingredient with id {} added", ingredient1.getId());
                           return ResponseEntity.status(HttpStatus.CREATED).body(ingredient1);
                       })
                       .onErrorResume(RuntimeException.class, e -> {
                           log.error("Error adding ingredient", e);
                           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<IngredientResource>build());
                       });
    }
    
}
