package edu.unam.app.forwarding;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.thrift.TException;
import edu.unam.app.forwarding.Forwarding.Iface;
import edu.unam.app.store.Error;
import edu.unam.app.store.StoreInterface;

public class ForwardingHandler implements Iface {
    @Override
    public int save(String key, String value) throws Excep, TException {
        try {
            StoreInterface store = (StoreInterface) Naming.lookup("//store:15000/store");
            return store.save(key, value);
        } catch (MalformedURLException | NotBoundException | Error e) {
            throw new Excep(0, "guardar - error en formato.");
        } catch (RemoteException e) {
            throw new Excep(3, "instancia principal caída.");
        }
    }

    @Override
    public String get(String key) throws Excep, TException {
        try {
            StoreInterface store = (StoreInterface) Naming.lookup("//store:15000/store");
            return store.get(key);
        } catch (MalformedURLException | NotBoundException | Error e) {
            throw new Excep(1, "obtener - clave no existe.");
        } catch (RemoteException e) {
            throw new Excep(3, "instancia principal caída.");
        }
    }

    @Override
    public String destroy(String key) throws Excep, TException {
        try {
            StoreInterface store = (StoreInterface) Naming.lookup("//store:15000/store");
            return store.delete(key);
        } catch (MalformedURLException | NotBoundException | Error e) {
            throw new Excep(2, "eliminar - clave no existe.");
        } catch (RemoteException e) {
            throw new Excep(3, "instancia principal caída.");
        }
    }
}