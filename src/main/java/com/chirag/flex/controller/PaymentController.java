package com.chirag.flex.controller;

import com.chirag.flex.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService service;

    // ✅ Create Razorpay Order
    @PostMapping("/create")
    public String createOrder(@RequestParam Long adId) throws Exception {
        return service.createOrder(adId);
    }

    // ✅ Verify Payment
    @PostMapping("/verify")
    public String verify(
            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String signature,
            @RequestParam Long adId){

        return service.verifyPayment(orderId, paymentId, signature, adId);
    }
}