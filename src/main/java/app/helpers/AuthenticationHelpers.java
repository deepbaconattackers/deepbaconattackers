package app.helpers;

import app.user.User;
import spark.*;

public class AuthenticationHelpers {

    public static boolean isLoggedIn(Request request)
    {
        String user = request.session().attribute("currentUser");
        return user != null && !user.isEmpty();
    }

    public static boolean isManager(User user)
    {
        return  (user.getRole().toLowerCase()).compareTo("manager") == 0;
    }
}
