package analicia_projects.nutritionInfo.api.controller.swagger;

import analicia_projects.nutritionInfo.api.controller.resource.DishResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Tag(name = "Dish", description = "The dish controller.")
public interface DishInterface {
    
    @Operation(
            summary = "Add a new dish",
            description = "Add a new dish",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Dish added",
                            content = @Content(schema = @Schema(implementation = DishResource.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )
    Mono<ResponseEntity<DishResource>> addDish(@RequestBody DishResource dish);
    
    @Operation(
            summary = "Get a dish by id",
            description = "Get a dish by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dish retrieved",
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
    Mono<ResponseEntity<DishResource>> getDish(@PathVariable String dishId);
    
    @Operation(
            summary = "Update a dish",
            description = "Update a dish",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dish updated",
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
    Mono<ResponseEntity<DishResource>> updateDish(@RequestBody DishResource dish);
    
    @Operation(
            summary = "Delete a dish",
            description = "Delete a dish by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dish deleted"
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
    Mono<ResponseEntity<Void>> deleteDish(@PathVariable String dishId);
    
}
