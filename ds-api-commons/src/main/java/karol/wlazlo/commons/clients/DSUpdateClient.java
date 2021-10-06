package karol.wlazlo.commons.clients;

import karol.wlazlo.model.CommentItem.CommentRequest;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "DSUpdateClient", url = "localhost:8911/ds/update")
public interface DSUpdateClient {

    @PostMapping("/comment/add")
    ResponseEntity<ProductItemResponse> addCommentToProduct(@RequestBody CommentRequest commentRequest);

    @PostMapping("/product")
    ResponseEntity<ProductItemResponse> updateProduct(@RequestBody ProductItem productItem);
}
