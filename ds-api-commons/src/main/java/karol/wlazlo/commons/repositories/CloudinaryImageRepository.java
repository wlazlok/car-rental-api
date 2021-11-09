package karol.wlazlo.commons.repositories;

import karol.wlazlo.model.CloudinaryImageItem.CloudinaryImageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CloudinaryImageRepository extends JpaRepository<CloudinaryImageItem, Long> {

    void deleteByCloudinaryId(String id);
}
