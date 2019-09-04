package es.ua.dlsi.dai;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProductosTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    @Test
    public void testGetIt() {
        String responseMsg = target.path("productos").request().get(String.class);
        System.out.println("eeeeee "+target.path("productos"));
        assertEquals("[{\"nombre\":\"galletas\",\"unidades\":2},{\"nombre\":\"tomates\",\"unidades\":12}]", responseMsg);
    }
}
