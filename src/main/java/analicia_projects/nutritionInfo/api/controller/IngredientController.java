package analicia_projects.nutritionInfo.api.controller;


import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.core.service.dish.DishService;
import analicia_projects.nutritionInfo.core.model.Dish;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class IngredientController {
    
    private final DishService dishService;
    
    @PostMapping
    public Mono<ResponseEntity<DishResource>> addNewDish(
            @RequestBody @Valid DishResource dish) {
        
        return dishService.addDish(Dish.of(dish))
                       .map(DishResource::of)
                       .map(dishResource -> ResponseEntity.status(HttpStatus.CREATED).body(dishResource))
                       .onErrorResume(RuntimeException.class, e -> Mono.just(
                               ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                       ));
    }
    
}
