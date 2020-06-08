# Laboratorio 2 😎

# Integrantes 👦👧
- Nacimiento, Francisco Martin
- Senghaas, Evelin Yaneth

# Vision 📖
Este laboratorio consta de dos partes.
En la primera parte, se debe crear un almacenamiento de tipo clave-valor en un único sitio accesible
mediante RMI o RPC.
En la segunda parte, se debe crear un servicio que constará de varias instancias, donde la instancia
principal es el almacenamiento clave valor y todas las otras instancias procesan solicitudes mediante
el reenvío de las mismas a la instancia principal. 

# Requisitos ✋
- Docker
- Docker-compose
- Python
- Pip

# Comenzar 🚀
Para utilizar el proyecto usted debe:

- Clonar el repositorio.
- Una vez clonado el repo ejecuta en tu consola (bash) `sh init.sh`. Podes ir a prepararte tu bebida favorita, comida y mirarte un capitulo de tu serie hasta que el comando termine de preparar el proyecto.
- Una vez listo el proyecto ejecuta `docker-compose up --scale forwarding=3` para probarlo. Puede escalar el servicio de servidores de reenvio *forwarding* al numero que desee.
- Ahora que tiene listo el almacen clave-valor y los servidores de reenvio. Debe instalar con pip la depencia de thrift con el siguiente comando `pip install -r requirements.txt`, debe tener en cuenta la version de pip que utilice, puede variar a `pip3` si tiene la version 3.
- Una vez finalizado la instalacion, debe ejecutar `python client.py` para empezar a utlizar el cliente. Tenga en cuenta la version de python que utilice, por lo que puede variar el comando.
- Para una guia de como utilizar el cliente, vea la seccion del cliente.

# Estructura del proyecto 👷
```
app/
    src/main/java/edu/unam/app
        forwarding/
        store/
        App.java
client.py
```
Dentro del directorio `app/src/` se encuentra toda la logica del almacenamiento clave-valor en el directorio `store/` y la logica de los servidores de reenvio en el directorio `forwarding/`. El almacenamiento de clave-valor y los servidores de reenvio estan implementados en el lenguaje **Java** ☕ y comunican a traves de **RMI**. Los servidores de reenvio y los clientes se comunican a traves de **Apache Thrift**, estando el cliente implementado en **Python** 🐍.

## App 🔀
El archivo `App.java` es el inicio del proyecto, en el se encuentra lo logica para determinar si el proyecto se ejecuta como un almacen de clave-valor o como una servidor de reenvio, mediante el recibiendo o no de un parametro, si recibe un parametro debe ejecutarse como un servidor de reenvio, en caso contrario como un almacenamiento clave-valor.

## Store 🏪
La logica del almacen clave-valor se encuentra en `/store/Store.java` en donde se implementa la interface de RMI `/store/StoreInterface.java`. Los metodos que deben implementar son `save` para guardar una clave y un valor, `get` para recuperar un valor mediante una clave y `delete` para borrar una clave y un valor mediante la clave. Para mas informacion, el proxima seccion se muestra la API del alcenamiento.

### API del Almacenamiento clave-valor

| Metodo | Parametros   | Retorno      | Ejemplo          |
| ------ | ------------ | ------------ | ---------------- |
| save   | (key, value) | 0, 1, error  | save(key, value) |
| get    | (key)        | value, error | value = get(key) |
| delete | (key)        | value. error | delete(key)      |


`save(key, value)`:
  - si la clave no existe debe devolver 0.
  - si la clave existe debe devolver 1
  - si no se satisfacen los requerimientos sobre formato de clave o valor se genera una excepción
error con los valores: id = 0 y detalle = guardar - error en formato.

`get(key)`:
  - Si la clave no existe se genera una excepción error con los valores: id = 1 y detalle = obtener -
clave no existe.
  - Si la clave existe se devuelve el valor asociado a la clave.

`delete(key)`:
  - Si la clave no existe se genera una excepción error con los valores: id = 2 y detalle = eliminar -
clave no existe.
  - Si la clave existe se devuelve el valor asociado a la clave eliminada.

En las instancias de reenvió, en el caso de que la instancia principal se encuentre caída se debe
devolver una excepción error con los valores: id = 3 y detalle = instancia principal caída.

## Forwarding 🔄
La logica de los servidores de reenvio se encuentra en `/forwarding/ForwardingHandler.java` el cual implementa la interface de Thrift `/forwarding/Forwarding.java`. `/forwarding/ForwardingServer` es el encargado de levantar el servidor y `/forwarding/Excep.java` maneja las excepciones, fue creado con thrift tambien.

# Archivo thrift
El archivo thrift del proyecto se encuentra en la raiz `/Forwarding.thrift`.
```thrift
# Para el cliente python se comenta la linea de abajo para generar los archivos thrift
namespace java edu.unam.app.forwarding

exception Excep {
	1: i32 id,
	2: string detail
}

service Forwarding {
	#Guardar clave y valor en el almacen
    i32 save(1: string key, 2: string value) throws (1: Excep e),

    #Recuperar un valor mediante su clave
    string get(1: string key) throws (1: Excep e),

    #Elimina una clave y valor mediante la clave
    string destroy(1: string key) throws (1: Excep e)
}
```
# Archivo docker-compose 🐳
El el archivo `/docker-compose.yml` puede encontrar informacion de como se encuentra construido el entorno de ejecucion el proyecto.

# Cliente magico 🐍
El cliente consiste en un CLI (Interface de Linea de Comandos) implementado con Python.

Cuando inicie el cliente lo primero que vera es:

![](img/init_cli.png)

Para saber que puertos tiene disponible puede utilizar el comando `docker ps` estan dentro del rango de 15000-15100, pero debe ver los que estan disponibles ya que no todos estan en escucha, depende a cuanto escalo el servicio *forwarding*.

En el caso de no dar con un puerto en escucha, no se preocupe, que sera informado y podra volver a ingresar un puerto que si este en escucha:

![](img/error_port.png)

Ingresando un puerto en escucha vera el siguiente menu:

![](img/menu.png)

Los errores que pueden tener durante la opeacion con el cliente son los siguientes:

![](img/errors.png)

Durante operaciones normales podra ver las siguientes respuestas:

![](img/normal.png)

![](img/exit.png)