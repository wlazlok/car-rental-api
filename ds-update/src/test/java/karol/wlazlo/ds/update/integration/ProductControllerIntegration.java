package karol.wlazlo.ds.update.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import karol.wlazlo.model.ProductItem.ProductItem;
import karol.wlazlo.model.ProductItem.ProductItemResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String PATH = "/ds/update";

    @Test
    void saveProduct() throws Exception {
        ProductItem productItem = new ProductItem();
        productItem.setProductName("Test product");
        productItem.setDayPrice(250);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(
                                PATH + "/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productItem)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        ProductItemResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), ProductItemResponse.class);

        assertNotNull(response);
        assertNull(response.getErrors());
        assertNotNull(response.getProduct());
        //weryfikujemy nadanie koljnego ID z bazy
        assertNotNull(response.getProduct().getId());
    }




}
