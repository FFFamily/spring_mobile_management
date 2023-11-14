package org.example.enums;

import java.util.Arrays;
import java.util.Objects;

public interface DefaultEnum<T> {

    public T getCode();

    static <K extends DefaultEnum> K  of(Class<K> tClass, Object value){
        return Arrays.stream(tClass.getEnumConstants()).filter((constant) -> Objects.equals(constant.getCode(),value)).findFirst().orElse(null);
    }
}
