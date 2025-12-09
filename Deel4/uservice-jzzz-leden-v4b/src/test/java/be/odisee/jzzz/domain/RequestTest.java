package be.odisee.jzzz.domain;

import be.odisee.jzzz.domain.legalFly.Request;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RequestTest {

    @Test
    void testRequestCreation() {
        Request request = new Request("Contract Review", "Need legal review", "client@example.com");

        assertEquals("Contract Review", request.getTitle());
        assertEquals("Need legal review", request.getDescription());
        assertEquals("client@example.com", request.getClientEmail());
        assertEquals("PENDING", request.getStatus());
        assertNotNull(request.getCreatedAt());
    }
}
