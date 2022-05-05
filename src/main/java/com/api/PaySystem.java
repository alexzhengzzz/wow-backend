package com.api;

import com.api.dto.CancelBillListDTO;
import com.api.dto.PaymentDTO;
import com.api.vo.BillStatusVO;
import com.api.vo.NewBillVO;
import com.utils.cache.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author alexzhengzzz
 * @date 5/4/22 15:00
 */
@Service
@Slf4j
public class PaySystem {
    private RestTemplate restTemplate = new RestTemplate();
    private String URL = "http://67.207.80.139:8081";

    /**
     * 1. pay one of the bill, need check the card_num, cvv and expiry_date first
     * 2. create a new bill record but not pay it
     * @param paymentDTO
     * @return
     */
    public Response<NewBillVO> startOnePayment(PaymentDTO paymentDTO) {
        String url = URL + "/payment";
        Response<NewBillVO> res = restTemplate.postForObject(url, paymentDTO, Response.class);
        log.warn(String.valueOf(res));
        return res;
    }

    /**
     * ckeck the bill status  1: paid 2: unpaid
     * @param billId
     * @return
     */
    public Response<BillStatusVO> checkBillStatus(Long billId) {
        String url = URL + "/bill/" + billId;
        log.warn(url);
        Response<BillStatusVO> res = restTemplate.getForObject(url, Response.class);
        return res;
    }

    // pay one of the bill by billId
    public Response<BillStatusVO> payByBillId(Long billId) {
        String url = URL + "/bill/u/" + billId;
        log.warn(url);
        Response<BillStatusVO> res = restTemplate.postForObject(url, null, Response.class);
        return res;
    }

    // rollback all the bill by billId
    public Response<BillStatusVO> rollBackAllBills(CancelBillListDTO cancelBillListDTO) {
        String url = URL + "/bills";
        log.warn(url);
        Response res = restTemplate.postForObject(url, cancelBillListDTO, Response.class);
        return res;
    }

}
