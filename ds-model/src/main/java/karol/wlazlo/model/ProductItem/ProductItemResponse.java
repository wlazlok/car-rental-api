package karol.wlazlo.model.ProductItem;

import karol.wlazlo.model.Response.AbstractResponse;
import lombok.*;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemResponse extends AbstractResponse {

    private ProductItem product;
}
