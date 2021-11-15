package karol.wlazlo.ds.react.controller;

import karol.wlazlo.commons.clients.DSReadClient;
import karol.wlazlo.model.CardItem.CardItemResponse;
import karol.wlazlo.model.PriceItem.PriceItemResponse;
import karol.wlazlo.model.ProductImages.ProductImagesResponse;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/react")
public class ProductController {

    @Autowired
    private DSReadClient dsReadClient;

    @GetMapping("/products")
    public ResponseEntity<CardItemResponse> get() {
        return dsReadClient.getCards();
    }

    @GetMapping("/product/{productId}/details")
    public ResponseEntity<ProductItemResponse> getProductDetails(@PathVariable Long productId) {
        log.info("api.react.product.details productId {}", productId);
        return dsReadClient.fetchProductDetails(productId);
    }

    @GetMapping("/product/prices")
    public ResponseEntity<PriceItemResponse> getProductPrices() {
        return dsReadClient.fetchProductPrices();
    }

    @GetMapping("/products/images")
    public ResponseEntity<ProductImagesResponse> getProductsImages() {
        return dsReadClient.fetchProductImages();
    }
}
