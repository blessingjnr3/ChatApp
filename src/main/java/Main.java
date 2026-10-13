import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();

        // Registration
        System.out.println("=== Registration ===");
        System.out.print("Enter username: ");
        String user = sc.nextLine();

        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        System.out.print("Enter cell number: ");
        String cell = sc.nextLine();

        String regMsg = login.registerUser(user, pass, cell);
        System.out.println(regMsg);

        if (!regMsg.equals("User registered successfully.")) {
            System.out.println("Registration failed");
            return;
        }

        // Login
        System.out.println("\n=== Login ===");
        System.out.print("Username: ");
        String loginUser = sc.nextLine();
        System.out.print("Password: ");
        String loginPass = sc.nextLine();

        boolean loggedIn = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(loggedIn));

        if (!loggedIn) {
            System.out.println("Login failed");
            return;
        }

        // Part 2 starts here
        System.out.println("\nWelcome to QuickChat.");

        boolean running = true;

        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter choice: ");
            String option = sc.nextLine();

            if (option.equals("1")) {
                sendMessages(sc);
            } else if (option.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (option.equals("3")) {
                System.out.println("Goodbye");
                running = false;
            } else {
                System.out.println("Wrong option");
            }
        }

        sc.close();
    }

    public static void sendMessages(Scanner sc) {
        System.out.print("How many messages do you want to send? ");
        int count = Integer.parseInt(sc.nextLine());

        for (int i = 1; i <= count; i++) {
            System.out.println("\nMessage " + i);

            Message msg = new Message();
            msg.setMessageNum(i);

            String id = msg.generateMessageID();
            System.out.println("Message ID generated: " + id);

            System.out.print("Enter recipient number: ");
            String recip = sc.nextLine();
            String check = msg.checkRecipientCell(recip);
            System.out.println(check);

            if (!check.equals("Cell phone number successfully captured.")) {
                continue;
            }

            System.out.print("Enter message: ");
            String text = sc.nextLine();

            if (text.length() > 250) {
                System.out.println("Please enter a message of less than 250 characters.");
                i--;
                continue;
            }

            msg.setMessage(text);
            String hash = msg.createMessageHash();
            System.out.println("Hash: " + hash);

            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message");
            System.out.print("Choice: ");
            String action = sc.nextLine();

            String result = msg.SentMessage(action);
            System.out.println(result);

            if (action.equals("1")) {
                System.out.println("\nMessage ID: " + msg.getMessageID());
                System.out.println("Message Hash: " + msg.getHash());
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getMessage());
            }
        }

        Message m = new Message();
        System.out.println("\nTotal messages sent: " + m.returnTotalMessages());
    }
}