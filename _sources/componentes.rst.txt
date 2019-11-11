.. role:: problema-contador

Componentes web
===============

Los componentes web son un conjunto de estándares que facilitan la reutilización de los elementos que se suelen definir en una aplicación web. La tecnología de los componentes web se basa en dos elementos estandarizados principales: el *DOM ensombrecido* (*shadow DOM*), que permite modificar la presentación de una parte de la página web sin modificar el DOM global (o *DOM iluminado*, *light DOM*, nombre que se ha popularizado para diferenciar el DOM clásico del DOM ensombrecido), y las *plantillas* (*templates*), que permiten encapsular bloques de contenido (HTML), presentación (CSS) y funcionalidad (JavaScript) de forma que no interaccionen con el resto de elementos de la página y solo quede expuesta la parte de ellos que desee el desarrollador. Otra tecnología estándar implicada son los *elementos personalizados* (*custom elements*), que permiten al desarrollador crear sus propios elementos de HTML.


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
  :linenos:

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
  :linenos:

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


Que el componente web realice siempre la multiplicación de los mismos números no tiene mucha gracia. Vamos a hacer que los valores a multiplicar se definan como atributos del elemento. Según la especificación de los componentes web, en el constructor todavía no se puede acceder a los atributos del elemento; por ello, el acceso con el método ``this.getAttribute`` lo dejamos para el método estándar ``connectedCallback`` que será invocado por el navegador tras instanciar el componente web.

.. code-block:: html
  :linenos:

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
  :linenos:

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
  :linenos:

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

Finalmente, vamos a sobrescribir el método estándar ``attributeChangedCallback``, que se dispara cuando alguno de los atributos del componente web se inicializa o se cambia. Sin este código, si el valor del atributo ``a`` o ``b`` cambiara dinámicamente (mediante JavaScript), el resultado de la multiplicación no se actualizaría; puedes comprobarlo fácilmente cambiando el valor de uno de los atributos desde las Chrome DevTools. Dado que ``attributeChangedCallback`` se llama también cuando el atributo recibe valor por primera vez y dado que nuestra implementación de ``connectedCallback`` no hacía nada más que usar los atributos, podemos eliminar esta última función. El método ``attributeChangedCallback`` solo se invoca para aquellos atributos incluidos en la lista devuelta por el método ``static get observedAttributes()``.

.. code-block::
  :linenos:

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

      static get observedAttributes() { return ['a', 'b']; }

      constructor() {
        super();
        let clone = template.content.cloneNode(true);
        let shadowRoot = this.attachShadow({
          mode: 'open'
        });
        shadowRoot.appendChild(clone);
      }

      actualizaPrimerOperando() {
        this.shadowRoot.querySelector('#a').textContent= this.a;
      }

      actualizaSegundoOperando() {
        this.shadowRoot.querySelector('#b').textContent= this.b;
      }

      actualizaResultado() {
        let resultado= this.shadowRoot.querySelector('#resultado');
        fetch(encodeURI(`https://api.mathjs.org/v4/?expr=${this.a}*${this.b}`))
        .then( r => r.text() )
        .then( t => resultado.textContent= t )
        .catch( () => resultado.textContent= 'error' );
      }

      attributeChangedCallback(name,oldValue,newValue) {
        // ningún uso de this es opcional en esta función
        if (name==='a') {
          this.a= newValue;
          this.actualizaPrimerOperando();
          if (this.b) {
            this.actualizaResultado();
          }
        } else if (name==='b') {
          this.b= newValue;
          this.actualizaSegundoOperando();
          if (this.a) {
            this.actualizaResultado();
          }
        }
      }
    }

    customElements.define("calcula-operaciones", Operaciones);

  })();

La palabra reservada ``get`` liga un atributo a un método (diremos que la función es un *getter* para la propiedad) de forma que aunque en el código cliente estemos accediendo a una propiedad en realidad estamos llamando a una función:

.. code-block:: javascript
  :linenos:

  class Polygon {
    constructor(height, width) {
      this.height = height;
      this.width = width;
    }

    get area() {
      return this.calcArea()
    }

    calcArea() {
      return this.height * this.width;
    }
  }

  var p = new Polygon(10, 20);
  console.log(p.area);


Como el método de nuestro componente anterior es estático, nos podríamos referir a él como ``Operaciones.observedAttributes``. Observa que, en cualquier caso, el código que accede a ``Operaciones.observedAttributes`` en el ejemplo anterior no es nuestro sino que forma parte de la API del navegador.

Hemos cambiado también el cálculo de la multiplicación para que se realice en un servidor web externo; observa cómo, aunque en este caso no es necesario ya que los atributos deberían ser números (opcionalmente con un punto o un signo más o menos, que también son un caracteres válidos en URLs), hemos seguido la buena práctica de utilizar ``encodeURI`` para obtener un URL sin caracteres no aceptados. Los caracteres válidos en un URL son::

  ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~:/?#[]@!$&'()*+,;=

Cualquier otro carácter necesita ser convertido a la *codificación por ciento*, como ya hemos comentado otras veces.


.. admonition:: :problema-contador:`Problema`

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

  Enumera en qué colores se muestran las palabras *suelo*, *fango* y *comodidad*. Si la palabra no se muestra en el documento web, o no puedes saber su color, indica *ninguno*. Una posible respuesta (incorrecta) sería: red, ninguno, blue.

  .. solución: navajowhite, navajowhite, ninguno; https://jsfiddle.net/jncq9ra8/

.. Note::

  Si tu aplicación web tiene que funcionar en versiones de los navegadores de hace unos años, es posible que estos no tuvieran implementados todavía los estándares relacionados con los componentes web. Para que los componentes web funcionen en navegadores antiguos es necesario cargar el `polyfill`_ correspondiente. El nombre de *polyfill* se utiliza para referirse a una librería que añade a un navegador una funcionalidad que no tiene implementada. Otra forma de asegurar el funcionamiento en navegadores antiguos de programas que usan características recientes de JavaScript es mediante compiladores como `Babel`_.

  .. _`polyfill`: https://cdnjs.com/libraries/webcomponentsjs
  .. _`Babel`: https://github.com/babel/babel
  