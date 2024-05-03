package analicia_projects.nutrition_info.api.controller;


import analicia_projects.nutrition_info.core.NutritionInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    
    private final NutritionInfoService nutritionInfoService;
    
//    @PostMapping
//    public Mono<ResponseEntity<AddNewShiftRequestResponse>> addNewShiftRequest(
//            @RequestBody ShiftRequestRequest shiftRequest) {
//
//        var error = verifyShiftRequestBody(shiftRequest);
//        if (error != null) {
//            return Mono.just(error);
//        }
//
//        return shiftRequestService.createShiftRequest(shiftRequest.sourceId(), shiftRequest.targetId())
//                       .map(ShiftRequestResource::of)
//                       .map(shiftRequestResource -> ResponseEntity.status(HttpStatus.CREATED)
//                                                            .body(new AddNewShiftRequestResponse("ok", shiftRequestResource)))
//                       .onErrorResume(RuntimeException.class, e -> Mono.just(internalErrorAddingNewShiftRequest(e)));
//
//    }
}
