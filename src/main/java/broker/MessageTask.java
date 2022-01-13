package broker;

import model.Message;
import model.RequestProcessor;

public class MessageTask implements Runnable {

    private final Message message;
    private final MessageBroker messageBroker;

    public MessageTask(Message message, MessageBroker messageBroker) {
        this.message = message;
        this.messageBroker = messageBroker;
    }

    @Override
    public void run() {
        RequestProcessor recipient = messageBroker.findRecipient(message.getTo());
        if (recipient != null) recipient.processRequest(message);
    }
}
