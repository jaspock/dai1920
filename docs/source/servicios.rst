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

.. raw:: html

  <script src="https://embed.runkit.com" data-element-id="promesas567"></script>
  <div id="promesas567">
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
  </div>

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

.. raw:: html

  <script src="https://embed.runkit.com" data-element-id="promesas830"></script>
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
  </div>


Además, si encapsulamos el código de creación de la promesa en una función, usamos funciones flecha y gestionamos el error mediante una excepción (clase ``Error``), el código anterior se convierte en:

.. raw:: html

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

.. raw:: html

  <script src="https://embed.runkit.com" data-element-id="promesas794"></script>
  <div id="promesas794">
    
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
  </div>

En este caso, la segunda promesa establece una clausura con el parámetro de la función que indica el umbral para que la promesa se cumpla. Si encapsulamos todos los bloques en funciones, el código principal queda muy compacto y legible:

.. raw:: html

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
  </div>

Uno de los motivos de la introducción de las promesas en JavaScript fue precisamente el de simplificar la escritura de código en los frecuentes casos que los que un evento asíncrono dispara a su terminación otro evento asíncrono que dispara a su vez un nuevo evento asíncrono, etc. El código sin promesas termina teniendo una cantidad tal de ámbitos que su escritura y su lectura se hacen muy dificultosas:

.. raw:: html

  <script src="https://embed.runkit.com" data-element-id="promesas551"></script>
  <div id="promesas551">
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
  </div>

Finalmente, JavaScript ha incluido más recientemente las `funciones asíncronas`_ que permiten simplificar aún más las cadenas de promesas al utilizar la misma notación secuencial empleada en segmentos síncronos de código con los bloques de código que contienen llamadas asíncronas. No los veremos, sin embargo, este curso.

.. _`funciones asíncronas`: https://developers.google.com/web/fundamentals/primers/async-functions


El objeto XMLHttpRequest
------------------------

En los primeros años de la web, la mayoría de las aplicaciones web seguían el siguiente patrón de comportamiento al realizar, por ejemplo, una búsqueda de un determinado elemento en una base de datos: el usuario rellenaba los valores correspondientes para buscar el elemento en un formulario y pulsaba el botón de enviar; en ese momento, el navegador realizaba una petición al servidor y borraba la página web actual; el servidor realizaba la búsqueda en la base de datos y devolvía entonces una página web completa que el navegador mostraba en sustitución de la anterior. Además de generar una experiencia incómoda al usuario si se compara con una aplicación tradicional de escritorio (el usuario ha de esperar a que se cargue de nuevo toda la página para seguir interactuando con la aplicación), este procedimiento es muy ineficiente cuando la  la página web tiene mucho contenido que apenas cambia, y que, sin embargo, es enviado continuamente por el servidor. 

Sin embargo, a partir de finales de los noventa y especialmente en los primeros años del siglo XXI, los desarrolladores comienzan a explotar el uso de funcionalidades de los navegadores que permiten realizar peticiones a un servidor sin tener que recargar la página completa. A estas técnicas se les denomina Ajax por razones históricas: el término lo acuñó un desarrollador en 2005 como acrónimo de *Asynchronous JavaScript and XML*. Con Ajax, los datos devueltos por el servidor se usan para generar dinámicamente HTML que es insertado convenientemente en el árbol DOM. La más usada de las técnicas Ajax se basaba en el objeto ``XMLHttpRequest`` (para abreviar se suele llamar *XHR*) que permite, como se ha comentado, solicitar (normalmente de forma asícrona) una serie de datos al servidor desde JavaScript en lugar de una página completa que reemplazará a la actual. Estos datos serán, entonces, procesados por una función definida por el programador.

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

  .. raw:: html

    <div class="postman-run-button"
    data-postman-action="collection/import"
    data-postman-var-1="10c494041155cf189a7f"
    data-postman-param="env%5BCarrito%5D=W3sia2V5IjoidXJsIiwidmFsdWUiOiJodHRwczovL3BhY2lmaWMtcmV0cmVhdC02NzM1Ni5oZXJva3VhcHAuY29tIiwiZW5hYmxlZCI6dHJ1ZX0seyJrZXkiOiJjYXJyaXRvIiwidmFsdWUiOiJlbzl5eDUiLCJlbmFibGVkIjp0cnVlfV0="></div>
    <script type="text/javascript">
      (function (p,o,s,t,m,a,n) {
        !p[s] && (p[s] = function () { (p[t] || (p[t] = [])).push(arguments); });
        !o.getElementById(s+t) && o.getElementsByTagName("head")[0].appendChild((
          (n = o.createElement("script")),
          (n.id = s+t), (n.async = 1), (n.src = m), n
        ));
      }(window, document, "_pm", "PostmanRunObject", "https://run.pstmn.io/button.js"));
    </script>

  Ten en cuenta que si ningún cliente ha realizado una petición a la API en los últimos minutos, la primera respuesta puede tardar hasta un minuto en producirse. Más adelante, veremos una aplicación web que accede mediante la API Fetch a esta API REST. Los accesos también pueden realizarse desde la línea de órdens con programas como ``curl``.

Los servicios web se pueden programar en prácticamente cualquier lenguaje de programación existente hoy día. Para el servicio web anterior, hemos usado JavaScript con Node.js y la librería Express como puedes ver en `este código`_.

.. _`este código`: _static/data/carrito/server.js

Finalmente, aunque no lo estudiaremos en esta asignatura, hay que tener en cuenta que existen en la web multitud de APIs disponibles para su uso desde aplicaciones de terceros, pero estas APIs suelen tener términos de uso (mira las condiciones de la `API de Twitter`_, por ejemplo) que es importante leer antes de decidirse a basar una determinada aplicación en ellas. 

.. _`API de Twitter`: https://developer.twitter.com/en/developer-terms/agreement-and-policy

