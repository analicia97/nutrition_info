package analicia_projects.nutritionInfo.api.controller;


import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.core.service.dish.DishService;
import analicia_projects.nutritionInfo.core.model.Dish;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class IngredientController {
    
    private final DishService dishService;
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<DishResource>> getDishById(@PathVariable String id) {
        
        return dishService.getDishById(id)
                       .map(DishResource::of)
                       .map(ResponseEntity::ok)
                       .doOnSuccess(dishResource -> log.info("Dish with id {} and name {} retrieved",
                               Objects.requireNonNull(dishResource.getBody()).getId(),
                               dishResource.getBody().getName())
                       )
                       .onErrorResume(RuntimeException.class, e -> Mono.just(
                               ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                       ));
    }
    
    
    @PostMapping
    public Mono<ResponseEntity<DishResource>> addNewDish(
            @RequestBody @Valid DishResource dish) {
        
        return dishService.addDish(Dish.of(dish))
                       .map(DishResource::of)
                       .map(dishResource -> ResponseEntity.status(HttpStatus.CREATED).body(dishResource))
                       .doOnSuccess(dishResource -> log.info("Dish with id {} and name {} added",
                               Objects.requireNonNull(dishResource.getBody()).getId(),
                               dishResource.getBody().getName())
                       )
                       .onErrorResume(RuntimeException.class, e -> Mono.just(
                               ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                       ));
    }
    
}
