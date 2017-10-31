package app.util;

import java.net.URI;

public class ConnInfo {
    private String dbUrl;
    private String userName;
    private String password;

    public ConnInfo(String dbUrl, String userName, String password) {
        this.dbUrl = dbUrl;
        this.userName = userName;
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
    public String getDbUrl() {
        return this.dbUrl;
    }
    public String getUserName() {
        return this.userName;
    }

    //todo: probably need to move connection junk up to a common class to avoid copy/pasting in all controllers
    public static ConnInfo getConnectionInfo()  {
        URI dbUri;
        try {
            //todo: we all should add to env variables so this isn't hardcoded.
            //dbUri = new URI(System.getenv("DATABASE_URL"));
            dbUri = new URI("postgres://vpkztxjdkdexzz:74bc207ffc100be564cfc4684496e15f9c1973b5c8ab9c59c0e638391c40e2ab@ec2-23-21-101-249.compute-1.amazonaws.com:5432/dc9hargo78rjl0");
        }
        catch (Exception e)
        {
            return null; //todo: cry about it in the log or something
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        return new ConnInfo("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath(),
                username, password);
    }

}
