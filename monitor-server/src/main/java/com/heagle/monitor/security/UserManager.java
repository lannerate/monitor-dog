
package com.heagle.monitor.security;

import java.util.List;

/**
* @author hui.zhang
 *
 */
public interface UserManager {
    List<User> listUsers();

     void registerUser(User user)    ;

    public User loadUserByUsername(String userName);

    List<String> loadAdmins();

    boolean isSystemAdmin(String username);

    void monitorUser(User user);
}
