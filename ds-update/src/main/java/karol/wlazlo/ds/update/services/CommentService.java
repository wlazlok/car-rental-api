package karol.wlazlo.ds.update.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.commons.repositories.CommentItemRepository;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.model.CommentItem.CommentItem;
import karol.wlazlo.model.CommentItem.CommentRequest;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentItemRepository commentItemRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    public ProductItemResponse addComment(CommentRequest request) {
        ProductItemResponse response;
        try {
            ProductItem product = productItemRepository.getById(request.getProductId());
            CommentItem commentItem = new CommentItem();

            commentItem.setProductId(product);
            commentItem.setMessage(request.getMessage());
            commentItem.setDate(new Date());
            commentItem.setUserId(request.getUser());
            commentItem.setAuthor(String.format("%s %s", request.getUser().getName(), request.getUser().getSurname()));

            commentItemRepository.save(commentItem);

             response = ProductItemResponse.builder()
                    .product(product)
                    .build();

            return response;
        } catch ( Exception ex) {
            log.warn("Error during adding new comment");
            throw new CarRentalException("msg.err.during.adding.comment");
        }
    }
}
