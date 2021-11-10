package karol.wlazlo.ds.read.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import karol.wlazlo.model.Admin.Product.ProductListItem;
import karol.wlazlo.model.Admin.Product.ProductsListResponse;
import karol.wlazlo.model.CardItem.CardItem;
import karol.wlazlo.model.CardItem.CardItemResponse;
import karol.wlazlo.model.CloudinaryImageItem.CloudinaryImageItem;
import karol.wlazlo.model.PriceItem.PriceItem;
import karol.wlazlo.model.PriceItem.PriceItemResponse;
import karol.wlazlo.model.ProductImages.ProductImages;
import karol.wlazlo.model.ProductImages.ProductImagesResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductItemRepository productItemRepository;

    public CardItemResponse getProducts() {

        CardItemResponse cardItemResponse = new CardItemResponse();
        try {
            List<ProductItem> products = productItemRepository.findAll();

            cardItemResponse.setCardItems(
                    products
                            .stream()
                            .map(item -> CardItem.builder()
                                    .productId(item.getId())
                                    .productName(item.getProductName())
                                    .cloudinaryMainImageId(!item.getCloudinaryIds().isEmpty() ? item.getCloudinaryIds().get(0).getCloudinaryId() : null)
                                    .build())
                            .collect(Collectors.toList()));

            return cardItemResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("product.service.get.products.err {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.fetch.data.error");
        }
    }

    public ProductItemResponse getProductDetails(Long productId) {

        try {
            return ProductItemResponse.builder()
                    .product(productItemRepository.getById(productId))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("product.service.get.product.details.err {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.fetch.data.error");
        }
    }

    public PriceItemResponse getPrices() {
        try {
            return PriceItemResponse
                    .builder()
                    .productPrices(productItemRepository.findAll().stream()
                            .map(item -> PriceItem.builder()
                                    .id(item.getId())
                                    .productName(item.getProductName())
                                    .dayPrice(item.getDayPrice())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("product.service.get.product.prices {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.fetch.data.error");
        }
    }

    public ProductImagesResponse getProductImages() {
        try {
            return ProductImagesResponse
                    .builder()
                    .images(productItemRepository.findAll().stream()
                            .map(item -> ProductImages.builder()
                                    .productId(item.getId())
                                    .productName(item.getProductName())
                                    .cloudinaryIds(
                                            item.getCloudinaryIds().stream()
                                                    .map(CloudinaryImageItem::getCloudinaryId).
                                                    collect(Collectors.toList()))
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("product.service.get.product.images {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.fetch.data.error");
        }
    }

    public ProductsListResponse getAdminProducts() {
        try {
            return ProductsListResponse
                    .builder()
                    .products(productItemRepository.findAll().stream()
                            .map(item -> ProductListItem.builder()
                                    .productId(item.getId())
                                    .productName(item.getProductName())
                                    .build())
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("product.service.get.product.admin {}", ex.getLocalizedMessage());
            throw new CarRentalException("msg.err.fetch.data.error");
        }
    }
}
