
Enunciados de las prácticas
===========================

Calendario
----------

Este es el calendario de cada uno de los entregables de la asignatura. No se admitirán entregas fuera de plazo.

.. list-table::
    :widths: 15 60 25
    :header-rows: 1
    :class: tablita

    * - Entregable
      - Título
      - Fecha límite de entrega
    * - Práctica #1
      - `Una página web con HTML5`_
      - 7 octubre 2019
    * - Práctica #2
      - `Una aplicación web local`_
      - 4 noviembre 2019
    * - Práctica #3
      - `Una aplicación con acceso a servicios web de terceros y con componentes web`_
      - 25 noviembre 2019
    * - Práctica #4
      - `Una aplicación en la nube`_
      - 22 diciembre 2019


Instrucciones de entrega de las prácticas
-----------------------------------------

Realiza tu entrega en un único fichero comprimido a través del `servidor web del Departamento`_ antes de las 23.59 del día de la fecha límite. Recuerda que no se admitirán entregas fuera de plazo.

.. _`servidor web del Departamento`: https://pracdlsi.dlsi.ua.es/index.cgi?id=val


Una página web con HTML5
------------------------

En esta práctica vas a crear un documento HTML5 en el que *todo* el formato recaiga en hojas de estilo CSS (por tanto, no es posible usar atributos como ``style`` para el formato). Tu documento se llamará ``index.html`` y tendrá dos vistas diferentes, *normal* (la vista por defecto) y *compacta*; el usuario podrá cambiar de vista en cualquier momento usando los enlaces a pie de página. El objetivo es que consigas un documento que se muestre exactamente como puedes ver en estas imágenes de la `vista normal`_ y de la `vista compacta`_. Tu documento usará tres hojas de estilo: una con todo el contenido común a ambas vistas y dos más correspondientes a cada una de las vistas. Para poder alternar entre ambas hojas de estilo, añade este código a la cabecera (``head``) de tu página:

.. _`vista normal`: _static/img/p1-vista-normal.png
.. _`vista compacta`: _static/img/p1-vista-compacta.png
.. _`este código`: http://www.omnimint.com/A6/JavaScript/Change-external-CSS-stylesheet-file-with-JavaScript.html

.. code-block:: html

    <script type="text/javascript">
      function changeCSS(cssFile) {
        var oldlink = document.getElementById("estilo");
        var newlink = document.createElement("link")
        newlink.setAttribute("rel", "stylesheet");
        newlink.setAttribute("type", "text/css");
        newlink.setAttribute("id", "estilo");
        newlink.setAttribute("href", cssFile);
        document.getElementsByTagName("head")[0].replaceChild(newlink, oldlink);
      }
    </script>

Recuerda también añadir posteriormente este código en el pie de la página cuando lo crees:

.. code-block:: html

    <a href="#" onclick="changeCSS('./css/normal.css');">Vista normal</a> |
    <a href="#" onclick="changeCSS('./css/compact.css');">Vista compacta</a>

Añade, finalmente, un elemento como este a la cabecera de tu documento para que la vista normal sea la que se muestre por defecto:

.. code-block:: html

    <link id="estilo" rel="stylesheet" type="text/css" href="./css/normal.css">

Es muy importante que cuando implementes tu solución te asegures de que respetas **estrictamente** estas instrucciones, incluidos los nombres de identificadores, nombres de fichero, etc.; respeta las mayúsculas y minúsculas, las tildes, la posición de cada elemento en el documento, etc.

Marcado
~~~~~~~

Comienza preparando el documento HTML, sin tener en cuenta, por ahora, los aspectos estilísticos. El documento HTML seguirá los principios del lenguaje estudiados en clase. En particular, tendrá:

- una cabecera (elemento ``header``) que incluya el título (elemento ``h1``), el párrafo descriptivo y el índice (el índice será un elemento de tipo ``nav`` que incluirá una lista no numerada, ``ul``), los tres como descendientes inmediatos (hijos) del elemento ``header``;
- la cabecera irá seguida de un fragmento principal (encerrado en el elemento ``main``) que incluirá ambos cuestionarios;
- la página acabará con un pie de página (elemento ``footer``) con tus datos y los enlaces para cambiar la vista; encierra tu nombre completo (nombre y apellidos, en este orden) en un ``span`` (hijo de ``footer``) con identificador ``nombre`` y el DNI en otro elemento ``span`` (también hijo de ``footer``) con identificador ``dni``; utiliza el carácter ``'|'`` en el documento HTML para separar los diferentes contenidos del pie de página.

Cada cuestionario estará incluido en su propia sección (mediante sendos elementos ``section``, que han de incluir atributos ``id``, con valores ``paris``, sin tilde y todo en minúsculas, y ``londres``, todo en minúsculas, que serán referenciados desde el índice) y tendrá un título de segundo nivel (un elemento ``h2``, que será hijo de ``section``) que incluirá una imagen (que no ha de coincidir necesariamente en tu solución con la de este enunciado, pero no ha de tener tamaño superior a 512x512 píxeles) seguida del texto del título tal como sigue:

.. code-block::

    <section ...>
      <h2 ...>
        <img ...>
        Cuestionario sobre...
      </h2>

La forma de codificar cada pregunta será la siguiente:

.. code-block:: html

    <div class="bloque">
      <div class="pregunta">
      La ciudad de París se sitúa a ambos lados del río Sena.
      </div>
      <div class="respuesta" data-valor="true">
      </div>
    </div>

El contador de pregunta se ha de inicializar para cada nuevo cuestionario. El atributo ``data-valor`` es un atributo personalizado de HTML que usaremos para almacenar la respuesta (true/false) a la pregunta. En general, no es posible añadir a un elemento atributos que no estén especificados en el estándar excepto si estos comienzan por el prefijo ``data-``. 

Tanto los números de pregunta como el texto usado en la página para indicar la respuesta correcta no pueden aparecer explícitamente en el documento HTML, sino que han de ser generados dinámicamente desde CSS.

Estilo
~~~~~~

Una vez tengas el documento HTML finalizado, puedes pasar a diseñar las hojas de estilo. Para el contador de preguntas, añade un número secuencial a cada pregunta obtenido automáticamente mediante un uso adecuado de los `contadores de CSS`_. Para las respuestas usa los `pseudoelementos CSS`_ ``::before`` y ``::after``.

.. _`contadores de CSS`: https://developer.mozilla.org/en-US/docs/Web/Guide/CSS/Counters
.. _`pseudoelementos CSS`: http://www.smashingmagazine.com/2011/07/13/learning-to-use-the-before-and-after-pseudo-elements-in-css/

Se describen a continuación las características comunes de ambas vistas:

- la página completa (elemento ``body``) tiene fondo blanco, letra de color ``#333333`` y no tiene margen (esto es, el margen se ha de establecer explícitamente a cero);
- la cabecera (elemento ``header``) tiene un ancho máximo de 1080px y márgenes automáticos a derecha e izquierda; su ancho, además, es el 98% del de la página para que siempre haya un pequeño margen entre el contenido de la página y la ventana del navegador; el texto de la cabecera está centrado;
- los encabezados de nivel 1 usan letra negrita de 36px;
- los encabezados de nivel 2 usan letra negrita de 25px;
- el índice no usa ningún adorno especial de lista; los enlaces del índice no aparecen subrayados; lo único que los identifica como enlaces es su color (``cornflowerblue``) y el hecho de que el cursor del ratón cambia al pasar sobre ellos;
- el fragmento principal (elemento ``main``) tiene un ancho máximo de 1080px y márgenes automáticos a derecha e izquierda; su ancho, además, es el 98% del de la página para que siempre haya un pequeño margen entre el contenido de la página y la ventana del navegador;
- la sección correspondiente a cada cuestionario tiene un margen superior de 80px;
- cada pregunta (selector ``.pregunta``) tiene un margen superior e inferior de 1ex;
- el texto en otro idioma (*arrondissement*) se marca con la clase *idioma* (usa un elemento ``span`` para rodear la palabra) y se muestra en itálica;
- la imagen junto al título de cada cuestionario está alineada verticalmente con la parte superior de la línea (``text-top``) y se escala *mediante CSS* a un tamaño de 50x50 píxels; la separa del encabezado un margen de 10px por la derecha; la imagen tiene un borde de 1px sólido de color ``lightgray``;
- el pie de página (elemento ``footer``) tiene una altura de 50px y un margen superior de 100px; el color de fondo es ``steelblue`` y su anchura abarca el 100% de la ventana del navegador; el texto de una sola línea incluido usa una letra de tamaño 80% de color ``white``, excepto para los enlaces, que usan color ``lightgray``; el texto, además, está centrado verticalmente, lo que puedes conseguir siguiendo la primera recomendación de `esta respuesta`_; ten en cuenta, además, que si el tamaño de la ventana de tu navegador es superior al tamaño de la página (lo que puede suceder si abres la página sin haber añadido los diferentes cuestionarios), el pie de página no quedará pegado al borde inferior de la ventana; el comportamiento anterior es correcto y no has de cambiarlo.

.. _`esta respuesta`: http://stackoverflow.com/questions/9249359/is-it-possible-to-vertically-align-text-within-a-div/14850381#14850381

Las características particulares de la vista compacta son:

- usa el tipo de letra Ubuntu_ para todo el documento; para ver cómo usar en tus estilos un tipo de letra de Google Fonts, haz clic en :guilabel:`Select this font` en la página correspondiente al tipo de letra y después haz clic en la caja que aparece en la parte inferior de la ventana;
- cada pregunta/respuesta (selector ``.bloque``) tiene  un margen superior de 10px e inferior de 20px.

.. _Ubuntu: https://fonts.google.com/specimen/Ubuntu?selection.family=Ubuntu
.. _`página correspondiente al tipo de letra`: https://fonts.google.com/specimen/Ubuntu?selection.family=Ubuntu

Las características particulares de la vista normal son:

- usa el tipo de letra Droid Serif  para todo el documento; la web que describía_ este tipo de letra ya no está en Google Fonts, pero puedes seguir usándola añadiendo lo siguiente a tu página:

.. _describía: https://fonts.google.com/specimen/Droid+Serif

.. code-block:: html

    <link href='https://fonts.googleapis.com/css?family=Droid+Serif' rel='stylesheet' type='text/css'>

y lo siguiente a tu hoja de estilo:

.. code-block:: css

  font-family: 'Droid Serif', serif;

- cada pregunta/respuesta (selector ``.bloque``) tiene un fondo de color ``whitesmoke``; su borde es sólido de 1px de ancho y color ``lightgray``; el margen superior es de 10px y el inferior de 20px; el relleno (*padding*) es de 10px; la sombra de la caja se obtiene dando el siguiente valor a la propiedad CSS ``box-shadow`` (averigua para qué sirve cada parámetro):

.. code-block:: css

    box-shadow: 6px 6px 3px slategray;


Recomendaciones finales
~~~~~~~~~~~~~~~~~~~~~~~

Asegúrate de que tus ficheros se validan correctamente con los validadores HTML5 y CSS del W3C (usando la pestaña :guilabel:`Validate by File Upload` en ambos casos). Además, usa Chrome DevTools para comprobar que el estilo aplicado en cada punto del documento es correcto. Finalmente, asegúrate de que cumple con todas las especificaciones de este enunciado (por ejemplo, los nombres o valores de atributos, elementos o ficheros).

Recuerda poner tu nombre completo y DNI en el pie del documento. Realiza tu entrega en un único fichero comprimido llamado ``p1-dai.zip`` a través del `servidor web del Departamento`_. El archivo comprimido contendrá directamente (sin ninguna carpeta contenedora) el fichero ``index.html``, una carpeta ``css`` con los ficheros con las hojas de estilo que hayas usado y una carpeta ``img`` con las imágenes.

Por último, coloca en algún punto del pie de la página un fragmento de HTML como ``<span id="tiempo">[5 horas]</span>`` donde has de sustituir el 5 por el número de horas aproximadas que te haya llevado hacer esta prática.

.. _`servidor web del Departamento`: https://pracdlsi.dlsi.ua.es/index.cgi?id=val



Una aplicación web local
------------------------

En esta práctica extenderás la práctica anterior con la incorporación de elementos dinámicos mediante JavaScript. En particular, será posible añadir y eliminar cuestionarios, así como añadir y eliminar sus preguntas; todo ello en el navegador, sin interaccionar con ningún servidor o base de datos. Para ello, la sección ``main`` del documento tendrá al principio un formulario que permitirá añadir nuevos cuestionarios indicando su título y su imagen asociada; además, al principio de cada cuestionario (tras el título e inmediatamente antes de la primera pregunta, si la hubiera) se mostrará otro formulario que permitirá añadir una nueva pregunta y su respuesta (verdadero o falso) al final del cuestionario correspondiente. Se permitirá, además, borrar individualmente las preguntas de los cuestionarios.

Asegúrate de que sigues los siguientes pasos en el orden en que aparecen en estas instrucciones para que la realización de la práctica sea más sencilla. Repasa, además, todo lo estudiado en clase sobre JavaScript antes de comenzar la implementación. No puedes utilizar ninguna librería externa (por ejemplo, la clásica *jQuery*) en tu solución. Al igual que en la práctica anterior, tu documento ha de ser válido en cada momento.

Eliminación de la doble vista
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Elimina las dos vistas del documento de la práctica anterior y deja únicamente la vista *normal*. Para ello, borra todo el código JavaScript relacionado con el cambio de estilo, así como los dos enlaces que había en el pie de página para alternar entre los dos estilos. Fusiona todo el CSS que afectaba a la vista *normal* en un único documento CSS de nombre ``normal.css``.

Adición del formulario para insertar un nuevo cuestionario
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Añade el siguiente código al principio del bloque ``main`` de tu documento:

.. code-block:: html

  <div class="formulario" id="nuevoCuestionario">
    <ul>
      <li>
        <label>Tema del cuestionario:</label>
        <input type="text" name="tema" autofocus>
      </li>
      <li>
        <label>URL de la imagen:</label>
        <input type="url" name="imagen">
      </li>
      <li>
        <input type="button" name="crea" value="Crear nuevo cuestionario">
      </li>
    </ul>
  </div>

Este código define los elementos necesarios para el formulario de creación de un nuevo cuestionario. Cada campo a insertar se representa en el formulario con una etiqueta (elemento ``<label>``) y una entrada (elemento ``<input>``); ambos se incluyen como elementos dentro de una lista. El botón que se añade como último elemento de la lista ejecutará al ser pulsado el código JavaScript de creación del cuestionario. Es importante que respetes escrupulosamente el fragmento de código anterior, incluyendo los nombres de las clases e identificadores.

Date cuenta de que, en este caso, no usamos un elemento de tipo ``<form>``, sino un ``<div>`` para contener el formulario, con lo que no es necesario desactivar el envío de datos y la recarga automática de la página que ocurre con los formularios de tipo ``<form>``.

Estilo del formulario
~~~~~~~~~~~~~~~~~~~~~

Respeta las siguientes directrices a la de hora de dar estilo al formulario. Como más adelante usarás estos mismos estilos para el resto de formularios, basa tus selectores de CSS en la clase ``.formulario`` y no en el atributo ``id`` del formulario del apartado anterior:

- el elemento ``<ul>`` que contiene los distintos campos no usa ningún estilo de lista para sus elementos (de lo contrario, aparecería un topo o bala antes de cada elemento de la lista) y no tiene relleno (el *padding* es cero); además, su margen superior es de 30px, el inferior de 20px y el derecho e izquierdo son de 0px;
- cada elemento de la lista (elemento ``<li>``) tiene un relleno (por los cuatro lados) de 12px y un borde inferior sólido de grosor 1px y color ``#eee``;
- además, el primer elemento de la lista tendrá un borde superior sólido de grosor 1px y color #777; el último elemento de la lista tendrá un borde inferior de idénticas características; identifica cuál de las `pseudoclases de CSS`_ te puede ser útil para esto;
- el contenido de los elementos ``<label>`` se ha de mostrar con el valor ``inline-block`` para la propiedad ``display`` (que trata el contenido del elemento como una combinación de ``inline`` y ``block``) lo que nos permitirá darle un ancho fijo de 15em y conseguir que las cajas de introducción de texto queden bien alineadas unas respecto a otras;
- aquellos elementos de tipo ``<input>`` del formulario que tengan su atributo ``type`` con valor ``text`` o ``url`` (el botón, por tanto, queda excluido) tendrá un borde sólido de 1px de grosor y color ``#aaa``; añádeles, además, estos atributos para conseguir una mayor definición de la caja:

.. code-block:: css

  box-shadow: 0px 0px 3px #ccc, 0 5px 8px #eee inset;
  border-radius:2px;

.. _`pseudoclases de CSS`: https://developer.mozilla.org/en-US/docs/Web/Guide/CSS/Getting_started/Selectors

Documento HTML
~~~~~~~~~~~~~~

Las únicas diferencias en el documento HTML respecto a la práctica anterior es la supresión de todo lo relacionado con el uso de las dos hojas de estilo, la incorporación del formulario de creación de cuestionarios y, evidentemente, la inserción de un elemento ``<script>`` para cargar desde un fichero externo (atributo ``src`` de ``<script>``) el código JavaScript que escribas. Ten en cuenta que tu documento HTML no puede contener ningún código en CSS ni en JavaScript.

Adición de iconos para borrar cada pregunta
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

En primer lugar, vamos a añadir a las (cinco) cajas existentes para bloques de pregunta un icono que permita eliminar el bloque completo (número, enunciado y respuesta) de la página. Crea para ello una función ``addCruz`` que reciba como parámetro un objeto de tipo nodo que apunte a un elemento de clase ``.bloque``. La función creará un nodo que contega un elemento como el siguiente

.. code-block:: html

  <div class="borra">☒</div>

y lo insertará como primer hijo del nodo ``.bloque`` pasado como parámetro. El contenido corresponde al carácter Unicode `2612`_.

.. _`2612`: http://unicode-table.com/en/2612/

El estilo de los elementos de clase ``.borra`` usará posicionamiento absoluto para situarse a 2px del extremo derecho y 1px del extremo superior de la caja del elemento ``.bloque`` que lo contiene. *Nota:* para que este posicionamiento funcione tendrás que *posicionar* el elemento ``.bloque``. Además, el cursor del ratón al pasar por encima de la cruz de borrado adoptará el estilo ``pointer``.

Por último, añade un manejador de evento al nuevo nodo de manera que se invoque a una función ``borraPregunta`` (definida más adelante) cuando se haga clic en el elemento.

Recuerda que puedes evaluar la corrección de tu función desde la consola de JavaScript del navegador.

Funciones auxiliares a crear
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Durante la implementación de la práctica te serán de utilidad algunas funciones que puedes definir y evaluar ahora:

- ``insertAsLastChild(padre,nuevoHijo)``: inserta el nodo ``nuevoHijo`` como último hijo del nodo ``padre``.
- ``insertAsFirstChild(padre,nuevoHijo)``: inserta el nodo ``nuevoHijo`` como primer hijo del nodo ``padre``.
- ``insertBeforeChild(padre,hijo,nuevoHijo)``: inserta el nodo ``nuevoHijo`` como hijo del nodo ``padre`` inmediatamente antes del nodo ``hijo``.
- ``removeElement(nodo)``: elimina del DOM el nodo pasado como parámetro.

Además, te será de suma utilidad disponer de una función que funcione de forma similar a ``querySelector`` pero buscando el primer ancestro (en lugar de descendiente) que concuerde con el selector:

- ``queryAncestorSelector(node,selector)``: devuelve el ancestro más cercano a ``node`` que case con el selector indicado como segundo parámetro o ``null`` si no existe ninguno; ``node`` ha de ser un nodo inferior en el árbol a ``document.body``.

La siguiente es una posible implementación de la función que puedes copiar en tu práctica después de asegurarte de que la entiendes perfectamente:

.. code-block:: javascript

  function queryAncestorSelector (node,selector) {
    var parent= node.parentNode;
    var all = document.querySelectorAll(selector);
    var found= false;
    while (parent !== document && !found) {
      for (var i = 0; i < all.length && !found; i++) {
        found= (all[i] === parent)?true:false;
      }
      parent= (!found)?parent.parentNode:parent;
    }
    return (found)?parent:null;
  }

.. Note::

  Versiones recientes de los navegadores permiten usar la función closest_ que tiene un comportamiento similar a ``queryAncestorSelector``, pero no es necesario que la uses en tu práctica.

  .. _closest: https://developer.mozilla.org/en-US/docs/Web/API/Element/closest

Esta función la usaras cuando desde un nodo determinado del DOM quieras acceder a un ancestro para el que conoces un selector, pero no conoces la *distancia* exacta a la que se encuentra o no te interesa hacer que tu código dependa en exceso de dicha distancia porque en el futuro podría haber más nodos intermedios en el árbol y no quieres tener que modificar el código de JavaScript si esto ocurre. Por ejemplo, considera este fragmento de HTML:

.. code-block::  html

  <a>
    <b>
      ...
      <c>
        <d>
        </d>
      </c>
      ...
      <e>
      </e>
      ...
      <f>
      </f>
    </b>
  </a>

Si ``x`` representa el nodo correspondiente al elemento ``d`` y quieres acceder a información del nodo ``a``, podría hacerse algo como:

.. code-block::  javascript

  x.parentNode.parentNode.parentNode

o, con ayuda de la nueva funcion, simplemente

.. code-block::  javascript

  queryAncestorSelector(x,"a");

Otro ejemplo: para acceder al elemento ``f`` desde ``x``, se puede hacer:

.. code-block::  javascript

  queryAncestorSelector(x,"b").querySelector("f");

Borrado de preguntas
~~~~~~~~~~~~~~~~~~~~

Escribe ahora el código para ``borraPregunta``, el manejador del evento discutido en más arriba. Esta función usará el objeto de tipo evento recibido como parámetro para acceder al elemento sobre el que se ha hecho clic. A partir de este elemento, usando ``queryAncestorSelector``, accederá al ancestro con selector ``.bloque`` y lo eliminará del documento, es decir, eliminará el nodo correspondiente del DOM. Además, cuando el cuestionario se quede sin ninguna pregunta, este se eliminará por completo del DOM, así como su entrada en el índice al principio de la página.

Incorporación automática de los botones de borrado
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Crea una función ``init``, que sea invocada por el manejador del evento ``load`` y que recorra todos los elementos de clase ``.bloque`` e invoque la función ``addCruz`` (definida anteriormente) sobre cada uno de ellos. En estos momentos, al abrir tu documento, cada pregunta debería tener su icono de borrado y debería ser posible dejar el documento sin cuestionarios tras borrar todos los bloques de preguntas.

Adición de formularios de inserción de preguntas
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

A diferencia del formulario de creación de cuestionarios, el formulario de adición de una nueva pregunta se crea dinámicamente para cada cuestionario. Crea ahora una función ``addFormPregunta`` que se encargue de ello. Esta función recibe como parámetro el nodo correspondiente al elemento ``section`` de un determinado cuestionario, crea dinámicamente un formulario como el siguiente y lo inserta a continuación del título del cuestionario (antes de la primera pregunta, si la hubiera):

.. code-block:: html

  <div class="formulario">
    <ul>
      <li>
        <label>Enunciado de la pregunta:</label>
        <input type="text" name="paris_pregunta">
      </li>
      <li>
        <label>Respuesta:</label>
        <input type="radio" name="paris_respuesta" value="verdadero" checked>Verdadero
        <input type="radio" name="paris_respuesta" value="falso">Falso
      </li>
      <li>
        <input type="button" value="Añadir nueva pregunta">
      </li>
    </ul>
  </div>

Un comentario sobre los atributos ``name`` del formulario: dado que estos no pueden tener los mismos valores para los distintos formularios del documento (de otro modo, todos los botones de radio serían considerados como un único conjunto por el navegador y activar uno de ellos en un cuestionario desactivaría el resto de botones en los otros cuestionarios), en esta práctica has de añadir como prefijo el nombre del atributo ``id`` del elemento ``section`` correspondiente seguido de un carácter subrayado.

La función finalizará especificando la función ``addPregunta`` (analizada a continuación) como función manejadora del evento de clic sobre el botón.

Inserción de nuevas preguntas en un cuestionario
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Al igual que la función ``borraPregunta``, la función ``addPregunta`` utilizará también el objeto de tipo evento recibido como parámetro para acceder (con ayuda de funciones como ``querySelectorAncestor`` o ``querySelector``) a los datos introducidos en el formulario correspondiente.

En primer lugar, la función comprobará que ninguno de los campos del formulario haya quedado sin rellenar; si alguno de los campos estuviera vacío, se mostrará un `diálogo de alerta`_ con un texto descriptivo de la causa del error que puedes escoger libremente. En otro caso, se procederá a crear un nuevo elemento ``<div>`` de clase ``.bloque`` para la nueva pregunta, al que se añadirá el icono de borrado mediante una llamada a la función ``addCruz``.

.. _`diálogo de alerta`: https://developer.mozilla.org/en-US/docs/Web/API/Window.alert

Finalmente, la función dejará en blanco el contenido de los campos del formulario, excepto el correspondiente a los botones de radio, que se quedará en el valor *verdadero*.

Incorporación automática de los formularios de inserción de preguntas
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Añade código a la función ``init`` que recorra todos los elementos de tipo ``section`` del documento inicial e inserte en ellos los formularios de adición de preguntas con la función definida anteriormente.

Creación de nuevos cuestionarios
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Añade también a la función ``init`` código que asocie la función ``addCuestionario`` explicada a continuación como manejadora del evento de clic sobre el botón del formulario de creación de nuevo formulario.

La función ``addCuestionario`` utilizará el objeto de tipo evento recibido como parámetro para acceder (con ayuda de funciones como ``querySelectorAncestor`` o ``querySelector``) a los datos introducidos en el formulario de creación de cuestionarios. La función comprobará que ninguno de los campos del formulario haya quedado sin rellenar; si alguno de los campos estuviera vacío, se mostrará un diálogo de alerta con un texto descriptivo de la causa del error que puedes escoger libremente. No has de comprobar, en cualquier caso, que la URL indicada sea válida ni corresponda a una imagen existente en internet. Si todos los campos del formulario se han rellenado, se procederá a crear un elemento de tipo ``<section>`` que acogerá un nuevo formulario y que se añadirá a continuación del último formulario de la página. El título del cuestionario será "Cuestionario sobre " seguido del valor del primer campo del formulario; la URL de la imagen a usar será la indicada en el segundo campo. Recuerda también que has de añadir una nueva entrada al índice de cuestionarios de la cabecera del documento.

Dado que cada sección ha de tener un atributo de tipo ``id`` (por ejemplo, para enlazarlo desde el índice), en tu implementación usa como valor del identificador el carácter "c" seguido del valor de una variable global que se inicializará a 1 y se incrementará tras la creación de cada cuestionario. Date cuenta de que los cuestionarios presentes inicialmente en la página web ya tienen sus propios valores de ``id``, por lo que el primer cuestionario que se cree tendrá ``c1`` por ``id``, el segundo ``c2``, etc. El contador global nunca se decrementará, aunque se borre un cuestionario. Observa, también, que la única manera de borrar un cuestionario es mediante el borrado de la última de sus preguntas, por lo que no es posible en esta práctica borrar un cuestionario para el que no se ha introducido ninguna pregunta aún.

Tras la creación e inserción del nuevo elemento ``<section>``, se procederá a incorporarle el formulario de creación de preguntas mediante la oportuna llamada a ``addFormPregunta``.

Además, la función ``addCuestionario`` dejará en blanco el contenido de los campos del formulario.

Captura de pantalla
~~~~~~~~~~~~~~~~~~~

Observa en `esta imagen`_ como quedaría la página web una vez añadidos dos cuestionarios con sendas preguntas.

.. _`esta imagen`: _static/img/dai-p2-captura.png

Entrega de la práctica
~~~~~~~~~~~~~~~~~~~~~~

Asegúrate de que tanto tus ficheros iniciales como cualquier estado posterior del DOM se validan correctamente con los validadores HTML5 y CSS del W3C. Además, usa Chrome DevTools para comprobar que el estilo aplicado en cada punto del documento es correcto y para depurar tu código en JavaScript. Finalmente, asegúrate de que tu implementación cumple con todas las especificaciones de este enunciado.

Recuerda mantener tu nombre completo y DNI en el pie del documento. Realiza tu entrega en un único fichero comprimido llamado ``p2-dai.zip`` a través del servidor web del Departamento. El archivo comprimido contendrá directamente (sin ninguna carpeta contenedora) el fichero ``index.html``, una carpeta ``css`` con el fichero ``normal.css``, una carpeta ``img`` con las imágenes de Londres y París, y una carpeta ``js`` con el código en JavaScript.

Por último, coloca en algún punto del pie de la página un fragmento de HTML como ``<span id="tiempo">[10 horas]</span>`` donde has de sustituir el 10 por el número de horas aproximadas que te haya llevado hacer esta práctica.



Una aplicación con acceso a servicios web de terceros y con componentes web
---------------------------------------------------------------------------

En esta práctica ampliarás tu práctica anterior para integrarla con diferentes servicios web proporcionados por terceros a través de APIs; en particular, la imagen a mostrar junto al título de cada cuestionario será tomada de alguna de las imágenes relevantes ofrecidas por `Flickr`_; además, cada cuestionario mostrará un pequeño texto extraido de `Wikipedia en español`_ sobre el tema en cuestión. En la segunda parte, crearás algunos componentes web para encapsular adecuadamente toda esta información.

.. _`Flickr`: https://www.flickr.com/
.. _`Wikipedia en español`: https://es.wikipedia.org/

No está permitido usar jQuery u otras librerías para interactuar con los distintos servicios web, sino que lo has de hacer con el API Fetch estándar estudiado en clase. Tampoco está permitido usar librerías de alto nivel para los componentes web.

Ejemplo de peticiones GET
~~~~~~~~~~~~~~~~~~~~~~~~~

Lo siguiente es un ejemplo de la petición que has de realizar para obtener información sobre París:

`<https://es.wikipedia.org/w/api.php?origin=*&format=json&action=query&prop=extracts&exintro&explaintext&continue&titles=parís>`_

Consulta en la `documentación del API de Wikipedia`_ el propósito de cada parámetro; la mayor parte de ellos, en cualquier caso, proviene de la `extensión TextExtracts`_. Usa algunos ejemplos para determinar cuál es la propiedad de la cadena en JSON devuelta que contiene la información que te interesa y qué ocurre cuando el término no se encuentra en la Wikipedia.

.. _`documentación del API de Wikipedia`: https://www.mediawiki.org/wiki/API:Main_page/en
.. _`extensión TextExtracts`: https://www.mediawiki.org/wiki/Extension:TextExtracts

Por otro lado, lo siguiente es un ejemplo de la petición que has de realizar a Flickr para obtener las imágenes más relevantes de París (es necesario indicar un valor correcto de ``api_key`` en lugar de ``xxxxx``, según se indica más adelante):

`<https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=xxxxx&text=par%C3%ADs&format=json&per_page=10&media=photos&sort=relevance&nojsoncallback=1>`_

Consulta la `documentación del API de Flickr`_ para entender el propósito de cada parámetro de la llamada anterior; el resultado es una lista de imágenes de la que nos interesa el *id* de la primera para realizar una segunda llamada que nos permita acceder a la URL de dicha imagen:

.. _`documentación del API de Flickr`: https://www.flickr.com/services/api/

`<https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=xxxxxx&photo_id=13942935893&format=json&nojsoncallback=1>`_

De la lista de imágenes devuelta por la petición anterior, te has de quedar con la primera de ellas, que corresponderá a la versión de menor tamaño; ten en cuenta, en cualquier caso, que tu estilo CSS seguirá ajustando la imagen a un tamaño concreto, como se hizo en prácticas anteriores. Usa siempre en tu práctica las dos peticiones consecutivas a Flickr y no intentes componer automáticamente la URL de la imagen tras la primera petición. Además, utiliza el protocolo *https* en todas las peticiones a Wikipedia y Flickr.

Incorporación de información de la Wikipedia
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

La petición a Wikipedia se hará de forma asíncrona tras añadir el formulario del cuestionario; el resultado devuelto se añadirá inmediatamente antes del nodo del formulario y después del título del cuestionario en un ``div`` con clase ``wiki``. Si no existe ninguna entrada en Wikipedia para el término correspondiente, este ``div`` ha de incluirse pero sin contenido alguno. El estilo aplicado a los elementos de clase ``wiki`` utiliza un tamaño de tipo de letra del 90%; este es el único cambio en el CSS que has de realizar por ahora.

Ten en cuenta que al borrar un cuestionario también se ha de borrar ahora el texto descriptivo asociado. Muestra el contenido del atributo ``extract``, cuando exista, de todas aquellas claves contenidas en ``query.pages`` (``query.pages.*.extract``); si hay más de uno, cada uno aparecerá a continuación del anterior y en el mismo orden en que aparezcan en la respuesta.

Comienza modificando la función ``addFormPregunta`` para que devuelva el nodo del formulario creado. A continuación, crea una función ``addWikipedia`` que reciba como parámetros la cadena con el término a buscar y el nodo que representa el formulario del cuestionario; esta función utiliza el API de la Wikipedia de forma asíncrona para añadir la descripción devuelta por la Wikipedia para el término indicado; el punto de inserción será antes del formulario cuyo nodo se ha pasado como parámetro.

Asegúrate de que a la vez que añades los formularios a los cuestionarios existentes inicialmente (sobre París y Londres), también llamas a ``addWikipedia`` para incorporar la descripción correspondiente. Usa como término a buscar el ``id`` de cada elemento ``section``; para que esto funcione cambia el ``id`` del primer cuestionario de ``paris`` a ``parís`` en el fichero HTML (HTML5 permite casi cualquier carácter como valor de un atributo *id* por lo que puedes utiliza caracteres acentuados sin problema). *Nota:* para los cuestionarios añadidos dinámicamente no usarás el *id*, ya que este tendrá la forma *c1*, *c2*, etc., sino que usarás el tema obtenido del formulario de inserción.

No olvides añadir la correspondiente llamada a ``addWikipedia`` a la función ``addCuestionario``.

Usa una `expresión regular`_ y el método ``replace`` aplicado a cadenas de JavaScript para eliminar todos los números entre corchetes (incluyendo los corchetes) del resultado devuelto por la Wikipedia.

.. _`expresión regular`: https://www.tutorialrepublic.com/javascript-tutorial/javascript-regular-expressions.php

Incorporación de la información de Flickr
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Créate en primer lugar un usuario en `Flickr`_ y accede al `apartado de desarrolladores`_ para obtener una clave (*API key*) que usar en el parámetro ``api_key`` de la petición.

.. _`apartado de desarrolladores`: https://www.flickr.com/services/api/misc.api_keys.html

Sigue unos pasos similares a los del texto de la Wikipedia, pero ahora con la imagen. Crea una función ``addFlickr`` que reciba como parámetros la cadena con el término a buscar y el nodo que representa la imagen del cuestionario; esta función utiliza el API de Flickr de forma asíncrona para colocar (como valor del atributo ``src``) en el nodo recibido como parámetro la primera imagen devuelta por Flickr para el término correspondiente según se ha indicado anteriormente. En caso de que no exista ninguna imagen para dicho término, la imagen a mostrar ha de ser `esta del planeta Tierra`_.

.. _`esta del planeta Tierra`: http://eoimages.gsfc.nasa.gov/images/imagerecords/57000/57723/globe_east_540.jpg

Asegúrate de que a la vez que añades los formularios y la descripción de la Wikipedia a los cuestionarios existentes inicialmente (sobre París y Londres), también llamas a ``addFlickr`` para incorporar la imagen correspondiente, que sustituirá a la mostrada inicialmente. Usa de nuevo como término a buscar el ``id`` de cada elemento ``section``.

Finalmente, añade la correspondiente llamada a ``addFlickr`` a la función ``addCuestionario`` y comprueba que se añade correctamente una nueva imagen con cada nuevo cuestionario. Elimina el campo del formulario de nuevo cuestionario que permitía indicar la URL de la imagen a incluir (borra el elemento ``li`` correspondiente), ya que ya no es necesario; asegúrate también de que no queda rastro de él en el código JavaScript.

Creación de los componentes web
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

En esta parte final de la práctica has de crear un componente web que muestre el encabezado de cada cuestionario. Este componente web sustituirá al actual bloque de HTML que contiene el título, la imagen y la descripción de cada cuestionario, de manera que en lugar de introducirlo con un código similar a este (y con el correspondiente código de JavaScript):

.. code-block:: html

  <section id="parís">

  <h2><img src="..." alt="...">Cuestionario sobre París</h2>
  <div class="wiki">...</div>

bastará con hacer:

.. code-block:: html

  <section id="parís">

  <encabezado-cuestionario tema="París"></encabezado-cuestionario>

Si repasas bien los contenidos sobre componentes web estudiados en clase, no te debería costar mucho implementar el componente web en un fichero ``encabezado-cuestionario.js``. Ve definiendo el componente sin eliminar hasta el final el código que ya tenías. Ten en cuenta los siguientes detalles:

- Los estilos de ``h2``, ``img`` y ``wiki`` se han de eliminar del CSS global y añadirlos al estilo del componente ``encabezado-cuestionario``.

- Gran parte del código necesario para interactuar con los servicios web de Flickr y Wikipedia lo puedes tomar de las funciones ``addFlickr`` y ``addWikipedia`` que ya tenías definidas, aunque los elementos que creaban estas funciones se añadían al DOM y ahora se añadirán al *shadow DOM*.

- Intenta, en principio, conseguir que algo como:

.. code-block:: html

  <encabezado-cuestionario tema="Berlín"></encabezado-cuestionario>

funcione en ``index.html``. Cuando lo consigas, sustituye el encabezado de los cuestionarios iniciales existentes en ``index.html`` por el uso del elemento personalizado ``encabezado-cuestionario``. Ya no es necesario, tampoco, tener que añadir el texto de la Wikipedia o la imagen de Flickr a los cuestionarios preexistentes mediante código explícito en JavaScript, sino que el nuevo elemento se encargará de ello.

- Repasa el tema de componentes web visto en clase antes de comenzar a escribir los componentes web. Pon el código en JavaScript que se encarga de acceder a los servicios de Flickr y Wikipedia en la función ``connectedCallback`` de la clase correspondiente y no en el constructor. Pon también en ``connectedCallback`` el acceso al atributo ``tema``.

- Ten en cuenta a qué apunta ``this`` en cada momento; tu código del interior del método ``then`` asociado a una promesa ``fetch`` se ejecutará (cuando el servidor devuelva su respuesta) de forma asíncrona fuera del contexto del componente web; en ese caso, ``this`` no estará apuntando al componente web, por lo que para acceder a sus propiedades tendrás que utilizar correctamente una clausura:


.. code-block:: javascript

  connectedCallback() {
    var componente= this;  // aquí this apunta al componente web
    fetch(...)
    .then(...)
    .then(
      componente.shadowRoot.querySelector(...).textContent= ...;   
    )
    ...
  }


- Para terminar, puedes eliminar también las antiguas ``addFlickr`` y ``addWikipedia`` del código de JavaScript, así como sustituir su uso cuando se crean nuevos cuestionarios por código que se encargue de la inserción oportuna del componente web.

Captura de pantalla
~~~~~~~~~~~~~~~~~~~

Observa `en una imagen`_ cómo quedaría la página web una vez añadidos dos cuestionarios y algunas preguntas. Ten en cuenta que el texto descriptivo o las imágenes podrían no corresponderse exactamente con las que los servicios web de Wikipedia o Flickr ofrezcan en el momento en que pruebes tu práctica; en la imagen, además, no se han eliminado los números entre corchetes.

.. _`en una imagen`: _static/img/dai-p3-captura.png

Entrega de la práctica
~~~~~~~~~~~~~~~~~~~~~~

Asegúrate de que tanto tus ficheros iniciales como cualquier estado posterior del DOM se validan correctamente con los validadores HTML5 y CSS del W3C con excepción de los elementos personalizados, que posiblemente generen algún tipo de error. Además, usa Chrome Developer Tools o Firebug para comprobar que el estilo aplicado en cada punto del documento es correcto y para depurar tu código en JavaScript.

Nota: recuerda mantener un tu nombre completo y DNI en el pie del documento. Realiza tu entrega en un único fichero comprimido llamado ``p3-dai.zip`` a través del `servidor web del Departamento`. El archivo comprimido contendrá directamente (sin ninguna carpeta contenedora) el fichero ``index.html``, una carpeta ``css`` con el fichero ``normal.css`` y una carpeta ``js`` con el código en JavaScript.

Por último, coloca en algún punto del pie de la página un fragmento de HTML como ``<span id="tiempo">[10 horas]</span>`` donde has de sustituir el 10 por el número de horas aproximadas que te haya llevado hacer esta práctica.



Una aplicación en la nube
-------------------------

Esta práctica tiene dos partes. En la primera parte vas a ampliar la práctica anterior, que solo tenía *front-end*, para añadirle un *back-end* que dé persistencia a la aplicación y permita gestionar una base de datos con la información de los cuestionarios. La aplicación resultante se implantará en la plataforma Google App Engine y los datos se almacenarán en una base de datos MySQL alojada en el servicio Google Cloud SQL, de manera muy similar a la aplicación del carrito que has estudiado en clase. Como en prácticas anteriores, no puedes usar ninguna librería, como jQuery o Angular, en la parte del cliente.

.. Note::

  La segunda parte del enunciado se publicará próximamente. 

Cuando el usuario entre en la aplicación, se le mostrará el formulario para insertar nuevos cuestionarios; inicialmente no habrá ningún cuestionario creado y, por tanto, ya no aparecerán los cuestionarios de París ni Londres. La aplicación funcionará como una *aplicación de una única página* (en inglés, *SPA* por *single-page application*): cada vez que el usuario introduzca o elimine datos, los cuestionarios se actualizarán convenientemente en la página como hasta ahora, pero también lo harán en la base de datos del servidor.

Como en el ejemplo del carrito visto en clase, el código del servidor estará escrito con Node.js y Express, y funcionará con una base de datos SQLite mientras se ejecute localmente y con MySQL cuando se ejecute desde Google App Engine. Al usar Knex.js, la mayor parte del código para ambas opciones será el mismo, como vimos en la plaicación del carrito. Asimismo, la aplicación será la encargada de crear el esquema de la base de datos si las tablas no existen.


Comprobación de la aplicación del carrito
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Comienza asegurándote de que entiendes cómo funciona la apicación del carrito vista en clase y que eres capaz de ejecutarla localmente en ``localhost`` y en la nube de Google App Engine. 

Incorporación e implantación de la aplicación de la práctica anterior
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Añade ahora en la carpeta adecuada de la aplicación del carrito, los ficheros de tu práctica 3 de forma que *index.html* esté alojado donde corresponda y el resto de elementos de tu aplicación mantengan su estructura relativa. Lanza la aplicación en modo local y comprueba que funciona correctamente. A continuación, implanta tu aplicación en la nube de Google App Engine y comprueba que, de nuevo, sigue funcionando correctamente. El único cambio que quizás tengas que hacer para que tu aplicación funcione en la nube de Google vendría dado porque a la hora de indicar los tipos de letra de Google Fonts o las direcciones de acceso a las APIs de Wikipedia o Flickr hubieras usado el protocolo *http* en lugar de *https*; en ese caso, tendrías que cambiarlo ahora ya que a las aplicaciones de Google App Engine se accede mediante *https* y desde una página descargada de forma segura no es posible referenciar recursos con URLs no seguras.

Como la página ya no contiene inicialmente ningún cuestionario, puedes borrar de la función *init* el código que se encargaba de añadir a cada uno de los cuestionarios existentes la cruz de borrado y el formulario de inserción de preguntas.

Características comunes de los servicios web a implementar
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Los servicios web a implementar serán de tipo GET, POST o DELETE según su cometido y recibirán la información y la devolverán en formato JSON. La respuesta del servidor siempre seguirá el siguiente formato::

  {"result":...,"error":...}

Cuando la petición se haya podido atender correctamente, el atributo ``result`` contendrá la información relevante que haya que devolver al cliente y el atributo ``error`` valdrá ``null``; el código de estado HTTP devuelto en este caso será 200. Por otro lado, si algún problema impide en tu código atender correctamente la solicitud (por ejemplo, se intenta eliminar un cuestionario inexistente), la respuesta del servidor contendrá la información adecuada en el atributo ``error`` y el atributo ``result`` valdrà ``null``; el código de estado devuelto en este caso será 404. Una situación típica de error que has de controlar es que los parámetros esperados de cada servicio web sean incorrectos o no existan.

Cada vez que en el código de JavaScript realices una petición Fetch a uno de los servicios del *back-end*, tendrás que comprobar si el JSON devuelto contiene un valor distinto de ``null`` en la propiedad ``error``; en ese caso, la aplicación mostrará una ventana de alerta (función ``alert``) con un error informativo seguido del contenido de ``error``; además, todas tus peticiones Fetch definirán una función que muestre una ventana de alerta similar ante el resto de posibles situaciones de error (por ejemplo, no se puede establecer la conexión con el servidor).

Ninguna de las acciones que se tengan que efectuar sobre la página actual del navegador como consecuencia de una acción de inserción o borrado por parte del usuario se llevarán a cabo si el servidor devuelve un error (por ejemplo, no se añadirá un cuestionario a la página actual si el servidor no informa de que lo ha añadido con éxito a la base de datos). Ante estas situaciones de error, como ya se ha comentado, habrá, además, que mostrar una ventana de alerta.

Almacenamiento de los cuestionarios en el Datastore
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

En esta práctica crearás servicios web siguiendo el patrón REST para lo siguiente:

- añadir un tema de cuestionario (POST) y devolver el id asignado en la base de datos;
- recuperar todos los temas (GET); 
- borrar un tema a partir de su id y todas sus preguntas (DELETE);
- añadir una pregunta y su correspondiente respuesta a un cuestionario dado el id del tema (POST) y devolver el id de la pregunta en la base de datos;
- obtener todas las preguntas y respuestas dado el id del tema (GET);
- borrar una pregunta dado su id (DELETE).

Usa URLs con patrones similares a los de la aplicación del carrito del tema de servicios web.

Representación de los datos en la base de datos
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

La base de datos contendrá una tabla para representar los cuestionarios y otra para representar las preguntas. Cada cuestionario vendrá representado por un identificador único generado automáticamente (clave primaria) y su tema. Cada pregunta vendrá representada por un identificador único (clave primaria), el identificador de su tema (clave ajena), el texto de la pregunta y su respuesta correcta.

Conéctate tanto a la base de datos SQLite (cuando pruebes la aplicación en local) como a MySQL (en Google App Engine) para comprobar que las tablas se están rellenando o actualizando correctamente tras cada llamada a un servicio web.

Gestión de los temas de los cuestionarios
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Comienza creando un servicio web que añada un nuevo tema a la base de datos. Como ya se ha comentado, el cuestionario solo se añade a la página actual si la respuesta del servicio es positiva; en otro caso, la solicitud del usuario de crear un nuevo cuestionario no tiene efecto sobre la página del navegador. Este servicio no permite tener más de un cuestionario con el mismo tema. Si, por ejemplo, ya existiera un cuestionario sobre Lugano en la base de datos, la respuesta sería (tu mensaje de error no ha de coincidir necesariamente)::

  { "result":null,"error":"el tema Lugano ya existe en la base de datos"}

A continuación, crea un servicio web que elimine de la base de datos un tema. Después, modifica el código JavaScript del cliente para que invoque este servicio cuando proceda borrar un cuestionario. El cuestionario solo se elimina de la página actual si la respuesta del servicio es positiva. A diferencia de prácticas anteriores, por tanto, en esta es posible que al borrar la última pregunta de un cuestionario, este no desaparezca; esto puede ocurrir ya que la pregunta se borra en primer lugar y, una vez borrada, se ha de proceder a intentar borrar el cuestionario que la incluía; si este último borrado falla, la pregunta eliminada no se recupera. El servicio devolverá en la respuesta en JSON un error si el identificador del tema indicado en los datos de la petición no existe en la base de datos.

Ahora crea un servicio que liste los temas disponibles en la base de datos. Añade el código necesario para invocar el nuevo servicio desde la función *init* de forma que se muestren los encabezados (y los formularios de inserción de preguntas) de los cuestionarios almacenados en la base de datos al cargar la aplicación. Observa que gran parte del código para lo anterior ya existe en la función *addCuestionario*, por lo que te puede interesar refactorizar y crear una nueva función con el código común. Asimismo, observa, que el identificador (*c1*, *c2*, etc.) asignado inicialmente a un cuestionario no tiene por qué mantenerse cuando se recarga la aplicación; eso sí, igual que se ha venido haciendo hasta ahora, el primer cuestionario que se añada a la página será *c1*, el segundo *c2*, etc., independientemente de si se ha creado en ese momento por el usuario o se ha recuperado de la base de datos. 

En estos momentos, tu aplicación ha de permitir crear nuevos formularios y añadirles preguntas. Los temas de los formularios se almacenan ya correctamente en la base de datos, por lo que, aunque se cierre la ventana de la aplicación, esta información se vuelve a mostrar al abrirla de nuevo. Las preguntas, sin embargo, se pierden si se recarga la aplicación; en el apartado siguiente vas a solucionarlo.

Gestión de las preguntas de los cuestionarios
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

De forma análoga a como has hecho con los cuestionarios, vas a crear tres servicios web que permitan añadir, borrar y listar las preguntas asociadas a un cuestionario. Al igual que con los cuestionarios, no será posible añadir a la base de datos dos preguntas con el mismo enunciado para un tema concreto. Los posibles errores a detectar y las respuestas de los servicios son análogas a las del caso de los cuestionarios

Comprueba que tu aplicación permite crear y eliminar tanto cuestionarios como preguntas y que los datos persisten correctamente aun cuando se recarga la aplicación.

Segunda parte
~~~~~~~~~~~~~

La segunda parte del enunciado se publicará próximamente.

Entrega de la práctica
~~~~~~~~~~~~~~~~~~~~~~

Asegúrate de que tanto tus ficheros iniciales como cualquier estado posterior del DOM se validan correctamente con los validadores HTML5 y CSS del W3C con excepción de los elementos personalizados, que posiblemente generen algún tipo de error. Además, usa Chrome Developer Tools o Firebug para comprobar que el estilo aplicado en cada punto del documento es correcto y para depurar tu código en JavaScript del lado del cliente; usa Visual Studio Code para depurar el código de Node.js de la parte del servidor.

*Nota:* recuerda mantener un tu nombre completo y DNI en el pie del documento. Realiza tu entrega en un único fichero comprimido llamado ``p4-dai.zip`` a través del `servidor web del Departamento`_. El archivo comprimido contendrá directamente (sin ninguna carpeta contenedora) el fichero ``app.js`` y las carpetas que sean necesarias. Asegúrate de borrar la carpeta ``node_modules`` antes de crear el *zip* para que su contenido no se incluya en el fichero generado y evitar así que la práctica que entregues tenga más tamaño del necesario.

Sube tu aplicación a Google App Engine e incluye en el pie de página de tu aplicación un enlace al URL correspondiente en ``appspot.com``. Tu práctica será corregida descomprimiendo el fichero *zip*, haciendo::

  npm start

y accediendo al URL correspondiente en ``localhost``. También se evaluará usando el enlace a la aplicación en Google App Engine suministrado a pie de página.

Por último, coloca en algún punto del pie de la página un fragmento de HTML como ``<span id="tiempo">[10 horas]</span>`` donde has de sustituir el 10 por el número de horas aproximadas que te haya llevado hacer esta práctica.
