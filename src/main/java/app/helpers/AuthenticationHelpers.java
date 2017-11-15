package app.helpers;

import spark.*;

public class AuthenticationHelpers {

    public static boolean isLoggedIn(Request request)
    {
        String user = request.session().attribute("currentUser");
        return user != null && !user.isEmpty();
    }

    public static boolean isManager(Request request)
    {
        String role = request.session().attribute("currentRole");
        return role != null && (role.toLowerCase()).compareTo("manager") == 0;
    }
}
