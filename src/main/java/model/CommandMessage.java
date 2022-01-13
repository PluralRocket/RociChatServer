package model;

import java.io.Serializable;
import java.util.HashMap;

public class CommandMessage implements Serializable {

    private String from;
    private String commandType;
    private HashMap<String, String> parameters = new HashMap<String, String>();

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }
}
