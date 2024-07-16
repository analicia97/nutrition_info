package analicia_projects.nutritionInfo.core.service.ingredient;

import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import analicia_projects.nutritionInfo.core.repository.DishRepository;
import analicia_projects.nutritionInfo.core.repository.IngredientRepository;
import analicia_projects.nutritionInfo.core.service.dish.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    
    private final DishRepository dishRepository;
    private final DishService dishService;
    
    @Override
    public Flux<Ingredient> getIngredients(String dishId) {
        
        return dishRepository.getDishById(dishId)
                       .map(Dish::getIngredients)
                       .flatMapMany(Flux::fromIterable);
    }
    
    @Override
    public Mono<Ingredient> addIngredient(String dishId, Ingredient ingredient) {
        return dishRepository.getDishById(dishId)
                       .switchIfEmpty(Mono.error(new NoSuchElementException("Dish not found with id: " + dishId)))
                       .flatMap(dish -> {
                           dish.getIngredients().add(ingredient);
                           return dishService.updateDish(dish);
                       })
                       .thenReturn(ingredient);
    }
    
}
