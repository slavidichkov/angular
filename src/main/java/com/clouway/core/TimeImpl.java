package com.clouway.core;

import java.util.Date;
/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class TimeImpl implements Time{
    public Date now() {
        return new Date();
    }

    public Date after(int hour) {
        return new Date(now().getTime() + 1000*60*60*hour);
    }
}
