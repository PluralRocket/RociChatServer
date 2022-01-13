package broker;

import command.Command;
import command.CreateChatroomCommand;
import command.MoveToChatroomCommand;
import model.Chatroom;
import model.Message;
import model.RequestProcessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class CommandProcessor implements RequestProcessor {

    private HashMap<String, Command> commandHashMap = new HashMap<String, Command>();

    public CommandProcessor(MessageBroker messageBroker) {
        this.messageBroker = messageBroker;
        commandHashMap.put("move", new MoveToChatroomCommand(messageBroker));
        commandHashMap.put("create", new CreateChatroomCommand(messageBroker));
    }

    private final MessageBroker messageBroker;

    @Override
    public void processRequest(Message message) {

        String commandString = message.getMessageText().split(" ")[0];

        Command command = commandHashMap.get(commandString);
        command.execute(message);

    }
}
