package examen.networking.utils;

import examen.networking.objectprotocols.ClientObjectWorker;
import examen.services.IServices;

import java.net.Socket;

public class ObjectConcurrentServer extends AbsConcurrentServer {
    private IServices companieServices;
    public ObjectConcurrentServer(int port, IServices chatServer) {
        super(port);
        this.companieServices = chatServer;
        System.out.println("Chat- ChatObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        System.out.println("CompanieObjectConcurrentServer: Creating new CompanieClientObjectWorker");
        ClientObjectWorker worker=new ClientObjectWorker(companieServices, client);
        Thread tw=new Thread(worker);
        return tw;
    }
}
