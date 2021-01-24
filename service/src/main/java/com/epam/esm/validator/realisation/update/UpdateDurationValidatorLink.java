package com.epam.esm.validator.realisation.update;

import com.epam.esm.validator.realisation.IntermediateValidatorForUpdateLink;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateDurationValidatorLink extends IntermediateValidatorForUpdateLink {

    private static final String KEY = "duration";
    private static final String REG_EXP_DURATION = "^P[\\d]Y[\\d]M[\\d]D$";

    @Override
    public boolean validate(Map<String, String> paramsMap) {
        if (paramsMap.containsKey(KEY)) {
            String duration = paramsMap.get(KEY);
            Pattern pattern = Pattern.compile(REG_EXP_DURATION);
            Matcher matcher = pattern.matcher(duration);
            if (!matcher.matches()) {
                return false;
            }
        }
        return checkNextLink(paramsMap);
    }
}
