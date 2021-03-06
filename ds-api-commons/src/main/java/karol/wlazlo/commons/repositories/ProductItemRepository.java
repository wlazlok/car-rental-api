package karol.wlazlo.commons.repositories;

import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.Security.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
}
