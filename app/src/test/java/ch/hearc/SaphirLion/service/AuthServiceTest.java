package ch.hearc.SaphirLion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import ch.hearc.SaphirLion.service.impl.AuthService;

/**
 * @deprecated Remplaced by Spring Security
 */
@Deprecated
@SpringBootTest
@TestPropertySource(locations = { "/application-test.properties", "/application-test-h2.properties" })
public class AuthServiceTest {

    @Autowired
    private AuthService auth;

    @Test
    public void success() {

        var user = auth.authenticate("User 1", "password", null);

        assertNotNull(user);
        assertEquals(user.getUsername(), "User 1");
    }

    @Test
    public void badPassword() {
        var userBadPassword = auth.authenticate("User 1", "psw", null);

        assertNull(userBadPassword);
    }

    @Test
    public void badName() {
        var userBadName = auth.authenticate("unknown", "password", null);

        assertNull(userBadName);
    }
}
