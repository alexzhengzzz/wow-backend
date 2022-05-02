package com.listener;

import com.entity.Coupons;
import com.google.gson.Gson;
import com.service.ICouponsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CouponDBListener {
    @Autowired
    private ICouponsService couponService;

    private static final String TOPIC_NAME = "coupon.issued";
    @KafkaListener(topics = {TOPIC_NAME},groupId = "zmh1")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
//        log.debug("消费消息："+record.topic()+"----"+record.partition()+"----"+record.value());
        Boolean isSuccess = couponService.save(new Gson().fromJson((String) record.value(), Coupons.class));
        if (isSuccess){
            ack.acknowledge();
        } else {
            ack.nack(1000);
            log.warn("消费失败："+record.topic()+"----"+record.partition()+"----"+record.value());
        }

    }
}