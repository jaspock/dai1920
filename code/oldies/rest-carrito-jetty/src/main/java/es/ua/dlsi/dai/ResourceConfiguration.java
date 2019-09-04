package es.ua.dlsi.dai;

import org.glassfish.jersey.server.ResourceConfig;

public class ResourceConfiguration extends ResourceConfig {
    public ResourceConfiguration() {
        packages("es.ua.dlsi.dai");
        register(ProductosREST.class);
    }

}
