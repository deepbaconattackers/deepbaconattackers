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

    //        Username    Salt for hash                    Hashed password (the password is "password" for all users)
    private final List<User> users = ImmutableList.of(
            new User("jbarna", "$2a$10$h.dl5J86rGH7I8bD9bZeZe", "$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwFTGujlnEaZXPf/q7vM5wO"),
            new User("abuhman",  "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe", "$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1HlCS4bZJ18JuywdEMLT83E1KDmUhCy"),
            new User("jpstratman",  "$2a$10$E3DgchtVry3qlYlzJCsyxe", "$2a$10$E3DgchtVry3qlYlzJCsyxeSK0fftK4v0ynetVCuDdxGVl1obL.ln2"),
            new User("jkrutz",  "$2a$10$E3DgchtVry3qlYlzJCsyxe", "$2a$10$E3DgchtVry3qlYlzJCsyxeSK0fftK4v0ynetVCuDdxGVl1obL.ln2")
    );

    public User getUserByUsername(String username) {
        //return users.stream().filter(b -> b.getUsername().equals(username)).findFirst().orElse(null);
        try (Connection c = sql2o.open())
        {
                List<User> users = c.createQuery("select username, password as hashedPassword, salt from users where username=:username")
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

    public Iterable<String> getAllUserNames() {
        return users.stream().map(User::getUsername).collect(Collectors.toList());
    }

}