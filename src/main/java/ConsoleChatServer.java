import broker.CommandProcessor;
import broker.MessageBroker;
import client.ClientHandler;
import client.ClientInput;
import client.ClientOutput;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Client;
import model.Message;
import server.ClientConnectionListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ConsoleChatServer {

    private static final MessageBroker messageBroker = new MessageBroker();
    private static final CommandProcessor commandProcessor = new CommandProcessor(messageBroker);

    public static void main(String[] args) {
//        // TODO: extract main method and server configuration
//        int TCP_PORT = 9090;
//        try {
//            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
//
//            while (true) {
//                Socket clientSocket = null;
//                try {
//                    // Listen for client connections (blocking)
//                    clientSocket = serverSocket.accept();
//
//                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//
////                    Client client = new Client();
//
//                    // Create a handler to handle client requests
//                    ClientHandler clientHandler = new ClientHandler(messageBroker, commandProcessor, out, in);
//
//                    Client client = new Client("", clientHandler);
//
//                    String request = in.readLine();
//
//                    ObjectMapper objectMapper = new ObjectMapper();
//
//                    Message clientMessage = objectMapper.readValue(request, Message.class);
//                    if (client.getClientName().equals("")) {
////                System.out.println("clientName == null");
//                        client.setClientName(clientMessage.getFrom());
//                        messageBroker.addClient(client);
//                        clientHandler.brokerMessage(clientMessage);
//                    }
//
//                    // Create objects to handle writing and reading from the client streams.
//                    ClientInput clientInput = new ClientInput(clientHandler, new ObjectMapper());
//
//                    // Start a separate thread to read from the client.
//                    new Thread(clientInput).start();
////                    clientHandler.initialize();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    System.out.println("First catch");
//                    // Close the client socket on client disconnect, but keep the server socket open for other connections.
//                    clientSocket.close();
//                }
//            }
//
//        } catch (IOException e) {
//            // TODO: Think about how to handle the exception message etc.
//            e.printStackTrace();
//            System.out.println("Second catch");
//        }
//    }
        ClientConnectionListener listener = new ClientConnectionListener(9090);
        new Thread(listener).start();


    }
}
