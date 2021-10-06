package karol.wlazlo.model.CloudinaryImageItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import karol.wlazlo.model.ProductItem.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryImageItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIgnore
    private ProductItem productId;

    private String cloudinaryId;
}
