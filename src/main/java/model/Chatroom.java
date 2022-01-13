package model;

import java.util.ArrayList;
import java.util.List;

public class Chatroom implements RequestProcessor {

    private String chatroomName;
    private List<Client> chatroomMembers = new ArrayList<>();

    public Chatroom(String name) {
        chatroomName = name;
    }

    public void addMember(Client client) {
        chatroomMembers.add(client);
    }

    @Override
    public void processRequest(Message message) {
        chatroomMembers
                .stream()
                .forEach(member -> member.processRequest(message));
    }

    public String getChatroomName() {
        return chatroomName;
    }
}
