package karol.wlazlo.commons.repositories;

import karol.wlazlo.model.CommentItem.CommentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentItemRepository extends JpaRepository<CommentItem, Long> {
}
