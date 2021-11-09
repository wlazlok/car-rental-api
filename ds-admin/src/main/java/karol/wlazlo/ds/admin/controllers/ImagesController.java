package karol.wlazlo.ds.admin.controllers;

import karol.wlazlo.commons.clients.DSUpdateClient;
import karol.wlazlo.commons.services.UploadImageService;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/admin/images")
public class ImagesController {

    @Autowired
    private DSUpdateClient dsUpdateClient;

    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping(value = "/upload/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductItemResponse> uploadImage(@PathVariable("productId") Long productId,
                                                           HttpServletRequest servletRequest) throws ServletException, IOException {

        //todo obsługa błędów

        ProductItemResponse response = uploadImageService.upload(servletRequest, productId);

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ProductItemResponse> deleteImage(@RequestParam("id") String id, @RequestParam("pId") Long productId) {
        ProductItemResponse response = dsUpdateClient.deleteImage(id, productId).getBody();

        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
