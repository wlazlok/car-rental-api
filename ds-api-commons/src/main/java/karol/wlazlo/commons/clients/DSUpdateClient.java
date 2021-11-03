package karol.wlazlo.commons.clients;

import karol.wlazlo.model.CommentItem.CommentRequest;
import karol.wlazlo.model.ProductItem.DeleteProductItemResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import karol.wlazlo.model.Register.RegisterForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.Response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "DSUpdateClient", url = "localhost:8911/ds/update")
public interface DSUpdateClient {

    @PostMapping("/comment/add")
    ResponseEntity<ProductItemResponse> addCommentToProduct(@RequestBody CommentRequest commentRequest);

    @PostMapping("/product")
    ResponseEntity<ProductItemResponse> updateProduct(@RequestBody ProductItem productItem);

    @DeleteMapping("/product/{productId}")
    ResponseEntity<DeleteProductItemResponse> deleteProductById(@PathVariable("productId") Long productId);

    @PostMapping("/user/register")
    ResponseEntity<Response> registerUser(@RequestBody RegisterForm registerForm);

    @PostMapping("/user/reset-password")
    ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordForm resetPasswordForm);
}
