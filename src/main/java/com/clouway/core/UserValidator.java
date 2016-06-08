package com.clouway.core;

import java.util.List;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface UserValidator {
    Map<String,String> validate(ValidationUser user);
}
