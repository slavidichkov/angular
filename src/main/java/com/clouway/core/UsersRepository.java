package com.clouway.core;

import com.google.common.base.Optional;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface UsersRepository {
    void register(User user);
    Optional<User> getUser(String nickName);
}
