package karol.wlazlo.ds.react.controller;

import karol.wlazlo.commons.clients.DSUpdateClient;
import karol.wlazlo.commons.exceptions.UserContextException;
import karol.wlazlo.ds.react.services.UserContextService;
import karol.wlazlo.model.CommentItem.CommentRequest;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/react/comment")
public class CommentController {

    @Autowired
    private DSUpdateClient dsUpdateClient;

    @Autowired
    private UserContextService userContextService;

    @PostMapping("/add")
    public ResponseEntity<ProductItemResponse> addComment(@RequestBody CommentRequest commentRequest) throws UserContextException {
        log.info("api.react.comment.add.comment productId {} user {}, message {}", commentRequest.getProductId(), commentRequest.getUser().getId(), commentRequest.getMessage());
        commentRequest.setUser(userContextService.getUserForContext());

        ProductItemResponse response = dsUpdateClient.addCommentToProduct(commentRequest).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
