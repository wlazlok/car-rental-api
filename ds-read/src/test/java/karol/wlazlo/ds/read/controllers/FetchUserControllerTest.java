package karol.wlazlo.ds.read.controllers;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.model.AdminUserResponse.AdminUserResponse;
import karol.wlazlo.model.Response.AbstractResponse;
import karol.wlazlo.model.Security.AppUser;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FetchUserControllerTest extends ControllerMockConfig {

    private final String PATH = "/ds/read/user";

    private MockMvc mockMvc;

    @InjectMocks
    private FetchUserController controller;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void getUsers() throws Exception {
        //given
        AdminUserResponse adminUserResponse = mockAdminUserResponse();

        //when
        when(userService.getUsers()).thenReturn(adminUserResponse);

        //then
        MvcResult mvcResult = mockMvc.perform(get(PATH + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        AdminUserResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AdminUserResponse.class);

        assertNotNull(response);
        assertNull(response.getErrors());
        verify(userService, times(1)).getUsers();
    }

    @Test
    void getUsersBadRequest() throws Exception {
        //given
        AdminUserResponse adminUserResponse = mockAdminUserResponse();
        adminUserResponse.setErrors(List.of(mockErrorMessage()));

        //when
        given(userService.getUsers()).willAnswer(inv -> {
            throw new CarRentalException("err");
        });

        //then
        MvcResult mvcResult = mockMvc.perform(get(PATH + "/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        AdminUserResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AdminUserResponse.class);

        assertNotNull(response);
        assertNotNull(response.getErrors());
        verify(userService, times(1)).getUsers();
    }

    @Test
    void getUserById() throws Exception {
        //given
        AppUser user = mockAppUser();
        Long userId = 1L;

        //when
        when(userService.getUserById(anyLong())).thenReturn(user);

        //then
        MvcResult mvcResult = mockMvc.perform(get(PATH)
                        .param("uId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        AppUser response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AppUser.class);

        assertNotNull(response);
        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    void getUserByIdBadRequest() throws Exception {
        //given
        AbstractResponse abstractResponse = new AbstractResponse();
        abstractResponse.setErrors(List.of(mockErrorMessage()));
        Long userId = 1L;

        //when
        given(userService.getUserById(anyLong())).willAnswer(inv -> {
            throw new CarRentalException("err");
        });

        //then
        MvcResult mvcResult = mockMvc.perform(get(PATH)
                        .param("uId", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        AbstractResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AbstractResponse.class);

        assertNotNull(response);
        assertNotNull(response.getErrors());
        verify(userService, times(1)).getUserById(anyLong());
    }
}
