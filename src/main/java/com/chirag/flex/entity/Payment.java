package com.chirag.flex.entity;

import jakarta.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String status;

    // ✅ Razorpay Payment ID
    private String razorpayPaymentId;

    // ✅ ADD THIS (Fix for your error)
    private String orderId;

    @OneToOne
    private Ad ad;

    // 🔹 Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRazorpayPaymentId() { return razorpayPaymentId; }
    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    // ✅ NEW Getter/Setter
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public Ad getAd() { return ad; }
    public void setAd(Ad ad) { this.ad = ad; }
}