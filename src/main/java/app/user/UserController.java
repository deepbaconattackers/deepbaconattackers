package app.user;

import app.util.ConnInfo;
import org.mindrot.jbcrypt.*;
import org.sql2o.Sql2o;

import java.net.URI;

public class UserController {

    static ConnInfo connInfo = ConnInfo.getConnectionInfo() == null ? new ConnInfo("","","") : ConnInfo.getConnectionInfo();
    static Sql2o sql2o = new Sql2o(connInfo.getDbUrl() + "?sslmode=require",
            connInfo.getUserName(), connInfo.getPassword()
    );

    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    //the last parameter is an "out" parameter that can be used by the caller
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
        boolean isValidUser = hashedPassword.equals(user.getHashedPassword());

        return isValidUser;
    }

    // This method doesn't do anything, it's just included as an example
    public static void setPassword(String username, String oldPassword, String newPassword) {
        if (authenticate(username, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password
        }
    }




}

