package com.clouway.core;

import com.clouway.core.UIDGenerator;

import java.util.UUID;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class UIDGeneratorImpl implements UIDGenerator{
    public String randomID() {
        return UUID.randomUUID().toString();
    }
}
