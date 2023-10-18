package com.stella.rememberall.diary.common;

public interface Validator {

    static boolean isNull(Object object) {
        return object == null;
    }

    static boolean isBlank(String string) {
        if (isNull(string)) return true;
        return string.isEmpty();
    }

}
