package com.clearpicture.platform.enums;

/**
 * DateTimePattern
 * Created by nuwan on 9/2/18.
 */
public enum DateTimePattern {

    STRICT_DATE("uuuu-MM-dd"), STRICT_TIME("HH:mm"), STRICT_DATE_TIME("uuuu-MM-dd HH:mm");

    private String pattern;

    DateTimePattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
