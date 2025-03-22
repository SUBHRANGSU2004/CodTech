import java.io.*;
import java.net.*;

public class ChatClient1 {
    public static void main(String[] args) {
        System.out.println("Welcome to ChatRoom of Subhrangsu Samadder");
        String serverAddress = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to chat server.");
            System.out.println(in.readLine()); // Ask for name
            out.println(userInput.readLine()); // Send name

            // Thread to read messages from server
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // Read user input and send to server
            String userMessage;
            while ((userMessage = userInput.readLine()) != null) {
                out.println(userMessage);
                if ("exit".equalsIgnoreCase(userMessage)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
