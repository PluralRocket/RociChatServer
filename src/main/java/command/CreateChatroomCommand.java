package command;

import broker.MessageBroker;
import model.Chatroom;
import model.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateChatroomCommand implements Command {

    private MessageBroker messageBroker;

    public CreateChatroomCommand(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void execute(Message message) {
        Chatroom chatroom = messageBroker.createChatroom(message.getMessageText().split(" ")[1]);
        String commandResult = "Created chatroom: " + chatroom.getChatroomName();
        commandResult += "All chatrooms: " + messageBroker.getChatroomList();
        Message serverResponse = new Message();
        serverResponse.setFrom("command");
        serverResponse.setTo(message.getFrom());
        serverResponse.setMessageText(commandResult);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        serverResponse.setTimestamp(LocalDateTime.now().format(formatter));
        messageBroker.processRequest(serverResponse);
    }
}
