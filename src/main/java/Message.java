import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Message {
    // Handles message creation, validation and storage

    private String messageID;
    private int messageNum;
    private String recipient;
    private String message;
    private String hash;

    private static int totalSent = 0;
    private static ArrayList<String> allMessages = new ArrayList<>();

    // Generates a random 10-digit message ID
    public String generateMessageID() {
        Random r = new Random();
        long num = 1000000000L + (long)(r.nextDouble() * 9000000000L);
        messageID = String.valueOf(num);
        return messageID;
    }

    public boolean checkMessageID() {
        if (messageID != null && messageID.length() == 10) {
            return true;
        }
        return false;
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

    // Remove punctuation from the message before splitting
    String cleanMessage = message.replaceAll("[^a-zA-Z0-9\\s]", "");
    String[] words = cleanMessage.trim().split("\\s+");

    String first = words[0].toUpperCase();
    String last = words[words.length - 1].toUpperCase();

    hash = firstTwo + ":" + messageNum + ":" + first + last;
    return hash;
}

    public String SentMessage(String choice) {
        if (choice.equals("1")) {
            totalSent++;
            String details = "Message ID: " + messageID +
                    "\nMessage Hash: " + hash +
                    "\nRecipient: " + recipient +
                    "\nMessage: " + message;
            allMessages.add(details);
            return "Message successfully sent.";
        } else if (choice.equals("2")) {
            return "Press 0 to delete the message.";
        } else if (choice.equals("3")) {
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid option.";
        }
    }

    public String printMessages() {
        if (allMessages.size() == 0) {
            return "No messages sent yet.";
        }

        String result = "";
        for (int i = 0; i < allMessages.size(); i++) {
            result = result + allMessages.get(i) + "\n\n";
        }
        return result;
    }

    public int returnTotalMessages() {
        return totalSent;
    }

    // stores the message in a json file
    public void storeMessage() {
        try {
            FileWriter fw = new FileWriter("storedMessages.json", true);
            fw.write("{\n");
            fw.write("\"messageID\": \"" + messageID + "\",\n");
            fw.write("\"hash\": \"" + hash + "\",\n");
            fw.write("\"recipient\": \"" + recipient + "\",\n");
            fw.write("\"message\": \"" + message + "\"\n");
            fw.write("},\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving message");
        }
    }

    public void setMessageNum(int num) {
        messageNum = num;
    }

    public void setMessage(String msg) {
        message = msg;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getHash() {
        return hash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}