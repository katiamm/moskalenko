package org.karazin.kateryna;

import lombok.Getter;

@Getter
public enum LogOutputFormat {
    XML("xml"),
    TEXT("text"),
    DATABASE("database");

    private final String value;

    LogOutputFormat(String value) {
        this.value = value;
    }
}