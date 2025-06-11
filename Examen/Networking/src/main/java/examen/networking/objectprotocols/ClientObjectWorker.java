package examen.networking.objectprotocols;


import examen.model.Joc;
import examen.model.User;
import examen.networking.dto.DTOUtils;
import examen.networking.dto.JocDTO;
import examen.networking.dto.UserDTO;
import examen.persistence.ValidationException;
import examen.services.IObserver;
import examen.services.IServices;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientObjectWorker implements Runnable, IObserver {

    private IServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientObjectWorker(IServices server, Socket connection) {
        System.out.println("ClientObjectWorker constructor");
        this.server = server;
        this.connection = connection;
        try{
            System.out.println("server before output");
            output=new ObjectOutputStream(connection.getOutputStream());
            System.out.println("server after output");
            output.flush();
            System.out.println("server before input");
            input=new ObjectInputStream(connection.getInputStream());
            System.out.println("server after input");
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse((Response) response);
                }
            } catch (EOFException e) {
                e.printStackTrace();
                connected=false;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private Response handleRequest(Request request){
        Response response=null;
        if (request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq=(LoginRequest)request;
            UserDTO udto=logReq.getUser();
            User user= DTOUtils.getFromDTO(udto);
            try {
                User user1 = server.login(user, this);
                return new LoggedInResponse(DTOUtils.getDTO(user1));
            } catch (ValidationException e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GhicestePozitieRequest){
            System.out.println("Ghiceste pozitie request ...");
            GhicestePozitieRequest ghReq=(GhicestePozitieRequest)request;
            JocDTO jocDTO=ghReq.getJocDTO();
            Joc joc =DTOUtils.getFromDTO(jocDTO);
            try{
                server.ghicestePozitie(joc, ghReq.getPozitieLinie(), ghReq.getPozitieColoana());
            } catch (ValidationException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        if( request instanceof IncepeJocRequest ){
            System.out.println("IncepeJoc request ...");
            IncepeJocRequest terminaReq=(IncepeJocRequest)request;
            UserDTO userDTO =terminaReq.getUser();
            User user =DTOUtils.getFromDTO(userDTO);
            try{
                System.out.println("inainte");
                server.incepeJoc(user);
                System.out.println("dupa");
            } catch (ValidationException e){
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof TerminaJocRequest){
            System.out.println("TerminaJoc request ...");
            TerminaJocRequest terminaReq=(TerminaJocRequest)request;
            JocDTO jocDTO=terminaReq.getJocDTO();
            Joc joc =DTOUtils.getFromDTO(jocDTO);
            try {
                server.terminaJoc(joc, this);
            } catch (ValidationException e) {
                return new ErrorResponse(e.getMessage());
            }
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void jocTerminat(Joc[] jocuri) throws ValidationException {
        JocDTO[] jocDTOS = DTOUtils.getDTO(jocuri);
        System.out.println(jocDTOS);
        try{
            sendResponse(new ClasamentResponse(jocDTOS));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
