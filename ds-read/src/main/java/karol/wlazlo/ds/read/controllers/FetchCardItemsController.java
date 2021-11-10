package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.ds.read.services.ProductService;
import karol.wlazlo.model.CardItem.CardItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static karol.wlazlo.commons.utils.HandleErrorMessage.mapErrorMessage;

@RestController
@RequestMapping("/ds/read")
public class FetchCardItemsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/fetch/cards")
    public ResponseEntity<CardItemResponse> getCards() {

        CardItemResponse response = new CardItemResponse();

        try {
            response = productService.getProducts();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception ex) {
            response.setErrors(List.of(mapErrorMessage(ex)));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
