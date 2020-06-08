package edu.unam.app.store;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.*;

/**
 * Store
 */
public class Store extends UnicastRemoteObject implements StoreInterface {

    public Store() throws RemoteException {
        super();
        this.store = new ConcurrentHashMap<String, String>();
    }

    private ConcurrentHashMap store;

    @Override
    public Integer save(String key, String value) throws RemoteException, Error {
        if (!Pattern.matches("[a-zA-Z0-9_]{1,50}", key) || value.getBytes().length > 1500000) {
            throw new Error("Error en formato.");
        }
        var response = 0;
        if (this.store.containsKey(key)) {
            response = 1;
        }
        this.store.put(key, value);
        return response;
    }

    @Override
    public String get(String key) throws RemoteException, Error {
        String value = (String) this.store.get(key);
        if (value == null) {
            throw new Error("No existe la clave ingresada.");
        }
        return value;
    }

    @Override
    public String delete(String key) throws RemoteException, Error {
        if (!this.store.containsKey(key)) {
            throw new Error("No existe la clave ingresada.");
        }
        return (String) this.store.remove(key);
    }
}