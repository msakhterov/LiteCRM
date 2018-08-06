package ru.msakhterov.db_manager.maps;

import ru.msakhterov.crm_common.entity.User;

import java.util.HashMap;
import java.util.Map;

public class UserMap {

    private Map<String, User> users = new HashMap<>();

    public void addUser(User user){
        this.users.put(user.getLogin(), user);
    }

    public User getUser(String login){
        return users.get(login);
    }

}
