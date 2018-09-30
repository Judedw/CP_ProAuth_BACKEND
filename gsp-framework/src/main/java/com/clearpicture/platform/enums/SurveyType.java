package com.clearpicture.platform.enums;

/**
 * SurveyType
 * Created by nuwan on 8/22/18.
 */
public enum SurveyType {

    PRODUCT("Product", "P"),
    EVOTE("Evote", "V");

    private String label;
    private String value;

    private SurveyType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static SurveyType getEnum(String value) {
        for(SurveyType item:SurveyType.values()) {
            if(item.getValue().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }
}
