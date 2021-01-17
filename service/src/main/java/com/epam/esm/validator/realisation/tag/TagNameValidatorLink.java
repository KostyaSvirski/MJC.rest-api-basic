package com.epam.esm.validator.realisation.tag;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.validator.PreparedValidatorChain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagNameValidatorLink extends PreparedValidatorChain<TagDTO> {

    private static final String REG_EXP_NAME = "^[A-Za-z ]{1,}$";

    @Override
    public boolean validate(TagDTO bean) {
        Pattern pattern = Pattern.compile(REG_EXP_NAME);
        Matcher matcher = pattern.matcher(bean.getName());
        if(!matcher.matches()) {
            return false;
        }
        return checkNextLink(bean);
    }
}
