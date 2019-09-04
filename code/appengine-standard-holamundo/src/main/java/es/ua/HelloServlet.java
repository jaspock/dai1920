package es.ua;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.logging.Logger;

@WebServlet(name="HelloServlet",urlPatterns={"/hello"})
public class HelloServlet extends HttpServlet {

  private static final Logger log = Logger.getLogger(HelloServlet.class.getName());

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    PrintWriter out = resp.getWriter();

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity palabraEntity = new Entity("Palabra");
    palabraEntity.setProperty("lema", "mundo");

    Key palabraKey = datastore.put(palabraEntity);
    long k= palabraKey.getId();

    log.warning("Palabra añadida al Datastore");

    try {
      Entity palabraEntity2 = datastore.get(KeyFactory.createKey("Palabra",k));
      out.println("Hola, "+palabraEntity2.getProperty("lema"));
      log.warning("Palabra recuperada del Datastore");
    } catch (EntityNotFoundException e) {
      out.println("Error");
      log.warning("Error al recuperar la palabra del Datastore");
    }

  }

}
