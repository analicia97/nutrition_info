package analicia_projects.nutritionInfo.data.dish;

import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DishRepositoryImpl implements DishRepository {
    
    private final CrudDishRepository crudDishRepository;
    
    @Override
    public Mono<Dish> getDishById(String id) {
        return crudDishRepository.findById(id).map(DishDocument::toModel);
    }
    
    @Override
    public Mono<Dish> save(Dish dish) {
        return crudDishRepository.save(DishDocument.of(dish)).map(DishDocument::toModel);
    }
    
}

