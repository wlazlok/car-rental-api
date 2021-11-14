package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.commons.repositories.DetailProductItemRepository;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.ds.update.services.ProductService;
import karol.wlazlo.model.ProductItem.DeleteProductItemResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static karol.wlazlo.commons.utils.HandleErrorMessage.mapErrorMessage;

@RestController
@RequestMapping("/ds/update")
public class ProductController {

    //todo exception handler BAD_REQUEST

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private DetailProductItemRepository detailProductItemRepository;

    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<ProductItemResponse> updateProduct(@RequestBody ProductItem productItem) {
        ProductItemResponse response = new ProductItemResponse();

        try {
            response = productService.updateProduct(productItem);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<DeleteProductItemResponse> deleteProductById(@PathVariable("productId") Long productId) {

        DeleteProductItemResponse response = new DeleteProductItemResponse();

        try {
            response = productService.deleteProduct(productId);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
