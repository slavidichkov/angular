package com.clouway.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class RegularExpressionUserValidator implements UserValidator {
  public Map<String, String> validate(ValidationUser user) {
    Map<String, String> errors = new HashMap<String, String>();
    onlyLetters(user.name, user.nameErrorField, errors);
    onlyLetters(user.city, user.cityErrorField, errors);
    onlyLettersAndDigits(user.nickName, user.nickNameErrorField, errors);
    onlyLettersAndDigits(user.password, user.passwordErrorField, errors);
    validAge(user.age, user.ageErrorField, errors);
    correctEmail(user.email, user.emailErrorField, errors);
    same(user.password, user.confirmPassword, user.confirmPasswordErrorField, errors);
    return errors;
  }

  private void onlyLetters(String value, String errorField, Map<String, String> errors) {
    if (value != null && !value.matches("^([a-zA-Z]){3,10}$")) {
      errors.put(errorField, "have to be betwin 3 and 15 symbols and must contain only letters");
    }
  }

  private void onlyLettersAndDigits(String value, String errorField, Map<String, String> errors) {
    if (value != null && !value.matches("^([a-zA-Z0-9]){6,18}$")) {
      errors.put(errorField, "have to be betwin 6 and 18 symbols and must contain only digits and letter");
    }
  }

  private void validAge(String value, String errorField, Map<String, String> errors) {
    if (value != null && !value.matches("^(1[89]|[2-9][0-9]|1[10][0-8])$")) {
      errors.put(errorField, "have to be betwin 18 and 118");
    }
  }

  private void correctEmail(String value, String errorField, Map<String, String> errors) {
    if (value != null && !value.matches("^[A-Za-z0-9-+]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})$")) {
      errors.put(errorField, "is incorrect");
    }
  }

  private void same(String a, String b, String errorField, Map<String, String> errors) {
    if (a !=null && b!=null && !a.equals(b)) {
      errors.put(errorField,  "both password are not the same");
    }
  }
}
