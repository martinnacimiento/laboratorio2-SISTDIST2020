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