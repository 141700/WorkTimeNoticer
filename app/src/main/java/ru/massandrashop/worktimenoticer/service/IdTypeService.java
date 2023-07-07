package ru.massandrashop.worktimenoticer.service;

import ru.massandrashop.worktimenoticer.model.IdType;

import java.util.Arrays;

public class IdTypeService {

    public static IdType getTypeByButton(int checkedId) {
        return Arrays.stream(IdType.values())
                .filter(type -> type.button == checkedId)
                .findFirst().orElse(IdType.UNDEFINED);
    }

    public static int getButtonByPermission(String permission) {
        IdType type = Arrays.stream(IdType.values())
                .filter(t -> permission.equals(t.permission))
                .findFirst().orElse(IdType.UNDEFINED);
        return type.button;
    }

}
