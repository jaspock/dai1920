.. role:: problema-contador

Servicios web
=============

Los *servicios web* proporcionan una forma estándar de interoperabilidad entre diferentes aplicaciones, posiblemente heterogéneas. En este tema, veremos cómo crear y cómo acceder a estos servicios, así como su relación con el estándar HTTP. Además, abordaremos el estudio de la arquitectura de servicios conocida como REST (por el inglés, *representation state transfer*), en la que los agentes proporcionan una interfaz con una semántica uniforme (que, básicamente, se corresponde con las operaciones para crear, recuperar, actualizar y borrar recursos), en lugar de interfaces arbitrarias o específicas de una aplicación concreta. En un primer momento, nos centraremos en el acceso a servicios web ofrecidos por terceros; posteriormente, veremos cómo definir nuestros propios servicios web.

.. Important::

  Las habilidades que deberías adquirir con este tema incluyen las siguientes:

  - Saber usar Ajax, especialmente a través de la API Fetch, como una tecnología para crear aplicaciones de una única página.
  - Saber crear y encadenar promesas en un programa de JavaScript.
  - Entender la *política del mismo origen* y cómo superarla con la técnica CORS.
  - Entender los fundamentos de la arquitectura REST.
  - Comprender los fundamentos del protocolo HTTP y la configuración cliente-servidor.
  - Diferenciar los distintos componentes de un navegador.
  - Saber implementar aplicaciones *mashups* que accedan e integren servicios web de terceros.
 
El protocolo HTTP
-----------------

HTTP (por *hypertext transfer protocol*) es el protocolo de comunicación (a nivel de capa de aplicación) diseñado para permitir el envío de información en la *world wide web* entre distintos ordenadores. Sus primeras versiones fueron desarrolladas en la década de los noventa a partir de los trabajos de Tim Berners Lee y su equipo (los creadores de la web). Las diferentes versiones del estándar son coordinadas por el `HTTP Working Group`_ de la Internet Engineering Task Force. HTTP/1.1 se publicó en 1997, HTTP/2 en 2015 y HTTP/3 en 2018, aunque tanto navegadores como servidores siguen soportando las versiones antiguas y son capaces de adaptarse para usar el mismo protocolo: por ejemplo, un navegador reciente que soporte HTTP/3 usará HTTP/2 o incluso HTTP/1.1 al comunicarse con un servidor que no haya sido actualizado en mucho tiempo. HTTP/3, a diferencia de anteriores versiones, ya no se apoya en el protocolo de transporte TCP (*transmission control protocol*), sino que usa QUIC (que no son siglas, sino un término que pretende evocar a la palabra inglesa *quick*). QUIC mejora la capacidad de multiplexación de TCP y suele mejorar la latencia y velocidad de las conexiones.

.. _`HTTP Working Group`: https://httpwg.org/

Los conceptos que tienes que comprender del protocolo HTTP se encuentran recogidos en `estas diapositivas`_.

.. _`estas diapositivas`: _static/slides/050-http-slides.html


Herramientas para desarrolladores
---------------------------------

Las herramientas para desarrolladores que incorporan los navegadores permiten estudiar los detalles de todas las conexiones establecidas por el navegador al ejecutar una aplicación web.

.. Note:

Las peticiones lanzadas desde la barra de direcciones del navegador son siempre de tipo GET. Para estudiar los otros tipos de peticiones de HTTP, necesitamos crear un formulario para peticiones de tipo POST, o, si queremos poder usar cualquier verbo, escribir código en JavaScript (usando la API Fetch que estudiáremos después) u otro lenguaje de programación. También hay herramientas como `Postman`_, una aplicación que permite crear cómodamente colecciones de peticiones a APIs (*application programming interfaces*).

.. _`Postman`: https://www.getpostman.com/

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Familiarízate con las opciones de inspección de la actividad en red de la pestaña :guilabel:`Network` del entorno de las Chrome DevTools siguiendo la página de su documentación `Inspect Network Activity`_ . Practica las distintas posibilidades en DevTools con peticiones GET por ahora, pero recuerda volver a esta actividad cuando estudies cómo realizar desde JavaScript peticiones con otros verbos.

  .. _`Inspect Network Activity`: https://developers.google.com/web/tools/chrome-devtools/network


Promesas de JavaScript
----------------------

Los objetos de tipo ``Promise`` (una clase definida en la API de JavaScript de los navegadores modernos) se usan para representar la finalización con éxito de una tarea (normalmente asíncrona, es decir, que no bloquea el bucle de eventos, sino que define una función de *callback* que será ejecutada más adelante) o su fracaso. En términos informales, diremos que una promesa se cumple (en caso de éxito), se incumple (en caso de fracaso) o que está pendiente (cuando aún no se ha resuelto); en inglés se usan los términos *fullfilled*, *rejected* o *pending*, respectivamente. Los objetos promesa nos serán muy útiles en tareas como la comunicación con un servidor, que veremos más adelante en este tema.

.. promesas: https://stackoverflow.com/a/42005046/1485627

Veamos un primer ejemplo. El siguiente código crea un objeto de tipo promesa ``p`` invocando al constructor de ``Promise``. Este constuctor recibe dos funciones de *callback* que son aportadas por el sistema (es decir, no son definidas por el programador); estas dos funciones (``resolve`` y ``reject`` en el código de abajo) serán invocadas en los lugares de nuestro código en los que determinemos que la tarea se ha desarrollado con éxito (en el caso del primer parámetro) o ha fracasado (función pasada como segundo parámetro). En este caso, vamos a suponer que nuestra promesa ejecuta una tarea asíncrona muy sencilla: esperar un segundo y generar un número aleatorio entre 0 y 1; la tarea se considera un éxito si el número es menor de 0,5. En lenguaje de la calle, sería como decir "te prometo que voy a generar (asíncronamente) un número aleatorio y que será menor que 0,5"; como cualquier promesa, en cualquier caso, esta puede cumpirse o no.

.. code-block:: 
  :linenos:

    let p= new Promise(function(resolve,reject) {
      console.log('Entrando en el constructor de la promesa');
      setTimeout( function() {
        const r= Math.random();
        if (r<0.5) {
          resolve('la cosa fue bien');
        }
        else {
          reject('algo salió mal');
        }
      }, 1000);
    });

    p.then(function(mensaje) {
        console.log(`Promesa cumplida: ${mensaje}`);
      }, function(mensaje) {
        console.log(`Promesa incumplida: ${mensaje}`);
      }
    );


Las funciones ``resolve`` y ``reject`` reciben un único argumento que usaremos para dar información adicional relacionada con el éxito o fracaso de la tarea asíncrona; en este caso, es una simple cadena con un mensaje, pero puede ser un objeto que incluya un conjunto de atributos. Nada más invocar al contructor de ``Promise`` se establece el estado de la promesa a *pendiente* y se ejecuta el código de la función pasada como parámetro al constructor. Este código normalmente definirá una operación asíncrona (como realizar una petición a un servidor) y terminará llamando a ``resolve`` o ``reject``; estas funciones (recordemos que son creadas por el sistema y no pertenecen a nuestro código) cambian el estado de la promesa a *cumplida* o *incumplida* y llaman a las funciones que el programador haya definido para manejar ambas situaciones.

El vínculo entre las funciones del sistema ``resolve`` y ``reject`` y nuestro código se establece llamando al método ``then`` sobre el objeto de tipo promesa. Al método ``then`` se le pasan dos funciones: la primera se vincula a ``resolve`` y la segunda a ``reject``. Estas funciones pueden tener un parámetro que será el mismo que el usado como argumento en las llamadas a ``resolve`` y ``reject`` del constructor de ``Promise``. 

.. Note:

  Realmente, las llamadas a ``resolve`` y ``reject`` también se realizan de forma asíncrona, de manera que para entonces el intérprete ya habrá ejecutado el método ``then`` correspondiente.

Finalmente, observa en el código anterior cómo hemos usado *cadenas con plantillas* (*template strings*) para imprimir los mensajes por consola. Las cadenas con plantillas de JavaScript usan comillas invertidas en lugar de rectas, pueden contener variables embebidas como en el ejemplo, y pueden también ocupar más de una línea.

.. figure:: https://csharpcorner-mindcrackerinc.netdna-ssl.com/UploadFile/BlogImages/01262017214716PM/Screen%20Shot%202017-01-26%20at%208.28.19%20pm.png
  :target: https://www.c-sharpcorner.com/blogs/overview-of-promises-in-javascript
  :alt: diagrama de los elementos implicados en una promesa
  
  Diagrama de los elementos de una promesa por Sumant Mishra

El código anterior es equivalente al siguiente en el que en lugar de pasar dos funciones a ``then`` se define la función asociada al incumplimiento de la promesa en un método ``catch``:

.. code-block:: 
  :linenos:

  <div id="promesas830">
    let p= new Promise(function(resolve,reject) {
      setTimeout( function() {
        const r= Math.random();
        if (r<0.5) {
          resolve('la cosa fue bien');
        }
        else {
          reject('algo salió mal');
        }
      }, 1000);
    });

    p.then(function(mensaje) {
        console.log(`Promesa cumplida: ${mensaje}`);
      })
      .catch(function(mensaje) {
        console.log(`Promesa incumplida: ${mensaje}`);
      });


Además, si encapsulamos el código de creación de la promesa en una función, usamos funciones flecha y gestionamos el error mediante una excepción (clase ``Error``), el código anterior se convierte en:

.. code-block:: 
  :linenos:

  <script src="https://embed.runkit.com" data-element-id="promesas731"></script>
  <div id="promesas731">
    function aleatorio() {
      return new Promise( (resolve,reject) => {
        setTimeout( () => {
          const r= Math.random();
          if (r<0.5) {
            resolve('la cosa fue bien');
          }
          else {
            reject(Error('algo salió mal'));
          }
        }, 1000);
    });

    aleatorio()
      .then( (mensaje) => {console.log(`Promesa cumplida: ${mensaje}`);})
      .catch( (mensaje) => {console.log(`Promesa incumplida: ${error.message}`);});
  </div>


Las promesas pueden concatenarse simplemente haciendo que la función asociada al cumplimiento de la promesa (la indicada en la llamada al método ``then``) devuelva a su vez una promesa:

.. code-block:: 
  :linenos:

    aleatorio().then( (mensaje) => {
      console.log(`Primera promesa cumplida: ${mensaje}`);
      return aleatorio2(0.8);
    })
    .then( (mensaje) => {
      console.log(`Segunda promesa cumplida: ${mensaje}`);
    })
    .catch( (error) => {
      console.log(`Una de las promesas fue incumplida: ${error.message}`);
    }
    );

    function aleatorio() {
      return new Promise( (resolve,reject) => {
        setTimeout( () => {
          const r= Math.random();
          if (r<0.5) {
            resolve('la cosa fue bien');
            }
          else {
            reject(Error('algo salió mal'));
          }
        }, 1000);
      });
    }

    function aleatorio2(delta) {
      return new Promise( (resolve,reject) => {
        setTimeout( () => {
          const r= Math.random();
          if (r<delta) {
            resolve('me encanta que los planes salgan bien');
          }
          else {
            reject(Error('peor imposible'));
          }
        }, 1000);
      });
    }
  

En este caso, la segunda promesa establece una clausura con el parámetro de la función que indica el umbral para que la promesa se cumpla. Si encapsulamos todos los bloques en funciones, el código principal queda muy compacto y legible:

.. code-block:: 
  :linenos:

  <script src="https://embed.runkit.com" data-element-id="promesas230"></script>
  <div id="promesas230">
    aleatorio()
      .then(primerMensaje)
      .then(segundoMensaje)
      .catch(mensajeError);

    function primerMensaje (mensaje) {
      console.log(`Primera promesa cumplida: ${mensaje}`);
      return aleatorio2(0.8);
    }

    function segundoMensaje (mensaje) {
      console.log(`Segunda promesa cumplida: ${mensaje}`);
    }

    function mensajeError (error) {
      console.log(`Una de las promesas fue incumplida: ${error.message}`);
    }

    function aleatorio() {
      return new Promise( (resolve,reject) => {
        setTimeout( () => {
          const r= Math.random();
          if (r<0.5) {
            resolve('la cosa fue bien');
          }
          else {
            reject(Error('algo salió mal'));
          }
        }, 1000);
      });
    }

    function aleatorio2(delta) {
      return new Promise( (resolve,reject) => {
        setTimeout( () => {
          const r= Math.random();
          if (r<delta) {
            resolve('me encanta que los planes salgan bien');
          }
          else {
            reject(Error('peor imposible'));
          }
        }, 1000);
      });
    }
  

Uno de los motivos de la introducción de las promesas en JavaScript fue precisamente el de simplificar la escritura de código en los frecuentes casos que los que un evento asíncrono dispara a su terminación otro evento asíncrono que dispara a su vez un nuevo evento asíncrono, etc. El código sin promesas termina teniendo una cantidad tal de ámbitos que su escritura y su lectura se hacen muy dificultosas:

.. code-block:: 
  :linenos:

    function aleatorio() {
        setTimeout( () => {
            const r= Math.random();
            if (r<0.5) {
              let mensaje= 'la cosa fue bien';
              alert(`Primera promesa cumplida: ${mensaje}`);
              let delta= 0.8;
              setTimeout( () => {
                const r= Math.random();
                if (r<delta) {
                  let mensaje= 'me encanta que los planes salgan bien';
                  alert(`Segunda promesa cumplida: ${mensaje}`);
                }
                else {
                  let error= Error('peor imposible');
                  alert(`Una de las promesas fue incumplida: ${error.message}`);
                }
              }, 1000);
            }
            else {
              let error= Error('algo salió mal');
              alert(`Una de las promesas fue incumplida: ${error.message}`);
            }
      }, 1000);
    }

    aleatorio();
  

Finalmente, JavaScript ha incluido más recientemente las `funciones asíncronas`_ que permiten simplificar aún más las cadenas de promesas al utilizar la misma notación secuencial empleada en segmentos síncronos de código con los bloques de código que contienen llamadas asíncronas. No los veremos, sin embargo, este curso.

.. _`funciones asíncronas`: https://developers.google.com/web/fundamentals/primers/async-functions


El objeto XMLHttpRequest
------------------------

En los primeros años de la web, la mayoría de las aplicaciones web seguían el siguiente patrón de comportamiento al realizar, por ejemplo, una búsqueda de un determinado elemento en una base de datos: el usuario rellenaba los valores correspondientes para buscar el elemento en un formulario y pulsaba el botón de enviar; en ese momento, el navegador realizaba una petición al servidor y borraba la página web actual; el servidor realizaba la búsqueda en la base de datos y devolvía entonces una página web completa que el navegador mostraba en sustitución de la anterior. Además de generar una experiencia incómoda al usuario si se compara con una aplicación tradicional de escritorio (el usuario ha de esperar a que se cargue de nuevo toda la página para seguir interactuando con la aplicación), este procedimiento es muy ineficiente cuando la página web tiene mucho contenido que apenas cambia, y que, sin embargo, es enviado continuamente por el servidor. 

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Prepárate para este tema, leyendo en primer lugar los capítulos sobre `desarrollo en el lado del cliente`_ y `peticiones Ajax`_ del libro "Client-Side Web Development".

  .. _`desarrollo en el lado del cliente`: https://info340.github.io/client-side-development.html
  .. _`peticiones Ajax`: https://info340.github.io/ajax.html

A partir de finales de los noventa y especialmente en los primeros años del siglo XXI, los desarrolladores comienzan a explotar el uso de funcionalidades de los navegadores que permiten realizar peticiones a un servidor sin tener que recargar la página completa. A estas técnicas se les denomina Ajax por razones históricas: el término lo acuñó un desarrollador en 2005 como acrónimo de *Asynchronous JavaScript and XML*. Con Ajax, los datos devueltos por el servidor se usan para generar dinámicamente HTML que es insertado convenientemente en el árbol DOM, conformando lo que se conoce como *aplicaciones de una solo página* (*single-page applications*). La más usada de las técnicas Ajax se basaba en el objeto ``XMLHttpRequest`` (para abreviar se suele llamar *XHR*) que permite, como se ha comentado, solicitar (normalmente de forma asícrona) una serie de datos al servidor desde JavaScript en lugar de una página completa que reemplazará a la actual. Estos datos serán, entonces, procesados por una función definida por el programador.

El siguiente es un ejemplo típico de uso:

.. code-block:: javascript
  :linenos:

  // Los datos del servidor se insertarán en el elemento con id 'results':
  var resultado= document.querySelector("#results");

  // Borra el contenido previo del elemento:
  resultado.textContent= "";

  // Crea el objeto XHR:
  var client = new XMLHttpRequest();

  var url= "https://ghibliapi.herokuapp.com/films/";
  // Identifica el verbo, la URL y que la petición será asíncrona:
  client.open("GET", url, true);
  
  // La función asignada a onreadystatechange es invocada varias veces por
  // el navegador durante el transcurso de las diferentes fases de comunicación 
  // con el servidor (conexión establecida, petición recibida, petición en proceso, 
  // petición finalizada); la fase de la llamada actual se almacena en readyState; 
  // normalmente, nos interesa la llamada en la que readyState tiene el valor 4,
  // que se hace en el momento en el que el servidor ha devuelto todos los 
  // datos:
  client.onreadystatechange = function () {
    console.log(client.readyState);
    if (client.readyState == 4) {
      // Código de estado de HTTP:
      if (client.status != 200) {
        console.error("Hubo un error (" + client.status + ")!");
      }
      else {
        // JSON.parse analiza la cadena pasada como argumento y la convierte
        // a un objeto de JavaScript; la respuesta de esta petición es un array
        // que se almacena en r:
        var r= JSON.parse(client.responseText);

        // Los datos devueltos son de la forma [ {"id": "0440483e", "title": "Princess 
        // Mononoke", "description": "Ashitaka, a prince of the disappearing Ainu tribe...", 
        // "director": ..., "producer": ...},   {"id": dc2e6bd1", "title": "Spirited Away",
        // "description": "Spirited Away is an Oscar winning Japanese animated film about 
        // a ten year old girl who wanders away from her parents...", "director": "Hayao 
        // Miyazaki", ...},  {...},  {...} ] 
        for (var i=0; i<r.length;i++) {
          resultado.textContent+= r[i].title+"; ";
        }
      }
    }
  };
  // Realiza la petición:
  client.send(null);


Este código accede a modo de ejemplo a una `API web`_ sobre las películas del estudio Ghibli. Los datos devueltos por esta API web (y por muchas otras) están codificados en una notación independiente del lenguaje denominada JSON (por *JavaScript Object Notation*), que es muy parecida a la que se usa en JavaScript para definir literalmente un objeto, pero con algunas diferencias: principalmente, que los atributos van siempre entrecomillados, que no pueden usarse comillas simples y que no pueden incluirse valores de algunos tipos especiales de JavaScript como, por ejemplo, funciones. Como ves en el código anterior, la función ``JSON.parse`` permite convertir una cadena en formato a JSON a objeto de JavaScript; para lo opuesto, puede usarse la función ``JSON.stringify``. En APIs web más antiguas se usaba el formato XML en lugar de JSON.

.. _`API web`: https://ghibliapi.herokuapp.com/

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Prueba el código del ejemplo anterior (por ejemplo, en una web como JSFiddle) para comprender su funcionamiento y estudia todo el tráfico de red mediante las Chrome DevTools. Asegúrate de crear un elemento con id ``results`` en el documento HTML. Escribe código para probar también otras APIs públicas, como la de `información del juego Clash Royale`_.

  .. _`información del juego Clash Royale`: https://github.com/martincarrera/clash-royale-api


La API Fetch
------------

En los últimos años, sin embargo, los navegadores han comenzado a implementar la API Fetch (desarrollada por el WHATWG, Web Hypertext Application Technology Working Group, que también supervisa la evolución de otros estándares como HTML o la API DOM), que es una forma más potente, extensible y flexible de acceder a recursos externos. Usando esta API en lugar de XHR, el código mostrado anteriormente quedaría como sigue:

.. code-block:: javascript
  :linenos:

  var resultado= document.querySelector("#results");
  resultado.textContent= "";
  fetch('https://ghibliapi.herokuapp.com/films/')
  .then(function(response) {
    if (!response.ok) {
      throw Error(response.statusText);
    }
    return response.json();  
  })
  .then(function(responseAsJson) {
    for (var i=0; i<responseAsJson.length;i++) {
          resultado.textContent+= responseAsJson[i].title+"; ";
  }
  })
  .catch(function(error) {
    console.log('Ha habido un problema: ', error);
  });

No te costará entender este código si repasas lo estudiado en una sección anterior sobre las promesas en JavaScript y te decimos que la función ``fetch`` devuelve una promesa que se cumple cuando el servidor devuelve un resultado (aunque la respuesta incluya un código de error de HTTP) y se incumple cuando por cualquier motivo no es posible establecer la comunicación con el servidor. También debería ser fácil deducir que la función ``json`` devuelve otra promesa que se cumple si el cuerpo de la respuesta del servidor (que se pasa por ``fetch`` a la función ``resolve`` y de ahí a la función del primer método ``then``) es una cadena en formato JSON que se puede convertir sin errores (usando ``JSON.parse()``) en un objeto de JavaScript.

También debería ser fácil de entender el siguiente código, que añade un paso intermedio al procesamiento de la respuesta del servidor que convierte los títulos de películas almacenados en el objeto de JavaScript a minúsculas. Para ello, define una función que devuelve una promesa que no se cumple únicamente en el caso de que la cadena del atributo ``title`` sea vacía.

.. code-block:: javascript
  :linenos:

  function minusculas(r) {
    var promise= new Promise(function(resolve, reject) {
      if (r.length>0) {
        for (var i=0; i<r.length;i++) {
          r[i].title= r[i].title.toLowerCase();
        }
        resolve(r);
      }
      else {
        reject(Error("String cannot be empty!"));
      }
    });
    return promise;
  }

  var resultado= document.querySelector("#results");
  resultado.textContent= "";
  fetch('https://ghibliapi.herokuapp.com/films/')
  .then(function(response) {
    if (!response.ok) {
      throw Error(response.statusText);
    }
    return response.json();  // llama a JSON.parse()
  })
  .then(minusculas)
  .then(function(responseAsJson) {
    for (var i=0; i<responseAsJson.length;i++) {
          resultado.textContent+= responseAsJson[i].title+"; ";
  }
  })
  .catch(function(error) {
    console.log('Ha habido un problema: \n', error);
  });


Las peticiones realizadas por ``fetch`` son, por defecto, de tipo GET. Más adelante, veremos como realizar peticiones con otros verbos, añadir información en JSON o dar valor a ciertas cabeceras de HTTP.


La política del mismo origen
----------------------------

Todos los navegadores implementan una restricción conocida como *política del mismo origen* (en inglés, *same-origin policy*), un concepto de seguridad existente desde la época de Netscape 2.0 para peticiones basadas en XHR o en la API Fetch. Esta política impide por defecto que desde un script bajado de un determinado servidor se realicen peticiones a servicios web disponibles en un servidor con un dominio diferente. Permitir este tipo de accesos abre la puerta a toda una serie de potenciales problemas: por ejemplo, *MaliciousSite.com* usa los servicios web de la web de *MyBank.com* (en la que el usuario tiene sesión abierta en otra pestaña del navegador) para obtener información confidencial; los servicios web de *MyBank.com* devuelven la información solicitada porque el usuario está autenticado y la *cookie* de autenticación es enviada por el navegador junto con la petición; el script con origen *MaliciousSite.com* puede ahora compartir esta información con otros servidores.

Si la política del mismo origen no pudiera evitarse, muchas aplicaciones web que usan servicios web *de terceros* desde el cliente no podrían existir o deberían implementar un proxy a dichos servicios web en su propio servidor. Por ello, los navegadores permiten bajo determinadas condiciones que estos accesos puedan realizarse. En particular, la técnica estándar CORS (*cross-origin resource sharing*) utiliza la cabecera *Origin* (que es añadida por el navegador y no puede modificarse desde JavaScript) en la petición para informar al servidor del origen del código que está haciendo la solicitud. El servidor puede autorizar o denegar entonces el acceso añadiendo a la respuesta un valor adecuado en la cabecera *Access-Control-Allow-Origin*; si el valor de esta última cabecera en la respuesta coincide con el valor de *Origin* en la petición o si toma un valor como `*`, entonces el navegador autoriza el acceso. En peticiones que pueden modificar datos en el servidor (como las de tipo PUT o DELETE), el navegador realiza una comunicación previa con el servidor (usando el verbo *OPTIONS*) para realizar algunas comprobaciones *pre-vuelo* (*pre-flight*) usando las cabeceras ya mencionadas.

Aunque no es fácil engañar al servidor modificando el valor enviado por el navegador en la cabecera *Origin*, debe tenerse en cuenta que el propósito de CORS no es el de hacer un sitio web más seguro; si el servidor devuelve datos privados, es necesario usar *cookies* o *tokens*, por ejemplo.

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  En una actividad anterior tienes un ejemplo de acceso a un servicio (el de información sobre el estudio Ghibli) que usa CORS. Estúdialo con ayuda de las herramientas para desarrolladores del navegador comprobando las cabeceras. Estudia también una petición vía Fetch a un servidor que no soporte CORS, como el de `esta API`_ de días festivos_.

  .. _`esta API`: https://date.nager.at/Home/Api
  .. _festivos: http://date.nager.at/api/v1/get/ES/2020

Finalmente, es importante resaltar que estas restricciones afectan a los servicios web a los que se intenta acceder desde un navegador. Una aplicación de escritorio o un programa ejecutándose en un servidor no tienen estas restricciones.


La arquitectura REST
--------------------

REST es una arquitectura para implementar servicios web sobre el protocolo HTTP, que es usada actualmente por la gran mayoría de APIs web. Bajo REST los recursos se representan mediante URLs y las acciones a realizar con ellos se indican mediante los correspondientes verbos de HTTP (principalmente, GET, POST, PUT y DELETE). 

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Estudia una API REST *de juguete* para gestionar `carritos de la compra`_. Realiza las peticiones al servidor desde el programa `Postman`_ cargando una `colección ya creada`_ de peticiones pulsando el siguiente botón.
      
  .. _`carritos de la compra`: https://pacific-retreat-67356.herokuapp.com
  .. _`colección ya creada`: https://www.getpostman.com/collections/10c494041155cf189a7f

En esta actividad vamos a explorar una API REST *de jueguete* para gestionar carritos de la compra. Para acceder a la API vamos a usar ``curl``, un programa que permite realizar peticiones HTTP desde la línea de órdenes y observar la respuesta devuelta por el servidor. En primer lugar, vamos a asignar a una variable de entorno el URL base de la API::

  endpoint=https://shrieking-caverns-53704.herokuapp.com/carrito/v1/

.. Note::

  La sintaxis que seguiremos aquí para manejar variables de entorno es la usada en sistemas basados en Unix. Para tu sistema operativo, la sintaxis podría ser ligeramente diferente.

El primer paso con la API del carrito suele ser obtener un identificador de carrito válido, lo que haremos con el verbo GET::

  curl --request POST --header 'content-type:application/json' -v $endpoint/carrito

La opción ``--request`` indica el verbo a usar y la opción ``--header`` sirve para identificar las cabeceras de la petición; en este caso, usamos la cabecera ``content-type`` que se usa para indicar al servidor en qué formato (JSON, en este caso) queremos recibir los datos de la respuesta; el servidor podría ignorar nuestra solicitud si no soportara dicho formato, lo que no es el caso. Finalmente, la opción ``--v`` hace que ``curl``muestre información más detallada sobre la petición y la respuesta. La petición anterioir nos devolverá en formato JSON el nombre del carrito recién creado en el atributo ``result.nombre``. Asigna dicho valor (por ejemplo, ``fada6``) a la variable de entorno ``carrito``::

  carrito=fada6

Ten en cuenta que si ningún cliente ha realizado una petición a la API en los últimos minutos, la primera respuesta puede tardar hasta un minuto en producirse. Vamos a añadir ahora un item al carrito. Para ello usamos el verbo POST sobre la ruta ``$endpoint/$carrito/productos``; los datos del nuevo item los pasaremos en JSON dentro del cuerpo (*payload*) del mensaje, al que damos valor con la opción ``--data`` de ``curl``::

  curl --request POST --data '{"item":"queso","cantidad":1}' --header 'content-type:application/json' $endpoint/$carrito/productos

El servidor nos devuelve un resultado en JSON con dos atributos, ``result`` y ``error``; el primero contiene información adicional si la petición pudo satisfacerse (el código de estado es 200 en ese caso); el atributo ``error`` contiene mas información sobre el error en caso de hacerlo (el código de estado es 404 en ese caso); si no procede dar valor a ``result``o ``error``, estos atributos tomarán el valor ``null``. Vamos a añadir otro item al carrito::

  curl --request POST --data '{"item":"leche","cantidad":4}' --header 'content-type:application/json' $endpoint/$carrito/productos

Para obtener la composición de un carrito, usaremos el verbo GET::

  curl --request GET --header 'content-type:application/json' $endpoint/$carrito/productos

Obtendremos una respuesta como la siguiente::

  {
    "result": {
      "nombre":"xxxxx",
      "productos":[{"item":"queso","cantidad":1},
                   {"item":"leche","cantidad":4}]
    },
    "error":null
  }

Para modificar la cantidad de un item ya existente en el carrito, usaremos la acción PUT e indicaremos la nueva cantidad en JSON en el bloque de datos::

  curl --request PUT --data '{"cantidad":2}' --header 'content-type:application/json' $endpoint/$carrito/productos/queso

Comprobamos que el carrito ha sido actualizado con la nueva cantidad::

  curl --request GET --header 'content-type:application/json' $endpoint/$carrito/productos

Finalmente, podemos borrar un producto con la acción DELETE:: 

  curl --request DELETE --header 'content-type:application/json' $endpoint/$carrito/productos/queso
  curl --request DELETE --header 'content-type:application/json' $endpoint/$carrito/productos/queremos

Con la segunda petición, el servidor devolverá un error indicando que el producto no existe.

Si quisiéramos añadir un nuevo item cuyo nombre lleve algún carácter especial (por ejemplo, la vocal con tilde de *jamón*), lo podemos hacer como en los casos anteriores::

  curl --request POST --data '{"item":"jamón","cantidad":2}' --header 'content-type:application/json' $endpoint/$carrito/productos

Pero a la hora de hacer una petición en la que el nombre del item forme parte del URL (y no del bloque de datos), es necesario convertir los caracteres especiales a aquellos que puedan formar parte de un URL a través de lo que se conoce como `codificación por ciento`_ (*percent-encoding*)::

  curl --request PUT --data '{"cantidad":2}' --header 'content-type:application/json' $endpoint/$carrito/productos/jam%C3%B3n

En JavaScript tenemos funciones como ``decodeURIComponent`` y ``encodeURIComponent`` que se encargan del trabajo de conversión. Para codificar un símbolo para ``curl`` podemos usar `herramientas en línea`_.

.. _`codificación por ciento`: https://developer.mozilla.org/en-US/docs/Glossary/percent-encoding
.. _`herramientas en línea`: https://meyerweb.com/eric/tools/dencoder/

Ahora vamos a ver cómo interactuar con la API del carrito desde JavaScript (en concreto, usando la API Fetch que hemos estudiado antes) por medio de una aplicación web de `gestión de carritos de la compra`_. Abre las DevTools de Google Chrome y estudia cada una de las peticiones Fetch realizadas por la aplicación. El código de este cliente de la API es el siguiente:

.. literalinclude:: ../../code/carrito/public/carrito.html
  :language: html
  :linenos:

.. _`gestión de carritos de la compra`: https://shrieking-caverns-53704.herokuapp.com/carrito.html


Peticiones CORS
~~~~~~~~~~~~~~~

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  La API REST del carrito soporta peticiones Fetch realizadas desde programas en JavaScript descargados de dominios diferentes al dominio en el que está ubicada la API. Para comprobarlo, abre el fichero ``carrito.html`` desde un servidor web local; recuerda cambiar antes la variable ``base`` de JavaScript para que apunte al *endpoint* de la API que has usado en la actividad anterior. Si tienes Python 2 instalado, ejecuta desde el directorio donde está ``carrito.html`` una de las dos siguientes órdenes::

    python -m SimpleHttpServer
    python2 -m SimpleHttpServer

  Si tienes Python 3 instalado en tu sistema, ejecuta desde el directorio donde está ``carrito.html``una de las dos siguientes órdenes::

    python -m http.server
    python3 -m http.server

  El servidor te informará del puerto en ``localhost`` desde el que puedes acceder al contenido del directorio. Realiza peticiones desde la aplicación web del carrito y analiza las cabeceras relacionadas con CORS de la petición y la respuesta. Observa cómo las peticiones de tipo POST, PUT o DELETE realizan una comprobación *pre-vuelo* con el verbo OPTIONS. Modifica el cliente para que envíe una cabecera adicional no convencional y observa cómo la respuesta del servidor hace que la petición falle.


Programación de servicios web en Node.js
----------------------------------------

Los servicios web se pueden programar en prácticamente cualquier lenguaje de programación existente hoy día. Para el servicio web anterior, hemos usado JavaScript con Node.js y la librería Express como puedes ver en `este código`_.

.. literalinclude:: ../../code/carrito/index.js
  :language: javascript
  :linenos:


Edición y publicación de la API REST en la nube
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

En esta actividad, vas a realizar una pequeña modificación a la API del carrito y a la aplicación web que la utiliza. El desarrollo lo realizarás en tu máquina y, cuando hayas comprobado que todo funciona correctamente, lo subirás a un servidor de aplicaciones en la nube.

.. admonition:: Hazlo tú ahora
  :class: hazlotu

.. Hint::

  Si vas a desarrollar frecuentemente con Node.js, te vendrá bien utilizar la herramienta `nodemon`_, que evita que tengas que matar y volver a lanzar el servidor local cada vez que hagas un cambio en la aplicación.

  .. _`nodemon`: https://www.npmjs.com/package/nodemon


.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Modifica ambas para que junto con la cantidad se pueda añadir el precio unitario de cada item. Sigue para ello los siguientes pasos.

  Instala Node.js en tu ordenador por medio de `Node Version Manager`_ (``nvm``). Descarga el código de la parte del cliente y la parte del servidor de la aplicación del carrito; clona para ello el `repositorio de la asignatura `_ haciendo::

    git clone https://github.com/jaspock/dai1920.git

  .. _`Node Version Manager`:: https://github.com/nvm-sh/nvm
  .. _`repositorio de la asignatura`: https://github.com/jaspock/dai1920
  
  Copia ahora la carpeta `dai1920/code/carrito` en otra ubicación de tu sistema. Abre un terminal dentro de ella y ejecuta::

    npm install
    node index.js

  La primera línea instala en la carpeta ``node_modules`` todas las dependencias indicadas en el fichero ``package.json``. La segunda línea lanza el motor de JavaScript sobre el fichero indicado. Como este fichero contiene una aplicación web escrita con el framework Express, este la ejecuta sobre un puerto de ``localhost``, por lo que podremos acceder a ella abriendo en el navegador algo como ``localhost:5000``.

  Puedes ahora realizar los cambios en la aplicación. Salvo que uses ``nodemon`, como se ha comentado antes, tendrás que matar y relanzar el servidor para que se apliquen los cambios. 

  Cuando tengas la aplicación lista en local, puedes desplegarla en `Heroku`_ como sigue. Comienza instalando el cliente de línea de órdenes de Heroku con las `instrucciones de esta página`_. En el caso de Linux basta con hacer::

    sudo snap install --classic heroku

  Continúa haciendo::
  
    git init
    git add .
    git commit -m "cambios"

  Con lo anterior, se crea un repositorio con los ficheros del proyecto, que podrás subir (*push*) a Heroku. Identifícate en el cliente de Heroku ejecutando::

    heroku login

  Crea un proyecto haciendo::

    heroku create

  Desde este momento ya podrás `desplegar la aplicación`_ con::

    git push heroku master

  Y abrirla en el navegador con::

    heroku open

  .. _`instrucciones de esta página`: https://devcenter.heroku.com/articles/heroku-cli#download-and-install
  .. _`desplegar la aplicación`: https://devcenter.heroku.com/articles/git
  .. _`Heroku`: https://www.heroku.com/

  Si haces cambios en la aplicación, basta con repetir estos pasos para actualizar la aplicación en Heroku::

    git add .
    git commit -m "cambios"
    git push heroku master


Términos de uso de las APIs web
-------------------------------

Finalmente, aunque no lo estudiaremos en esta asignatura, hay que tener en cuenta que existen en la web multitud de APIs disponibles para su uso desde aplicaciones de terceros, pero estas APIs suelen tener términos de uso (mira las condiciones de la `API de Twitter`_, por ejemplo) que es importante leer antes de decidirse a basar una determinada aplicación en ellas. 

.. _`API de Twitter`: https://developer.twitter.com/en/developer-terms/agreement-and-policy
