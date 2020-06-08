# Laboratorio 2

# Integrantes
- Nacimiento, Francisco Martin
- Senghaas, Evelin Yaneth

# API del Almacenamiento key-value

| Metodo | Parametros   | Retorno      | Ejemplo          |
| ------ | ------------ | ------------ | ---------------- |
| save   | (key, value) | 0, 1, error  | save(key, value) |
| get    | (key)        | value, error | value = get(key) |
| delete | (key)        | value. error | delete(key)      |


# Archivo thrift
```thrift
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