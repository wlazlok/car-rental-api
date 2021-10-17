package karol.wlazlo.model.ProductItem;

import karol.wlazlo.model.Admin.Product.ProductListItem;
import karol.wlazlo.model.Response.AbstractResponse;
import lombok.*;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProductItemResponse extends AbstractResponse {

    private List<ProductListItem> products;
}
