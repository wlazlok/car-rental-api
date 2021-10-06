package karol.wlazlo.model.ProductItem;

import karol.wlazlo.model.CloudinaryImageItem.CloudinaryImageItem;
import karol.wlazlo.model.CommentItem.CommentItem;
import karol.wlazlo.model.DetailProductItem.DetailProductItem;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productName;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CloudinaryImageItem> cloudinaryIds;

    private Integer dayPrice;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_details_id")
    private DetailProductItem productDetails;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CommentItem> comments;
}
