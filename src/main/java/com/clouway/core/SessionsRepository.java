package com.clouway.core;

import com.google.common.base.Optional;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface SessionsRepository {
    void register(Session session);
    Optional<Session> getSession(String sessionID);
    void remove(Session session);
    void updateSessionExpiresOn(String sessionID);
}
