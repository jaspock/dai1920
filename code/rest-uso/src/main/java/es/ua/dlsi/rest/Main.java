package es.ua.dlsi.rest;

// Ejemplo de uso de JAX-RS Client API para acceder a JSON Server
// https://docs.oracle.com/javaee/7/tutorial/jaxrs-client.htm
// https://github.com/typicode/json-server

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Main {

  private static final String REST_URI = "http://localhost:3000/";

  private static Client client = ClientBuilder.newClient();

  // La clase (POJO) Asignatura solo se usa en este ejemplo, así que la definimos como
  // clase anidada (https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html)
  static class Asignatura {
    private int creditos;
    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;

    public int getCreditos() {return creditos;}
    public void setCreditos(int creditos) {this.creditos= creditos;}
    public int getId() {return id;}
    public void setId(int id) {this.id= id;}
    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo= codigo;}
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre= nombre;}
    public String getDescripcion() {return descripcion;}
    public void setDescripcion(String codigo) {this.descripcion= descripcion;}
  }


  private static void consultaTodas() {
    // Consulta de todas las asignaturas:
    // curl -i -H "Accept: application/json" -X GET http://localhost:3000/asignaturas/
    Asignatura[] all= client.target(REST_URI)
                            .path("asignaturas")
                            .request(MediaType.APPLICATION_JSON)
                            .get()
                            .readEntity(Asignatura[].class);
    System.out.println("Consulta de todas las asignaturas (códigos y nombres):");
    for (Asignatura a:all) {
      System.out.println("    " + a.getCodigo() + ": " + a.getNombre());
    }
  }


  public static void main(String[] args) {

    consultaTodas();

    // Consulta por id:
    // curl -i -H "Accept: application/json" -X GET http://localhost:3000/asignaturas/2
    String s1= client.target(REST_URI)
                     .path("asignaturas/2")
                     .request(MediaType.APPLICATION_JSON)
                     .get(String.class);
    System.out.println("Consulta por id (JSON devuelto):\n" + s1);

    // Otra forma de lo anterior:
    Asignatura a1= client.target(REST_URI)
                         .path("asignaturas/2")
                         .request(MediaType.APPLICATION_JSON)
                         .get(Asignatura.class);
    System.out.println("Consulta por id: " + a1.getNombre());

    // Búsqueda compuesta:
    Asignatura[] a2= client.target(REST_URI)
                           .path("asignaturas")
                           .queryParam("creditos",6)
                           .queryParam("codigo",34066)
                           .request(MediaType.APPLICATION_JSON)
                           .get()
                           .readEntity(Asignatura[].class);
    System.out.println("Consulta con búsqueda compuesta: " + a2[0].getNombre());

    // Nueva asignatura:
    // curl -i -H "Content-Type: application/json" --data '{"créditos":6,"código":"34068","nombre":"Interconexión de Redes","descripción:":"La asignatura aborda..."}' -X POST http://localhost:3000/asignaturas
    Asignatura a3= new Asignatura();
    a3.setCreditos(6);
    a3.setId(0);
    a3.setCodigo("34068");
    a3.setNombre("Interconexión de Redes");
    a3.setDescripcion("La asignatura aborda aspectos avanzados de las redes de comunicaciones IP, como ampliación "
                      + "de los conceptos introducidos en la asignatura obligatoria Redes de Computadores.");
    client.target(REST_URI)
          .path("asignaturas")
          .request(MediaType.APPLICATION_JSON)
          .post(Entity.json(a3));
    System.out.println("Nueva asignatura añadida: " + a3.getNombre());

    consultaTodas();

    // Modificación de asignatura:
    Asignatura a4= new Asignatura();
    a4.setCreditos(6);
    a4.setCodigo("34068");
    a4.setNombre("Interconexión de Redes 2");
    a4.setDescripcion("La asignatura aborda aspectos avanzados de las redes de comunicaciones IP.");
    client.target(REST_URI)
          .path("asignaturas/4")
          .request(MediaType.APPLICATION_JSON)
          .put(Entity.entity(a4, MediaType.APPLICATION_JSON));
    System.out.println("Modificación de asignatura: " + a4.getNombre());

    consultaTodas();

    // Borrado de asignatura:
    // curl -i -X DELETE http://localhost:3000/asignaturas/4
    client.target(REST_URI)
          .path("asignaturas/4")
          .request(MediaType.APPLICATION_JSON)
          .delete();
    System.out.println("Borrado de asignatura realizado");

    consultaTodas();
  }

}
