package com.epam.esm.validator.realisation.update;

import com.epam.esm.validator.realisation.IntermediateValidatorForUpdateLink;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateNameValidatorLink extends IntermediateValidatorForUpdateLink {

    private static final String KEY = "name";
    private static final String REG_EXP_NAME = "^[A-Za-z ]+$";

    @Override
    public boolean validate(Map<String, String> paramsMap) {
        if (paramsMap.containsKey(KEY)) {
            String name = paramsMap.get(KEY);
            if (name.length() > 50) {
                return false;
            }
            Pattern pattern = Pattern.compile(REG_EXP_NAME);
            Matcher matcher = pattern.matcher(name);
            if (!matcher.matches()) {
                return false;
            }
        }
        return checkNextLink(paramsMap);
    }
}
