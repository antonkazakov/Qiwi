package com.antonkazakov.qiwitask.ui.validation;

import com.antonkazakov.qiwitask.data.beans.Validator;

import java.util.regex.Pattern;

/**
 * This validator checks if RegEx pattern can be applied to input String
 *
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public class SimpleRegexValidator implements Validatable<String> {

    private Validator validator;

    public SimpleRegexValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean isValid(String s) {
        return Pattern.matches(validator.getPredicate().getPattern(), s);
    }

    @Override
    public String getMessage() {
        return validator.getMessage();
    }


    public String getPattern(){
        return validator.getPredicate().getPattern();
    }

}
