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
public class Corporation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "corp_id", type = IdType.AUTO)
    private Long corpId;

    private String companyName;

    private String registerCode;

}
