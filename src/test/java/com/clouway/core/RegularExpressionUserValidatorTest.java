package com.clouway.core;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static com.clouway.core.ValidationUser.newValidationUser;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class RegularExpressionUserValidatorTest {
  private RegularExpressionUserValidator regularExpressionUserValidator;

  @Before
  public void setUp(){
    regularExpressionUserValidator=new RegularExpressionUserValidator();
  }

  @Test
  public void happyPath() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.isEmpty(),is(true));
  }

  @Test
  public void validateUserNameLessThanThreeLetters() {
    ValidationUser validationUser = newValidationUser()
            .name("iv","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongName"),is(equalTo("have to be betwin 3 and 15 symbols and must contain only letters")));
  }

  @Test
  public void validateUserNameMoreThanFifteenLetters() {
    ValidationUser validationUser = newValidationUser()
            .name("ivanivanovivanov","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongName"),is(equalTo("have to be betwin 3 and 15 symbols and must contain only letters")));
  }

  @Test
  public void validateUserNameNotFromLetters() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan12","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongName"),is(equalTo("have to be betwin 3 and 15 symbols and must contain only letters")));
  }

  @Test
  public void validateIncorrectUsersEmail() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan.abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongEmail"),is(equalTo("is incorrect")));
  }

  @Test
  public void validateUserNickNameLessThanSixSymbols() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongNickName"),is(equalTo("have to be betwin 6 and 18 symbols and must contain only digits and letter")));
  }

  @Test
  public void validateUserNickNameMoreThanEighteenSymbols() {
    ValidationUser validationUser = newValidationUser()
            .name("ivanivanivanivan","wrongName")
            .nickName("ivan","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongNickName"),is(equalTo("have to be betwin 6 and 18 symbols and must contain only digits and letter")));
  }

  @Test
  public void validateUserPasswordLessThanSixSymbols() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan","wrongPassword")
            .confirmPassword("ivan","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongPassword"),is(equalTo("have to be betwin 6 and 18 symbols and must contain only digits and letter")));
  }

  @Test
  public void validateUserPasswordMoreThanEighteenSymbols() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan","wrongPassword")
            .confirmPassword("ivan","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongPassword"),is(equalTo("have to be betwin 6 and 18 symbols and must contain only digits and letter")));
  }

  @Test
  public void validateNotSamePasswords() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan12345","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongConfirmPassword"),is(equalTo("both password are not the same")));
  }

  @Test
  public void validateUserCityLessThanThreeLetters() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sl","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongCity"),is(equalTo("have to be betwin 3 and 15 symbols and must contain only letters")));
  }

  @Test
  public void validateUserCityMoreThanFifteenLetters() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("slivenslivensliven","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongCity"),is(equalTo("have to be betwin 3 and 15 symbols and must contain only letters")));
  }

  @Test
  public void validateUserCityNotFromLetters() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven12","wrongCity")
            .age("23","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongCity"),is(equalTo("have to be betwin 3 and 15 symbols and must contain only letters")));
  }

  @Test
  public void validateUserAgeLessThanEighteen() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("17","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongAge"),is(equalTo("have to be betwin 18 and 118")));
  }

  @Test
  public void validateUserAgeMoreThanOneHundredEighteen() {
    ValidationUser validationUser = newValidationUser()
            .name("ivan","wrongName")
            .nickName("ivan123","wrongNickName")
            .email("ivan@abv.bg","wrongEmail")
            .password("ivan123","wrongPassword")
            .confirmPassword("ivan123","wrongConfirmPassword")
            .city("sliven","wrongCity")
            .age("120","wrongAge")
            .build();
    Map<String,String> errors = regularExpressionUserValidator.validate(validationUser);
    assertThat(errors.get("wrongAge"),is(equalTo("have to be betwin 18 and 118")));
  }
}