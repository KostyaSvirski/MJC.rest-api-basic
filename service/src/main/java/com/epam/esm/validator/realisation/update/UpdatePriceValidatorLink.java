package com.epam.esm.validator.realisation.update;

import com.epam.esm.validator.realisation.IntermediateValidatorForUpdateLink;

import java.util.Map;

public class UpdatePriceValidatorLink extends IntermediateValidatorForUpdateLink {

    private static final String KEY = "price";

    @Override
    public boolean validate(Map<String, String> paramMap) {
        if (paramMap.containsKey(KEY)) {
            if (Long.parseLong(paramMap.get(KEY)) < 0) {
                return false;
            }
        }
        return checkNextLink(paramMap);
    }
}
