package com.clearpicture.platform.survey.enums;

/**
 * AnswerTemplateType
 * Created by nuwan on 8/18/18.
 */
public enum  AnswerTemplateType {

    FREE_TEXT("FREE_TEXT", "F"),
    SINGLE_ANSWER_OPTION("SINGLE_ANSWER_OPTION", "S"),
    MULTIPLE_OPTIONS("MULTIPLE_OPTIONS", "M");

    private String label;
    private String value;

    private AnswerTemplateType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static AnswerTemplateType getEnum(String value) {
        for (AnswerTemplateType item : AnswerTemplateType.values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}
