package com.epam.esm.validator.realisation;

import com.epam.esm.validator.PreparedValidatorChain;

import java.util.Map;

public class IntermediateValidatorForUpdateLink extends PreparedValidatorChain<Map<String, String>> {

    @Override
    public boolean validate(Map<String, String> paramsMap) {
        if(paramsMap.isEmpty()) {
            return false;
        }
        return checkNextLink(paramsMap);
    }
}
