package com.chirag.flex.service;


import com.chirag.flex.entity.Ad;
import com.chirag.flex.entity.Payment;
import com.chirag.flex.repository.AdRepository;
import com.chirag.flex.repository.PaymentRepository;
import com.chirag.flex.util.RazorpayUtil;
import com.razorpay.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired private AdRepository adRepo;
    @Autowired private PaymentRepository paymentRepo;

    private final String KEY = "rzp_live_SoV4tm42C3kWRj";
    private final String SECRET = "L5MGIcVlEhCjCcBhi7OV87kF";

    public String createOrder(Long adId) throws Exception {

        Ad ad = adRepo.findById(adId).orElseThrow();

        RazorpayClient client = new RazorpayClient(KEY, SECRET);

        JSONObject obj = new JSONObject();
        obj.put("amount", ad.getAmount() * 100);
        obj.put("currency","INR");

        Order order = client.orders.create(obj);

        // ✅ SAVE orderId
        Payment p = new Payment();
        p.setAd(ad);
        p.setAmount(ad.getAmount());
        p.setStatus("CREATED");
        p.setOrderId(order.get("id"));

        paymentRepo.save(p);

        return order.toString();
    }

    public String verifyPayment(String orderId,String paymentId,String signature,Long adId){

        boolean valid = RazorpayUtil.verify(orderId,paymentId,signature,SECRET);

        if(!valid)
            throw new RuntimeException("Payment verification failed");

        Ad ad = adRepo.findById(adId).orElseThrow();
        ad.setPaymentStatus("SUCCESS");
        adRepo.save(ad);

        Payment p = new Payment();
        p.setAd(ad);
        p.setAmount(ad.getAmount());
        p.setStatus("SUCCESS");
        p.setRazorpayPaymentId(paymentId);

        paymentRepo.save(p);

        return "Payment Success";
    }
}