package analicia_projects.nutritionInfo.api.controller;

import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.repository.DishRepository;
import analicia_projects.nutritionInfo.core.service.dish.DishServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DishControllerTest {
    
    @InjectMocks
    private DishController dishController;
    
    @Mock
    private DishRepository dishRepository;
    
    @Mock
    private DishServiceImpl dishService;
    
    private final DishResource dishResource = new DishResource("dishId", "Dish Name", "Dish Description", "20.58", new ArrayList<>(), true, true);
    private final Dish dish = new Dish("dishId", "Dish Name", "Dish Description", "20.58", new ArrayList<>(), true, true);
    
    @Test
    public void addDishReturnsCreatedStatusWhenDishIsAddedSuccessfully() {
        when(dishService.addDish(any(Dish.class))).thenReturn(Mono.just(dish));
        
        Mono<ResponseEntity<DishResource>> result = dishController.addDish(dishResource);
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.CREATED)
                .verifyComplete();
    }
    
    @Test
    public void addDishReturnsInternalServerErrorWhenExceptionOccurs() {
        when(dishService.addDish(any(Dish.class))).thenReturn(Mono.error(new RuntimeException()));
        
        Mono<ResponseEntity<DishResource>> result = dishController.addDish(dishResource);
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
                .verifyComplete();
    }
    
    @Test
    public void getDishReturnsOkStatusWhenDishExists() {
        when(dishService.getDishById(anyString())).thenReturn(Mono.just(dish));
        
        Mono<ResponseEntity<DishResource>> result = dishController.getDish("dishId");
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK)
                .verifyComplete();
    }
    
    @Test
    public void getDishReturnsInternalServerErrorWhenExceptionOccurs() {
        when(dishService.getDishById(anyString())).thenReturn(Mono.error(new RuntimeException()));
        
        Mono<ResponseEntity<DishResource>> result = dishController.getDish("dishId");
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
                .verifyComplete();
    }
    
    @Test
    public void updateDishReturnsOkStatusWhenDishExists() {
        when(dishService.updateDish(any(Dish.class))).thenReturn(Mono.just(dish));
        
        Mono<ResponseEntity<DishResource>> result = dishController.updateDish(dishResource);
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK)
                .verifyComplete();
    }
    
    @Test
    public void updateDishReturnsInternalServerErrorWhenExceptionOccurs() {
        when(dishService.updateDish(any(Dish.class))).thenReturn(Mono.error(new RuntimeException()));
        
        Mono<ResponseEntity<DishResource>> result = dishController.updateDish(dishResource);
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
                .verifyComplete();
    }
    
    @Test
    public void deleteDishReturnsOkStatusWhenDishExists() {
        when(dishService.deleteDish(anyString())).thenReturn(Mono.empty());
        
        Mono<ResponseEntity<Void>> result = dishController.deleteDish("dishId");
        
        StepVerifier.create(result)
                .verifyComplete();
    }
    
    @Test
    public void deleteDishReturnsInternalServerErrorWhenExceptionOccurs() {
        when(dishService.deleteDish(anyString())).thenReturn(Mono.error(new RuntimeException()));
        
        Mono<ResponseEntity<Void>> result = dishController.deleteDish("dishId");
        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
                .verifyComplete();
    }
    
}
