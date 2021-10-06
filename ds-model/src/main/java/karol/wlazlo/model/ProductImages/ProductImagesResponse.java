package karol.wlazlo.model.ProductImages;

import karol.wlazlo.model.Response.AbstractResponse;
import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductImagesResponse extends AbstractResponse {

    private List<ProductImages> images;
}
