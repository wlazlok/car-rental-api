package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.commons.repositories.CloudinaryImageRepository;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.ds.update.services.CloudinaryService;
import karol.wlazlo.model.CloudinaryImageItem.CloudinaryImageItem;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static karol.wlazlo.commons.utils.HandleErrorMessage.mapErrorMessage;

@Slf4j
@RestController
@RequestMapping("/ds/update/image")
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ProductItemRepository productRepository;

    @Autowired
    private CloudinaryImageRepository cloudinaryImageRepository;

    @PostMapping(value = "/upload/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductItemResponse> uploadImage(HttpServletRequest request, @PathVariable("productId") Long productId) {

        ProductItemResponse response = new ProductItemResponse();

        try {
            String id = cloudinaryService.upload(request.getPart("file").getInputStream());

            ProductItem product = productRepository.getById(productId);

            CloudinaryImageItem imageItem = new CloudinaryImageItem();
            imageItem.setProductId(product);
            imageItem.setCloudinaryId(id);

            product.getCloudinaryIds().add(imageItem);

            cloudinaryImageRepository.save(imageItem);
            response.setProduct(productRepository.getById(productId));

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ProductItemResponse> deleteImage(@RequestParam("id") String id, @RequestParam("pId") Long productId) {

        ProductItemResponse response = new ProductItemResponse();

        try {
            cloudinaryService.delete(id);
            cloudinaryImageRepository.deleteByCloudinaryId(id);

            response.setProduct(productRepository.getById(productId));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
