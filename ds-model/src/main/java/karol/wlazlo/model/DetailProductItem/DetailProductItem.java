package karol.wlazlo.model.DetailProductItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import karol.wlazlo.model.ProductItem.ProductItem;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class DetailProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "productDetails")
    @JsonIgnore
    private ProductItem product;

    private Integer productionYear;

    private String engine;

    private Integer power;

    private String drive;

    private String gearbox;

    private Integer distanceLimitPerDay;
}
