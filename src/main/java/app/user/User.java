package app.user;

public class User {

    private String username;
    private String salt;
    private String hashedPassword;
    private int id;
    private String role;

    public User() {}

    public User(int id)
    {
        this.id = id;
    }

    public User(int id, String name)
    {
        this.id = id; this.username = name;
    }

    public User(int id, String username, String salt, String hash, String role)
    {
        this.id = id;
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hash;
        this.role = role;
    }

    public int getUserId()
    {
        return this.id;
    }

    public int getId()
    {
        return this.id;
    }

    public void setUserId(int id){
        this.id = id;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUserName(String userName){
        this.username = userName;
    }

    public String getSalt()
    {
        return this.salt;
    }

    public String getHashedPassword()
    {
        return this.hashedPassword;
    }

    public String getRole()
    { 
        return this.role;
    }
}