package com.clearpicture.platform.survey.enums;

/**
 * @author nuwan
 *
 */
public enum QuestionStatus {

    NEW("New", "N"),
    FETCHED("Fetched", "F"),
    ANSWERED("Answerd", "A");

    private String label;
    private String value;

    private QuestionStatus(String lable,String value) {
        this.label = lable;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public static QuestionStatus getEnum(String value) {
        for(QuestionStatus item:QuestionStatus.values()) {
            if(item.getValue().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }
}
