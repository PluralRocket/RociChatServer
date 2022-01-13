package model;

import java.io.Serializable;

public class Message implements Serializable {

    private String from;
    private String to;
    private String messageText;
    private String timestamp;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    //    @Override
//    public String toString() {
//        return "model.Message{" +
//                "from='" + from + '\'' +
//                ", to='" + to + '\'' +
//                ", messageText='" + messageText + '\'' +
//                '}';
//    }
}

