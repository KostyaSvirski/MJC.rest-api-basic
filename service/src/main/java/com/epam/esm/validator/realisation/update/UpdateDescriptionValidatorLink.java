package com.epam.esm.validator.realisation.update;

import com.epam.esm.validator.realisation.IntermediateValidatorForUpdateLink;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateDescriptionValidatorLink extends IntermediateValidatorForUpdateLink {

    private static final String KEY = "description";
    private static final String REG_EXP_DESCRIPTION = "^[A-Za-z1-9 ]+$";

    @Override
    public boolean validate(Map<String, String> paramsMap) {
        if (paramsMap.containsKey(KEY)) {
            String description = paramsMap.get(KEY);
            Pattern pattern = Pattern.compile(REG_EXP_DESCRIPTION);
            Matcher matcher = pattern.matcher(description);
            if (!matcher.matches()) {
                return false;
            }
        }
        return checkNextLink(paramsMap);
    }
}
