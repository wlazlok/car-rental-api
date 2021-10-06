package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.commons.repositories.CommentItemRepository;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.model.CommentItem.CommentItem;
import karol.wlazlo.model.CommentItem.CommentRequest;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/ds/update")
public class CommentController {

    //todo obsługa błędów, przeniesc do serwisów

    @Autowired
    private CommentItemRepository commentItemRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @PostMapping("/comment/add")
    public ResponseEntity<ProductItemResponse> addCommentToProduct(@RequestBody CommentRequest commentRequest) {

        ProductItem productItem = productItemRepository.getById(commentRequest.getProductId());

        CommentItem commentItem = new CommentItem();

        commentItem.setProductId(productItem);
        commentItem.setMessage(commentRequest.getMessage());
        commentItem.setDate(new Date());

        commentItemRepository.save(commentItem);

        ProductItemResponse productItemResponse = ProductItemResponse.builder()
                .product(productItem)
                .build();

        return ResponseEntity.ok(productItemResponse);
    }

}
