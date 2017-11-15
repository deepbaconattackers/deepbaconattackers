package app.user;

public class User {

    private String username;
    private String salt;
    private String hashedPassword;
    private int id;
    private String role;

    public User(int id)
    {
        this.id = id;
    }

    public User(int id, String username, String salt, String hash, String role){
        this.id = id;
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hash;
        this.role = role;
    }

    public int getUserId() { return this.id; }
    String getUsername() {
        return this.username;
    }

    String getSalt() {
        return this.salt;
    }

    String getHashedPassword() {
        return this.hashedPassword;
    }
    public String getRole() { return this.role; }

}