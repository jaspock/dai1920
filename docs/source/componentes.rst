
Componentes web
===============

Los componentes web son un nuevo conjunto de estándares que facilitan la reutilización de los elementos que se suelen definir en una aplicación web. La tecnología de los componentes web se basa en dos elementos estandarizados principales: el *DOM ensombrecido* (*shadow DOM*), que permite modificar la presentación de una parte de la página web sin modificar el DOM global (o *DOM iluminado*, *light DOM*, nombre que se ha popularizado para diferenciar el DOM clásico del DOM ensombrecido), y las *plantillas* (*templates*), que permiten encapsular bloques de contenido (HTML), presentación (CSS) y funcionalidad (JavaScript) de forma que no interaccionen con el resto de elementos de la página y solo quede expuesta la parte de ellos que desee el desarrollador. Otras tecnologías estándar implicadas son los *elementos personalizados* (*custom elements*) y las *importaciones de HTML* (*HTML imports*), aunque estos últimos ya no se usan. Puedes encontrar una breve descripción de cada una de las cuatro tecnologías en `Webcomponents.org`_. 

.. _``Webcomponents.org`: http://webcomponents.org/

.. Important::

  Las habilidades que deberías adquirir con este tema incluyen las siguientes:

    - Comprender la diferencia entre *shadow DOM* y *light DOM*.
    - Entender cómo se modifica la representación de una página tras incluir un *shadow tree* en ella.
    - Comprender la utilidad de encapsular partes de una página web en un componente web.
    - Saber como crear componentes web mediante el API estándar del W3C.
    - Saber cómo modificar la presentación de un componente sin modificar el DOM global.


Componentes web
---------------

Vamos a ver las tecnologías de *shadow DOM*, elementos personalizados y plantillas juntas en acción:

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

Permitamos ahora personalizar los mensajes. Vamos a mejorar un poco el estilo del componente web rodeándolo con un borde. En este ejemeplo también vemos cómo podemos instanciar más de un componente web.

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
        <span slot="title">Binary operations</span>
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


Que el componente web realice siempre la multiplicación de los mismos números no tiene mucha gracia. Vamos a hacer que los valores a multiplicar se definan como atributos del elemento. En el constructor todavía no puede accederse a los atributos con el método this.getAttribute, así que lo dejamos para connectedCallback.

.. code-block: html

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

Ahora vamos a modularizar y encapsular el diseño anterior para que otros puedan usar nuestro componente web si tener que incluir todo lo anterior en el HTML.

.. code-block: html

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

Y el fichero ``calcula-operaciones.js``:

.. code-block: JavaScript

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


Finalmente, vamos a añadir un par de operaciones más y delegar en un servicio externo su cálculo y usar encodeURI...


Aunque hay librerías como [Polymer](https://www.polymer.org) que simplifican algunos de los pasos en la creación de componentes web, en esta actividad nos vamos a centrar en la API estándar del W3C, que hoy en día es comprendida por todos los navegadores web. Estudia con este [tutorial](https://alligator.io/web-components/your-first-custom-element/) los fundamentos de los componentes web. Amplía con ayuda de este otro [tutorial](https://developer.mozilla.org/en-US/docs/Web/Web_Components) (especialmente los tres documentos "Using custom elements", "Using shadow DOM" y "Using templates and slots") tus conocimientos sobre el tema. El objetivo es que puedas entender un [ejemplo sencillo](https://next.plnkr.co/edit/fWGz2tI1ni3555ed) de definición y uso de un componente web y [otro ejemplo](https://next.plnkr.co/edit/Shhk4F9rKLWvoeJ4) ligeramente más avanzado.

Para que los componentes web funcionen en navegadores antiguos es necesario cargar el [polyfill](https://cdnjs.com/libraries/webcomponentsjs) correspondiente.

*Shadow DOM* es un estándar del W3C que permite modificar sensiblemente la presentación de una página web sin modificar el DOM de la misma. Este estándar permite que cualquier nodo del DOM se convierta en *shadow host*, simplemente añadiendo un *shadow root* como hijo de éste nodo. A partir de este momento, solo se renderiza el *shadow tree* que es hijo del *shadow root* y se ignoran el resto de hijos del nodo.

El *shadow tree* se comporta como un árbol DOM normal, salvo que no es visible desde fuera: si el usuario inspecciona el *shadow host*, solo verá un hijo, que será el *shadow root*. La *shadow boundary* actúa como una barrera que protege el contenido del *shadow tree*, dejando visible sólo el *shadow root* y evitando que los elementos internos sean expuestos usando un selector con JavaScript así como la propagación de estilo. Por ejemplo, si tenemos un *h1* dentro del *shadow DOM*, no aparecerá nunca si buscamos $("h1") desde fuera; además, el estilo que definamos para *h1* dentro del *shadow DOM* no afectará a los de fuera.

Dentro del *shadow tree* podemos definir puntos de inserción. Los puntos de inserción permiten definir qué elementos hijos del *shadow host* (que dejaron de pintarse cuando añadimos el *shadow root*) se incluyen. Los puntos de inserción pueden incluirse en cualquier punto del *shadow DOM*, permitiendo modificar sensiblemente la presentación de los mismos. Aquellos hijos del *shadow host* que no coincidan con algún punto de inserción no serán visibles en la página web. Por último, todos los eventos que se disparen dentro del *shadow tree* se redirigen al *shadow host*.

El *shadow DOM* es extremadamente útil, pero tener que crear mediante JavaScript todo el contenido del mismo es tedioso y puede contener errores fácilmente. Para remediar este problema, existe el [éstándar para componentes web](http://www.w3.org/TR/components-intro/), que define etiquetas HTML que nos permiten definir de manera declarativa un *shadow DOM*, junto con su CSS y JavaScript. Una vez definido declarativamente nuestro template, podemos añadirlo a nuestra página web usando JavaScript, o podemos registrar una nueva etiqueta mediante JavaScript, lo que nos permitirá instanciar nuestra template de forma declarativa.


.. content-block:: html

  <!DOCTYPE html>
  <html>

  <head>
  <title>Template</title>
  </head>

  <body>

  <template id="first-template">

      <style>
      h1 {
          color: red;
      }
      .content {
          background-color: lightgray;
          color: red;
      }
      </style>

      <script>
      console.log("Template instantiated!");
      </script>

      <h1>My first template</h1>

      <div class="content">
      <content></content>
      </div>

  </template>

  <h1>Custom elements!</h1>

  <my-first-template>
      This text will appear inside of <strong>my component</strong>.
  </my-first-template>

  <script>
      class MyFirstTemplate extends HTMLElement {
      constructor() {
          super();
          var t = document.querySelector('#first-template');
          var clone = document.importNode(t.content, true);
          this.createShadowRoot().appendChild(clone);
      }
      }
      customElements.define("my-first-template", MyFirstTemplate);
  </script>

  <p>End of the page</p>

  </body>

  </html>


Todo el código HTML dentro de la etiqueta  ``template`` es inerte, es decir, no se ejecutará hasta que lo instanciemos. La única restricción a la hora de registrar el template es que su ``id`` debe contener al menos un guión rodeado de texto.

Los templates usan en sus versiones más recientes las clases de ES6.

El ejemplo más avanzado de componente web que se enlaza desde el enunciado es este:

~~~
<!-- Learn about this code on MDN: https://developer.mozilla.org/en-US/docs/Web/Web_Components/Using_templates_and_slots -->

<!DOCTYPE html>
<html>
  <head>
    <title>slot example</title>
  </head>
  <body>
    <template id="element-details-template">
      <style>
      details {font-family: Helvetica,Arial}
      .name {font-weight: bold; color: #217ac0; font-size: 120%}
      </style>
      <details>
        <summary>
          <span>
            <span id="number"></span>
            <code class="name">&lt;<slot name="element-name">NEED NAME</slot>&gt;</code>
            <i class="desc"><slot name="description">NEED DESCRIPTION</slot></i>
          </span>
        </summary>
      </details>
      <hr>
    </template>

    <element-details>
      <span slot="element-name">slot</span>
      <span slot="description">A placeholder inside a web
        component that users can fill with their own markup,
        with the effect of composing different DOM trees
        together.</span>
    </element-details>

    <element-details number="99">
      <span slot="element-name">template</span>
      <span slot="description">A mechanism for holding client-
        side content that is not to be rendered when a page is
        loaded but may subsequently be instantiated during
        runtime using JavaScript.</span>
    </element-details>

    <script>
    customElements.define('element-details',
      class extends HTMLElement {
        constructor() {
          super();
          const template = document
            .getElementById('element-details-template')
            .content;
          const shadowRoot = this.attachShadow({mode: 'open'})
            .appendChild(template.cloneNode(true));
        }
        connectedCallback() {
          var num= this.shadowRoot.querySelector("#number");
          if(this.hasAttribute('number')) {
            num.textContent = this.getAttribute('number')+".";
          } else {
            num.textContent = "";
          }
        }
      })
    </script>
  </body>
</html>
~~~

Los ejemplos anteriores con una forma separada en un fichero de JavaScript como se discute en el tutorial serían:

~~~
<!DOCTYPE html>
<html>

<head>
  <title>Template</title>
</head>

<body>

  <h1>Custom elements!</h1>

  <my-first-template>
    This text will appear inside of <strong>my component</strong>.
  </my-first-template>

  <script src="script.js"></script>

  <p>End of the page</p>

</body>

</html>
~~~


~~~
(function() {
  const template = document.createElement('template');

  template.innerHTML = `
    <style>
      h1 {
        color: red;
      }
      .content {
        background-color: lightgray;
        color: red;
      }
    </style>

    <script>
      console.log("Template instantiated!");
    </script>

    <h1>My first template</h1>

    <div class="content">
      <slot></slot>
    </div>
  `;

  class MyFirstTemplate extends HTMLElement {
    constructor() {
      super();
      this.attachShadow({ mode: 'open' });
      this.shadowRoot.appendChild(template.content.cloneNode(true));
    }
  }

  window.customElements.define('my-first-template', MyFirstTemplate);
})();
~~~

Y el segundo:

~~~
<!doctype html>

<html>
  <head>
    <link rel="stylesheet" href="style.css">
    <script src="script.js"></script>
  </head>

  <body>
    <element-details>
      <span slot="element-name">slot</span>
      <span slot="description">A placeholder inside a web
        component that users can fill with their own markup,
        with the effect of composing different DOM trees
        together.</span>
    </element-details>

    <element-details number="99">
      <span slot="element-name">template</span>
      <span slot="description">A mechanism for holding client-
        side content that is not to be rendered when a page is
        loaded but may subsequently be instantiated during
        runtime using JavaScript.</span>
    </element-details>
  </body>
</html>
~~~


~~~
(function() {
  const template = document.createElement('template');

  template.innerHTML = `
    <style>
      details {font-family: Helvetica,Arial}
      .name {font-weight: bold; color: #217ac0; font-size: 120%}
    </style>
    <details>
        <summary>
          <span>
            <span id="number"></span>
            <code class="name">&lt;<slot name="element-name">NEED NAME</slot>&gt;</code>
            <i class="desc"><slot name="description">NEED DESCRIPTION</slot></i>
          </span>
        </summary>
    </details>
    <hr>
  `;

  class ElementDetails extends HTMLElement {
    constructor() {
      super();
      this.attachShadow({ mode: 'open' });
      this.shadowRoot.appendChild(template.content.cloneNode(true));
    }
    connectedCallback() {
      var num= this.shadowRoot.querySelector("#number");
      if(this.hasAttribute('number')) {
        num.textContent = this.getAttribute('number')+".";
      } else {
        num.textContent = "0. ";
      }
    }
  }

  window.customElements.define('element-details', ElementDetails);
})();
~~~

</note>

#### Ejercicio de repaso

Crea un componente web que muestre encerrado en un marco con borde gris el producto de dos números que se incluyan como atributos del elemento correspondiente. Delante del resultado se mostrará una frase que se habrá indicado como contenido del elemento:

~~~
<calcula-producto x="5" y="10">El resultado es:</calcula-producto>