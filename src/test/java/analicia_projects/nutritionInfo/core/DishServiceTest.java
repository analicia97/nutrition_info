package analicia_projects.nutritionInfo.core;

import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.repository.DishRepository;
import analicia_projects.nutritionInfo.core.service.dish.DishServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {
    
    @Mock
    private DishRepository dishRepository;
    
    @InjectMocks
    private DishServiceImpl dishService;
    
    
    @Test
    public void getDishByIdReturnsDishWhenDishExists() {
        Dish expectedDish = new Dish("dishId", "Dish Name", "Dish Description", "20.58", new ArrayList<>(), true, true);
        when(dishRepository.getDishById(anyString())).thenReturn(Mono.just(expectedDish));
        
        Mono<Dish> result = dishRepository.getDishById("dishId");
        
        StepVerifier.create(result)
                .expectNext(expectedDish)
                .verifyComplete();
    }
    
    @Test
    public void addDishReturnsAddedDish() {
        Dish dishToAdd = new Dish("dishId", "Dish Name", "Dish Description", "20.58", new ArrayList<>(), true, true);
        when(dishRepository.save(any(Dish.class))).thenReturn(Mono.just(dishToAdd));
        
        Mono<Dish> result = dishService.addDish(dishToAdd);
        
        StepVerifier.create(result)
                .expectNext(dishToAdd)
                .verifyComplete();
    }
    
    @Test
    public void updateDishReturnsUpdatedDishWhenDishExists() {
        Dish existingDish = new Dish("dishId", "Dish Name", "Dish Description", "20.58", new ArrayList<>(), true, true);
        Dish updatedDish = new Dish("dishId2", "Dish Name", "Dish Description", "20.58", new ArrayList<>(), true, true);
        
        when(dishRepository.getDishById(anyString())).thenReturn(Mono.just(existingDish));
        when(dishRepository.save(any(Dish.class))).thenReturn(Mono.just(updatedDish));
        
        Mono<Dish> result = dishService.updateDish(updatedDish);
        
        StepVerifier.create(result)
                .expectNext(updatedDish)
                .verifyComplete();
    }
    
    @Test
    public void updateDishThrowsExceptionWhenDishDoesNotExist() {
        Dish nonExistentDish = new Dish("dishId", "Dish Name", "Dish Description", "20.58", new ArrayList<>(), true, true);
        when(dishRepository.getDishById(anyString())).thenReturn(Mono.empty());
        
        Mono<Dish> result = dishService.updateDish(nonExistentDish);
        
        StepVerifier.create(result)
                .expectError(NoSuchElementException.class)
                .verify();
    }
    
    @Test
    public void deleteDishCompletesWithoutErrorWhenDishExists() {
        Dish existingDish = new Dish("dishId", "Dish Name", "Dish Description", "20.58", new ArrayList<>(), true, true);
        when(dishRepository.getDishById(anyString())).thenReturn(Mono.just(existingDish));
        when(dishRepository.delete(any(Dish.class))).thenReturn(Mono.empty());
        
        Mono<Void> result = dishService.deleteDish("dishId");
        
        StepVerifier.create(result)
                .verifyComplete();
    }
    
    @Test
    public void deleteDishThrowsExceptionWhenDishDoesNotExist() {
        when(dishRepository.getDishById(anyString())).thenReturn(Mono.empty());
        
        Mono<Void> result = dishService.deleteDish("nonExistentDishId");
        
        StepVerifier.create(result)
                .expectError(NoSuchElementException.class)
                .verify();
    }
}
