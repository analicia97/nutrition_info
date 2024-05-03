package analicia_projects.nutrition_info.core.service.nutrition_info;

import analicia_projects.nutrition_info.core.model.Dish;
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
