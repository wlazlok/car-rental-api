package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.commons.repositories.DetailProductItemRepository;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.model.Admin.Product.ProductListItem;
import karol.wlazlo.model.ProductItem.DeleteProductItemResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ds/update")
public class ProductController {

    //todo obsługa błędów, przeniesc do serwisów
    //todo exception handler
    //todo dodac logi wszedzie!
    //todo resourceBundle wszędzie

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private DetailProductItemRepository detailProductItemRepository;

    @PostMapping("/product")
    public ResponseEntity<ProductItemResponse> updateProduct(@RequestBody ProductItem productItem) {
        productItemRepository.save(productItem);
        detailProductItemRepository.save(productItem.getProductDetails());

        ProductItemResponse response = ProductItemResponse.builder()
                .product(productItemRepository.getById(productItem.getId()))
                .build();

        response.setSuccessMessage("Pomyślnie zaktualizowano dane");

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<DeleteProductItemResponse> deleteProductById(@PathVariable("productId") Long productId) {
        productItemRepository.deleteById(productId);

        List<ProductListItem> products = productItemRepository.findAll().stream()
                .map(item -> ProductListItem.builder()
                        .productId(item.getId())
                        .productName(item.getProductName())
                        .build())
                .collect(Collectors.toList());

        DeleteProductItemResponse response = DeleteProductItemResponse.builder()
                .products(products)
                .build();

        response.setSuccessMessage("Pomyślnie usunięto dane");

        return ResponseEntity.ok(response);
    }
}
