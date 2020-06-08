package edu.unam.app.forwarding;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;
import org.apache.thrift.protocol.TBinaryProtocol;

public class ForwardingServer {
    public void run() {
        ForwardingHandler handler = new ForwardingHandler();
        Forwarding.Processor processor = new Forwarding.Processor(handler);
        
        try {
            TServerTransport serverTransport = new TServerSocket(15000);
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
            
            args.processor(processor);
            args.transportFactory(new TTransportFactory());
            args.protocolFactory(new TBinaryProtocol.Factory());
        
            args.minWorkerThreads(10);
            args.maxWorkerThreads(50);
        
            TServer server = new TThreadPoolServer(args);
        
            server.serve();
        
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    
    }
}