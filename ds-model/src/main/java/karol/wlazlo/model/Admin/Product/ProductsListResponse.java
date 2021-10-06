package karol.wlazlo.model.Admin.Product;

import karol.wlazlo.model.Response.AbstractResponse;
import lombok.*;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductsListResponse extends AbstractResponse {

    private List<ProductListItem> products;
}
