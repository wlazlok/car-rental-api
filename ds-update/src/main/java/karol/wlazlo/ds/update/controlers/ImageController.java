package karol.wlazlo.ds.update.controlers;

import karol.wlazlo.ds.update.services.ImageService;
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
    private ImageService imageService;

    @PostMapping(value = "/upload/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductItemResponse> uploadImage(HttpServletRequest request, @PathVariable("productId") Long productId) {

        ProductItemResponse response = new ProductItemResponse();

        try {
            response = imageService.uploadImage(request, productId);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ProductItemResponse> deleteImage(@RequestParam("id") String id, @RequestParam("pId") Long productId) {

        ProductItemResponse response = new ProductItemResponse();

        try {
            response = imageService.deleteImage(id, productId);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
