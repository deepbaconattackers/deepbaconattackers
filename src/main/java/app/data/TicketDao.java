package app.data;

import app.index.TicketSummary; //probably should move to models folder or something
import app.models.Room;
import app.models.Ticket;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

public class TicketDao {

    private Sql2o sql2o;

    public TicketDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public Iterable<Room> GetRooms()
    {
        try (Connection c = sql2o.open())
        {
            List<Room> rooms = c.createQuery("select room_id as id, room_name as name from rooms order by room_name asc")
                    .executeAndFetch(Room.class);

            return rooms;
        }
        catch(Exception e)
        {
            //todo: log it or do something
            return null;
        }
    }

    public Iterable<TicketSummary> GetRecentTickets()
    {
        try (Connection c = sql2o.open())
        {
            List<TicketSummary> tickets = c.createQuery("select created, room_name as room, ticket_name as title, ticket_status as status from tickets join rooms on tickets.room_id = rooms.room_id order by created desc limit 10")
                    .executeAndFetch(TicketSummary.class);

            return tickets;
        }
        catch(Exception e)
        {
            //todo: log it or do something
            return null;
        }
    }

    /**
     * Method which will persist a ticket to the database -- id is ignored and will be set to the id of the inserted ticket
     * if successful (so send in 0).  CreatedOn and Modified on are also ignored.
     * @param ticket
     * @return the ticket with the inserted id
     */
    public Ticket CreateTicket(Ticket ticket)
    {
        int ticketId = 0;

        try (Connection c = sql2o.open())
        {
            ticketId = c.createQuery("insert into tickets VALUES (NEXTVAL('tickets_ticket_id_seq'), :name, :type, :description, :status, :createdById, :room)",true)
                    .addParameter("name", ticket.getName())
                    .addParameter("type", ticket.getType())
                    .addParameter("description", "")
                    .addParameter("status", ticket.getStatus())
                    .addParameter("createdById", ticket.getCreatedBy().getUserId())
                    .addParameter("room", ticket.getRoom().getId())
                    .executeUpdate()
                    .getKey(Integer.class);
        }
        catch(Exception e)
        {
            //todo: log it or do something
            System.out.println(e.getMessage());
            return ticket;
        }

        ticket.setId(ticketId);
        return ticket;
    }
}
