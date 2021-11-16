package karol.wlazlo.ds.read.services;

import karol.wlazlo.commons.repositories.AppUserRepository;
import karol.wlazlo.commons.repositories.ProductItemRepository;
import org.mockito.Mock;

public class ServiceMockConfig {

    @Mock
    protected AppUserRepository appUserRepository;

    @Mock
    protected ProductItemRepository productItemRepository;
}
