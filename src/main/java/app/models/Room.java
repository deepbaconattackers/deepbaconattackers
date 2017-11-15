package app.models;

public class Room {
    public int id;
    public String name;

    public Room() {}

    public Room(int id)
    {
        this.id = id;
    }

    public Room(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }
}
