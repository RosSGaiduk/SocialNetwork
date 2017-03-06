package com.social_network.ua.validations;

import com.social_network.ua.entity.User;
import com.social_network.ua.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Rostyslav on 06.03.2017.
 */
@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstName","firstName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"lastName","lastName.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","email.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","password.empty");


        if (userService.findUserByEmail(user.getEmail()))
            errors.rejectValue("email","email.exists");

        if (!user.getPassword().equals(user.getConfirmPassword()))
            errors.rejectValue("password","passwordsDifferent");
    }
}
