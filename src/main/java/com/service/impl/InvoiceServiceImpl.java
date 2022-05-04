package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Invoice;
import com.exception.ErrorCode;
import com.exception.GeneralExceptionFactory;
import com.mapper.InvoiceMapper;
import com.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zmh
 * @since 2022-05-02
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {
    public Invoice getInvoiceById(Long id) {
        Invoice invoice = this.baseMapper.selectById(id);
        if (invoice == null) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_QUERY_ERROR, "invoice id not found");
        }
        return invoice;
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        if (invoice == null || invoice.getAmount() == null || invoice.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "invoice amount error");
        }
        int res = this.baseMapper.insert(invoice);
        if (res != 1) {
            throw GeneralExceptionFactory.create(ErrorCode.DB_INSERT_ERROR, "invoice insert error");
        }
        return invoice;
    }
}
