.. role:: problema-contador

Computación en la  nube
=======================

La computación en la nube se ha convertido en la manera más habitual de gestionar los diferentes recursos necesarios para una aplicación web.

.. Important::

  Las habilidades que deberías adquirir con este tema incluyen las siguientes:

  - Entender la diferencia entre los distintos niveles de abstracción proporcionados por los diferentes modelos de computación en la nube.
  - Conocer y relacionar entre ellos varios de los servicios de infraestructura y plataforma ofrecidos por proveedores como Google Cloud Platform.
  - Saber implantar aplicaciones web en Google App Engine.


Configuración del entorno de trabajo
------------------------------------

En las actividades de este tema vamos a usar diversos servicios de Google Cloud Platform. Aunque plataformas como Amazon Web Services, Microsoft Azure o Google Cloud Platform suelen ofrecer de forma gratuita un acceso limitado a algunos de sus servicios, en esta asignatura vas a a usar un proyecto en Google Cloud Platform que el profesor ya ha creado para ti y que está asignado a una cuenta de facturación para centros educativos que Google ofrece a la universidad. Tu proyecto está asociado a la cuenta que la universidad te asigna con `dominio gcloud.ua.es`_. Para no generar gastos innecesarios, recuerda eliminar los recursos que no vayas a seguir utilizando cuando acabes.

.. _`dominio gcloud.ua.es`: https://si.ua.es/es/manuales/uacloud/uacloudse/servicios-externos.html


.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Comienza instalando `Node.js`_, el entorno que te permitirá ejecutar programas en JavaScript fuera del navegador. Las instrucciones para cada sistema operativo son diferentes. Para el caso de Linux, la instalación se puede realizar fácilmente sin necesidad de tener privilegios de administrador con ayuda de `nvm`_. En este caso, basta con ejecutar desde la línea de órdenes::

    curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.35.1/install.sh | bash
    exec -l $SHELL

  .. _`Node.js`: https://nodejs.org/
  .. _`nvm`: https://github.com/nvm-sh/nvm

  Lo anterior instala ``nvm``. Para instalar una versión de Node.js concreta, comprueba qué versiones hay disponibles e instala una de ellas::

    nvm ls-remote
    nvm install 10.10.0

  También necesitarás tener instalado `gcloud`_, un cliente que permite interactuar con Google Cloud Platform desde la línea de órdenes y que forma pate del Google Cloud SDK. Las `instrucciones de instalación del SDK`_ cambian de un sistema operativo a otro; para el caso de Linux, y de nuevo sin necesidad de tener privilegios de administrador, puedes hacer::

    curl https://sdk.cloud.google.com | bash
    exec -l $SHELL
  
  .. _`gcloud`: https://cloud.google.com/sdk/gcloud/?hl=EN
  .. _`instrucciones de instalación del SDK`: https://cloud.google.com/sdk/docs/downloads-interactive?hl=EN

  Una vez instalado, has de vincular el SDK con tu cuenta de Google Cloud Platform (en este caso, tu cuenta ``gcloud.ua.es``)::

    gcloud init

  Si posteriormente el SDK se desvinculara por algún motivo de tu cuenta, puedes volver a emprajerlo haciendo::

    gcloud auth login

  Puedes ver los proyectos que tienes creados en Google Cloud Platform con::

    gcloud projects list

  Lo más habitual es que solo tengas el proyecto de la asignatura, que comienza por *dai*, pero si el proyecto selecionado es uno diferente (quizás un proyecto que hayas creado tú por tu cuenta) puedes cambiarlo haciendo::

    gcloud config set project id

  donde ``id`` sería el id del proyecto obtenido de la lista anterior.

  Finalmente, para algunas actividades tendrás que tener instalado el gestor de bases de datos `SQLite3`_. Comprueba si ya lo tienes instalado ejecutando ``sqlite3``. Si no lo tienes, para el caso de Linux puedes descargar este fichero::

    curl -O https://www.sqlite.org/2019/sqlite-tools-linux-x86-3300100.zip

  .. _`SQLite3`: https://www.sqlite.org/index.html

  Descomprime el fichero anterior en cualquier carpeta de tu espacio de usuario y añade la carpeta al *PATH* del sistema añadiendo al fichero ``~/.profile``  o  ``~/bash_profile`` la línea::

    PATH=$PATH:~/carpeta/sqlite


Caso de uso: integración de servicios de Google Cloud Platform
--------------------------------------------------------------

En esta actividad vas a crear una solución que integre diversos servicios de Google Cloud Platform. Ve unos vídeos, explora los documentos de introducción enlazados y, a continuación realiza el ejercicio propuesto.

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Ve los vídeos de la lista de reproducción sobre los `elementos básicos de Google Cloud Platform`_ y toma nota de las principales ideas allí expuestas. La duración total de todos los vídeos de la lista es ligeramente inferior a una hora.

  .. _`elementos básicos de Google Cloud Platform`: https://www.youtube.com/playlist?list=PLIivdWyY5sqKh1gDR0WpP9iIOY00IE0xL

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Lanza en una estancia de Google Compute Engine un servidor web escrito en Node.js con Express que sirva una página web sencilla y ofrezca un servicio que realice una tarea simple, como por ejemplo, invertir una cadena de texto. La página web ha de invocar mediante la API Fetch al servicio web y, además, contener una imagen que previamente habrás subido a Google Cloud Storage. Añade a continuación en Google Cloud Functions una función en JavaScript que devuelva la cadena recibida como parámetro con las vocales eliminadas e invoca también este servicio desde la página web. Para simplificar, usa la consola de Google Cloud Platform siempre que te sea posible. ¡Elimina todos los recursos reservados cuando acabes!

  Para conseguir lo anterior, necesitarás leerte las siguientes guías rápidas:

  - Creación de una `máquina virtual con Linux`_ en Compute Engine.
  - Ejecutar un `servidor Apache básico`_ en Compute Engine.
  - `Subir ficheros`_ a Cloud Storage.
  - Implementar una `aplicación sencilla`_ en Node.js en App Engine.
  - Crear una `función HTTP`_ en Cloud Functions (sigue el ejemplo con Node.js).
  - Crear `una función`_ en Cloud Functions.
  - Crear `una función desde la línea de órdenes`_ en Cloud Functions.

  .. _`máquina virtual con Linux`: https://cloud.google.com/compute/docs/quickstart-linux?hl=EN
  .. _`servidor Apache básico`: https://cloud.google.com/compute/docs/tutorials/basic-webserver-apache?hl=EN
  .. _`Subir ficheros`: https://cloud.google.com/storage/docs/quickstart-console?hl=EN
  .. _`aplicación sencilla`: https://cloud.google.com/appengine/docs/standard/nodejs/quickstart?hl=EN
  .. _`función HTTP`: https://cloud.google.com/functions/docs/tutorials/http#functions-deploy-command-node8?hl=EN
  .. _`una función`: https://cloud.google.com/functions/docs/quickstart-console?hl=EN
  .. _`una función desde la línea de órdenes`: https://cloud.google.com/functions/docs/quickstart?hl=EN


Una definición de computación en la nube
----------------------------------------

Después de la toma de contacto de la actividad anterior, estamos en disposición de poder caracterizar de forma más precisa a qué nos referimos con la computación en la nube.

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Lee detenidamente la definición de computación en la nube dada por el National Institute of Standards and Technology en el documento "`The NIST Definition of Cloud Computing`_" y asegúrate de que entiendes cuáles son las características fundamentales de la computación en la nube, así como los diferentes modelos de servicios e implantación existentes. Lee con calma el documento ya que tiene una gran densidad terminológica y conceptual.

  .. _`The NIST Definition of Cloud Computing`: http://dx.doi.org/10.6028/NIST.SP.800-145

Los conceptos anteriores y algunos adicionales se recogen en `estas diapositivas`_.

.. _`estas diapositivas`: _static/slides/500-cloud-slides.html


