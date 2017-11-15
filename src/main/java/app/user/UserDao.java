package app.user;

import app.interfaces.*;
import com.google.common.collect.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.*;
import java.util.stream.*;

public class UserDao implements IUserStore {

    //using sql2o as it seems like it will be quicker than configuring nhibernate
    //if you want to use the latter go ahead and change this
    private Sql2o sql2o;

    public UserDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public User getUserByUsername(String username) {
        //return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
        try (Connection c = sql2o.open())
        {
                List<User> users = c.createQuery("select user_id as id, username, password as hashedPassword, role, salt from users where username=:username")
                        .addParameter("username", username)
                        .executeAndFetch(User.class);

                if(users.size() > 1) {
                    throw new Exception("More than one user found with this username.");
                }

                if(users.size() < 1)
                    return null; //or throw an exception, i don't know yet

                return users.get(0);
        }
        catch(Exception e)
        {
            //todo: log it or do something
            return null;
        }
    }

}