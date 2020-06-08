package edu.unam.app.store;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StoreInterface extends Remote {
    
    Integer save(String key, String value) throws RemoteException, Error;
    String get(String key) throws RemoteException, Error;
    String delete(String key) throws RemoteException, Error;

}