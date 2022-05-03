package com.vo;

import com.entity.Invoice;
import com.entity.RentalOrder;
import lombok.Data;

@Data
public class OrderInvoiceVO {
    private RentalOrder order;
    private Invoice invoice;
}
