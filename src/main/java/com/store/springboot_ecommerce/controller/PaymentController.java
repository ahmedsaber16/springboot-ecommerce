package com.store.springboot_ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.store.springboot_ecommerce.service.PaymentService;
import com.store.springboot_ecommerce.dto.PayReq;
import com.store.springboot_ecommerce.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService ;

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> pay(@RequestBody PayReq rq ){
        PaymentResponse payment = paymentService.pay(rq.getOrderId());
        return ResponseEntity.ok(payment);
    }
}