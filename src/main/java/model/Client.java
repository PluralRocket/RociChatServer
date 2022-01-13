package model;

import broker.CommandProcessor;
import broker.MessageBroker;
import client.ClientHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Client implements RequestProcessor, Sender, Recipient {

    private String clientName;
    private ClientHandler clientHandler;
    private MessageBroker messageBroker;
    private CommandProcessor commandProcessor;
    // TODO: Implement MessageQueue for queuing incoming messages.
    // private Queue messageQueue;

//    public Client(String clientName, ClientHandler clientHandler) {
//        this.clientName = clientName;
//        this.clientHandler = clientHandler;
//    }

    private Client() {
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public MessageBroker getMessageBroker() {
        return messageBroker;
    }

    public void setMessageBroker(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    public CommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

    public void setCommandProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    public void processRequest(Message message) {
        clientHandler.sendMessage(message);
    }


    @Override
    public void sendMessage(Message message) {

        if (message.getTo().equals("command"))
            commandProcessor.processRequest(message);
        else
            messageBroker.brokerMessage(message);

//        clientHandler.brokerMessage(message);
    }

    public static Client initializeClient(PrintWriter out, BufferedReader in, MessageBroker messageBroker, CommandProcessor commandProcessor) throws IOException {
        Client client = new Client();
        ClientHandler clientHandler = new ClientHandler(out, in, client);
        client.setClientName("");
        client.setClientHandler(clientHandler);
        client.setMessageBroker(messageBroker);
        client.setCommandProcessor(commandProcessor);

        String request = in.readLine();

        ObjectMapper objectMapper = new ObjectMapper();

        Message clientMessage = objectMapper.readValue(request, Message.class);
        if (client.getClientName().equals("")) {
//                System.out.println("clientName == null");
            client.setClientName(clientMessage.getFrom());
            messageBroker.addClient(client);
            messageBroker.addRecipient(client);
            client.sendMessage(clientMessage);
        }

        clientHandler.startListenForMessages();

        return client;
    }

    public void disconnectClient() {
        messageBroker.removeClient(this);
    }

    @Override
    public String toString() {
        return clientName;
    }

    @Override
    public void receiveMessage(Message message) {
        clientHandler.sendMessage(message);
    }
}
