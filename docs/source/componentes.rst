
Componentes web
===============

Los componentes web son un nuevo conjunto de estándares que facilitan la reutilización de los elementos que se suelen definir en una aplicación web. La tecnología de los componentes web se basa en dos elementos estandarizados principales: el *DOM ensombrecido* (*shadow DOM*), que permite modificar la presentación de una parte de la página web sin modificar el DOM global (o *DOM iluminado*, *light DOM*, nombre que se ha popularizado para diferenciar el DOM clásico del DOM ensombrecido), y las *plantillas* (*templates*), que permiten encapsular bloques de contenido (HTML), presentación (CSS) y funcionalidad (JavaScript) de forma que no interaccionen con el resto de elementos de la página y solo quede expuesta la parte de ellos que desee el desarrollador. Otra tecnología estándar implicada son los *elementos personalizados* (*custom elements*), que permiten al desarrollador crear sus propios elementos de HTML.


.. Important::

  Las habilidades que deberías adquirir con este tema incluyen las siguientes:

    - Comprender la diferencia entre *shadow DOM* y *light DOM*.
    - Entender cómo se modifica la representación de una página tras incluir un *shadow tree* en ella.
    - Comprender la utilidad de encapsular partes de una página web en un componente web.
    - Saber como crear componentes web mediante el API estándar del W3C.
    - Saber cómo modificar la presentación de un componente sin modificar el DOM global.


.. admonition:: Hazlo tú ahora
  :class: hazlotu

  A modo introductorio, lee la breve descripción de las tecnologías de los componentes web que puedes encontrar en `webcomponents.org`_. 

  .. _`webcomponents.org`: https://www.webcomponents.org/introduction


Componentes web
---------------

Vamos a ver las tecnologías de *shadow DOM*, elementos personalizados y plantillas juntas en acción. La página web que aparece más abajo incluye un elemento ``<template>`` cuyo contenido no es mostrado por el motor del navegador; la idea es que un script de JavaScript se referirá posteriormente a esta plantilla (a través de su id) para instanciar un elemento e insertarlo convenientemente en el árbol DOM, como puedes observar en el constructor de la clase ``Operaciones``. Como esta plantilla será instanciada dentro de un *shadow DOM*, los estilos CSS que incluye no modificarán a elementos de otras partes del árbol DOM. También se incluye código en JavaScript que no será ejecutado hasta que el componente se instancie. Por último, la plantilla contiene código HTML que será el que se inserte en el árbol DOM al instanciar el elemento.

Como veremos a continuación, nuestro ejemplo define un elemento personalizado ``<calcula-operaciones>`` que se puede usar (es decir, instanciar) en una o más partes de nuestro documento HTML. 

La definición del elemento personalizado se hace mediante una clase (recuerda que desde ES6 puede usarse ``class``) que deriva de ``HTMLElement``. El nombre de la clase (``Operaciones`` en este caso) se pasa al método ``customElements.define`` para definir el elemento HTML personalizado; el nombre del elemento ha de llevar obligatoriamente un guión. La clase ``Operaciones`` tiene en este ejemplo solo un método constructor que llama al constructor de la clase padre, clona la plantilla mediante el método ``cloneNode``, crea un nodo ensombrecido vinculado a la instancia del elemento y, finalmente, añade el nodo clonado al nodo ensombrecido del elemento.

.. code-block:: html

  <!DOCTYPE html>
  <html>
    <head>
      <title>Definición de componentes web</title>
      <meta charset="utf-8">
    </head>
    <body>

      <template id="operaciones">
        <style>
          h1 {
            color: steelblue;
          }
          .contenido {
            background-color: gainsboro;
            color: royalblue;
          }
        </style>

        <script>
          console.log("Template instanciado");
        </script>

        <h1>Operaciones binarias</h1>
        <div class="contenido">
          Multiplicación: 3 x 2 = 6.
        </div>
      </template>

      <h1>Definición de componentes web</h1>
      
      <calcula-operaciones></calcula-operaciones>
      
      <script>
        class Operaciones extends HTMLElement {
          constructor() {
            super();
            let template = document.querySelector('#operaciones');
            let clone = template.content.cloneNode(true);
            let shadowRoot = this.attachShadow({
              mode: 'open'
            });
            shadowRoot.appendChild(clone);
          }
        }
        customElements.define("calcula-operaciones", Operaciones);
      </script>
      
      <p>Fin de la pagina.</p>

    </body>
  </html>

El *árbol ensombrecido* se comporta como un árbol DOM normal, salvo que no es visible desde fuera: por ejemplo, el elemento ``<h1>`` del *DOM ensombrecido* no aparecerá nunca si buscamos con ``document.querySelectorAll("h1")`` desde un script de fuera del componente web; además, los estilos que definamos para ``<h1>`` dentro del *shadow DOM* no afectarán a los elementos del árbol principal.

Permitamos ahora personalizar los mensajes. Para ello, vamos a incluir dentro de cada instancia del elemento ``calcula-operaciones`` dos bloques de código HTML identificados mediante un nombre indicado en el atributo ``slot``. Dentro de la plantilla, podemos insertar el contenido de estos bloques usando el elemento ``slot`` e indicando en su atributo ``name`` el identificador del bloque a insertar. Además, vamos a mejorar también un poco el estilo del componente web rodeándolo con un borde; como se trata de un estilo asociado al elemento completo (que se conoce como *shadow host*), usamos el selector de CSS ``:host``. Observa en el ejemplo que podemos instanciar un componente web más de una vez en un mismo documento HTML.

.. code-block:: html

  <!DOCTYPE html>
  <html>
    <head>
      <title>Definición de componentes web</title>
      <meta charset="utf-8">
    </head>
    <body>

      <template id="operaciones">
        <style>
          * {
            margin: 0;
            padding: 0;
          }
          h1 {
            color: steelblue;
            font-size: 110%;
            margin-bottom: 10px;
            border-bottom: 1px solid lightgray;
          }
          .contenido {
            background-color: gainsboro;
            color: royalblue;
          }
          :host {
            border: 1px solid lightgray;
            padding: 5px;
            display: block;
            margin: 5px;
            margin-bottom: 15px;
          }
        </style>

        <script>
          console.log("Template instanciado");
        </script>

        <h1><slot name="title">Sin título</slot></h1>
        <div class="contenido">
          <slot name="mult">Sin nombre</slot>: 3 x 2 = 6.
        </div>
      </template>

      <h1>Definición de componentes web</h1>
      
      <calcula-operaciones>
        <span slot="title">Operaciones binarias</span>
        <span slot="mult">Multiplicación</span>
      </calcula-operaciones>

      <calcula-operaciones>
        <span slot="title">Binary <strong>operations</strong></span>
        <span slot="mult">Multiplication</span>
      </calcula-operaciones>

      <calcula-operaciones>
        <span slot="mult">Multiplicación</span>
      </calcula-operaciones>
      
      <script>
        class Operaciones extends HTMLElement {
          constructor() {
            super();
            let template = document.querySelector('#operaciones');
            let clone = template.content.cloneNode(true);
            let shadowRoot = this.attachShadow({
              mode: 'open'
            });
            shadowRoot.appendChild(clone);
          }
        }
        customElements.define("calcula-operaciones", Operaciones);
      </script>
      
      <p>Fin de la pagina.</p>

    </body>
  </html>


Que el componente web realice siempre la multiplicación de los mismos números no tiene mucha gracia. Vamos a hacer que los valores a multiplicar se definan como atributos del elemento. Como en el constructor todavía no puede accederse a los atributos con el método ``this.getAttribute``, lo dejamos para el método ``connectedCallback`` que será invocado por el navegador tras instanciar el componente web.

.. code-block:: html

  <!DOCTYPE html>
  <html>
    <head>
      <title>Definición de componentes web</title>
      <meta charset="utf-8">
    </head>
    <body>

      <template id="operaciones">
        <style>
          * {
            margin: 0;
            padding: 0;
          }
          h1 {
            color: steelblue;
            font-size: 110%;
            margin-bottom: 10px;
            border-bottom: 1px solid lightgray;
          }
          .contenido {
            background-color: gainsboro;
            color: royalblue;
          }
          :host {
            border: 1px solid lightgray;
            padding: 5px;
            display: block;
            margin: 5px;
            margin-bottom: 15px;
          }
        </style>

        <script>
          console.log("Template instanciado");
        </script>

        <h1><slot name="title">Sin título</slot></h1>
        <div class="contenido">
          <slot name="mult">Sin nombre</slot>: 
          <span id="a"></span> x <span id="b"></span> = 
          <span id="resultado"></span>.
        </div>
      </template>

      <h1>Definición de componentes web</h1>
      
      <calcula-operaciones a="4" b="5">
        <span slot="title">Operaciones binarias</span>
        <span slot="mult">Multiplicación</span>
      </calcula-operaciones>

      <calcula-operaciones a="8">
        <span slot="title">Binary operations</span>
        <span slot="mult">Multiplication</span>
      </calcula-operaciones>
    
      <script>
        class Operaciones extends HTMLElement {
          constructor() {
            super();
            let template = document.querySelector('#operaciones');
            let clone = template.content.cloneNode(true);
            let shadowRoot = this.attachShadow({
              mode: 'open'
            });
            shadowRoot.appendChild(clone);
          }

          connectedCallback() {
            this.a= this.hasAttribute('a')?this.getAttribute('a'):0;
            this.b= this.hasAttribute('b')?this.getAttribute('b'):0;
            this.shadowRoot.querySelector('#a').textContent= this.a;
            this.slotb= this.shadowRoot.querySelector('#b');
            this.slotb.textContent= this.b;
            let resultado= this.shadowRoot.querySelector('#resultado');
            resultado.textContent= this.a*this.b;
          }

        }
        customElements.define("calcula-operaciones", Operaciones);
      </script>
      
      <p>Fin de la pagina.</p>

    </body>
  </html>

Ahora vamos a modularizar y encapsular el diseño anterior para que otros puedan usar nuestro componente web sin tener que incluir todo lo anterior en su documento HTML.

.. code-block:: html

  <!DOCTYPE html>
  <html>
    <head>
      <title>Definición de componentes web</title>
      <meta charset="utf-8">
      <script defer src="calcula-operaciones.js"></script>
    </head>
    <body>

      <h1>Definición de componentes web</h1>
      
      <calcula-operaciones a="4" b="5">
        <span slot="title">Operaciones binarias</span>
        <span slot="mult">Multiplicación</span>
      </calcula-operaciones>
  
      <p>Fin de la pagina.</p>

    </body>
  </html>

El contenido del fichero ``calcula-operaciones.js`` es el siguiente:

.. code-block::

  (function() {
    const template = document.createElement('template');

    template.innerHTML = `
      <style>
        * {
          margin: 0;
          padding: 0;
        }
        h1 {
          color: steelblue;
          font-size: 110%;
          margin-bottom: 10px;
          border-bottom: 1px solid lightgray;
        }
        .contenido {
          background-color: gainsboro;
          color: royalblue;
        }
        :host {
          border: 1px solid lightgray;
          padding: 5px;
          display: block;
          margin: 5px;
          margin-bottom: 15px;
        }
      </style>

      <script>
        console.log("Template instanciado");
      </script>

      <h1><slot name="title">Sin título</slot></h1>
      <div class="contenido">
        <slot name="mult">Sin nombre</slot>: 
        <span id="a"></span> x <span id="b"></span> = 
        <span id="resultado"></span>.
      </div>`;

    class Operaciones extends HTMLElement {
      constructor() {
        super();
        let clone = template.content.cloneNode(true);
        let shadowRoot = this.attachShadow({
          mode: 'open'
        });
        shadowRoot.appendChild(clone);
      }

      connectedCallback() {
        this.a= this.hasAttribute('a')?this.getAttribute('a'):0;
        this.b= this.hasAttribute('b')?this.getAttribute('b'):0;
        this.shadowRoot.querySelector('#a').textContent= this.a;
        this.slotb= this.shadowRoot.querySelector('#b');
        this.slotb.textContent= this.b;
        let resultado= this.shadowRoot.querySelector('#resultado');
        resultado.textContent= this.a*this.b;
      }
    }

    customElements.define("calcula-operaciones", Operaciones);

  })();

El código anterior se ha encapsulado dentro de lo que se conoce como una *función invocada inmediatamente* (*immediately-invoked function expressions*, IIFE), que permite no contaminar el espacio de nombres global con variables que podrían estar siendo también definidas en otras librerías, evitando así potenciales conflictos.

Finalmente, vamos a añadir un par de operaciones más y delegar en un servicio externo su cálculo y usar encodeURI...

.. Note::

  Si tu aplicación web tiene que funcionar en versiones de los navegadores de hace unos años, es posible que estos no tuvieran implementados todavía los estándares relacionados con los componentes web. Para que los componentes web funcionen en navegadores antiguos es necesario cargar el `polyfill`_ correspondiente. El nombre de *polyfill* se utiliza para referirse a una librería que añade a un navegador una funcionalidad que no tiene implementada.

  .. _`polyfill`: https://cdnjs.com/libraries/webcomponentsjs

  