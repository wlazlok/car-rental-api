package karol.wlazlo.commons.repositories;

import karol.wlazlo.model.DetailProductItem.DetailProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailProductItemRepository extends JpaRepository<DetailProductItem, Long> {
}
