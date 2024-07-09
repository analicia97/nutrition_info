package analicia_projects.nutritionInfo.api.controller;

import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.service.dish.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishController {
    
    private final DishService dishService;
    
    @PostMapping
    public  Mono<ResponseEntity<DishResource>> addDish(@RequestBody Dish dish) {
        return dishService.addDish(dish)
                       .map(DishResource::of)
                       .map(dish1 -> {
                           log.info("Dish with id {} added", dish1.getId());
                           return ResponseEntity.status(HttpStatus.CREATED).body(dish1);
                       })
                       .onErrorResume(RuntimeException.class, e -> {
                           log.error("Error adding dish", e);
                           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<DishResource>build());
                       });
    }
    
    @GetMapping("/{dishId}")
    public Mono<ResponseEntity<DishResource>> getDish(@PathVariable String dishId) {
        return dishService.getDishById(dishId)
                       .map(DishResource::of)
                       .map(dish -> {
                           log.info("Dish with id {} retrieved", dish.getId());
                           return ResponseEntity.ok(dish);
                       })
                       .switchIfEmpty(Mono.error(new NoSuchElementException("Dish not found with id: " + dishId)))
                       .onErrorResume(Exception.class, e -> {
                           log.error("Error retrieving the dish with id {}", dishId, e);
                           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<DishResource>build());
                       });
    }
    
    @PutMapping
    public Mono<ResponseEntity<DishResource>> updateDish(@RequestBody Dish dish) {
        return dishService.updateDish(dish)
                       .map(DishResource::of)
                       .map(dish1 -> {
                           log.info("Dish with id {} updated", dish1.getId());
                           return ResponseEntity.ok(dish1);
                       })
                       .switchIfEmpty(Mono.error(new NoSuchElementException("Dish not found with id: " + dish.getId())))
                       .onErrorResume(RuntimeException.class, e -> {
                           log.error("Error updating dish", e);
                           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<DishResource>build());
                       });
    }
    
    @DeleteMapping("/{dishId}")
    public Mono<ResponseEntity<Void>> deleteDish(@PathVariable String dishId) {
        return dishService.deleteDish(dishId)
                       .map(aVoid -> {
                           log.info("Dish with id {} deleted", dishId);
                           return ResponseEntity.ok().<Void>build();
                       })
                       .onErrorResume(Exception.class, e -> {
                           log.error("Error deleting the dish with id {}", dishId, e);
                           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<Void>build());
                       });
    }
    
    
}
