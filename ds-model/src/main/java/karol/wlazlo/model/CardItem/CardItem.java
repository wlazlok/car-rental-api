package karol.wlazlo.model.CardItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardItem {

    private Long productId;

    private String productName;

    private String cloudinaryMainImageId;

}
