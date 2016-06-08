package com.clouway.core;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class ValidationUser {
    public final String name;
    public final String nameErrorField;
    public final String nickName;
    public final String nickNameErrorField;
    public final String email;
    public final String emailErrorField;
    public final String password;
    public final String passwordErrorField;
    public final String confirmPassword;
    public final String confirmPasswordErrorField;
    public final String city;
    public final String cityErrorField;
    public final String age;
    public final String ageErrorField;

    private ValidationUser(String name, String nameErrorField, String nickName, String nickNameErrorField, String email, String emailErrorField, String password, String passwordErrorField, String confirmPassword, String confirmPasswordErrorField, String city, String cityErrorField, String age, String ageErrorField) {
        this.name = name;
        this.nameErrorField = nameErrorField;
        this.nickName = nickName;
        this.nickNameErrorField = nickNameErrorField;
        this.email = email;
        this.emailErrorField = emailErrorField;
        this.password = password;
        this.passwordErrorField = passwordErrorField;
        this.confirmPassword = confirmPassword;
        this.confirmPasswordErrorField = confirmPasswordErrorField;
        this.city = city;
        this.cityErrorField = cityErrorField;
        this.age = age;
        this.ageErrorField = ageErrorField;
    }

    public static Builder newValidationUser(){
        return new Builder();
    }

    public static class Builder {
        private  String name;
        private  String nameErrorField;
        private  String nickName;
        private  String nickNameErrorField;
        private  String email;
        private  String emailErrorField;
        private  String password;
        private  String passwordErrorField;
        private  String confirmPassword;
        private  String confirmPasswordErrorField;
        private  String city;
        private  String cityErrorField;
        private  String age;
        private  String ageErrorField;

        public Builder name(String name,String nameErrorField) {
            this.name = name;
            this.nameErrorField = nameErrorField;
            return this;
        }

        public Builder nickName(String nickName,String nickNameErrorField) {
            this.nickName = nickName;
            this.nickNameErrorField = nickNameErrorField;
            return this;
        }

        public Builder email(String email,String emailErrorField) {
            this.email = email;
            this.emailErrorField = emailErrorField;
            return this;
        }

        public Builder password(String password,String passwordErrorField) {
            this.password = password;
            this.passwordErrorField = passwordErrorField;
            return this;
        }

        public Builder confirmPassword(String confirmPassword,String confirmPasswordErrorField) {
            this.confirmPassword = confirmPassword;
            this.confirmPasswordErrorField = confirmPasswordErrorField;
            return this;
        }

        public Builder city(String city, String cityErrorField) {
            this.city = city;
            this.cityErrorField = cityErrorField;
            return this;
        }

        public Builder age(String age, String ageErrorField) {
            this.age = age;
            this.ageErrorField = ageErrorField;
            return this;
        }

        public ValidationUser build(){
            return new ValidationUser(this.name,this.nameErrorField,this.nickName,this.nickNameErrorField,this.email,this.emailErrorField,this.password,this.passwordErrorField,this.confirmPassword,this.confirmPasswordErrorField,this.city,this.cityErrorField,this.age,this.ageErrorField);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationUser that = (ValidationUser) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (nameErrorField != null ? !nameErrorField.equals(that.nameErrorField) : that.nameErrorField != null)
            return false;
        if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) return false;
        if (nickNameErrorField != null ? !nickNameErrorField.equals(that.nickNameErrorField) : that.nickNameErrorField != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (emailErrorField != null ? !emailErrorField.equals(that.emailErrorField) : that.emailErrorField != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (passwordErrorField != null ? !passwordErrorField.equals(that.passwordErrorField) : that.passwordErrorField != null)
            return false;
        if (confirmPassword != null ? !confirmPassword.equals(that.confirmPassword) : that.confirmPassword != null)
            return false;
        if (confirmPasswordErrorField != null ? !confirmPasswordErrorField.equals(that.confirmPasswordErrorField) : that.confirmPasswordErrorField != null)
            return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (cityErrorField != null ? !cityErrorField.equals(that.cityErrorField) : that.cityErrorField != null)
            return false;
        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        return ageErrorField != null ? ageErrorField.equals(that.ageErrorField) : that.ageErrorField == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nameErrorField != null ? nameErrorField.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        result = 31 * result + (nickNameErrorField != null ? nickNameErrorField.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (emailErrorField != null ? emailErrorField.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (passwordErrorField != null ? passwordErrorField.hashCode() : 0);
        result = 31 * result + (confirmPassword != null ? confirmPassword.hashCode() : 0);
        result = 31 * result + (confirmPasswordErrorField != null ? confirmPasswordErrorField.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (cityErrorField != null ? cityErrorField.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (ageErrorField != null ? ageErrorField.hashCode() : 0);
        return result;
    }
}
