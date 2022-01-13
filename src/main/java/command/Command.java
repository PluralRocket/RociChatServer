package command;

import model.Message;

public interface Command {

    void execute(Message message);
}
