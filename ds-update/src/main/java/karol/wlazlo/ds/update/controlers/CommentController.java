package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.ds.update.services.CommentService;
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

import java.util.List;

import static karol.wlazlo.commons.utils.HandleErrorMessage.mapErrorMessage;

@Slf4j
@RestController
@RequestMapping("/ds/update")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/add")
    public ResponseEntity<ProductItemResponse> addCommentToProduct(@RequestBody CommentRequest commentRequest) {

        ProductItemResponse response = new ProductItemResponse();

        try {
            response = commentService.addComment(commentRequest);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            response.setErrors(List.of(mapErrorMessage(ex)));

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
