package com.service;

import com.entity.Invoice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
public interface InvoiceService extends IService<Invoice> {
    Invoice getInvoiceById(Long id);
    Invoice createInvoice(Invoice invoice);
}
