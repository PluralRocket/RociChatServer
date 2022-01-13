package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Message;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientInput implements Runnable {

//    private BufferedReader in;
    private ClientHandler clientHandler;
    private ObjectMapper objectMapper;

    public ClientInput(ClientHandler clientHandler, ObjectMapper objectMapper) {
//        this.in = in;
        this.clientHandler = clientHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run() {

        try {
            String request;
            while ((request = clientHandler.getStreamIn().readLine()) != null) {
                Message clientMessage = objectMapper.readValue(request, Message.class);
                System.out.println(clientMessage);
//                messageBroker.processRequest(clientMessage);
                clientHandler.brokerMessage(clientMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientHandler.closeStreams();
            System.out.println("finally clientHandler close streams");
        }
    }
}
