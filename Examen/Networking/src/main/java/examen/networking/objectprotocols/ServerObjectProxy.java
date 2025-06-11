package examen.networking.objectprotocols;

import examen.model.Configuratie;
import examen.model.Joc;
import examen.model.User;
import examen.networking.dto.DTOUtils;
import examen.networking.dto.JocDTO;
import examen.networking.dto.UserDTO;
import examen.persistence.ValidationException;
import examen.services.IObserver;
import examen.services.IServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerObjectProxy implements IServices {

    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    //private List<Response> responses;
    private final BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServerObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        //responses=new ArrayList<Response>();
        qresponses= new LinkedBlockingQueue<>();
    }

    @Override
    public User login(User user, IObserver client) throws ValidationException {
        initializeConnection();
        System.out.println("Connection ok");
        UserDTO udto= DTOUtils.getDTO(user);
        sendRequest(new LoginRequest(udto));
        System.out.println("Sending login request");
        Response response=readResponse();
        System.out.println("Response received");
        if (response instanceof LoggedInResponse){
            this.client=client;
            UserDTO userDTO = ((LoggedInResponse) response).getUser();
            return DTOUtils.getFromDTO(userDTO);
        }
        if (response instanceof ErrorResponse err){
            closeConnection();
            throw new ValidationException(err.getMessage());
        }
        return null;
    }

    @Override
    public Joc incepeJoc(User user) throws ValidationException {
        UserDTO userDTO = DTOUtils.getDTO(user);
        System.out.println(userDTO);
        sendRequest(new IncepeJocRequest(userDTO));
        System.out.println("Sending req");
        Response response=readResponse();
        System.out.println(response);
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new ValidationException(err.getMessage());
        }
        IncepeJocResponse incepeJocResponse = (IncepeJocResponse)response;
        JocDTO jocDTO = incepeJocResponse.getJoc();
        System.out.println(jocDTO);
        return DTOUtils.getFromDTO(jocDTO);
    }

    @Override
    public String ghicestePozitie(Joc joc, int pozitieLinie, int pozitieColoana) throws ValidationException {
        JocDTO jocDTO= DTOUtils.getDTO(joc);
        sendRequest(new GhicestePozitieRequest(jocDTO, pozitieLinie, pozitieColoana));
        Response response=readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new ValidationException(err.getMessage());
        }
        return "";
    }

    @Override
    public void terminaJoc(Joc joc, IObserver client) throws ValidationException {
        JocDTO jocDTO= DTOUtils.getDTO(joc);
        sendRequest(new TerminaJocRequest(jocDTO));
        Response response=readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new ValidationException(err.getMessage());
        }
    }

    @Override
    public Joc[] clasament() throws ValidationException {
        sendRequest(new ClasamentRequest());
        Response response=readResponse();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new ValidationException(err.getMessage());
        }
        ClasamentResponse clasamentResponse = (ClasamentResponse)response;
        JocDTO[] jocDTOS = clasamentResponse.getJocDTOS();
        return DTOUtils.getFromDTO(jocDTOS);
    }

    @Override
    public Joc[] jocuriPierdute(User user) throws ValidationException {
        return null;
    }

    @Override
    public Configuratie schimbaConfiguratie(int id, int pozitieLinie, int pozitieColoana, String animal, String url) throws ValidationException {
        return null;
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws ValidationException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ValidationException("Error sending object "+e);
        }

    }

    private Response readResponse() throws ValidationException {
        Response response=null;
        try{
            /*synchronized (responses){
                responses.wait();
            }
            response = responses.remove(0);    */
            synchronized(qresponses){
                qresponses.wait();
            }
            response=qresponses.take();
            System.out.println("Response received");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws ValidationException {
        try {
            connection=new Socket(host,port);
            System.out.println("Connected to "+host+":"+port+": "+connection.toString());
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            System.out.println("Output to "+host+":"+port+": "+output.toString());
            input=new ObjectInputStream(connection.getInputStream());
            System.out.println("Input to "+host+":"+port+": "+input.toString());
            finished=false;
            System.out.println("Finished");
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        System.out.println("Starting reader");
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(UpdateResponse update){
        if (update instanceof ClasamentResponse clasamentResponse) {
            Joc[] jocuri = DTOUtils.getFromDTO(clasamentResponse.getJocDTOS());
            System.out.println(jocuri);
            try {
                client.jocTerminat(jocuri);
            } catch (ValidationException e){
                e.printStackTrace();
            }
        }
    }
    private class ReaderThread implements Runnable{
        public void run() {
            System.out.println("ReaderThread entered");
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (response instanceof UpdateResponse){
                        handleUpdate((UpdateResponse)response);
                    }else{
//                        responses.add((Response)response);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        synchronized (responses){
//                            responses.notify();
//                        }
                        synchronized(qresponses){
                            qresponses.notify();
                        }
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
