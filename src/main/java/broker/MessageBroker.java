package broker;

import jdk.swing.interop.SwingInterOpUtils;
import model.Chatroom;
import model.Client;
import model.Message;
import model.RequestProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MessageBroker implements RequestProcessor, Broker {

    // Implement model.Message Queue and a thread pool for handling messages.
    private static MessageBroker messageBroker;

    // TODO: Switch to Map implementation.
    private final List<Client> clientList = new ArrayList<>();
    private final List<Chatroom> chatroomList = new ArrayList<>();
    private final List<RequestProcessor> recipients = new ArrayList<>();

    private final ExecutorService executor = Executors.newCachedThreadPool();

//    private MessageBroker(){};
//
//    public static MessageBroker getInstance() {
//        if (messageBroker == null) messageBroker = new MessageBroker();
//        return messageBroker;
//    }

    public void addClient(Client client) {
        clientList.add(client);
        System.out.println(clientList.get(0).getClientName());
    }

    public List<Client> removeClient(Client client) {
        clientList.remove(client);
        System.out.println(clientList);
        return clientList;
    }

    public Client findClient(String name) {
        return clientList
                .stream()
                .filter(client -> client.getClientName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Chatroom createChatroom(String chatroomName) {
        Chatroom chatroom = new Chatroom(chatroomName);
        chatroomList.add(chatroom);
        return chatroom;
    }

    public Chatroom findChatroom(String name){
        return chatroomList
                .stream()
                .filter(chatroom -> chatroom.getChatroomName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Chatroom> getChatroomList() {
        return chatroomList;
    }

    public RequestProcessor findRecipient(String name) {
        RequestProcessor recipient;
        if ((recipient = findClient(name)) == null)
            recipient = findChatroom(name);
        System.out.println(clientList);
        System.out.println(chatroomList);
        return recipient;
    }

    public void addRecipient(RequestProcessor rp) {
        recipients.add(rp);
    }

    public List<RequestProcessor> getRecipients() {
        return recipients;
    }

    @Override
    public void processRequest(Message message) {
        executor.submit(new MessageTask(message, this));
    }

    public void sendToGroups(Supplier<List<? extends RequestProcessor>> supplier, Message message) {
        supplier.get()
                .stream()
                .forEach(requestProcessor -> requestProcessor.processRequest(message));
    }

    public List<RequestProcessor> filterRecipients(Predicate<? super RequestProcessor> filter) {
        return recipients
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public void brokerMessage(Message message) {
        executor.submit(new MessageTask(message, this));
    }
}
