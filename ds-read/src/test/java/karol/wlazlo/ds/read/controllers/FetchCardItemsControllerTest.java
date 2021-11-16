package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.ds.read.MockModel;
import karol.wlazlo.model.CardItem.CardItemResponse;
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

import static karol.wlazlo.ds.read.MockModel.mockCardItemResponse;
import static karol.wlazlo.ds.read.MockModel.mockErrorMessage;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FetchCardItemsControllerTest extends ControllerMockConfig {

    private MockMvc mockMvc;

    @InjectMocks
    private FetchCardItemsController controller;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getCards() throws Exception {
        //given
        CardItemResponse cardItemResponse = mockCardItemResponse();

        //when
        when(productService.getProducts()).thenReturn(cardItemResponse);

        //then
        MvcResult mvcResult = mockMvc.perform(get("/ds/read/fetch/cards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        CardItemResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CardItemResponse.class);

        assertNotNull(response);
        assertNull(response.getErrors());
        verify(productService, times(1)).getProducts();
    }

    @Test
    void getCardsBadRequest() throws Exception {
        //given
        CardItemResponse cardItemResponse = mockCardItemResponse();
        cardItemResponse.setErrors(List.of(mockErrorMessage()));

        //when
        given(productService.getProducts()).willAnswer(inv -> {
            throw new CarRentalException("err");
        });

        //then
        MvcResult mvcResult = mockMvc.perform(get("/ds/read/fetch/cards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        CardItemResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CardItemResponse.class);

        assertNotNull(response);
        assertNotNull(response.getErrors());
        verify(productService, times(1)).getProducts();
    }
}
