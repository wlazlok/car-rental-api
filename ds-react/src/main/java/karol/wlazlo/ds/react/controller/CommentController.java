package karol.wlazlo.ds.react.controller;

import karol.wlazlo.commons.clients.DSUpdateClient;
import karol.wlazlo.model.CommentItem.CommentRequest;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/react")
public class CommentController {

    //todo obsługa błędów

    @Autowired
    private DSUpdateClient dsUpdateClient;

    @PostMapping("/comment/add")
    public ResponseEntity<ProductItemResponse> addComment(@RequestBody CommentRequest commentRequest) {
        ResponseEntity<ProductItemResponse> response = dsUpdateClient.addCommentToProduct(commentRequest);

        return response;
    }
}
