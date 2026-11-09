package quikchat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Message {

    private String messageID;
    private int messageNum;
    private String recipient;
    private String message;
    private String hash;

    private static int totalSent = 0;

    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages = new ArrayList<>();

    static {
        loadStoredMessagesFromFile();
    }

    public String generateMessageID() {
        Random r = new Random();
        String id = "";
        for (int i = 0; i < 10; i++) {
            id = id + r.nextInt(10);
        }
        messageID = id;
        return messageID;
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    public String checkRecipientCell(String cell) {
        if (cell.matches("^\\+27[0-9]{9}$")) {
            recipient = cell;
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String clean = message.replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = clean.trim().split("\\s+");
        String first = words[0].toUpperCase();
        String last = words[words.length - 1].toUpperCase();
        hash = firstTwo + ":" + messageNum + ":" + first + last;
        return hash;
    }

    public String SentMessage(String choice) {
        if (choice.equals("1")) {
            totalSent++;
            sentMessages.add(this);
            return "Message successfully sent.";
        } else if (choice.equals("2")) {
            disregardedMessages.add(this);
            return "Message disregarded.";
        } else if (choice.equals("3")) {
            storedMessages.add(this);
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid option.";
        }
    }

    public int returnTotalMessages() {
        return totalSent;
    }

    public void storeMessage() {
        try {
            FileWriter fw = new FileWriter("storedMessages.json", true);
            fw.write("{\n");
            fw.write("  \"messageID\": \"" + messageID + "\",\n");
            fw.write("  \"hash\": \"" + hash + "\",\n");
            fw.write("  \"recipient\": \"" + recipient + "\",\n");
            fw.write("  \"message\": \"" + message + "\"\n");
            fw.write("},\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving the message to file.");
        }
    }

    public static void loadStoredMessagesFromFile() {
        File file = new File("storedMessages.json");
        if (!file.exists()) return;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            Message currentMsg = new Message();
            
            while ((line = br.readLine()) != null) {
                if (line.contains("\"messageID\":")) {
                    currentMsg.messageID = line.split(":")[1].replace("\"", "").replace(",", "").trim();
                } else if (line.contains("\"hash\":")) {
                    currentMsg.hash = line.split(":", 2)[1].replace("\"", "").replace(",", "").trim();
                } else if (line.contains("\"recipient\":")) {
                    currentMsg.recipient = line.split(":")[1].replace("\"", "").replace(",", "").trim();
                } else if (line.contains("\"message\":")) {
                    currentMsg.message = line.split(":")[1].replace("\"", "").replace(",", "").trim();
                } else if (line.contains("},")) {
                    storedMessages.add(currentMsg);
                    currentMsg = new Message();
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Could not load stored messages.");
        }
    }

    public static void displayStoredSendersAndRecipients() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages.");
            return;
        }
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            System.out.println("Recipient: " + m.getRecipient());
            System.out.println("Message: " + m.getMessage());
            System.out.println("-------------------");
        }
    }

    public static void displayLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages.");
            return;
        }
        Message longest = storedMessages.get(0);
        for (int i = 1; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessage().length() > longest.getMessage().length()) {
                longest = storedMessages.get(i);
            }
        }
        System.out.println("Longest stored message:");
        System.out.println(longest.getMessage());
    }

    public static void searchByMessageID(String id) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (m.getMessageID().equals(id)) {
                System.out.println("Recipient: " + m.getRecipient());
                System.out.println("Message: " + m.getMessage());
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    public static void searchByRecipient(String cell) {
        boolean found = false;
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (m.getRecipient().equals(cell)) {
                System.out.println("Message: " + m.getMessage());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found for that recipient.");
        }
    }

    public static void deleteByHash(String hashToDelete) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (m.getHash().equals(hashToDelete)) {
                System.out.println("Message: \"" + m.getMessage() + "\" successfully deleted.");
                storedMessages.remove(i);
                return;
            }
        }
        System.out.println("Hash not found.");
    }

    public static void displayFullReport() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages.");
            return;
        }
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            System.out.println("Message Hash: " + m.getHash());
            System.out.println("Recipient: " + m.getRecipient());
            System.out.println("Message: " + m.getMessage());
            System.out.println("-------------------");
        }
    }

    // Getters and Setters
    public void setMessageNum(int num) { messageNum = num; }
    public void setMessage(String msg) { message = msg; }
    
    // --- THESE ARE THE METHODS I JUST ADDED TO FIX YOUR TEST ---
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public void setMessageID(String messageID) { this.messageID = messageID; }
    public void setHash(String hash) { this.hash = hash; }
    // -----------------------------------------------------------

    public String getMessageID() { return messageID; }
    public String getHash() { return hash; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public static ArrayList<Message> getStoredMessages() { return storedMessages; }
}