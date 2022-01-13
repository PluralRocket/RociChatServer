package model;

import broker.MessageBroker;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AdminNotifier implements Sender {

    private List<Client> admins;

    private MessageBroker messageBroker = new MessageBroker();

    public List<Chatroom> groupChatrooms(Predicate<Chatroom> predicate) {
        return messageBroker.getChatroomList()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public void sendToChatroomGroup(Predicate<Chatroom> filter, Message message) {
        messageBroker.getChatroomList()
                .parallelStream()
                .filter(filter)
                .forEach(chatroom -> chatroom.processRequest(message));
    }

    public void sendToRecipients(Predicate<? extends RequestProcessor> filter, Message message) {
        messageBroker.getRecipients()
                .stream()
                .filter((Predicate<? super RequestProcessor>) filter)
                .forEach(requestProcessor -> requestProcessor.processRequest(message));
    }

    @Override
    public void sendMessage(Message message) {

        messageBroker.processRequest(message);

    }
}
