package com.clouway.core;

import java.util.Date;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface Time {
    Date now();
    Date after(int hour);
}
