public class Login {
    private String storedUsername;
    private String storedPassword;
    private String storedCellNumber;
    
    // Hardcoded these because the registration prompt didn't ask for first/last name inputs
    private String firstName = "Kyle";
    private String lastName = "Smith";

    // Checks if username has an underscore and is 5 characters or less
    public boolean checkUserName(String userName) {
        if (userName.contains("_") && userName.length() <= 5) {
            return true;
        }
        return false;
    }

    // Checks password complexity using a loop
    public boolean checkPasswordComplexity(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        if (password.length() < 8) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // Reference: QuickBlox (2024) Beginner's guide to chat app architecture. 
    // Available at: https://quickblox.com/blog/beginners-guide-to-chat-app-architecture/ 
    // (Accessed: 17 September 2026).
    public boolean checkCellPhoneNumber(String cellNumber) {
        // Starts with +27 followed by 9 digits
        if (cellNumber.matches("^\\+27[0-9]{9}$")) {
            return true;
        }
        return false;
    }

    public String registerUser(String userName, String password, String cellNumber) {
        if (!checkUserName(userName)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number incorrectly formatted or does not contain an international code.";
        }

        // Save details if everything passes
        this.storedUsername = userName;
        this.storedPassword = password;
        this.storedCellNumber = cellNumber;

        return "User registered successfully.";
    }

    public boolean loginUser(String userName, String password) {
        if (userName.equals(storedUsername) && password.equals(storedPassword)) {
            return true;
        }
        return false;
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}