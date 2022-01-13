package client;

import broker.CommandProcessor;
import broker.MessageBroker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Client;
import model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {

//    private final Socket clientSocket;
//    private final MessageBroker messageBroker;
//    private final CommandProcessor commandProcessor;
//    private ClientOutput out;
//    private ClientInput in;
    private final PrintWriter streamOut;
    private final BufferedReader streamIn;
    private final Client client;

    public ClientHandler(/*MessageBroker messageBroker, CommandProcessor commandProcessor,*/ PrintWriter out, BufferedReader in, Client client) {
//        this.clientSocket = clientSocket;
//        this.messageBroker = messageBroker;
//        this.commandProcessor = commandProcessor;
        this.streamOut = out;
        this.streamIn = in;
        this.client = client;
    }

//    public void initialize() {
//
//        try {
//
//            streamOut = new PrintWriter(clientSocket.getOutputStream(), true);
//            streamIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//            out = new ClientOutput(streamOut, this);
//            in = new ClientInput(streamIn, this);
//
//            String request = streamIn.readLine();
//
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            client = new Client("", this);
//
//            Message clientMessage = objectMapper.readValue(request, Message.class);
//            if (client.getClientName().equals("")) {
//                System.out.println("clientName == null");
//                client.setClientName(clientMessage.getFrom());
//                messageBroker.addClient(client);
//                brokerMessage(clientMessage);
//            }
//
//            new Thread(in).start();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void brokerMessage(Message message) {

        client.sendMessage(message);

//        if (message.getTo().equals("command"))
//            commandProcessor.processRequest(message);
//        else
//            messageBroker.processRequest(message);
    }

    public void closeStreams() {
        try {
            this.streamIn.close();
            this.streamOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.disconnectClient();
        }
    }

    public PrintWriter getStreamOut(){
        return streamOut;
    }

    public BufferedReader getStreamIn() {
        return streamIn;
    }

    public void sendMessage(Message message) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            streamOut.println(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void startListenForMessages() {
        ClientInput clientInput = new ClientInput(this, new ObjectMapper());
        new Thread(clientInput).start();
    }

//    public Client getClient() {
//        return client;
//    }
}
