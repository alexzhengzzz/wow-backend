package com.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zmh
 * @since 2022-04-26
 */
@TableName("user_address")
@Data
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "user_id")
    private Long userId;
    private String country;
    private String state;
    private String street;
    private String city;
    private String zipcode;
}
