package com.api.dto;

import lombok.Data;

import java.util.List;

/**
 * @author alexzhengzzz
 * @date 5/5/22 14:09
 */
@Data
public class CancelBillListDTO {
    private List<Long> billIds;
}
