package karol.wlazlo.model.PriceItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PriceItem {

    private Long id;

    private String productName;

    private Integer dayPrice;
}
