package jmg.scoutingapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple immutable chat message object.
 * Includes multiple constructors to match common usages.
 */
public class ChatMessage {
    private final String senderName;    // optional (may be null)
    private final String text;          // message body
    private final boolean sender;       // true if this is from the current user
    private final LocalDateTime timestamp;

    // --- Constructor used in the controller (text, isSender) ---
    public ChatMessage(String text, boolean sender) {
        this(null, text, sender, LocalDateTime.now());
    }

    // --- Constructor with explicit sender name ---
    public ChatMessage(String senderName, String text, boolean sender) {
        this(senderName, text, sender, LocalDateTime.now());
    }

    // --- Full constructor (all fields) ---
    public ChatMessage(String senderName, String text, boolean sender, LocalDateTime timestamp) {
        this.senderName = senderName;
        this.text = text == null ? "" : text;
        this.sender = sender;
        this.timestamp = (timestamp == null) ? LocalDateTime.now() : timestamp;
    }

    // --- Utility: parse a raw line of form "username: body" and decide isSender by currentUser ---
    public static ChatMessage parse(String rawLine, String currentUser) {
        if (rawLine == null) return new ChatMessage("", false);

        String[] parts = rawLine.split(": ", 2);
        if (parts.length == 2) {
            String name = parts[0];
            String body = parts[1];
            boolean isSender = name.equals(currentUser);
            return new ChatMessage(name, body, isSender);
        } else {
            // No "username: " prefix — treat whole line as body from other side
            return new ChatMessage(rawLine, false);
        }
    }

    // --- Getters ---
    public String getSenderName() { return senderName; }
    public String getText() { return text; }
    public boolean isSender() { return sender; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // formatted time (HH:mm)
    public String getTimeShort() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String toString() {
        if (senderName != null) return senderName + ": " + text;
        return text;
    }
}
