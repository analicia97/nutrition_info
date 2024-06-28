package analicia_projects.nutritionInfo.api.controller;


import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.service.dish.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
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
                       .switchIfEmpty(Mono.error(new NoSuchElementException("Dish not found with id: " + id)))
                       .doOnSuccess(dishResource -> log.info("Dish with id {} and name {} retrieved",
                               Objects.requireNonNull(dishResource.getBody()).getId(),
                               dishResource.getBody().getName())
                       )
                       .onErrorResume(Exception.class, e -> {
                           log.error("Error retrieving dish with id {}", id, e);
                           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                       });
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
    
    @PutMapping
    public Mono<ResponseEntity<DishResource>> updateDish(
            @RequestBody @Valid DishResource dish) {
        
        return dishService.updateDish(Dish.of(dish))
                       .map(DishResource::of)
                       .map(dishResource -> ResponseEntity.status(HttpStatus.OK).body(dishResource))
                       .onErrorResume(RuntimeException.class, e -> Mono.just(
                               ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                       ));
    }
    
//    @DeleteMapping("/{id}")
//    public Mono<ResponseEntity<Void>> deleteDish(@PathVariable String id) {
//
//        return dishService.deleteDish(id)
//                       .map(deleted -> {
//                           if (deleted) {
//                               log.info("Dish with id {} deleted", id);
//                               return ResponseEntity.status(HttpStatus.NO_CONTENT).<Void>build();
//                           } else {
//                               log.info("Dish with id {} not found", id);
//                               return ResponseEntity.status(HttpStatus.NOT_FOUND).<Void>build();
//                           }
//                       })
//                       .onErrorResume(RuntimeException.class, e -> Mono.just(
//                               ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
//                       ));
//    }
    
    
}
