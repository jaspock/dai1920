.. role:: problema-contador

Computación en la  nube
=======================

La computación en la nube se ha convertido en la manera más habitual de gestionar los diferentes recursos necesarios para una aplicación web.

.. Important::

  Las habilidades que deberías adquirir con este tema incluyen las siguientes:

  - Entender la diferencia entre los distintos niveles de abstracción proporcionados por los diferentes modelos de computación en la nube.
  - Conocer, saber usar y relacionar entre ellos varios de los servicios de infraestructura y plataforma disponibles en la nube.
  - Saber implantar en Google App Engine aplicaciones web escritas en Node.js que usen las bases de datos relacionales de Google Cloud SQL.

.. _label-gcloud:

Configuración del entorno de trabajo para Google Cloud Platform
---------------------------------------------------------------

En las actividades de este tema vamos a usar diversos servicios de Google Cloud Platform. Aunque plataformas como Amazon Web Services, Microsoft Azure o Google Cloud Platform suelen ofrecer de forma gratuita un acceso limitado a algunos de sus servicios, en esta asignatura vas a a usar un proyecto en Google Cloud Platform que el profesor ya ha creado para ti y que está asignado a una cuenta de facturación para centros educativos que Google ofrece a la universidad. Tu proyecto está asociado a la cuenta que la universidad te asigna con `dominio gcloud.ua.es`_; asegúrate de que la tienes activada antes de seguir leyendo. Para no generar gastos innecesarios, recuerda eliminar los recursos que no vayas a seguir utilizando cuando acabes.

.. _`dominio gcloud.ua.es`: https://si.ua.es/es/manuales/uacloud/uacloudse/servicios-externos.html


.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Sigue las instrucciones que vienen a continuación para configurar tu entorno de trabajo para las actividades de este tema.

Comienza instalando Node.js y SQLite como se estudió en el tema anterior en la sección ":ref:`label-local`". También necesitarás tener instalado `gcloud`_, un cliente de línea de órdenes (CLI) que permite interactuar con Google Cloud Platform desde la línea de órdenes y que forma pate del Google Cloud SDK (por el inglés *software development kit*). 

.. Important::

  El fichero ``dai-bundle-dev`` que se menciona al comienzo del apartado ":ref:`label-local`" del capítulo anterior también te permite instalar el SDK de Google Cloud Platform. Edita el script ``install.sh`` y cambia, si procede, el valor de las variables de entorno del comienzo para que instale el SDK.

Las `instrucciones de instalación del SDK`_ cambian de un sistema operativo a otro; para el caso de Linux, y sin necesidad de tener privilegios de administrador, puedes ejecutar lo que sigue; sáltate este paso si has instalado el SDK con el script mencionado antes::

  curl https://sdk.cloud.google.com | bash

.. _`gcloud`: https://cloud.google.com/sdk/gcloud/?hl=EN
.. _`instrucciones de instalación del SDK`: https://cloud.google.com/sdk/docs/downloads-interactive?hl=EN

Ahora cierra el terminal actual y abre uno nuevo para que se actualicen los valores de las variables de entorno. Una vez instalado, has de vincular el SDK con tu cuenta de Google Cloud Platform (en este caso, tu cuenta ``gcloud.ua.es``)::

  gcloud init

Si posteriormente el SDK se desvinculara por algún motivo de tu cuenta, puedes volver a emparejarlo haciendo::

  gcloud auth login

Puedes ver los proyectos que tienes creados en Google Cloud Platform con::

  gcloud projects list

Y para ver cuál es el proyecto por defecto::

  gcloud config get-value project

Lo más habitual es que solo tengas el proyecto de la asignatura, cuyo nombre comienza por *dai*, pero si el proyecto seleccionado por defecto es uno diferente (quizás un proyecto que hayas creado tú por tu cuenta) puedes cambiarlo haciendo::

  gcloud config set project <id>

donde ``<id>`` sería el id del proyecto obtenido de la lista porporcionada por ``gcloud projects list``.

.. Para desinstalar gcloud borrar su directorio princial y ~/.config/gcloud en Linux.


Caso de uso: integración de servicios de Google Cloud Platform
--------------------------------------------------------------

En esta actividad vas a crear una solución que integre diversos servicios de Google Cloud Platform. Ve unos vídeos, explora los documentos de introducción enlazados y, a continuación realiza el ejercicio propuesto.

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Ve los vídeos de la lista de reproducción sobre los `elementos básicos de Google Cloud Platform`_ y toma nota de las principales ideas allí expuestas. Ten en cuenta que la duración total de todos los vídeos de la lista es ligeramente inferior a una hora.

  .. _`elementos básicos de Google Cloud Platform`: https://www.youtube.com/playlist?list=PLIivdWyY5sqKh1gDR0WpP9iIOY00IE0xL

Para conseguir llevar a cabo la siguiente tarea, necesitarás leerte y practicar con estas guías rápidas:

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


.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Lanza en una estancia de Google Compute Engine un servidor web escrito en Node.js con Express (esto se estudió en el tema anterior) que sirva una página web sencilla y ofrezca un servicio GET que realice una tarea simple, como por ejemplo, invertir una cadena de texto. La página web ha de invocar mediante la API Fetch al servicio web y, además, contener una imagen que previamente habrás subido a Google Cloud Storage. Añade a continuación en Google Cloud Functions una función en JavaScript que devuelva la cadena recibida como parámetro con las vocales eliminadas e invoca también este servicio desde la página web. Para simplificar, usa la consola web de Google Cloud Platform siempre que te sea posible. ¡Elimina todos los recursos reservados cuando acabes!


Una definición de computación en la nube
----------------------------------------

Después de la toma de contacto de la actividad anterior, estamos en disposición de poder caracterizar de forma más precisa a qué nos referimos con la computación en la nube.

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Lee detenidamente la definición de computación en la nube dada por el National Institute of Standards and Technology en el documento "`The NIST Definition of Cloud Computing`_" y asegúrate de que entiendes cuáles son las características fundamentales de la computación en la nube, así como los diferentes modelos de servicios e implantación existentes. Lee con calma el documento ya que tiene una gran densidad terminológica y conceptual.

  .. _`The NIST Definition of Cloud Computing`: http://dx.doi.org/10.6028/NIST.SP.800-145

Los conceptos anteriores y algunos adicionales se recogen en `estas diapositivas`_.

.. _`estas diapositivas`: _static/slides/500-cloud-slides.html


.. admonition:: :problema-contador:`Problema`

  Indica con qué sustituir ``@1`` en la siguiente frase para que sea correcta: "Los servicios de computación en la nube se pueden dividir en tres categorías en base al nivel de abstracción que ofrecen sobre los recursos utilizados: software como servicio, @1, e infraestructura como servicio"

  .. solución: @1=plataforma como servicio


.. _label-appengine:

Publicación de la aplicación del carrito en Google App Engine
-------------------------------------------------------------

Vamos a ver cómo desplegar la aplicación del carrito del tema anterior en Google App Engine, el servicio de plataforma de aplicaciones de Google Cloud Platform. Dado que las máquinas virtuales que se asignen a nuestra aplicación de Google App Engine pueden ser eliminadas o creadas en cualquier momento en base a la demanda, necesitamos almacenar los datos que maneje la aplicación en una base de datos alojada en otro lugar: Google Cloud Platform cuenta con Google Cloud SQL, un servicio de bases de datos relacionales en la nube que incluye el gestor MySQL. En el tema anterior vimos que la aplicación del carrito ya estaba configurada para trabajar con MySQL a través de Knex.js, por lo que no es necesario modificar el código. Al comienzo de este tema, en el apartado ":ref:`label-gcloud`", también se explica cómo configurar el cliente de línea de órdenes ``gcloud``. Para que la aplicación pueda usar MySQL debemos crear y configurar una instancia de base de datos en Google Cloud SQL.

.. admonition:: Hazlo tú ahora
  :class: hazlotu

  Sigue las instrucciones que vienen a continuación para aprender cómo desplegar una aplicación como la del carrito en Google App Engine.


Para crear la instancia de Cloud SQL desde el terminal es necesario ejecutar desde la línea de órdenes::

  gcloud sql instances create <instancia> --tier=db-n1-standard-1 --region=europe-west3 --root-password=<contraseñaAdmin>

donde ``<instancia>`` es el nombre la instancia de base de datos y ``<contraseñaAdmin>`` la contraseña que queremos para el administrador de la instancia. 

La primera vez que ejecutes la línea anterior, obtendrás un mensaje informando de que la API de SQL no está activada y ofreciéndote la opción de activarla; pídele que la active. Es posible que también se te solicite aceptar las `condiciones del servicio`_. Muchos servicios de Google Cloud Platform están desactivados inicialmente para evitar que las aplicaciones utilicen servicios de pago que no han sido autorizados explícitamente.

.. _`condiciones del servicio`: https://console.developers.google.com/terms/cloud

.. Note::

  La creación de la instancia de la base de datos puede tardar varios minutos. No sigas con este tutorial hasta que no se confirme su creación.

Ahora vamos a crear un nuevo usuario alternativo al administrador para acceder a la base de datos. Para ello, vamos a la `consola web de administración de instancias de bases de datos`_, seleccionamos la instancia creada anteriormente, y desde la pestaña :guilabel:`Usuarios` creamos una cuenta de usuario que pueda conectarse desde cualquier *host*.

.. _`consola web de administración de instancias de bases de datos`: https://console.cloud.google.com/sql/instances/

.. Note::

  Desde la línea de órdenes se debería también poder conseguir crear un usuario haciendo::
  
    gcloud sql users create <usuario> --host=% --instance=<instancia> --password=<contraseña>
    
  En el momento de escribir esto, sin embargo, la orden anterior no parece dar los privilegios adecuados al usuario (similares a los de administrador), lo que explica por qué para la creación de usuarios recurrimos a la consola web. 

A continuación, creemos una base de datos en la instancia de Cloud SQL para nuestra aplicación::

  gcloud sql databases create <bd> --instance=<instancia>

donde ``<bd>`` es el nombre de la base de datos e ``<instancia>`` es el nombre de la instancia de Cloud SQL que creamos anteriormente. Para comprobar que usuario y base de datos han sido creados correctamente, podemos consultar los usuarios y las bases de datos existentes haciendo::

  gcloud sql users list --instance=<instancia>
  gcloud sql databases list --instance=<instancia>

Ahora necesitamos conocer el nombre de conexión de la instancia que aparece en el campo ``connectionName`` al ejecutar::

  gcloud sql instances describe <instancia>

En Linux puedes mostrar solo la línea que contiene el nombre de conexión con::

  gcloud sql instances describe <instancia> | grep connectionName

Ahora, añade los datos anteriores a un fichero llamado ``.env`` que estará en la misma carpeta que ``config.js`` y ``app.js`` de la aplicación del carrito:

.. code-block::
  :linenos:

  GCP_SQL_USER=<usuario>
  GCP_SQL_PASSWORD=<contraseña>
  GCP_SQL_DATABASE=<bd>
  GCP_SQL_INSTANCE_CONNECTION_NAME=/cloudsql/<connectionName>

Observa que el nombre de la conexión va precedido de ``/cloudsql`` en la última variable de entorno.

El fichero ``app.yaml`` contiene información para Google App Engine como, entre otras cosas, las variables de entorno que se inicializarán antes de arrancar la aplicación. A diferencia de Heroku, estas variables de entorno no pueden definirse por medio del cliente de línea de órdenes (``gcloud``) sino únicamente a través de este fichero.

Para subir el código de la aplicación a Google App Engine y abrirla en el navegador basta con hacer::

  gcloud app deploy

.. Note::

  Un proyecto de Google Cloud Platform solo puede tener una aplicación de App Engine asociada como máximo. El proyecto que el profesor ha creado para ti ya tiene una aplicación de App Engine asociada; para otros proyectos sin aplicación vinculada, la línea anterior podría mostrar un mensaje ofreciendo crearla en ese momento.

La aplicación ya está desplegada en la nube. Podemos ahora abrirla directamente en el navegador sin necesidad de consultar su URL haciendo::

  gcloud app browse

Para ver todos los mensajes de *log* emitidos por la aplicación::

  gcloud app logs read

Y para ir viéndolos en un terminal mientras se van generando::

  gcloud app logs tail 

Para poder ejecutar instrucciones de SQL sobre la base de datos y asegurarnos de que nuestro programa está rellenándola correctamente, es necesario ir a la consola web de Google Cloud Platform, abrir el terminal web Cloud Shell y hacer::

  gcloud sql connect <instancia> --user=<usuario> 
  
Desde el terminal web podemos seleccionar nuestra base de datos con ``use <bd>;``, donde ``<bd>`` es el nombre correspondiente, y ejecutar instrucciones SQL como ``select * from productos;``. 

.. Note::

  La orden anterior de ``gcloud`` para conectarte a la base de datos también se puede lanzar desde un terminal local, pero es necesario que el sistema tenga MySQL instalado. También se pueden usar clientes de MySQL como `Adminer`_ o `phpMyAdmin`_. Si se desea usar la base de datos de Cloud SQL mientras se desarrolla en modo local se puede hacer con `Cloud SQL Proxy`_; en este curso, sin embargo, no será necesario porque nuestras aplicaciones usarán SQLite en modo local y MySQL solo cuando se implanten en la nube.

  .. _`Cloud SQL Proxy`: https://cloud.google.com/sql/docs/mysql/sql-proxy
  .. _`Adminer`: https://www.adminer.org/
  .. _`phpMyAdmin`: https://www.phpmyadmin.net/
