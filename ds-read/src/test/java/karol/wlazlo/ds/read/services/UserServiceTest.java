package karol.wlazlo.ds.read.services;

import karol.wlazlo.commons.exceptions.CarRentalException;
import karol.wlazlo.model.AdminUserResponse.AdminUserResponse;
import karol.wlazlo.model.Security.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static karol.wlazlo.ds.read.MockModel.mockAppUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends ServiceMockConfig {

    @InjectMocks
    private UserService userService;

    @Test
    void getUsers() {
        //given
        List<AppUser> users = List.of(mockAppUser());

        //when
        when(appUserRepository.findAll()).thenReturn(users);

        //then
        AdminUserResponse result = userService.getUsers();

        assertEquals(users.size(), result.getUsers().size());
        assertNull(result.getErrors());
    }

    @Test
    void getUsersThrowException() {
        //when
        given(appUserRepository.findAll()).willAnswer(inv -> {
            throw new Exception();
        });

        //then
        try {
            userService.getUsers();
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof CarRentalException);
            assertEquals("msg.err.get.users", ex.getMessage());
        }
    }

    @Test
    void getUserById() {
        //given
        AppUser user = mockAppUser();
        Long userId = 1L;

        //when
        when(appUserRepository.getById(anyLong())).thenReturn(user);

        //then
        AppUser result = userService.getUserById(userId);
        assertEquals(user, result);
    }

    @Test
    void getUserByIdThrowException() {
        //given
        Long userId = 1L;

        //when
        given(appUserRepository.getById(anyLong())).willAnswer(inv -> {
            throw new Exception();
        });

        //then
        try {
            userService.getUserById(userId);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof CarRentalException);
            assertEquals("msg.err.fetch.data.error", ex.getMessage());
        }
    }
}
