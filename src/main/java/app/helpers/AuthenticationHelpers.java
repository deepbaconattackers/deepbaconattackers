package app.helpers;

import spark.*;

public class AuthenticationHelpers {

    public static boolean isLoggedIn(Request request)
    {
        String user = request.session().attribute("currentUser");
        return user != null && !user.isEmpty();
    }
}
