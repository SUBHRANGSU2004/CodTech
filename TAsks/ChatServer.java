import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Welcome to ChatRoom of Subhrangsu Samadder");
        int port = 5000; // Server port
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter your name:");
                clientName = in.readLine();
                broadcast(clientName + " has joined the chat!");

                String message;
                while ((message = in.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(message)) {
                        break;
                    }
                    broadcast(clientName + ": " + message);
                }

                closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcast(String message) {
            System.out.println(message);
            for (ClientHandler client : clients) {
                client.out.println(message);
            }
        }

        private void closeConnection() {
            try {
                clients.remove(this);
                socket.close();
                broadcast(clientName + " has left the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
