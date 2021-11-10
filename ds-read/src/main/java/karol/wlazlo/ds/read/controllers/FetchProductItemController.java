package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.ds.read.services.ProductService;
import karol.wlazlo.model.Admin.Product.ProductsListResponse;
import karol.wlazlo.model.PriceItem.PriceItemResponse;
import karol.wlazlo.model.ProductImages.ProductImagesResponse;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static karol.wlazlo.commons.utils.HandleErrorMessage.mapErrorMessage;

@RestController
@RequestMapping("/ds/read")
public class FetchProductItemController {

    @Autowired
    private ProductService productService;

    @GetMapping("/fetch/product/{productId}/details")
    public ResponseEntity<ProductItemResponse> fetchProductDetails(@PathVariable Long productId) {
        ProductItemResponse response = new ProductItemResponse();

        try {
            response = productService.getProductDetails(productId);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/fetch/product/prices")
    public ResponseEntity<PriceItemResponse> fetchProductPrices() {
        PriceItemResponse response = new PriceItemResponse();

        try {
            response = productService.getPrices();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/fetch/product/images")
    public ResponseEntity<ProductImagesResponse> fetchProductImages() {
        ProductImagesResponse response = new ProductImagesResponse();

        try {
            response = productService.getProductImages();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/fetch/admin/products")
    public ResponseEntity<ProductsListResponse> fetchProducts() {
        ProductsListResponse response = new ProductsListResponse();

        try {
            response = productService.getAdminProducts();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
