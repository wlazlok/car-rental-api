package karol.wlazlo.ds.admin.controllers;

import karol.wlazlo.commons.clients.DSReadClient;
import karol.wlazlo.commons.clients.DSUpdateClient;
import karol.wlazlo.model.Admin.Product.ProductsListResponse;
import karol.wlazlo.model.ProductItem.DeleteProductItemResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private DSReadClient dsReadClient;

    @Autowired
    private DSUpdateClient dsUpdateClient;

    @GetMapping
    public ResponseEntity<ProductsListResponse> fetchProducts() {
        return dsReadClient.fetchProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductItemResponse> fetchProductById(@PathVariable Long productId) {
        log.info("admin.ProductController get product by ID: {}", productId);
        return dsReadClient.fetchProductDetails(productId);
    }

    @PostMapping("/update")
    public ResponseEntity<ProductItemResponse> updateProduct(@RequestBody ProductItem productItem) {
        log.info("admin.ProductController update/add product with ID: {}", productItem.getId() != null ? productItem.getId() : "new");
        ProductItemResponse response = dsUpdateClient.updateProduct(productItem).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<DeleteProductItemResponse> deleteProduct(@PathVariable("productId") Long productId) {
        log.info("admin.ProductController delete product by ID: {}", productId);
        DeleteProductItemResponse response = dsUpdateClient.deleteProductById(productId).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
