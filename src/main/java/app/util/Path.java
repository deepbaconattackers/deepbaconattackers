package app.util;

import lombok.*;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        //@Getter public static final String INDEX = "/index/";
//        @Getter public static final String LOGIN = "/login/";
//        @Getter public static final String LOGOUT = "/logout/";
//        @Getter public static final String INDEX = "/index/";

        public static final String LOGIN = "/login/";
        public static final String LOGOUT = "/logout/";
        public static final String INDEX = "/index/";

        public static String getINDEX()
        {
            return INDEX;
        }

        public static String getLOGIN()
        {
            return LOGIN;
        }

        public static String getLOGOUT()
        {
            return LOGOUT;
        }
    }

    public static class Template {
        public final static String INDEX = "/velocity/index/index.vm";
        public final static String LOGIN = "/velocity/login/login.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
    }

}

