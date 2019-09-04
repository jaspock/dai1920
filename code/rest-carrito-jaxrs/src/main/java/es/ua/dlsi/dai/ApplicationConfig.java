import javax.ws.rs.ApplicationPath;  // Usamos las interfaces de JAX-RS, pero la implementación de referencia Jersey
import javax.ws.rs.core.Application;

// Esta clase hace innecesaria mucha de la información en web.xml.

@ApplicationPath("api")
public class ApplicationConfig extends Application {}

