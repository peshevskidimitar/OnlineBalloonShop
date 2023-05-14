package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.model.enumerations.Role;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidRequiredArgumentsException;
import mk.ukim.finki.wp.lab.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wp.lab.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wp.lab.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.wp.lab.repository.jpa.UserRepository;
import mk.ukim.finki.wp.lab.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    private UserServiceImpl userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        User user = new User("username", "name", "surname", "password", LocalDate.now(), Role.ROLE_USER);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        userService = Mockito.spy(new UserServiceImpl(userRepository, shoppingCartRepository, passwordEncoder));
    }

    @Test
    public void testSuccessRegister() {
        User user = userService.register("username", "password", "password", "name", "surname", LocalDate.now(), Role.ROLE_USER);

        Mockito.verify(userService).register("username", "password", "password",
                "name", "surname", LocalDate.now(), Role.ROLE_USER);
        Assert.assertNotNull("User is null.", user);
        Assert.assertEquals("Username do not match.", "username", user.getUsername());
        Assert.assertEquals("Password do not match.", "password", user.getPassword());
        Assert.assertEquals("Name do not match.", "name", user.getFullName().getName());
        Assert.assertEquals("Surname do not match.", "surname", user.getFullName().getSurname());
        Assert.assertEquals("Role do not match.", Role.ROLE_USER, user.getRole());
    }

    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidRequiredArgumentsException expected.",
                InvalidRequiredArgumentsException.class,
                () -> userService.register(null, "password", "password", "name", "surname", LocalDate.now(), Role.ROLE_USER));
        Mockito.verify(userService).register(null, "password", "password", "name", "surname", LocalDate.now(), Role.ROLE_USER);
    }

    @Test
    public void testEmptyUsername() {
        String username = "";
        Assert.assertThrows("InvalidRequiredArgumentsException expected.",
                InvalidRequiredArgumentsException.class,
                () -> userService.register(username, "password", "password", "name", "surname", LocalDate.now(), Role.ROLE_USER));
        Mockito.verify(userService).register(username, "password", "password", "name", "surname", LocalDate.now(), Role.ROLE_USER);
    }

    @Test
    public void testEmptyPassword() {
        String username = "username";
        String password = "";
        Assert.assertThrows("InvalidRequiredArgumentsException expected.",
                InvalidRequiredArgumentsException.class,
                () -> userService.register(username, password, "password", "name", "surname", LocalDate.now(), Role.ROLE_USER));
        Mockito.verify(userService).register(username, password, "password", "name", "surname", LocalDate.now(), Role.ROLE_USER);
    }

    @Test
    public void testNullPassword() {
        String username = "username";
        String password = null;
        Assert.assertThrows("InvalidRequiredArgumentsException expected.",
                InvalidRequiredArgumentsException.class,
                () -> userService.register(username, password, "password", "name", "surname", LocalDate.now(), Role.ROLE_USER));
        Mockito.verify(userService).register(username, password, "password", "name", "surname", LocalDate.now(), Role.ROLE_USER);
    }

    @Test
    public void testPasswordMismatch() {
        String username = "username";
        String password = "password";
        String confirmPassword = "otherPassword";
        Assert.assertThrows("PasswordsDoNotMatchException expected.",
                PasswordsDoNotMatchException.class,
                () -> userService.register(username, password, confirmPassword, "name", "surname", LocalDate.now(), Role.ROLE_USER));
        Mockito.verify(userService).register(username, password, confirmPassword, "name", "surname", LocalDate.now(), Role.ROLE_USER);
    }

    @Test
    public void testDuplicateUsername() {
        User user = new User("username", "password", "name", "surname", LocalDate.now(), Role.ROLE_USER);
        Mockito.when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                UsernameAlreadyExistsException.class,
                () -> userService.register(username, "password", "password", "name", "surname", LocalDate.now(), Role.ROLE_USER));
        Mockito.verify(userService).register(username, "password", "password", "name", "surname", LocalDate.now(), Role.ROLE_USER);
    }

}