package Test;

import static org.junit.Assert.*;

import Domain.User;
import org.junit.Test;
import static org.junit.Assert.*;
public class UserTest {


    @Test
    public void testUserConstructor() {
        // Arrange
        String username = "myUsername";
        String password = "myPassword";

        // Act
        User user = new User(username, password);

        // Assert
        assertNotNull(user);
        assertEquals(username, user.getUser());
        assertEquals(password, user.getPassword());
    }
}