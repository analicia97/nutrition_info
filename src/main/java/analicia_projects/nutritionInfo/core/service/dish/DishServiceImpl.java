package analicia_projects.nutritionInfo.core.service.dish;

import analicia_projects.nutritionInfo.core.model.Dish;
import analicia_projects.nutritionInfo.core.repository.DishRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    
    private final DishRepository dishRepository;
    
    @Override
    public Mono<Dish> addDish(Dish dish) {
        return dishRepository.addDish(dish);
    }
    
}
