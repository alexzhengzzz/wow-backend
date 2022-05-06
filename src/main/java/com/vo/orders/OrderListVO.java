package com.vo.orders;

import lombok.Data;

/**
 * @author alexzhengzzz
 * @date 5/5/22 18:14
 */
@Data
public class OrderListVO {
    private OrderVO orderVO;
    private OrderVehicleVO orderVehicleVO;
    private Long invoiceId;
}
