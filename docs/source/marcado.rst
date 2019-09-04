.. section-numbering::
.. role:: problema-contador

Lenguajes de marcado
====================

HTML (*HyperText Markup Language*) es el lenguaje en el que están escritas las páginas web y es la piedra angular de la web. En este tema, aprenderemos sobre HTML centrándonos en las versiones más recientes del lenguaje.


.. Note::

  HTML es un *estándar vivo*, mantenido por el grupo de trabajo WHATWG_ (Web Hypertext Application Technology Working Group). Échale un vistazo por encima a la especificación_. Otra entidad, el W3C_ (World Wide Web Consortium), *empaqueta* la especificación estándar de vez en cuando y le asigna un número de versión (por ejemplo, 5.0 en 2014, 5.1 en 2016 o 5.2 en 2017). El W3C sí es el principal responsable de otros estándares de la web como CSS, que estudiaremos más adelante.

  .. _WHATWG: https://whatwg.org/
  .. _especificación: https://html.spec.whatwg.org/multipage/
  .. _W3C: https://www.w3.org/


.. Important::

  Las habilidades que deberías adquirir con este tema incluyen las siguientes:

  - Entender la diferencia entre un lenguaje de marcado con propósito semántico como HTML y un lenguaje de estilo.
  - Conocer la semántica de los elementos de HTML discutidos en clase.
  - Gestionar correctamente diferentes codificaciones de caracteres.
  - Saber crear documentos HTML válidos.
  - Usar correctamente las herramientas de desarrolladores integradas en navegadores; en particular, las Chrome DevTools.
  - Entender el papel jugado por un servidor web y cómo publicar en él contenido estático.


Sintaxis y elementos del lenguaje HTML
--------------------------------------

HTML es el lenguaje informático básico que se usa para escribir el contenido de las páginas web (por otro lado, CSS se usa para especificar los aspectos estéticos que afectan a la presentación de las páginas y JavaScript para programar los aspectos dinámicos). En esta actividad vas a conocer los elementos fundamentales de HTML. Te basarás para ello en el código de la página web mínima que aparece a continuación, que iremos ampliando con otros elementos del lenguaje. 


.. Attention::

  La forma normal de trabajar con HTML es usar tu editor de texto favorito para editar el fichero y abrir el documento resultante (con extensión ``.html``) en un navegador. No obstante, cuando se dan los primeros pasos en el aprendizaje del lenguaje puede ser más cómodo usar entornos en línea como JSBin_, que evitan tener que guardar y recargar constantemente. Hay otras herramientas similares como JSFiddle_ o CodePen_, pero son menos recomendables desde el punto de vista educativo, ya que ocultan ciertas partes del documento HTML (por ejemplo, la cabecera) para no *molestar* al usuario con elementos obvios.

  .. _JSBin: http://jsbin.com/
  .. _JSFiddle: https://jsfiddle.net/
  .. _CodePen: http://codepen.io/


Una de los documentos web más sencillos que se pueden escribir es el siguiente. Comienza con la declaración de tipo de documento (``doctype``). Le sigue el *elemento* (o *etiqueta*) raíz ``html`` con un *atributo* opcional ``lang`` que indica el idioma del texto. El elemento ``head`` incluye metadatos sobre el documento: en este caso, la codificación de caracteres utilizada (hablaremos sobre ello más adelante) y el título del documento que aparecerá en la pestaña del navegador. El elemento ``body`` contiene el texto principal del documento, que en este caso es un único párrafo (elemento ``p``) con un saludo.


.. code-block:: html
  :caption: Un documento HTML válido y corto.
  :linenos:

  <!doctype html>
  <html lang="es">
    <head>
      <meta charset="utf-8">
      <title>Título del documento</title>
    </head>
    <body>
      <p>¡Hola, mundo!</p>
    </body>
  </html>


Una de las ideas que tienes que tener más claras es que los diferentes elementos de HTML no representan propiedades *estéticas* de su contenido (como, por ejemplo, si un texto se muestra en negrita o si se ha de mostrar separado del texto precedente por un espacio vertical), sino únicamente propiedades *semánticas* (este texto enfatiza una determinada idea o este otro texto constituye un párrafo). Los aspectos estéticos se definen mediante lenguajes de estilos como CSS, que estudiaremos más adelante.

HTML tiene aproximadamente un centenar de elementos diferentes, cada uno de ellos con un propósito semántico bien definido. En este curso vamos a estudiar un subconjunto de ellos, cuyo cometido puedes consultar en `MDN web docs`_ y que se muestran en este documento más completo_.

.. _`MDN web docs`: https://developer.mozilla.org/en-US/docs/Web/HTML/Element
.. _completo: _static/data/franz.html


.. admonition:: Hazlo tú ahora
  :class: hazlotu

  La especificación estándar de HTML es un documento demasiado técnico para los propósitos de este curso y para la mayoría de los desarrolladores. La web `MDN`_ muestra esta información de forma más sencilla. Estudia en ella todos los elementos de HTML que aparecen en este tema y asegúrate de que entiendes su propósito. Elementos adicionales, como los relacionados con los formularios, se estudiarán en otro tema. Reserva cita para tutoría si crees que no lo tienes todo claro.

  .. _MDN: https://developer.mozilla.org/es/


El código del documento HTML más completo enlazado anteriormente es el siguiente:


.. literalinclude:: _static/data/franz.html
  :language: html
  :linenos:

.. ficheros de código: ../../code/a.java


.. admonition:: :problema-contador:`Problema`
  :class: problema

  Indica con qué código HTML es necesario sustituir la cadena @1 para que el siguiente bloque HTML sea válido y corresponga a una tabla con dos filas (la primera de encabezado) y una columna.

  .. code-block:: html

    <table>
      <thead><tr><td><em>Nombre del río</em>@1<td>Ebro</td></tr>
    </table>

  .. @1=</td></tr></thead><tr>

.. admonition:: :problema-contador:`Problema`
  :class: problema

  Indica con qué código es necesario sustituir la cadena @1 para que el siguiente bloque HTML se muestre como se ve más abajo.

  .. code-block:: html

    <table>
      <tr><td>1</td>@1</tr>
    </table>

  .. raw:: html

    <table style="border: 1px solid gray">
      <tr><td><strong>1</strong></td><td>2</td></tr>
    </table>


espacios en blanco. Un *screen reader* podría no mostrar el contenido de *nav*. Un navegador aplica unos estilos por defecto a cada elemento.
 hablar por ahora de *img* solo y dejar el resto para más adelante; no explicar aún el atributo *alt* de *img* para que luego se obtenga un error como mínimo al validar el documento.



Representación en memoria de un documento HTML
----------------------------------------------

Imaginemos que tenemos que escribir un programa que cargue en memoria un documento HTML para luego realizar algún procesamiento sobre sus elementos (por ejemplo, mostrarlo en la ventana de un navegador o extraer los datos de una tabla). La estructura de datos que se utiliza para ello es un árbol en el que cada nodo es un objeto que representa una parte del documento. El DOM (*Document Object Model*) es un conjunto estándar de métodos (es decir, una interfaz) independiente del lenguaje para interactuar con este árbol.

----- espacios en blanco, 


.. figure:: https://upload.wikimedia.org/wikipedia/commons/5/5a/DOM-model.svg
  :target: https://commons.wikimedia.org/wiki/File:DOM-model.svg
  :alt: ejemplo de árbol DOM
  
  Árbol DOM por Birger Eriksson


Herramientas para desarrolladores
---------------------------------

Abre la página web que usaste en una actividad anterior desde los navegadores Google Chrome o Chromium. Utiliza el panel *Elements* de las [Chrome DevTools](https://developers.google.com/chrome-developer-tools/docs/elements) para inspeccionar los distintos elementos de tu página; para ello, sitúa el puntero del ratón encima de alguna posición de tu página web y selecciona *Inspeccionar elemento* en el menú contextual (otra opción es abrir las DevTools desde el menú *Herramientas* / *Herramientas para desarrolladores* del navegador o mediante el atajo de teclado Ctrl+Shift+I).

Familiarízate con el entorno, ya que te será extremadamente útil. Estudia ahora también un documento de HTML más complejo como [este](http://html5up.net/uploads/demos/prologue/).

<note>
Tiempo:

El profesor comenzará mostrando cómo ver el código fuente ("Ver código fuente la página" en menú contextual o Ctrl+U) de una página y discutirá cómo está opción tiene una utilidad limitada para desarrolladores, especialmente si el código es muy extenso. Además, esta opción muestra siempre el código inicial descargado y no el que el navegador tiene en memoria, que puede ser diferente por manipulaciones del DOM. A continuación, los estudiantes abrirán las Chrome DevTools para inspeccionar elementos de la página y se les mostrará el uso de la lupa (ahora con icono de flecha).

Además, el profesor hará a los alumnos estudiar detenidamente la secuencia de elementos anidados que aparecen al pie de la herramienta (que define una ruta desde la raíz del DOM, aunque posiblemente que no define unívocamente un elemento concreto) para que vayan haciéndose idea de la anidación de elementos existentes en un documento HTML; se les ha de pedir, de paso, que observen cómo en la secuencia de elementos aparecen algunos con información adicional separada por puntos (class, todavía no estudiado) o almohadillas (id, que fue estudiado en la actividad anterior). Se buscará un elemento bastante profundo y se preguntará cuál es la forma más corta de referirnos a ese elemento; los alumnos deberían llegar a la idea de que el último elemento con id es un nodo de inicio *seguro* hacia el elemento que se pretende identificar. Aunque el uso a fondo de los conceptos de DOM y XPATH se realizará más adelante (temas sobre CSS o JavaScript, por ejemplo), es recomendable que observen también qué es lo que se copia al portapapeles cuando seleccionan un elemento en las DevTools del documento web más complejo y desde el menú contextual eligen *Copy / Copy Selector* (hacerlo con un elemento con id y otro sin id para estudiar las diferencias). Comentarles, también, *Edit as HTML* (para esta opción también pueden hacer doble click en el nombre del elemento) y *Copy as HTML* (para copiar en un editor la web modificada).

El profesor comentará la existencia de extensiones similares para otros navegadores, como Firebug para Mozilla Firefox.
</note>

Codificación de caracteres
--------------------------

Aunque hoy día la mayor parte de los sistemas operativos trabajan con la codificación de caracteres de longitud variable UTF-8, que permite representar todos los caracteres del juego de caracteres Unicode, es importante que seas capaz de saber qué codificación de caracteres se usa en tus documentos web, en los de terceros, o en tu servidor web. No tener esto en cuenta puede hacer que tu web se visualice incorrectamente en algunos navegadores. En esta actividad hablaremos de codificación de caracteres siguiendo `estas diapositivas`_. Para comprobarlo, usa un editor de textos que permita redactar documentos bajo diferentes codificaciones y graba tu documento HTML usando las codificaciones *UTF-8* e *ISO-8859-1* (Latin-1); prueba a poner el valor correcto y el incorrecto en la directiva *meta* y observa el resultado con los caracteres especiales al abrir el documento en el navegador. Estudia cómo se representan los caracteres en las distintas codificaciones con editores hexadecimales como [HexEd.it](https://hexed.it/).

.. _`estas diapositivas`: _static/slides/070-codificacion-slides.html


<note>
Tiempo:

En esta actividad se introducirá la importancia de Unicode (comentar los problemas que había en el pasado para representar caracteres de varios idiomas usando solo un byte por carácter) y los posibles problemas con las codificaciones de caracteres (por ejemplo, para representar caracteres de múltiples codificaciones en un mismo documento de texto).

Además, de realizar lo demandado por la actividad, los alumnos usarán un editor de texto para comprobar cuál es la longitud de un fichero de texto que contiene la secuencia *añ* con ambas codificaciones y se les pedirá que razonen cómo interpretaría esta secuencia un editor que esté prepararado para el otro formato. Se le mostrará a los estudiantes la [lista de caracteres](http://www.unicode.org/charts/) de Unicode para que vean su riqueza.

Suele ser necesario hacer mucho énfasis en que colocar la codificación correcta en *charset* no garantiza que el documento use dicha codificación. También en que un documento grabado desde un editor de texto no tiene negritas ni colores ni nada por el estilo, a diferencia de uno grabado desde un procesador de texto.
</note>

Alojamiento en un servidor
--------------------------

Una página web normalmente se aloja en un servidor web. En esta actividad vamos a instalar el servidor Jetty y *alojar* en él nuestra página. Si la máquina en la que lo hacemos fuera pública (nuestro ordenador personal normalmente no lo será), se podría acceder entonces al documento desde cualquier máquina conectada a internet usando convenientemente la URL del servidor.

Existen muchos servidores web diferentes; algunos de los más conocidos son Apache HTTP Server (HTTPD), Internet Information Services, Apache Tomcat y Jetty. Aunque este último está escrito en Java y pensado especialmente para devolver contenido dinámico generado por programas escritos en Java o en JSP (y para eso lo usaremos más adelante), también puede usarse como proveedor de contenidos estáticos. Descarga el [repositorio Git](https://github.com/jaspock/dai) de la asignatura, accede a la carpeta *simple-html-file* y haz

~~~
mvn jetty:run
~~~

para lanzar (y descargarlo antes, si procede) el servidor Jetty sobre la carpeta actual. Accede luego a los ficheros guardados en la carpeta local *src/main/webapp* (este directorio sigue la estructura común de los proyectos en Maven) a través del servidor usando para ello el URL [http://localhost:8080](http://localhost:8080).

Puedes lanzar también otro servidor HTTP sobre el mismo contenido. Por ejemplo, para usar el que Python incluye por defecto, colócate en el directorio raíz del contenido que quieres servir y haz:

~~~
python -m SimpleHTTPServer
~~~


<note>
Tiempo:
</note>


Validación de documentos HTML
-----------------------------

Un aspecto básico de los documentos HTML es que estos cumplan estrictamente con las directrices de HTML, tanto a nivel sintáctico (por ejemplo, las marcas de apertura y clausura respetan el anidamiento entre elementos) como semántico (no hay dos atributos *id* con el mismo valor). De esta manera, se allana el camino hacia la compatibilidad entre navegadores y la usabilidad de la página web; la validación, sin embargo, no asegura que el documento se vaya a ver como el desarrollador tiene en la cabeza ni que se muestre de igual manera en todos los navegadores. Usa el [validador del W3C](http://validator.w3.org/) para validar la página web que has confeccionado en actividades anteriores; corrige todos los errores que te indique el validador hasta conseguir validarla. A continuación, crea una copia de tu documento, introduce diversos errores en ella y comprueba que el validador del W3C informa de ellos correctamente.

Indicar, de paso, que lo que en HTML5 se representa como

    <meta charset=utf-8>

en XHTML o HTML4 se representaba como

    <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" >

Esta es una de las múltiples diferencias entre ambas versiones del estándar.

Ejercicios de repaso
--------------------

Crea el esqueleto de una única página web que contenga la información que habitualmente se muestra en la ficha de una asignatura. Asegúrate de que el documento HTML es válido según el [validador del W3C](http://validator.w3.org/).


