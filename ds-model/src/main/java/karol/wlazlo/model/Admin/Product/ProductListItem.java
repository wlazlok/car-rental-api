package karol.wlazlo.model.Admin.Product;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListItem {

    private Long productId;
    private String productName;
}
