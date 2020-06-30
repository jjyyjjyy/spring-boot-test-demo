package com.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.domain.enumerations.Gender;
import lombok.Data;

import java.time.Instant;

/**
 * @author jy
 */
@Data
@TableName("public.user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private Gender gender;

    private String address;

    private Instant createdAt;

    private Instant updatedAt;

}
