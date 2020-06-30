package com.example.domain.enumerations;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author jy
 */
public enum Gender implements IEnum<Integer> {

    MALE, FEMALE;

    @Override
    public Integer getValue() {
        return ordinal();
    }
}
