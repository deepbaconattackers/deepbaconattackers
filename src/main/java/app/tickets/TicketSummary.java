package app.tickets;

import java.util.Date;

public class TicketSummary {

    private Date created;
    private String room;
    private String title;
    private String status;
    private int id;

    public TicketSummary(Date created, String room, String title, String status, int id){
        this.created = created;
        this.room = room;
        this.title = title;
        this.status = status;
        this.id = id;
    }

    public Date getCreated() {
        return this.created;
    }

    public String getRoom() {
        return this.room;
    }

    public String getTitle() {
        return this.title;
    }

    public String getStatus() {
        return this.status;
    }

    public int getId() {
        return this.id;
    }

}
