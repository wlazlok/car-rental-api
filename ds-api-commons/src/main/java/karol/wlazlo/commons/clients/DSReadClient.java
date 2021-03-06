package karol.wlazlo.commons.clients;

import karol.wlazlo.model.Admin.Product.ProductsListResponse;
import karol.wlazlo.model.AdminUserResponse.AdminUserResponse;
import karol.wlazlo.model.CardItem.CardItemResponse;
import karol.wlazlo.model.PriceItem.PriceItemResponse;
import karol.wlazlo.model.ProductImages.ProductImagesResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import karol.wlazlo.model.ResetPassword.ResetPasswordFormAuth;
import karol.wlazlo.model.Security.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "DSReadClient", url = "localhost:8910/ds/read")
public interface DSReadClient {

    @GetMapping("/fetch/products")
    ResponseEntity<List<ProductItem>> getItems();

    @GetMapping("/fetch/cards")
    ResponseEntity<CardItemResponse> getCards();

    @GetMapping("/fetch/product/{productId}/details")
    ResponseEntity<ProductItemResponse> fetchProductDetails(@PathVariable("productId") Long productId);

    @GetMapping("/fetch/product/prices")
    ResponseEntity<PriceItemResponse> fetchProductPrices();

    @GetMapping("/fetch/product/images")
    ResponseEntity<ProductImagesResponse> fetchProductImages();

    @GetMapping("/fetch/admin/products")
    ResponseEntity<ProductsListResponse> fetchProducts();

    @GetMapping("/user/{username}")
    AppUser getUserByUsername(@PathVariable("username") String username);

    @PostMapping("/save")
    void saveUser(@RequestBody AppUser user);

    @PostMapping("/user/get/email")
    AppUser getUserByEmail(@RequestBody ResetPasswordFormAuth form);

    @GetMapping("/user/all")
    ResponseEntity<AdminUserResponse> getUsers();

    @GetMapping("/user")
    ResponseEntity<?> getUserById(@RequestParam("uId") Long userId);
}
