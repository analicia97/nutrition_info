package analicia_projects.nutritionInfo.api.controller;

import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.api.controller.resource.IngredientResource;
import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import analicia_projects.nutritionInfo.core.model.NutritionInfo;
import analicia_projects.nutritionInfo.core.repository.IngredientRepository;
import analicia_projects.nutritionInfo.core.service.ingredient.IngredientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {
    
    @InjectMocks
    private IngredientController ingredientController;
    
    @Mock
    private IngredientRepository ingredientRepository;
    
    @Mock
    private IngredientServiceImpl ingredientService;
    
    private final Ingredient ingredient = new Ingredient("1", "Tomato", 100.0, true, new NutritionInfo());
    private final IngredientResource ingredientResource = new IngredientResource("1", "Tomato", 100.0, true, new NutritionInfo());
    
    private final Dish dish = new Dish("dishId", "Dish Name", "Dish Description", "20.58", new ArrayList<>(), true, true);
    
    @Test
    public void getIngredientsReturnsOkStatusWhenIngredientsExist() {
       
        when(ingredientService.getIngredients(anyString())).thenReturn(Flux.just(ingredient));
        
        Mono<ResponseEntity<List<IngredientResource>>> result = ingredientController.getIngredients("dishId");
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK)
                .verifyComplete();
    }
    
    @Test
    public void getIngredientsReturnsNotFoundStatusWhenNoIngredientsExist() {
        when(ingredientService.getIngredients(anyString())).thenReturn(Flux.empty());
        
        Mono<ResponseEntity<List<IngredientResource>>> result = ingredientController.getIngredients("dishId");
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();
    }
    
    @Test
    public void getIngredientsReturnsInternalServerErrorWhenExceptionOccurs() {
        when(ingredientService.getIngredients(anyString())).thenReturn(Flux.error(new RuntimeException()));
        
        Mono<ResponseEntity<List<IngredientResource>>> result = ingredientController.getIngredients("dishId");
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
                .verifyComplete();
    }
    
    @Test
    public void addNewIngredientToDishReturnsCreatedStatusWhenDishExists() {
        when(ingredientService.addIngredient("dishId", ingredient)).thenReturn(Mono.just(dish));
        
        Mono<ResponseEntity<DishResource>> result = ingredientController.addNewIngredientToDish("dishId", ingredientResource);
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.CREATED)
                .verifyComplete();
    }
    
    @Test
    public void addNewIngredientToDishReturnsNotFoundStatusWhenDishDoesNotExist() {
        when(ingredientService.addIngredient(anyString(), any(Ingredient.class))).thenReturn(Mono.error(new NoSuchElementException()));
        
        Mono<ResponseEntity<DishResource>> result = ingredientController.addNewIngredientToDish("nonExistentDishId", ingredientResource);
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();
    }
    
    @Test
    public void addNewIngredientToDishReturnsInternalServerErrorWhenExceptionOccurs() {
        when(ingredientService.addIngredient(anyString(), any(Ingredient.class))).thenReturn(Mono.error(new RuntimeException()));
        
        Mono<ResponseEntity<DishResource>> result = ingredientController.addNewIngredientToDish("dishId", ingredientResource);
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
                .verifyComplete();
    }
    
}
