import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class ClientProgram {


    public static void main(String[] args) throws JsonProcessingException {

        var keyboardInput = new BufferedReader(new InputStreamReader(System.in));

        HashMap<String, Integer> params = new HashMap<>();
        params.put("param1", 1);
        params.put("param2", 2);

//        CommandMessage commandMessage = new CommandMessage();
//        commandMessage.setFrom("Uros");
//        commandMessage.setCommandType("Command");
//        commandMessage.setParameters(params);

        Sendable testMessage = new TestMessage(params);
        Sendable bannerMessage = new BannerMessage("Banner");

        ObjectMapper objectMapper = new ObjectMapper();


        System.out.println(objectMapper.writeValueAsString(testMessage));
        System.out.println(objectMapper.writeValueAsString(bannerMessage));

        try {
            System.out.println("Enter client name: ");
            String client = keyboardInput.readLine();
            Message msg = new Message();
            msg.setFrom(client);

            Socket socket = new Socket("127.0.0.1", 9090);
            try (var out = new PrintWriter(socket.getOutputStream(), true)) {


                new Thread(() -> {
                    try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        String serverResponse;
                        while ((serverResponse = in.readLine()) != null) {
                            System.out.println((serverResponse));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();


                String request;
                // TODO: replace input method
                while (true) {
                    System.out.println("Enter TO: ");
                    String to = keyboardInput.readLine();
                    System.out.println("Enter text: ");
                    request = keyboardInput.readLine();
                    msg.setTo(to);
                    msg.setMessageText(request);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    msg.setTimestamp(LocalDateTime.now().format(formatter));
//                    ObjectMapper objectMapper = new ObjectMapper();
//
//                    out.println(objectMapper.writeValueAsString(msg));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

