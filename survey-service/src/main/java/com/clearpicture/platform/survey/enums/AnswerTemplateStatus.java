package com.clearpicture.platform.survey.enums;

/**
 * AnswerTemplateStatus
 * Created by nuwan on 8/18/18.
 */
public enum  AnswerTemplateStatus {

    ACTIVE("Active", "A"),
    INACTIVE("Inactive", "I"),
    DELETED("Deleted", "D");

    private String label;
    private String value;

    private AnswerTemplateStatus(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static AnswerTemplateStatus getEnum(String value) {
        for (AnswerTemplateStatus item : AnswerTemplateStatus.values()) {
            if (item.getValue().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }
}
