package jmg.scoutingapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.*;
import java.net.Socket;
import java.io.PrintWriter;

public class OneToOneTextController {

    @FXML
    private ListView<ChatMessage> chatListView;  // <-- changed from TextArea

    @FXML
    private TextField inputField;

    @FXML
    private Button sendButton;

    private String username = "Mbewe03";
    private Socket socket;
    private PrintWriter out;

    public void initialize() {
        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);

            // send username once (as you did before)
            out.println(username);

            // === Cell factory: render each ChatMessage as a bubble aligned left/right ===
            chatListView.setCellFactory(lv -> new ListCell<ChatMessage>() {
                private final Label textLabel = new Label();
                private final HBox container = new HBox();

                {
                    textLabel.setWrapText(true);
                    textLabel.setMaxWidth(400);       // bubble max width
                    container.setSpacing(6);
                    container.setMaxWidth(Double.MAX_VALUE);
                    HBox.setHgrow(textLabel, Priority.ALWAYS);
                }

                @Override
                protected void updateItem(ChatMessage msg, boolean empty) {
                    super.updateItem(msg, empty);
                    if (empty || msg == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        textLabel.setText(msg.getText());
                        textLabel.getStyleClass().clear();

                        if (msg.isSender()) {
                            textLabel.getStyleClass().add("sender-bubble");
                            container.setAlignment(Pos.CENTER_LEFT);    // sender on LEFT (per your request)
                        } else {
                            textLabel.getStyleClass().add("receiver-bubble");
                            container.setAlignment(Pos.CENTER_RIGHT);   // receiver on RIGHT
                        }

                        container.getChildren().setAll(textLabel);
                        setGraphic(container);
                    }
                }
            });

            // === Listener thread: read from socket and add ChatMessage items ===
            new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        final String received = msg;
                        Platform.runLater(() -> {
                            // parse "username: body" (if present)
                            String[] parts = received.split(": ", 2);
                            boolean isSender = parts.length > 1 && parts[0].equals(username);
                            String body = parts.length > 1 ? parts[1] : received;
                            chatListView.getItems().add(new ChatMessage(body, isSender));
                            chatListView.scrollTo(chatListView.getItems().size() - 1);
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // make sure sendButton works even if FXML didn't wire onAction
            sendButton.setOnAction(e -> handleSend());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSend() {
        String message = inputField.getText().trim();
        if (message.isEmpty()) return;

        out.println(username + ": " + message);                 // send to server
        chatListView.getItems().add(new ChatMessage(message, true)); // show locally (sender)
        chatListView.scrollTo(chatListView.getItems().size() - 1);
        inputField.clear();
    }
}
