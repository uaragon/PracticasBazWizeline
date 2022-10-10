#Llave para el análisis en sSonar cloud : uaragon2022
#SonarToken 144acc45ce187da9a28dab49b6414ceee2d22071
1.- Iniciar el servidor de MongoDB
2.- En la carpeta RESOURCES que se encuentra dentro del proyecto econtrarás:
   - El archivo : aplication.properties donde debes cambiar la bd , ya que debes indicar la que usarás en tu local: spring.data.mongodb.database=test
   - El archivo : TestPeticiones.postman_collection.json el cual contiene la colleción postan de todos los endpoints que usarás durante las pruebas.
   - El archivo YAML : openapi3_0.yaml el cual debes abrir en alguna aplicación en línea que acepte las especificación de un Open API
     Se recomienda Swagger https://swagger.io, este archivo contiene los Happy Path de las pruebas.

