package app.util;

public class Path {

    public static class Web {

        public static final String LOGIN = "/login/";
        public static final String LOGOUT = "/logout/";
        public static final String INDEX = "/index/";
        public static final String CREATE_TICKETS = "/tickets/create/";
        public static final String EDIT_TICKETS = "/tickets/edit/";


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

        public static String getCreateTICKETS() { return CREATE_TICKETS; }

        public static String getEditTICKETS() { return EDIT_TICKETS; }
    }

    public static class Template {
        public final static String INDEX = "/velocity/index/index.vm";
        public final static String LOGIN = "/velocity/login/login.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";

        public static final String CREATE_TICKET = "/velocity/tickets/createTicket.vm";
        public static final String CREATE_TICKET_SUCCESS = "/velocity/tickets/success.vm";

        public static final String EDIT_TICKET = "/velocity/tickets/editTicket.vm";
    }

}

