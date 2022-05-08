package com.listener;

import com.entity.Coupons;
import com.entity.User;
import com.google.gson.Gson;
import com.service.EmailService;
import com.service.ICouponsService;
import com.service.UserService;
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
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    private static final String TOPIC_NAME = "coupon.issued";
    private static final String COUPON_MESSAGE = "You have received a new coupon, login to your account to see it!";
    private static final String COUPON_SUBJECT = "New Coupon from World of Wheels";
    @KafkaListener(topics = {TOPIC_NAME}, groupId = "zmh1")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//        log.debug("消费消息："+record.topic()+"----"+record.partition()+"----"+record.value());
        Coupons coupons = new Gson().fromJson((String) record.value(), Coupons.class);
        Boolean isSuccess = couponService.save(coupons);
        if (isSuccess) {
            sendCouponMail(coupons);
            ack.acknowledge();
        } else {
            ack.nack(1000);
            log.warn("消费失败：" + record.topic() + "----" + record.partition() + "----" + record.value());
        }
    }

    private void sendCouponMail(Coupons coupons) {
        User user = userService.getById(coupons.getUserId());
        emailService.sendSimpleMessage(user.getEmail(), COUPON_SUBJECT, COUPON_MESSAGE);
    }


}