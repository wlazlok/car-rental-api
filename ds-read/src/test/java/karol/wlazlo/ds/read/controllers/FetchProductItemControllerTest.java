package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.model.Admin.Product.ProductsListResponse;
import karol.wlazlo.model.PriceItem.PriceItemResponse;
import karol.wlazlo.model.ProductImages.ProductImagesResponse;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static karol.wlazlo.ds.read.MockModel.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class FetchProductItemControllerTest extends ControllerMockConfig {

    private final String PATH = "/ds/read/fetch/product";

    private MockMvc mockMvc;

    @InjectMocks
    private FetchProductItemController itemController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void fetchProductDetails() throws Exception {
        //given
        ProductItemResponse productItemResponse = generateProductItemResponse();
        Long productId = 1L;

        //when
        when(productService.getProductDetails(anyLong())).thenReturn(productItemResponse);

        //then
        MvcResult result = mockMvc.perform(get(PATH + "/1/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", String.valueOf(productId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ProductItemResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ProductItemResponse.class);

        assertNotNull(response);
        assertNull(response.getErrors());
        verify(productService, times(1)).getProductDetails(anyLong());
    }

    @Test
    void fetchProductDetailsBadRequest() throws Exception {
        //given
        ProductItemResponse productItemResponse = generateProductItemResponse();
        productItemResponse.setErrors(List.of(mockErrorMessage()));
        Long productId = 1L;

        //when
        given(productService.getProductDetails(anyLong())).willAnswer(inv -> {
            throw new CarRentalException("err");
        });

        //then
        MvcResult result = mockMvc.perform(get(PATH + "/1/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", String.valueOf(productId)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ProductItemResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ProductItemResponse.class);

        assertNotNull(response);
        assertNotNull(response.getErrors());
        verify(productService, times(1)).getProductDetails(anyLong());
    }

    @Test
    void fetchProductPrices() throws Exception {
        //given
        PriceItemResponse priceItemResponse = mockPriceItemResponse();

        //when
        when(productService.getPrices()).thenReturn(priceItemResponse);

        //then
        MvcResult mvcResult = mockMvc.perform(get(PATH + "/prices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        PriceItemResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PriceItemResponse.class);

        assertNotNull(response);
        assertNull(response.getErrors());
        verify(productService, times(1)).getPrices();
    }

    @Test
    void fetchProductPricesBadRequest() throws Exception {
        //given
        PriceItemResponse priceItemResponse = mockPriceItemResponse();
        priceItemResponse.setErrors(List.of(mockErrorMessage()));

        //when
        given(productService.getPrices()).willAnswer(onv -> {
            throw new CarRentalException("err");
        });

        //then
        MvcResult mvcResult = mockMvc.perform(get(PATH + "/prices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        PriceItemResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PriceItemResponse.class);

        assertNotNull(response);
        assertNotNull(response.getErrors());
        verify(productService, times(1)).getPrices();
    }

    @Test
    void fetchProductImages() throws Exception {
        //given
        ProductImagesResponse productImagesResponse = mockProductImagesResponse();

        //when
        when(productService.getProductImages()).thenReturn(productImagesResponse);

        //then
        MvcResult mvcResult = mockMvc.perform(get(PATH + "/images")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ProductImagesResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductImagesResponse.class);

        assertNotNull(response);
        assertNull(response.getErrors());
        verify(productService, times(1)).getProductImages();
    }

    @Test
    void fetchProductImagesBadRequest() throws Exception {
        //given
        ProductImagesResponse productImagesResponse = mockProductImagesResponse();
        productImagesResponse.setErrors(List.of(mockErrorMessage()));

        //when
        given(productService.getProductImages()).willAnswer(inv -> {
            throw new CarRentalException("err");
        });

        //then
        MvcResult mvcResult = mockMvc.perform(get(PATH + "/images")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ProductImagesResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductImagesResponse.class);

        assertNotNull(response);
        assertNotNull(response.getErrors());
        verify(productService, times(1)).getProductImages();
    }

    @Test
    void fetchProducts() throws Exception {
        //given
        ProductsListResponse productsListResponse = mockProductsListResponse();

        //when
        when(productService.getAdminProducts()).thenReturn(productsListResponse);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/ds/read/fetch/admin/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ProductsListResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductsListResponse.class);

        assertNotNull(response);
        assertNull(response.getErrors());
        verify(productService, times(1)).getAdminProducts();
    }

    @Test
    void fetchProductsBadRequest() throws Exception {
        //given
        ProductsListResponse productsListResponse = mockProductsListResponse();
        productsListResponse.setErrors(List.of(mockErrorMessage()));

        //when
        given(productService.getAdminProducts()).willAnswer(inv -> {
            throw new CarRentalException("err");
        });

        //then
        MvcResult mvcResult = mockMvc.perform(get("/ds/read/fetch/admin/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ProductsListResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductsListResponse.class);

        assertNotNull(response);
        assertNotNull(response.getErrors());
        verify(productService, times(1)).getAdminProducts();
    }
}
