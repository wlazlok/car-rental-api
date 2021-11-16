package karol.wlazlo.ds.read;

import karol.wlazlo.model.Admin.Product.ProductListItem;
import karol.wlazlo.model.Admin.Product.ProductsListResponse;
import karol.wlazlo.model.AdminUserResponse.AdminUserResponse;
import karol.wlazlo.model.CardItem.CardItem;
import karol.wlazlo.model.CardItem.CardItemResponse;
import karol.wlazlo.model.DetailProductItem.DetailProductItem;
import karol.wlazlo.model.ErrorMessage.ErrorMessage;
import karol.wlazlo.model.PriceItem.PriceItem;
import karol.wlazlo.model.PriceItem.PriceItemResponse;
import karol.wlazlo.model.ProductImages.ProductImages;
import karol.wlazlo.model.ProductImages.ProductImagesResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import karol.wlazlo.model.Security.AppUser;
import karol.wlazlo.model.Security.Role;

import java.util.List;

public class MockModel {

    public static AdminUserResponse mockAdminUserResponse() {
        return AdminUserResponse.builder()
                .users(List.of())
                .build();
    }

    public static CardItem mockCardItem() {
        CardItem cardItem = new CardItem();

        cardItem.setProductId(1L);
        cardItem.setProductName("test");
        cardItem.setCloudinaryMainImageId("123809asdns");

        return cardItem;
    }

    public static CardItemResponse mockCardItemResponse() {
        return CardItemResponse.builder()
                .cardItems(List.of(mockCardItem()))
                .build();
    }

    public static ProductListItem mockProductListItem() {
        ProductListItem productListItem = new ProductListItem();

        productListItem.setProductId(1L);
        productListItem.setProductName("TEST");

        return productListItem;
    }

    public static ProductsListResponse mockProductsListResponse() {
        return ProductsListResponse.builder()
                .products(List.of(mockProductListItem()))
                .build();
    }

    public static ProductImages mockProductImages() {
        ProductImages productImages = new ProductImages();

        productImages.setProductName("test");
        productImages.setProductId(1L);
        productImages.setCloudinaryIds(List.of());

        return productImages;
    }

    public static ProductImagesResponse mockProductImagesResponse() {
        return ProductImagesResponse.builder()
                .images(List.of(mockProductImages()))
                .build();
    }

    public static PriceItem mockPriceItem() {
        PriceItem priceItem = new PriceItem();
        priceItem.setDayPrice(50);
        priceItem.setProductName("test");

        return priceItem;
    }

    public static PriceItemResponse mockPriceItemResponse() {
        return PriceItemResponse.builder()
                .productPrices(List.of(mockPriceItem()))
                .build();
    }

    public static ErrorMessage mockErrorMessage() {
        return ErrorMessage.builder()
                .message("Wystąpił błąd systemu")
                .errorCode("err.code")
                .build();
    }

    public static ProductItemResponse generateProductItemResponse() {
        return ProductItemResponse.builder()
                .product(mockProductItem())
                .build();
    }

    public static AppUser mockAppUser() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setUsername("test");
        user.setName("Name");
        user.setSurname("Surname");
        user.setSurname("Street");
        user.setHouseNumber("45");
        user.setPostalCode("32-123");
        user.setCity("Krakow");
        user.setAvatarUrl("130jadaj139klaczxc");
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setRole(Role.ADMIN);

        return user;
    }

    public static DetailProductItem mockDetailProductItem() {
        DetailProductItem detailProductItem = new DetailProductItem();

        detailProductItem.setId(1L);
        detailProductItem.setProductionYear(2021);
        detailProductItem.setEngine("V6");
        detailProductItem.setPower(500);
        detailProductItem.setDrive("4x4");
        detailProductItem.setGearbox("automatyczna");
        detailProductItem.setDistanceLimitPerDay(300);

        return detailProductItem;
    }

    public static ProductItem mockProductItem() {
        ProductItem productItem = new ProductItem();

        productItem.setId(1L);
        productItem.setProductName("Test product");
        productItem.setCloudinaryIds(List.of());
        productItem.setDayPrice(250);
        productItem.setProductDetails(mockDetailProductItem());
        productItem.setComments(List.of());

        return productItem;
    }
}
