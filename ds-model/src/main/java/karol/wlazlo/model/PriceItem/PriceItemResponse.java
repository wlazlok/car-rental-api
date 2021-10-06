package karol.wlazlo.model.PriceItem;

import karol.wlazlo.model.Response.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PriceItemResponse extends AbstractResponse {

    private List<PriceItem> productPrices;
}
