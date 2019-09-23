
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
      - Una aplicación web local
      - 29 octubre 2019 aprox.
    * - Práctica #3
      - Una aplicación con acceso a servicios web de terceros y con componentes web
      - 25 noviembre 2019 aprox.
    * - Práctica #4
      - Una aplicación en la nube
      - 23 diciembre 2019 aprox.


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

Por último, coloca en algún punto del pie de la página un fragmento de HTML como ``<span id="tiempo">[5 horas]</span>`` donde has de sustiruir el 5 por el número de horas aproximadas que te haya llevado hacer esta prática.

.. _`servidor web del Departamento`: https://pracdlsi.dlsi.ua.es/index.cgi?id=val
