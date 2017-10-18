package app.user;

public class User {

    private String username;
    private String salt;
    private String hashedPassword;

    public User(String username, String salt, String hash){
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hash;
    }

    String getUsername() {
        return this.username;
    }

    String getSalt() {
        return this.salt;
    }

    String getHashedPassword() {
        return this.hashedPassword;
    }


}