import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login loginSystem = new Login();

        System.out.println("Chat App Registration");

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.print("Enter cell number (e.g. +27838968976): ");
        String cell = sc.nextLine();

        String regMessage = loginSystem.registerUser(username, password, cell);
        System.out.println(regMessage);

        // Only go to login if registration worked
        if (regMessage.equals("User registered successfully.")) {
            System.out.println("\nLogin");

            System.out.print("Enter username: ");
            String loginUser = sc.nextLine();

            System.out.print("Enter password: ");
            String loginPass = sc.nextLine();

            boolean loggedIn = loginSystem.loginUser(loginUser, loginPass);
            System.out.println(loginSystem.returnLoginStatus(loggedIn));
        }

        sc.close();
    }
}