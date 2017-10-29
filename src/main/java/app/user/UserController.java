package app.user;

import org.mindrot.jbcrypt.*;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.quirks.PostgresQuirks;

import java.net.URI;
import java.net.URISyntaxException;

public class UserController {

    static ConnInfo connInfo = getConnectionInfo() == null ? new ConnInfo("","","") : getConnectionInfo();
    static Sql2o sql2o = new Sql2o(connInfo.getDbUrl() + "?sslmode=require",
            connInfo.getUserName(), connInfo.getPassword()
    );


    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        //todo: inject dependencies for unit testing
        UserDao userDao = new UserDao(sql2o);

        //note: if you want this to work from your local machine,
        //no pg_hba.conf entry for host
        User user = userDao.getUserByUsername(username);

        if (user == null) {
            return false;
        }


        String hashedPassword = BCrypt.hashpw(password, user.getSalt());
        return hashedPassword.equals(user.getHashedPassword());
    }

    // This method doesn't do anything, it's just included as an example
    public static void setPassword(String username, String oldPassword, String newPassword) {
        if (authenticate(username, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password
        }
    }

    //todo: probably need to move connection junk up to a common class to avoid copy/pasting in all controllers
    private static ConnInfo getConnectionInfo()  {
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

class ConnInfo {
    private String dbUrl;
    private String userName;
    private String password;

    ConnInfo(String dbUrl, String userName, String password) {
        this.dbUrl = dbUrl;
        this.userName = userName;
        this.password = password;
    }

    String getPassword() {
        return this.password;
    }
     String getDbUrl() {
        return this.dbUrl;
    }
     String getUserName() {
        return this.userName;
    }

}