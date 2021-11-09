package karol.wlazlo.commons.clients;

import karol.wlazlo.model.AppUserResponse.AppUserResponse;
import karol.wlazlo.model.ChangePassword.ChangePasswordRequest;
import karol.wlazlo.model.CommentItem.CommentRequest;
import karol.wlazlo.model.ProductItem.DeleteProductItemResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import karol.wlazlo.model.Register.RegisterForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordForm;
import karol.wlazlo.model.ResetPassword.ResetPasswordFormAuth;
import karol.wlazlo.model.Response.Response;
import karol.wlazlo.model.Security.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user/reset-password/auth")
    ResponseEntity<Response> resetPasswordAuthenticate(@RequestBody ResetPasswordFormAuth resetPasswordFormAuth);

    @GetMapping("/user/activate")
    ResponseEntity<Response> activateUser(@RequestParam("id") String uuid, @RequestParam("usr") Long userId);

    @PostMapping("/user/reset-password")
    ResponseEntity<Response> resetPassword(@RequestBody ResetPasswordForm resetPasswordForm,
                                           @RequestParam("id") String uuid,
                                           @RequestParam("usr") Long userId);

    @DeleteMapping("/image/delete")
    ResponseEntity<ProductItemResponse> deleteImage(@RequestParam("id") String id, @RequestParam("pId") Long productId);

    @PostMapping("/user/update")
    ResponseEntity<AppUserResponse> updateUser(@RequestBody RegisterForm form);

    @PostMapping("/user/change-password")
    ResponseEntity<Response> changePassword(@RequestBody ChangePasswordRequest request, @RequestParam("username") String username);

    @PostMapping("/user/control")
    AppUser editControl(@RequestParam("type") String type, @RequestParam("uId") Long userId);
}
