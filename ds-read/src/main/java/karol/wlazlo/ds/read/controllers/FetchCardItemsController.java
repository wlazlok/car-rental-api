package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.model.CardItem.CardItem;
import karol.wlazlo.model.CardItem.CardItemResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ds/read")
public class FetchCardItemsController {

    @Autowired
    private ProductItemRepository productItemRepository;

    @GetMapping("/fetch/cards")
    public ResponseEntity<CardItemResponse> getCards() {

        CardItemResponse cardItemResponse = new CardItemResponse();
        List<ProductItem> products = productItemRepository.findAll();

        if (products.size() <= 0) {
            return ResponseEntity.ok(cardItemResponse);
        }

        cardItemResponse.setCardItems(
                products
                        .stream()
                        .map(item -> CardItem.builder()
                                .productId(item.getId())
                                .productName(item.getProductName())
                                .cloudinaryMainImageId(item.getCloudinaryIds().get(0).getCloudinaryId())
                                .build())
                        .collect(Collectors.toList()));


        return ResponseEntity.ok(cardItemResponse);
    }
}
