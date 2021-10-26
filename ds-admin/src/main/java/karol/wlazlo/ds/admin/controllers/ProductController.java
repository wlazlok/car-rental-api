package karol.wlazlo.ds.admin.controllers;

import karol.wlazlo.commons.clients.DSReadClient;
import karol.wlazlo.commons.clients.DSUpdateClient;
import karol.wlazlo.model.Admin.Product.ProductsListResponse;
import karol.wlazlo.model.ProductItem.DeleteProductItemResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/products")
public class ProductController {

    //todo obsługa błędów

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
        return dsUpdateClient.updateProduct(productItem);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<DeleteProductItemResponse> deleteProduct(@PathVariable("productId") Long productId) {
        log.info("admin.ProductController delete product by ID: {}", productId);
        return dsUpdateClient.deleteProductById(productId);
    }
}
