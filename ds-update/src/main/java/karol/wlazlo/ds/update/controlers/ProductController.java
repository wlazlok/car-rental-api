package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.commons.repositories.DetailProductItemRepository;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ds/update")
public class ProductController {

    //todo obsługa błędów, przeniesc do serwisów
    //todo exception handler
    //todo dodac logi wszedzie!

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
}
