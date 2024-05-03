package analicia_projects.nutritionInfo.data.nutritionInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(staticName = "empty")
@AllArgsConstructor(staticName = "of")
@Document("Nutrition_Info")
public class NutritionInfoDocument {
    private Double carbohydrates;
    private Double simpleCarbohydrates;
    private Double complexCarbohydrates;
    private Double fats;
    private Double saturatedFats;
    private Double unsaturatedFats;
    private Double proteins;
}
