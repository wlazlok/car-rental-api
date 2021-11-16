package karol.wlazlo.ds.read.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.ds.read.services.ProductService;
import karol.wlazlo.ds.read.services.UserService;
import org.mockito.Mock;

public class ControllerMockConfig {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    protected ProductService productService;

    @Mock
    protected UserService userService;

    @Mock
    protected AppUserRepository appUserRepository;
}
