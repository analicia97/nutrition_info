package analicia_projects.nutritionInfo.core.service.nutritionInfo;

import analicia_projects.nutritionInfo.core.model.Dish;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@NoArgsConstructor
public class DishServiceImpl implements DishService {
    
    @Override
    public Mono<Dish> addDish(Dish dish) {
        return null;
    }
}
