package server;

import broker.CommandProcessor;
import broker.MessageBroker;
import client.ClientHandler;
import client.ClientInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Client;
import model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnectionListener implements Runnable {

    private final int TCP_PORT;
    private static final MessageBroker messageBroker = new MessageBroker();
    private static final CommandProcessor commandProcessor = new CommandProcessor(messageBroker);

    public ClientConnectionListener(int tcpPort) {
        this.TCP_PORT = tcpPort;
    }

    @Override
    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);

            while (true) {
                Socket clientSocket = null;
                try {
                    // Listen for client connections (blocking)
                    clientSocket = serverSocket.accept();

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    Client client = Client.initializeClient(out, in, messageBroker, commandProcessor);

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("First catch");
                    // Close the client socket on client disconnect, but keep the server socket open for other connections.
                    clientSocket.close();
                    serverSocket.close();
                }
            }

        } catch (IOException e) {
            // TODO: Think about how to handle the exception message etc.
            e.printStackTrace();
            System.out.println("Second catch");
        }
    }
}
