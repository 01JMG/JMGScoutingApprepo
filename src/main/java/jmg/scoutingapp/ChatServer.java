package jmg.scoutingapp;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ChatServer {
    private static final int PORT = 3000;
    private static ConcurrentHashMap<String, PrintWriter> clients = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String> roles = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Chat server running on port " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ClientHandler(socket)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private String username;
        private String role;
        private PrintWriter out;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ) {
                out = new PrintWriter(socket.getOutputStream(), true);

                // First message from client = username
                username = in.readLine();
                role = getRoleFromDirectory(username);

                if (role == null) {
                    out.println("System: Invalid username.");
                    socket.close();
                    return;
                }

                clients.put(username, out);
                roles.put(username, role);
                System.out.println(username + " (" + role + ") connected.");

                String msg;
                while ((msg = in.readLine()) != null) {
                    // Expect format: "receiverUsername:messageContent"
                    int sep = msg.indexOf(":");
                    if (sep == -1) continue;
                    String receiver = msg.substring(0, sep);
                    String content = msg.substring(sep + 1);

                    if (canSendMessage(role, roles.get(receiver))) {
                        PrintWriter receiverOut = clients.get(receiver);
                        if (receiverOut != null) {
                            receiverOut.println(username + ": " + content);
                        } else {
                            out.println("System: User " + receiver + " is not online.");
                        }
                    } else {
                        out.println("System: You cannot message " + receiver);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (username != null) {
                    clients.remove(username);
                    roles.remove(username);
                    System.out.println(username + " disconnected.");
                }
                try { socket.close(); } catch (IOException ignored) {}
            }
        }
    }

    private static String getRoleFromDirectory(String username) {
        String baseDir = "users"; // root folder
        String[] roleFolders = {"scouts", "coaches", "players", "agents", "fans"};

        for (String folder : roleFolders) {
            File userFile = new File(baseDir + "/" + folder + "/" + username);
            if (userFile.exists()) {
                return folder.substring(0, folder.length() - 1); // scouts -> scout
            }
        }
        return null; // user not found
    }

    private static boolean canSendMessage(String senderRole, String receiverRole) {
        if (senderRole == null || receiverRole == null) return false;
        return switch (senderRole) {
            case "coach" -> receiverRole.equals("scout") || receiverRole.equals("player");
            case "scout" -> receiverRole.equals("coach") || receiverRole.equals("agent");
            case "player" -> receiverRole.equals("coach") || receiverRole.equals("agent");
            case "agent" -> receiverRole.equals("scout") || receiverRole.equals("player");
            case "fan" -> receiverRole.equals("scout");
            default -> false;
        };
    }
}
