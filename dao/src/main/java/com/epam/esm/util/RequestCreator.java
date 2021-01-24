package com.epam.esm.util;

import java.util.Map;

public class RequestCreator {

    private static final String DELIMITER = ",";
    private static final String SQL_BASE_UPDATE_PREFIX = "update gift_certificate set";
    private static final String SQL_BASE_UPDATE_SUFFIX = " last_update_date = ? where id_certificate = ?";

    public static String createUpdate(Map<String, String> params) {
        StringBuilder sb = new StringBuilder(SQL_BASE_UPDATE_PREFIX);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            for (TypesOfParamForUpdate param : TypesOfParamForUpdate.values()) {
                if (param.name().equalsIgnoreCase(entry.getKey())) {
                    sb.append(param.getRequiredField());
                    sb.append(DELIMITER);
                }
            }
        }
        sb.append(SQL_BASE_UPDATE_SUFFIX);
        return sb.toString();
    }

    private enum TypesOfParamForUpdate {
        NAME(" name = ?"),
        DESCRIPTION(" description = ?"),
        PRICE(" price = ?"),
        DURATION(" duration = ?");

        private String requiredField;

        TypesOfParamForUpdate(String requiredField) {
            this.requiredField = requiredField;
        }

        public String getRequiredField() {
            return requiredField;
        }
    }
}
