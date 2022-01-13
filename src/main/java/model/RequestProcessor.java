package model;

import model.Message;

public interface RequestProcessor {

    void processRequest(Message message);
}
