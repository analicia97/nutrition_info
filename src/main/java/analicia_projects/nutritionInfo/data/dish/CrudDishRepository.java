package analicia_projects.nutritionInfo.data.dish;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CrudDishRepository extends ReactiveMongoRepository<DishDocument, String> {
}
