package edu.unam.app;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;
import java.rmi.Naming;
import edu.unam.app.store.*;
import edu.unam.app.forwarding.*;

public class App {
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                ForwardingServer forwardingServer = new ForwardingServer();
                forwardingServer.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Store store = new Store();
                LocateRegistry.createRegistry(15000);
                Naming.rebind("//localhost:15000/store", store);
                System.out.println("Servidor iniciado!");
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
