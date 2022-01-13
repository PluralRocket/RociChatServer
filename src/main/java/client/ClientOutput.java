package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ClientOutput {

    private final PrintWriter out;
//    private final ClientHandler clientHandler;
    private OutputStream outputStream;

    public ClientOutput(PrintWriter out) {
        this.out = out;
    }

    public void sendMessage(Message message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            out.println(objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
