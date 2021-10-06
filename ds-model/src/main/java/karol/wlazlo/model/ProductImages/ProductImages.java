package karol.wlazlo.model.ProductImages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImages {

    private Long productId;
    private String productName;
    private List<String> cloudinaryIds;
}
