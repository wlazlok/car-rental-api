package karol.wlazlo.ds.read.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.model.Admin.Product.ProductsListResponse;
import karol.wlazlo.model.CardItem.CardItemResponse;
import karol.wlazlo.model.PriceItem.PriceItemResponse;
import karol.wlazlo.model.ProductImages.ProductImagesResponse;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static karol.wlazlo.ds.read.MockModel.mockProductItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest extends ServiceMockConfig {

    @InjectMocks
    private ProductService productService;

    @Test
    void getProducts() {
        //given
        List<ProductItem> products = List.of(mockProductItem());

        //when
        when(productItemRepository.findAll()).thenReturn(products);

        //then
        CardItemResponse response = productService.getProducts();

        assertEquals(products.size(), response.getCardItems().size());
        assertNull(response.getErrors());
    }

    @Test
    void getProductsThrowException() {
        //when
        given(productItemRepository.findAll()).willAnswer(inv -> {
            throw new Exception();
        });

        //then
        try {
            productService.getProducts();
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof CarRentalException);
            assertEquals("msg.err.fetch.data.error", ex.getMessage());
        }
    }

    @Test
    void getProductDetails() {
        //given
        ProductItem product = mockProductItem();
        Long productId = 1L;

        //when
        when(productItemRepository.getById(anyLong())).thenReturn(product);

        //then
        ProductItemResponse response = productService.getProductDetails(productId);

        assertNotNull(response.getProduct());
        assertNull(response.getErrors());
        assertEquals(product, response.getProduct());
    }

    @Test
    void getProductDetailsThrowException() {
        //given
        Long productId = 1L;

        //when
        given(productItemRepository.getById(anyLong())).willAnswer(inv -> {
            throw new Exception();
        });

        //then
        try {
            productService.getProductDetails(productId);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof CarRentalException);
            assertEquals("msg.err.fetch.data.error", ex.getMessage());
        }
    }

    @Test
    void getPrices() {
        //given
        List<ProductItem> products = List.of(mockProductItem());

        //when
        when(productItemRepository.findAll()).thenReturn(products);

        //then
        PriceItemResponse response =  productService.getPrices();

        assertEquals(products.size(), response.getProductPrices().size());
        assertNull(response.getErrors());
    }

    @Test
    void getPricesThrowException() {
        //when
        given(productItemRepository.findAll()).willAnswer(inv -> {
            throw new Exception();
        });

        //then
        try {
            productService.getPrices();
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof CarRentalException);
            assertEquals("msg.err.fetch.data.error", ex.getMessage());
        }
    }

    @Test
    void getProductImages() {
        //given
        List<ProductItem> products = List.of(mockProductItem());

        //when
        when(productItemRepository.findAll()).thenReturn(products);

        //then
        ProductImagesResponse response = productService.getProductImages();

        assertEquals(products.size(), response.getImages().size());
        assertNull(response.getErrors());
    }

    @Test
    void getProductImagesThrowException() {
        //when
        given(productItemRepository.findAll()).willAnswer(inv -> {
            throw new Exception();
        });

        //then
        try {
            productService.getProductImages();
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof CarRentalException);
            assertEquals("msg.err.fetch.data.error", ex.getMessage());
        }
    }

    @Test
    void getAdminProducts() {
        //given
        List<ProductItem> products = List.of(mockProductItem());

        //when
        when(productItemRepository.findAll()).thenReturn(products);

        //then
        ProductsListResponse response = productService.getAdminProducts();

        assertEquals(products.size(), response.getProducts().size());
        assertNull(response.getErrors());
    }

    @Test
    void getAdminProductsThrowException() {
        //when
        given(productItemRepository.findAll()).willAnswer(inv -> {
            throw new Exception();
        });

        //then
        try {
            productService.getAdminProducts();
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof CarRentalException);
            assertEquals("msg.err.fetch.data.error", ex.getMessage());
        }
    }
}
