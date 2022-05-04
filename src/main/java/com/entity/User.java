package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String email;

    private String password;

    private Character roleType;

    private String lname;

    private String fname;

    private String employeeId;

    private Long companyId;

    private String phoneNum;

    private String bakEmail;

}
