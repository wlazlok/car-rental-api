package karol.wlazlo.ds.update.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.commons.repositories.CloudinaryImageRepository;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.model.CloudinaryImageItem.CloudinaryImageItem;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static karol.wlazlo.commons.utils.HandleErrorMessage.mapErrorMessage;

@Slf4j
@Service
public class ImageService {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private CloudinaryImageRepository cloudinaryImageRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    public ProductItemResponse uploadImage(HttpServletRequest request, Long productId) {

        ProductItemResponse response = new ProductItemResponse();

        try {
            String id = cloudinaryService.upload(request.getPart("file").getInputStream());

            ProductItem product = productItemRepository.getById(productId);

            CloudinaryImageItem imageItem = new CloudinaryImageItem();
            imageItem.setProductId(product);
            imageItem.setCloudinaryId(id);

            product.getCloudinaryIds().add(imageItem);

            cloudinaryImageRepository.save(imageItem);
            response.setProduct(productItemRepository.getById(productId));

            return response;
        } catch (CarRentalException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("image.service.upload.image.err {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.upload.image");
        }
    }

    public ProductItemResponse deleteImage(String id, Long productId) {
        ProductItemResponse response = new ProductItemResponse();

        try {
            cloudinaryService.delete(id);
            cloudinaryImageRepository.deleteByCloudinaryId(id);

            response.setProduct(productItemRepository.getById(productId));

            return response;
        } catch (Exception ex) {
           ex.printStackTrace();
           log.warn("image.service.delete.image.err {}", ex.getLocalizedMessage());
           throw new CarRentalException("msg.err.delete.image");
        }
    }
}
