package com.business.impl;

import com.business.InvoiceBusiness;
import com.entity.Invoice;
import com.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceBusinessImpl implements InvoiceBusiness {
    @Autowired
    private InvoiceService invoiceService;

    @Override
    public Invoice createInvoice(Invoice invoice) {
        invoiceService.createInvoice(invoice);
        return invoice;
    }
}
