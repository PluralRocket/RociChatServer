package command;

import broker.MessageBroker;
import model.Chatroom;
import model.Message;

public class MoveToChatroomCommand implements Command {

    private MessageBroker messageBroker;
    private Chatroom chatroom;

    public MoveToChatroomCommand(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @Override
    public void execute(Message message) {

        String chatroomName = message.getMessageText().split(" ")[1];

        messageBroker.findChatroom(chatroomName).addMember(messageBroker.findClient(message.getFrom()));

    }
}
