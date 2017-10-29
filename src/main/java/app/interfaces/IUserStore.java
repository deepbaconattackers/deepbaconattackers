package app.interfaces;

import app.user.User;

public interface IUserStore {
    User getUserByUsername(String username);
}
