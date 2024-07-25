package analicia_projects.nutritionInfo.api.controller.swagger;

import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import analicia_projects.nutritionInfo.api.controller.resource.IngredientResource;
import analicia_projects.nutritionInfo.core.model.Ingredient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Ingredient", description = "The ingredient controller")
public interface IngredientInterface {
    
    @Operation(
            summary = "Get all ingredients for a dish",
            description = "Get all ingredients for a dish by dish id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ingredients retrieved",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = IngredientResource.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Dish not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    Mono<ResponseEntity<List<IngredientResource>>> getIngredients(@PathVariable String dishId);
    
    @Operation(
            summary = "Add a new ingredient to a dish",
            description = "Add a new ingredient to a dish by dish id",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Ingredient added to dish",
                            content = @Content(schema = @Schema(implementation = DishResource.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Dish not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    Mono<ResponseEntity<DishResource>> addNewIngredientToDish(
            @PathVariable String dishId,
            @RequestBody IngredientResource ingredient);
    
}
