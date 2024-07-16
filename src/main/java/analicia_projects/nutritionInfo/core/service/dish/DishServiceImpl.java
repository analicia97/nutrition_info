package analicia_projects.nutritionInfo.core.service.dish;


import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.NoSuchElementException;


@Service
@Slf4j
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    
    private final DishRepository dishRepository;
    
    @Override
    public Mono<Dish> getDishById(String id) {
        return dishRepository.getDishById(id);
    }
    
    @Override
    public Mono<Dish> addDish(Dish dish) {
        dish.setVegan(Dish.isVegan(dish));
        dish.setNutritionallyBalanced(Dish.isNutritionallyBalanced(dish));
        return dishRepository.save(dish);
    }
    
    @Override
    public Mono<Dish> updateDish(Dish dish) {
        return getDishById(dish.getId())
                       .switchIfEmpty(Mono.error(new NoSuchElementException("Dish not found with id: " + dish.getId())))
                       .flatMap(existingDish -> {
                           if (existingDish.equals(dish)) {
                               return Mono.just(existingDish)
                                              .doOnSuccess(retrievedDish -> log.info("Dish with id {} and name {} was already updated",
                                                      retrievedDish.getId(), retrievedDish.getName()));
                           } else {
                               dish.setVegan(Dish.isVegan(dish));
                               dish.setNutritionallyBalanced(Dish.isNutritionallyBalanced(dish));
                               return dishRepository.save(dish)
                                              .doOnSuccess(updatedDish -> log.info("Dish with id {} and name {} updated",
                                                      updatedDish.getId(), updatedDish.getName()));
                           }
                       });
    }
    
    @Override
    public Mono<Void> deleteDish(String id) {
        return dishRepository.getDishById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Dish not found with id: " + id)))
                .flatMap(dishRepository::delete);
    }
    
}
