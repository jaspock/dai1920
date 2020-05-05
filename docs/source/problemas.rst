.. role:: problema-contador-marcado
.. role:: problema-contador-estilo
.. role:: problema-contador-cliente
.. role:: problema-contador-servicios
.. role:: problema-contador-componentes
.. role:: problema-contador-nube

.. _label-problemas:

Problemas
=========

Lenguajes de marcado
--------------------

.. admonition:: :problema-contador-marcado:`Problema`
  :class: problema

  Indica con qué código HTML es necesario sustituir la cadena ``@1`` para que el siguiente bloque HTML sea válido y corresponda a una tabla con dos filas (la primera de encabezado) y una columna.

  .. code-block:: html
    :linenos:

    <table>
      <thead><tr><td><em>Nombre del río</em>@1<td>Ebro</td></tr>
    </table>

  .. @1=</td></tr></thead><tr>

.. ------

.. admonition:: :problema-contador-marcado:`Problema`
  :class: problema

  Indica en qué orden colocar las líneas de HTML de más abajo para que el fragmento de código HTML resultante sea correcto y se visualice en un navegador aproximadamente como sigue, sin usar ninguna hoja de estilo adicional:

  .. raw:: html

    <section><h4>Colores
    </h4>
    <ul>
    <li>azul
    </li>
    </ul>
    </section>
    
  .. code-block:: html
    :linenos:

    <li>azul
    <ul>
    </section>
    </li>
    <section><h4>Colores
    </h4>
    </ul>

  Una posible respuesta (incorrecta) tendría el formato ``1,2,3,4,5,6,7``.

  .. 5,6,2,1,4,7,3

.. ------

.. admonition:: :problema-contador-marcado:`Problema`
  :class: problema

  Dibuja el árbol DOM correspondiente al siguiente documento HTML. Representa todos los nodos, incluyendo aquellos que representan secuencias de blancos.

  .. code-block:: html
    :linenos:

    <!doctype html>
    <html lang="es"><head>
          <title>La historia interminable</title></head>
      <body><section><h2>La ciudad de los espectros</h2><p>Fújur se esforzó
      desesperadamente por encontrar otra vez el lugar en que Atreyu debía de haber
      caído al agua, pero hasta para un dragón blanco de la suerte es imposible
      descubrir en la espuma hirviente de un mar revuelto el puntito diminuto de un
      cuerpo que flota... o el de un ahogado en su fondo.</p><p>Sin embargo, Fújur
      no quiso renunciar.</p></section>
    </body>
    </html>

.. ------

.. admonition:: :problema-contador-marcado:`Problema`
  :class: problema

  Considera los siguientes datos: 

  - El carácter ``a`` (*Latin small letter a*, U+0061) en US-ASCII se repesenta como ``61`` en hexadecimal (``01100001`` en binario), igual que en ISO/IEC 8859 y que en UTF-8; en UTF-16 es ``FEFF0061`` (o en binario ``11111110 11111111 00000000 01100001``).
  - El carácter ``á`` (*Latin small letter a with acute*, U+00E1) se representa en ISO/IEC 8859-15 como ``E1`` y en UTF-8 como ``C3A1``.
  - El carácter ``Ã`` se representa en ISO/IEC 8859-15 como ``C3``.
  - El carácter ``¡`` se representa en ISO/IEC 8859-15 como ``A1``.

  Teniendo en cuenta los datos de las diapositivas anteriores, ¿cómo se ve un fichero de texto escrito en UTF-8 que contiene la cadena ``aáa`` en un editor de texto configurado para ISO/IEC 8859-15? ¿Cómo se ve un fichero de texto escrito en ISO/IEC 8859-15 que contiene la cadena ``aáa`` en un editor de texto configurado para UTF-8?

.. ------

.. admonition:: :problema-contador-marcado:`Problema`
  :class: problema

  Indica con qué código HTML es necesario sustituir las marcas ``@1`` y ``@2`` para que el siguiente bloque de HTML sea válido.

  .. code-block:: html
    :linenos:
    :force:

    <div>
      <img src="imagen.png" @1="diagrama de clases">
      <span @2-paquete="es.ua.dai">compilado sin errores</span>
    </div>

  .. solución: @1=alt,@2=data 
  .. examen enero 2020

.. ------

.. admonition:: :problema-contador-marcado:`Problema`
  :class: problema

  ¿Qué tamaño en bytes tiene en UTF-8 el carácter del avión (✈) si sabemos que con UTF-8 la cadena (sin las comillas) "Avión a reacción: ✈" ocupa 23 bytes? Nota: por si no se distingue bien, la cadena tiene 3 espacios en blanco.

  .. solución: 3
  .. examen enero 2020


Lenguajes de estilo
-------------------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Considera el siguiente fragmento de un documento HTML:

  .. code-block:: html
    :linenos:

    <body>
      <section>
        <header><h1>The Boy Who Lived</h1></header>
        <p>Mr. and Mrs. Dursley, of number four, Privet Drive, 
          were proud to say that they were perfectly normal, 
          thank you very much.</p>
        <p class="last">They were the last people you'd expect to 
          be involved in anything strange or mysterious, because they
          just didn't hold with such nonsense.</p>
      </section>
    </body>

  Considera también los siguientes estilos de CSS:

  .. code-block:: css
    :linenos:

    p {
      color: red;
    }
    p.last {
      color: gray;
    }
    section > p {
      color: blueviolet;
    }
    header h1 p {
      color: green;
    }
    section {
      color: lightskyblue;
    }
    p {
      color: black;
    }

  ¿De qué color se muestra el párrafo que comienza por "They were the last people..."? ¿Y el párrafo anterior a ese? Indica como respuesta los dos colores separados por una coma.

  .. solución: gray, blueviolet; https://jsfiddle.net/vhbc4t5s/

.. ------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Considera el siguiente fragmento de CSS:

  .. code-block:: css
    :linenos:

    .a {font-weight: normal;}
    .a .b {color: blue;}
    .a .b #c {color: red;}
    .destaca {font-weight: bold;}

  Indica con qué sustituir las dos arrobas (``@1``, ``@2``) para que dado el siguiente fragmento de HTML el texto *Privet Drive* se muestre en negrita y color rojo. Usa la notación ``@1=...,@2=...`` para tu respuesta.

  .. code-block:: html
    :linenos:

    <p class="a">El señor y la señora Dursley, que vivían en el 
    número 4 de @1 Privet Drive @2, estaban orgullosos de decir 
    que eran muy normales, afortunadamente.</p>

  .. solución: @1=<span class="b destaca" id="c"> / @1=<span class="destaca"><span class="b" id="c">, @2=</span>

.. ------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Dibuja de la forma más precisa que puedas cómo representaría un navegador el siguiente bloque de código. No es necesario que los colores o el tipo de letra coincidan. Todos los tamaños han de mantener de forma aproximada la misma proporcionalidad que tendrían en la ventana del navegador: decide cuál es el tamaño en papel de, por ejemplo, 10 píxeles, y mantén la escala en todos los elementos.

  .. code-block:: html
    :linenos:

    <body>
      <div id="peligrosas">
        colacuerno
        <div id="basilisco">basilisco</div>
      </div>
      <div id="hipogrifo">hipogrifo</div>
    <body>

  Considera que se están aplicando los siguientes estilos:

  .. code-block:: css
    :linenos:

    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }
    body {
      margin: 10px;
    }
    #peligrosas {         
      width: 200px;
      border: 1px solid darkgray;
      padding-left: 50px;
      padding-bottom: 50px;
    }
    #basilisco {
      width: 50px; 
    }
    #hipogrifo {
      width: 100px;
      border: 1px dotted darkgray;
      text-align: right;
      padding-bottom: 50px;
    }

  .. solución: https://jsfiddle.net/xep58sr7/

.. ------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Considera el siguiente fragmento de un documento HTML:

  .. code-block:: html
    :linenos:

    <body>
      <h1>Lista</h1>
      <section>
        <article>artículo1</article>
        <article>artículo2</article>
      </section>
    </body>

  Teníamos una hoja de estilo que asignaba estilos a cada elemento para que el documento se visualizara como sigue (el fondo gris representa la ventana del navegador):
  
  .. raw:: html

    <div id="problema-borrado">
      <script>
        var root = document.querySelector('#problema-borrado').attachShadow({mode:'open'});
        root.innerHTML = `
          <style>
          .cuadrados {
            background: gainsboro; 
            padding: 10px; 
            margin-bottom: 20px;
          }
          h1, section, article {
            display: inline;
          }
          h1::after {
            content: ": ";
          }
          h1 {
            font-family: sans-serif;
            font-style: italic;
          }
          article {
            font-family: serif;
          }
          </style>
          <div class="cuadrados">
            <h1>Lista</h1>
            <section>
              <article>artículo1</article>
              <article>artículo2</article>
            </section>
          </div>`;
      </script>
    </div>
  
  Lamentablemente, las propiedades del fichero CSS se nos han borrado y solo nos han quedado las siguientes reglas vacías que únicamente tienen selector pero ninguna propiedad:

  .. code-block:: css
    :linenos:

    h1, section, article {  }
    h1::after {  }
    h1 {  }
    article {  }

  Indica en qué regla de las anteriores hay que colocar cada una de las siguientes propiedades CSS para que el documento HTML se vuelva a visualizar como antes:

  1. ``font-family:serif``
  2. ``display:inline``
  3. ``font-family:sans-serif``
  4. ``content:": "``
  5. ``font-style:italic``

  Para abreviar, usa una notación como la de la siguiente posible respuesta (incorrecta): ``h1, section, article {1}`` / ``h1::after {1;2}`` / ``h1 {3;4}`` / ``article {5}``.

  .. solución: h1, section, article {2} / h1::after {4} / h1 {3,5} / article {1}; https://jsfiddle.net/b6qnrpy3/

.. ------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Dado el siguiente fragmento de un documento HTML, indica un selector que tenga menos de 10 caracteres y que permita seleccionar el párrafo que contiene la cadena ``dos``:

  .. code-block:: html
    :linenos:

    <body>
      <header>
        <h1>a</h1>
      </header>
      <main id="principal" class="info-descripción act">
        <h2>b</h2>
        <p>uno</p>
        <p id="info-detalle" class="act">dos</p>
      </main>
      <section>
        <h2>c</h2>
        <p>tres</p>
        <p lang="ca" class="act">quatre</p>
      </section>
    </body>

  .. solución: man .act; https://jsfiddle.net/2mt1p7he/

.. ------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Indica la palabra con la que rellenar el hueco de la siguiente frase para que sea correcta: el selector ``#a[href="https://example.org"]`` es un selector compuesto que incluye un selector de ``_____`` y un selector de identificador.

  .. solución: atributos

.. ------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Dados los siguientes estilos de CSS:
  
  .. code-block:: css
    :linenos:

    li {
      display: inline;
      margin: 0px 25px 0px 25px;
      padding: 10px 50px 10px 0px;
      border: 2px solid #000000;
    }

  Dibuja de la forma más aproximada posible cómo representaría el navegador el siguiente fragmento de HTML. Comienza pintando un recuadro que represente la ventana del navegador.

  .. code-block:: html
    :linenos:

    <p>Recuerdo cada varita que he vendido, Harry Potter. 
    Cada una de las varitas. 
    Y resulta que la cola de fénix de donde salió la pluma 
    que está en tu varita dio otra pluma,</p>
    <ul>
      <li>solo</li>
      <li>una</li>
      <li>más.</li>
    </ul>

  .. solución: https://jsbin.com/howativusi

.. ------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Considera el siguiente fragmento de un documento HTML:

  .. code-block:: html
    :linenos:

    <body>
      <div class="cuadrados">
        <div class="orange">naranja</div>
        <div class="blue">azul</div>
        <div class="lavender">lavanda</div>
        <div class="palegreen">verde</div>
      </div>
    </body>
    
  Considera también los siguientes estilos de CSS:

  .. code-block:: css
    :linenos:
    :force:

    .cuadrados {
      background: gainsboro; 
      padding: 10px; 
      margin-bottom: 20px;
    }
    .orange {         
      background: orange;
      height: 100px;
      width: 100px;
    }
    .blue {
      background: lightskyblue;
      height: 100px;
      width: 100px;
      position: relative;
      top: -100px;
      left: 100px;
    }
    .lavender {
      background: lavender;
      height: 100px;
      width: 100px;
      position: relative;
      top: -100px;
    }
    .palegreen {
      background: palegreen;
      height: 100px;
      width: 100px;
      position: relative;
      @1
    }

  Indica el código CSS por el que es necesario sustituir la marca ``@1`` para que el fragmento HTML se muestre como sigue:

  .. raw:: html

    <div id="problema-puzle">
    <script>
      var root = document.querySelector('#problema-puzle').attachShadow({mode:'open'});
      root.innerHTML = `
        <style>
        .cuadrados {
          background: gainsboro; 
          padding: 10px; 
          margin-bottom: 20px;
        }
        .orange {         
          background: orange;
          height: 100px;
          width: 100px;
        }
        .blue {
          background: lightskyblue;
          height: 100px;
          width: 100px;
          position: relative;
          top: -100px;
          left: 100px;
        }
        .lavender {
          background: lavender;
          height: 100px;
          width: 100px;
          position: relative;
          top: -100px;
        }
        .palegreen {
          background: palegreen;
          height: 100px;
          width: 100px;
          position: relative;
          top: -200px;
          left: 100px;
        }
        </style>
        <div class="cuadrados">
          <div class="orange">naranja</div>
          <div class="blue">azul</div>
          <div class="lavender">lavanda</div>
          <div class="palegreen">verde</div>
        </div>`;
    </script>
    </div>
    
  Considera que no hay otros estilos definidos que puedan entrar en conflicto con los que escribas.

.. ------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Dibuja de la forma más precisa que puedas cómo representaría un navegador el siguiente bloque de código. No es necesario que los colores o el tipo de letra coincidan. Todos los tamaños han de mantener de forma aproximada la misma proporcionalidad que tendrían en la ventana del navegador: decide cuál es el tamaño en papel de, por ejemplo, 50 píxeles, y mantén la escala en todos los elementos.

  .. code-block:: html
    :linenos:

    <div class="cuadrados">
      <div class="blue">azul</div>
      <div class="lavender">lavanda</div>
    </div>

  Considera que se están aplicando los siguientes estilos:

  .. code-block:: css
    :linenos:

    * {
      margin: 0;
      box-sizing: border-box;
    }
    .cuadrados {
      padding: 10px;
      border: 1px solid darkgray;
      height: 200px;
      width: 200px;
    }
    .blue {
      border: 1px dashed darkgray;
      height: 50px;
      width: 50px;
      position: relative;
      top: 100px;
      left: 50px;
    }
    .lavender {
      border: 1px solid darkgray;
      height: 100px;
      width: 100px;
    }

  .. solución: https://jsfiddle.net/3m6w1gbx/2/ 
  .. examen enero 2020

.. ------

.. admonition:: :problema-contador-estilo:`Problema`
  :class: problema

  Dibuja de la forma más precisa que puedas cómo representaría un navegador el siguiente bloque de código. No es necesario que los colores o el tipo de letra coincidan. Todos los tamaños han de mantener de forma aproximada la misma proporcionalidad que tendrían en la ventana del navegador: decide cuál es el tamaño en papel de, por ejemplo, 50 píxeles, y mantén la escala en todos los elementos.

  .. code-block:: html
    :linenos:

    <div class="cuadrados">
      <div class="blue">azul</div>
      <div class="lavender">lavanda</div>
    </div>

  Considera que se están aplicando los siguientes estilos:

  .. code-block:: css
    :linenos:

    * {
      margin: 0;
      box-sizing: border-box;
    }
    .cuadrados {
      padding: 10px;
      border: 1px solid darkgray;
      position: relative;
      height: 200px;
      width: 200px;
    }
    .blue {
      border: 1px dashed darkgray;
      height: 50px;
      width: 50px;
      position: absolute;
      top: 50px;
      left: 50px;
    }
    .lavender {
      border: 1px solid darkgray;
      height: 100px;
      width: 100px;
    }

  .. solución: https://jsfiddle.net/5zowyq0p/1/
  .. examen enero 2020


Programar el lado del cliente
-----------------------------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  ¿Qué imprime el siguiente programa de JavaScript por la consola?

  .. code-block:: javascript
    :linenos:

    function outer(z) {
      var b = z;
      function inner() {
        var a = 20; 
        console.log(a+b);
      }
      b+= 10;
      return inner;
    }

    var X = outer(10);
    var Y = outer(20); 
    X();
    Y();
    X();

  .. solución: 40, 50, 40

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Indica *una expresión* con la que sustituir la marca ``@1`` en el siguiente código en JavaScript para que la siguiente función permita contar el número de veces que el carácter indicado como parámetro aparece dentro de la cadena sobre la que se invoca el método (por ejemplo, ``"foo".count('o')`` ha de devolver 2).

  .. code-block:: javascript
    :linenos:
    :force:

    String.prototype.count=function(c) { 
      var count=0, i=0;
      while (i<this.length) {
        count+= @1;
      }
      return count;
    };

  .. solución: this[i++]===c

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Indica *una única instrucción* en JavaScript que use la siguiente declaración para imprimir un 100 por la consola.
  
  .. code-block:: javascript
    :linenos:

    var logger= function () {
      return function () {
        return function () {
          console.log(100);
        }
      }
    }

  .. solución: logger()()();

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Indica con qué es necesario sustituir las marcas ``@1``, ``@2`` y ``@3`` para que la siguiente función de JavaScript devuelva un valor booleano que indique si el array pasado como parámetro está ordenado de forma ascendente. Usa la notación ``@1=...,@2=...,@3=...`` para tu respuesta.

  .. code-block:: javascript
    :linenos:
    :force:

    function isSorted(array) {
      const l = array.length - 1;
      for (@1 i = 0; i < l; i++) {
        const c = @2;
        const n = @3;
        if (c > n) { return false; }
      }
      return true;
    }

  .. solución: @1=var/let, @2=array[i], @3=array[i + 1]

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Indica con qué es necesario sustituir la marca ``@1`` para que el siguiente código en JavaScript muestre por la consola el valor 42. Atención: el carácter de punto y coma no puede aparecer en tu respuesta para evitar que añadas instrucciones adicionales.

  .. code-block:: javascript
    :linenos:
    :force:

    (function(y) {
      var answer = 40 + y;
      console.log(answer);
    })@1;

  .. solución: @1=(2)    

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  ¿Qué salida muestra por consola el siguiente programa en JavaScript?
  
  .. code-block:: javascript
    :linenos:

    function done(){
      console.log("Done");
    }
      
    function increment(num, callBack){
      var f= callBack;
      for(var i = 0; i <= num; i++){
        console.log(i);
      }
      return callBack();
    }
      
    increment(4, done);

  .. solución: 0,1,2,3,4,Done

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Indica cuál es la salida por consola tras ejecutar el siguiente programa en JavaScript asumiendo que la función ``emit`` imprime por consola el valor pasado como parámetro tras realizar una serie de cálculos durante 1 segundo y que el tiempo de ejecución de cualquier otro elemento del código es despreciable.

  .. code-block:: javascript
    :linenos:

    function f(x) {
      emit("f"+(x||0));
    }

    function g() {
      emit("g1");
      f(3);
      setTimeout(f,5000);
      emit("g2");
    }

    f(1);
    setTimeout(f,6000);
    g();
    f(2);

  .. solución: f1,g1,f3,g2,f2,f0,f0; https://jsfiddle.net/obqj50zx/
  .. function emit(s) { function pause(milliseconds) { let dt = new Date(); while ((new Date())-dt<=milliseconds) { /* Do nothing */} } pause(1000); let dt= new Date(); let seconds= dt.getSeconds(); console.log(seconds+"': "+s); }

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Considera el siguiente fragmento de un documento de HTML:

  .. code-block:: html
    :linenos:

    <body>
      <section>
        <header><h1>The Boy Who Lived</h1></header>
        <p class="first">Mr. and Mrs. Dursley, of number four, 
          Privet Drive, were proud to say that they were perfectly 
          normal, thank you very much.</p>
        <p class="last">They were the last people you'd expect to
          be involved in anything strange or mysterious, because they
          just didn't hold with such nonsense.</p>
      </section>
    </body>

  Considera que al documento anterior se le están aplicando los siguientes estilos:

  .. code-block:: css
    :linenos:

    p {
      color: silver;
    }
    #ch1 {
      color: tomato;
    }
    p.last {
      color: blueviolet;
    }
    header h1 {
      color: forestgreen;
    }
    p.first {
      color: lightskyblue;
    }

  Indica en qué colores se mostrarán, por este orden, el primer y segundo párrafo tras ejecutar el siguiente código de JavaScript:

  .. code-block:: javascript
    :linenos:

    document.querySelector('p.last').parentNode.children[0].id= 'ch1';
    let e= document.querySelectorAll('p')[1]; 
    e.classList.toggle('first');
    e.previousSibling.previousSibling.classList.toggle('first');

  .. solución: silver, lightskyblue; https://jsfiddle.net/ztk4bw67/

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Considera el siguiente fragmento en HTML de una página web:

  .. code-block:: html
    :linenos:

    <aside>
      <nav>
        <h1>Obras de Federico García Lorca</h1>
        <ol>
          <li>Poema del cante jondo</li>
          <li>Romancero gitano</li>
          <li>Poeta en Nueva York</li>
        <ol>
        <div>Sonetos del amor oscuro</div>
        <ul>
          <li>Bodas de sangre</li>
        </ul>
        <ul>
          <li>La casa de Bernarda Alba</li>
          <li>La zapatera prodigiosa</li>
        </ul>
      </nav>
    </aside>

  Indica cuál es la salida por consola ejecutar el siguiente código en JavaScript:

  .. code-block:: javascript
    :linenos:

    let l=document.querySelectorAll("aside > nav li");
    for (var i=0; i<l.length; i++) {
      if (l[i].parentNode.querySelectorAll("li").length===1) {
        console.log(l[i].textContent.length);
      }
    }

  .. solución: 15, https://jsfiddle.net/34n9aw6o/
  .. examen enero 2020

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  ¿Qué imprime el siguiente programa de JavaScript por la consola?

  .. code-block:: javascript
    :linenos:

    function f(g,x) {
      return {a:g,b:g(x)}
    }
    var h= f(x=>3*x,4);
    console.log(h.b-h.a(2));

  .. solución: 6, https://jsfiddle.net/zdm4y21v/
  .. examen enero 2020

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Indica con qué sustituir las marcas ``@1`` y ``@2`` en el siguiente código en JavaScript para que la función *every* devuelva cierto si todos los elementos de un array cumplen una determinada condición pasada como parámetro y falso en otro caso.

  .. code-block:: javascript
    :linenos:
    :force:

    function every(array, predicate) {
      for (var i=0;i<array.@1;i++) {
        if (!@2) return false;
      }
      return true;
    }

  Lo siguiente son un par de ejemplos de llamadas a la función; la primera devuelve cierto y la segunda devuelve falso:

  .. code-block:: javascript
    :linenos:

    console.log(every([1, 3, 5], n => n < 10));
    console.log(every([2, 4, 16], n => n < 10));

  .. solución: @1=length,@2=predicate(array[i])
  .. examen enero 2020

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Indique con qué sustituir ``@1``, ``@2`` y ``@3`` en el siguiente código en JavaScript para que defina correctamente una clase ``Cilindro`` con un constructor y un método que calcula su volumen, además de crear un objeto de dicha clase e invocar sobre él la función ``volumen``.

  .. code-block:: javascript
    :linenos:
    :force:

    function Cilindro(a,d) {
      @1.altura = a;
      @1.diametro = d;
    }

    Cilindro.@2.volumen = function () {
      var r = this.diametro / 2;
      return this.altura * Math.PI * r * r;
    };

    var c = @3 Cilindro(7, 4);
    console.log(c.volumen().toFixed(4));  // imprime 87.9646

  .. solución: @1=this,@2=prototype,@3=new
  .. examen enero 2020

.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Indica cuál es la salida por consola tras ejecutar el siguiente programa en JavaScript, asumiendo que la función ``emit`` imprime por consola el valor pasado como parámetro tras realizar una serie de cálculos durante 1 segundo y que el tiempo de ejecución de cualquier otro elemento del código es despreciable. La función ``setTimeout`` es estándar de JavaScript y registra una función que se ejecutará asíncronamente después del número de milisegundos indicados como segundo parámetro.

  .. code-block:: javascript
    :linenos:

    function f(x) {
      if (x) {
        emit("f"+x);
      }
      else {
        setTimeout( ()=>emit("#") ,1000)
      }
    }

    function g() {
      f();
      setTimeout(f,1000);
      emit("g1");
      setTimeout(f,1000);
      emit("g2");
      for(var i=0;i<3;i++) {
        emit("g3");
      }
      
    }

    f(7);
    g();

  .. solución: f7,g1,g2,g3,g3,g3,#,#,#,  https://jsfiddle.net/0rvdw2xh/ 
  .. function emit(s) { function pause(milliseconds) { let dt = new Date(); while ((new Date())-dt<=milliseconds) { /* Do nothing */} } 
  .. pause(1000); let dt= new Date(); let seconds= dt.getSeconds(); console.log(seconds+"': "+s); }
  .. examen enero 2020
 
.. ------

.. admonition:: :problema-contador-cliente:`Problema`
  :class: problema

  Considera el siguiente fragmento de un documento en HTML:

  .. code-block:: html
    :linenos:

    <ol id="c3">
      <li class="a b">Gather ingredients</li>
      <li class="b">Mix ingredients together</li>
      <li class="a">Place ingredients in a baking dish</li>
      <li>Bake in oven for an hour</li>
      <li class="c">Remove from oven</li>
      <li class="b" id="c1">Allow to stand for ten minutes</li>
      <li id="c2">Serve</li>
    </ol>

  Considera que al documento anterior se le están aplicando los siguientes estilos:

  .. code-block:: css
    :linenos:

    *     {color: aqua;}
    li    {color: silver;}
    li#c1 {color: darksalmon;}
    li    {color: forestgreen;}
    #c1   {color: lightskyblue;}
    .a .b {color: orangered;}
    ol li {color: firebrick;}
    ol > li#c2 {color: lemonchiffon;}
    li.a  {color: darkorchid;}
    li.b  {color: tomato;}
    li    {color: chocolate;}
    li#c3 {color: mintcream;}

  Indica en el mismo orden la secuencia de los siete nombres de colores en los que se mostrarán los siete elementos de la lista de HTML tras ejecutar el siguiente código de JavaScript:

  .. code-block:: javascript
    :linenos:

    let e=document.querySelectorAll('li');
    for(var i=0;i<e.length;i++) {
      if (e[i].textContent.length===5) {
        e[i].classList.add("a");
      }  
    }

  .. solución: tomato,tomato,darkorchid,firebrick,darksalmon,darksalmon, lemonchiffon, https://jsfiddle.net/h9s4mv7y/
  .. examen enero 2020


Servicios web
-------------

.. admonition:: :problema-contador-servicios:`Problema`
  :class: problema

  Sabiendo que no hay ningún error en el siguiente código, indica la salida que emite por consola el siguiente bloque de JavaScript:

  .. code-block:: javascript

    p1()
    .then( (x) => { console.log(x*x); return p2(x+1,x+2,x+3); } )
    .then( (x) => { console.log(x.a+x.b); x.a++; return true; } )
    .catch( (x) => console.log(x.a*x.b) );

    function p1() {
      return new Promise( (resolve,reject) => resolve(2) );
    }
    
    function p2(a,b) {
      return new Promise( (resolve,reject) => reject( {a:a,b:a*b} ) );
    }

  .. solución: 4,36, https://jsfiddle.net/tb5vya3r/

.. ------

.. admonition:: :problema-contador-servicios:`Problema`
  :class: problema

  Sabiendo que no hay ningún error en el siguiente código, indica con qué valor hay que sustituir las expresiones ``@1`` y ``@2`` en el siguiente código para que la salida emitida por consola sea 5:

  .. code-block::

    new Promise( (resolve,reject) => resolve( {a:0,b:2} ) )
    .then( (x) => { 
      return new Promise (
        function (resolve,reject) {
          if (x.a+5===@1 && x.b*x.b*x.b===@2) {
            resolve(x.a+5);
          }
          else {
            reject(x.b+2);
          }
        }
      )
    })
    .then( (x) => console.log(x) )
    .catch( (x) => console.log(x) );

  .. solución: @1=5,@2=8, https://jsfiddle.net/dLxq5n0k/

.. ------

.. admonition:: :problema-contador-servicios:`Problema`
  :class: problema

  Teníamos un fragmento correcto de código en JavaScript que realizaba una petición asíncrona a ``http://example.com/movies.json/birdbox`` y mostraba por consola el valor del atributo ``title`` de los datos en JSON devueltos por el servidor. Lamentablemente, las líneas de nuestro programa se han desordenado y, además, mezclado con líneas de otros programas. Indica la secuencia de líneas, de entre las siguientes, que permiten reconstruir el programa original. Una posible respuesta (incorrecta) sería ``03,09,04``.

  .. code-block::
  
    01    .then(function(r) {
    02    .then(
    03    })
    04    console.log(title);
    05    .then(title)
    06    console.log(movie.title);
    07    .then(function(movie) {
    08    console.log(r.json().title);
    09    });
    10    fetch('http://example.com/movies.json/birdbox')
    11    return r.json();
    12    fetch(function('http://example.com/movies.json/birdbox'))

  .. solución: 10,01,11,03,07,06,09

.. ------

.. admonition:: :problema-contador-servicios:`Problema`
  :class: problema

  Indica los tres errores de formato que hay en la representación en JSON del siguiente dato y cómo los solucionarías con la menor cantidad de modificaciones.

  .. code-block::
    :linenos:

    {
      "name": "Duke",
      age: 18,
      "streetAddress": "100 Internet Dr",
      "city":"New York",
      "married": true
      "sex": male,
      "companies": [],
      "universities": [{}  ],
      "phoneNumbers": [
        { "Mobile": "1111111111" },
        { "Mobile": 2222222222 },
        33333
      ]
    }

  .. solución: "age", "true,", "male"

.. ------

.. admonition:: :problema-contador-servicios:`Problema`
  :class: problema

  Sabiendo que no hay ningún error en el siguiente código y que la llamada al URL indicado en ``fetch`` devuelve en el cuerpo de la respuesta el dato válido en JSON ``{"title":"天気の子","director":"新海誠","year":2019}``, indica la salida que emite por consola el siguiente bloque de JavaScript:

  .. code-block:: javascript

    function movie() {
      var g= 1;  
      fetch('http://example.com/movies.json/3400231').
      .then( r => {
        g++;
        return r.json();
      })
      .then( x => {
        g++;
        console.log(x.year+g);
      })
      .catch( e => console.log(e) );
      g++;
      console.log(g);
    }

    movie();

  .. solución: 2,2023, https://codesandbox.io/s/dazzling-paper-kpkyq

.. ------

.. admonition:: :problema-contador-servicios:`Problema`
  :class: problema

  El siguiente código define una función de *middleware* de Express que añade la cabecera ``Content-type`` con valor ``text/html`` a la respuesta del servidor. Indica con qué hay que sustituir ``@1`` y ``@2`` para que el código sea correcto.

  .. code-block:: javascript
    :linenos:
    :force:

    app.use( (request,response,foo) => {
      res.set('Content-Type', '@1')
      @2;
    });

  .. solución: @1=text/html,@2=foo()

.. ------

.. admonition:: :problema-contador-servicios:`Problema`
  :class: problema

  Sabiendo que no hay ningún error en el siguiente código y que la llamada al URL indicado en ``fetch`` devuelve en el cuerpo de la respuesta el dato válido en JSON ``{"title":"天気の子","director":"新海誠","year":2019}``, indica el código con el que hay que sustituir ``@1`` y ``@2`` en el siguiente bloque de JavaScript para que se imprima por consola el valor ``2021``:

  .. code-block:: javascript
    :linenos:
    :force:

    async function movie() {
      var g= 1;
      g+= @1;
      try {
        let r= await fetch('http://example.com/movies.json/3400231');
        let x= @2 r.json();
        console.log(x.year + g);
      } catch(e) {
        console.log(e)
      };
    }

    movie();

  .. solución: @1=1,@2=await, https://codesandbox.io/s/objective-leaf-9wx6z

.. ------

.. admonition:: :problema-contador-servicios:`Problema`
  :class: problema

  Indica qué cadena ha de devolver en el bloque de datos la llamada al servicio web para que la salida emitida por consola sea ``zz80``. Como siempre indica la respuesta más corta posible si hay más de una alternativa; si tu respuesta estuviera escrita en un formato (como HTML, CSS o JSON) que puede ser validado, asegúrate de que es válida.

  .. code-block:: javascript
    :linenos:

    function catalog() {
      fetch('http://example.com/service.json/discover/azz3').
      .then( r => {
        return r.json();
      })
      .then( x => {
        if (x.bool) {
          console.log(x.i+x.i-x.j);
        } else {
          console.log(x.i+x.i+x.j);
        }
      })
      .catch( e => console.log(e) );
    }

    catalog();

  .. solución: {"bool":false,"i":"z","j":80}
  .. solución más corta: {"i":"z","j":80}
  .. examen enero 2020

.. ------

.. admonition:: :problema-contador-servicios:`Problema`
  :class: problema

  Indica con qué sustituir las marcas ``@1``, ``@2`` y ``@3`` en el siguiente texto para que sea correcto: "El framework @1 de Node.js nos permite definir lo que en inglés se conoce como *middleware*. El *middleware* está formado por @2 encadenadas que se ejecutan durante el ciclo de vida de una petición al servidor. Cada una de ellas puede acceder a sendos objetos que representan la petición y la @3."

  .. solución: @1=Express,@2=funciones,@3=respuesta


Componentes web
---------------

.. admonition:: :problema-contador-componentes:`Problema`
  :class: problema

  Considera el siguiente fichero ``index.html``:

  .. code-block:: html
    :linenos:

    <!DOCTYPE html>
    <html>
    <head>
      <meta charset="utf-8">
      <title>Mi componente</title>
      <script src="micomp.js" defer></script>
      <style>
        :host {
          color: mintcream;
        }
        p.m {
          color: papayawhip;
        }
      </style>
    </head>
    <body>
      <h1>Mi componente</h1>

      <template id="mi-componente">
        <style>
          p {
            color: navajowhite;
          }
        </style>
        <p class="m"><slot name="mi-texto">Texto por defecto</slot></p>
      </template>

      <mi-componente>
        <span slot="mi-texto">En un agujero en el suelo, vivía un hobbit.</span>
      </mi-componente>

      <mi-componente>
        No un agujero   
          <ul slot="mi-texto">
            <li>húmedo, sucio, repugnante, con restos de gusanos 
                y olor a fango,</li>
            <li>ni tampoco un agujero seco, desnudo y arenoso, 
                sin nada en que sentarse o que comer:</li>
          </ul>
        era un agujero-hobbit, y eso significa comodidad.  
      </mi-componente>

    </body>
    </html>

  Considera también el contenido del fichero ``micomp.js``:

  .. code-block:: javascript
    :linenos:

    customElements.define('mi-componente',
      class extends HTMLElement {
        constructor() {
          super();

          const template = document.getElementById('mi-componente');
          const templateContent = template.content;

          this.attachShadow({mode: 'open'}).appendChild(
            templateContent.cloneNode(true)
          );
        }
      }
    );

  Enumera en qué colores se muestran las palabras *suelo*, *fango* y *comodidad*. Si la palabra no se muestra en el documento web, o no puedes saber su color, indica *ninguno*. Una posible respuesta (incorrecta) sería: ``red, ninguno, blue``.

  .. solución: navajowhite, navajowhite, ninguno; https://jsfiddle.net/jncq9ra8/

.. ------

.. admonition:: :problema-contador-nube:`Problema`
  :class: problema

  Considera el contenido del fichero ``invierte-cadena.js`` que define un componente web que muestra invertida y en color azul la cadena recibida como parámetro:

  .. code-block:: javascript
    :linenos:
    :force:

    (function() {
      const template = document.createElement('template');

      template.innerHTML = `
        <style>
          p {
            color: steelblue;
          }
        </style>
        <p @1></p>`;

      function reverse(s) {
        return s.split("").reverse().join("");
      }

      class ReverseString extends HTMLElement {
        constructor() {
          super();
          let clone = template.content.cloneNode(true);
          let shadowRoot = this.attachShadow({mode: 'open'});
          shadowRoot.appendChild(clone);
        }

        connectedCallback() {
          this.s= !this.hasAttribute('s')?"":this.getAttribute(@2);
          this.shadowRoot.querySelector('#cadena').textContent= reverse(@3);
        }
      }

      customElements.define("invierte-cadena", ReverseString);

    })();

  Un posible uso del componente web en un documento HTML sería:

  .. code-block:: html
    :linenos:

    <invierte-cadena s="hola"></invierte-cadena>

  Indica con qué sustituir las marcas ``@1``, ``@2`` y ``@3`` del código anterior para que el componente web funcione correctamente.

  .. solución: @1=id="#cadena",@2="s",@3=this.s,  https://jsfiddle.net/rqgwb5vd/ 
  .. examen enero 2020


Computación en la nube
----------------------

.. admonition:: :problema-contador-nube:`Problema`
  :class: problema

  Indica con qué sustituir ``@1`` en la siguiente frase para que sea correcta: "Los servicios de computación en la nube se pueden dividir en tres categorías en base al nivel de abstracción que ofrecen sobre los recursos utilizados: software como servicio, @1, e infraestructura como servicio"

  .. solución: @1=plataforma como servicio

.. ------

.. admonition:: :problema-contador-nube:`Problema`
  :class: problema

  Indica con qué sustituir las marcas ``@1`` y ``@2`` en el siguiente texto para que sea correcto: "Google Cloud Functions es un servicio de computación en la nube que permite ejecutar código de forma remota sin necesidad de lanzar una @1 explícita para ello, como sí ocurre con Google App Engine; ambos servicios son ejemplos de lo que se conoce en inglés como *@2 as a service*."

  .. solución: @1=instancia de máquina virtual,@2=platform
  .. examen enero 2020

