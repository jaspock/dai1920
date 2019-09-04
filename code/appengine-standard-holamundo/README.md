# Servlet sencillo en Google App Engine Standard

Si no lo tienes ya, crea un nuevo proyecto de GCP, una aplicación de Google App Engine dentro de él y habilita la facturación en ese proyecto.

Descarga el [SDK de Google Cloud](https://cloud.google.com/sdk/docs/), descomprímelo y actualiza el $PATH. Ejecuta ahora:

    gcloud init

Identifícate con tu cuenta de Google en el navegador y copia en el terminal el código que se te ofrece.

Para ejecutar la aplicación localmente, ejecuta:

    mvn appengine:devserver

y accede a [http://localhost:8080](http://localhost:8080) o a la [consola local](http://localhost:8080/_ah/admin) para ver las entidades creadas 
en el Datastore.

Para desplegar la aplicación en la nube de GCP haz:

    mvn clean appengine:update -Dappengine.appId=<id-del-proyecto> -Dappengine.version=<version>

Accede a la [consola de GCP](https://console.cloud.google.com/home/dashboard) para ver el nombre de tu proyecto si no lo conoces. 

En la consola te será de interés poder ver las URLs de las versiones de tu aplicación desplegadas (*App Engine / Versiones*), las entidades creadas en el Datastore (*Datastore / Entidades*) y los índices correspondientes (*Datastore / Índices*), así como los registros de logging (*Logging / Registros*).

Más [información](https://cloud.google.com/java/getting-started-appengine-standard/tutorial-app).
