package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.model.Admin.Product.ProductListItem;
import karol.wlazlo.model.Admin.Product.ProductsListResponse;
import karol.wlazlo.model.CloudinaryImageItem.CloudinaryImageItem;
import karol.wlazlo.model.PriceItem.PriceItem;
import karol.wlazlo.model.PriceItem.PriceItemResponse;
import karol.wlazlo.model.ProductImages.ProductImages;
import karol.wlazlo.model.ProductImages.ProductImagesResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ds/read")
public class FetchProductItemController {

    //todo obsługa błędów

    @Autowired
    private ProductItemRepository productItemRepository;

    @Deprecated
    @GetMapping("/fetch/products")
    public ResponseEntity<List<ProductItem>> getItems() {
        return ResponseEntity.ok(productItemRepository.findAll());
    }

    @GetMapping("/fetch/product/{productId}/details")
    public ResponseEntity<ProductItemResponse> fetchProductDetails(@PathVariable Long productId) {

        ProductItemResponse productItemResponse = ProductItemResponse.builder()
                .product(productItemRepository.getById(productId))
                .build();

        return ResponseEntity.ok(productItemResponse);
    }

    @GetMapping("/fetch/product/prices")
    public ResponseEntity<PriceItemResponse> fetchProductPrices() {

        List<PriceItem> prices = productItemRepository.findAll().stream()
                .map(item -> PriceItem.builder()
                        .id(item.getId())
                        .productName(item.getProductName())
                        .dayPrice(item.getDayPrice())
                        .build()).collect(Collectors.toList());

        PriceItemResponse response = PriceItemResponse.builder()
                .productPrices(prices)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetch/product/images")
    public ResponseEntity<ProductImagesResponse> fetchProductImages() {

        List<ProductImages> images = productItemRepository.findAll().stream()
                .map(item -> ProductImages.builder()
                        .productId(item.getId())
                        .productName(item.getProductName())
                        .cloudinaryIds(
                                item.getCloudinaryIds().stream()
                                        .map(CloudinaryImageItem::getCloudinaryId).
                                        collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        ProductImagesResponse response = ProductImagesResponse.builder()
                .images(images)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetch/admin/products")
    public ResponseEntity<ProductsListResponse> fetchProducts() {
        List<ProductListItem> products = productItemRepository.findAll().stream()
                .map(item -> ProductListItem.builder()
                        .productId(item.getId())
                        .productName(item.getProductName())
                        .build())
                .collect(Collectors.toList());

        ProductsListResponse response = ProductsListResponse.builder()
                .products(products)
                .build();

        return ResponseEntity.ok(response);
    }

}
