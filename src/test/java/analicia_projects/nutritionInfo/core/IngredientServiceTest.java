package analicia_projects.nutritionInfo.core;

import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import analicia_projects.nutritionInfo.core.model.NutritionInfo;
import analicia_projects.nutritionInfo.core.repository.DishRepository;
import analicia_projects.nutritionInfo.core.service.dish.DishService;
import analicia_projects.nutritionInfo.core.service.ingredient.IngredientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {
    
    @Mock
    private DishRepository dishRepository;
    
    @Mock
    private DishService dishService;
    
    @InjectMocks
    private IngredientServiceImpl ingredientService;
    
    private Dish dish;
    private Ingredient ingredient1;
    private Ingredient ingredient2;
    
    @BeforeEach
    public void setup() {
        ingredient1 = new Ingredient("ingredient1Id", "Ingredient 1", 100.0, false, new NutritionInfo());
        ingredient2 = new Ingredient("ingredient2Id", "Ingredient 2", 150.0, false, new NutritionInfo());
        List<Ingredient> ingredients = Arrays.asList(ingredient1, ingredient2);
        dish = new Dish("dishId", "Dish Name", "Dish Description", "20.58", ingredients, true, true);
    }
    
    @Test
    public void getIngredientsReturnsIngredientsWhenDishExists() {
        when(dishRepository.getDishById(anyString())).thenReturn(Mono.just(dish));
        
        Flux<Ingredient> result = ingredientService.getIngredients("dishId");
        
        StepVerifier.create(result)
                .expectNext(ingredient1, ingredient2)
                .verifyComplete();
    }
    
    @Test
    public void getIngredientsReturnsEmptyWhenDishDoesNotExist() {
        when(dishRepository.getDishById(anyString())).thenReturn(Mono.empty());
        
        Flux<Ingredient> result = ingredientService.getIngredients("nonExistentDishId");
        
        StepVerifier.create(result)
                .verifyComplete();
    }
    // TODO terminar este test
//    @Test
//    public void addIngredientReturnsAddedIngredientWhenDishExists() {
//        Ingredient ingredientToAdd = new Ingredient("ingredient3Id", "Ingredient 3", 120.0, false, new NutritionInfo());
//        when(dishRepository.getDishById(anyString())).thenReturn(Mono.just(dish));
//        when(dishService.updateDish(dish)).thenReturn(Mono.just(dish));
//
//        Mono<Ingredient> result = ingredientService.addIngredient("dishId", ingredientToAdd);
//
//        StepVerifier.create(result)
//                .expectNextMatches(ingredient -> ingredient.equals(ingredientToAdd) && dish.getIngredients().contains(ingredientToAdd))
//                .verifyComplete();
//    }
    
    @Test
    public void addIngredientThrowsExceptionWhenDishDoesNotExist() {
        Ingredient ingredientToAdd = new Ingredient("ingredient3Id", "Ingredient 3", 120.0, false, new NutritionInfo());
        when(dishRepository.getDishById(anyString())).thenReturn(Mono.empty());
        
        Mono<Ingredient> result = ingredientService.addIngredient("nonExistentDishId", ingredientToAdd);
        
        StepVerifier.create(result)
                .expectError(NoSuchElementException.class)
                .verify();
    }
    
}
