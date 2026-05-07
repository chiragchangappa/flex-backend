package com.chirag.flex.entity;



import jakarta.persistence.*;
import java.util.List;

@Entity
public class Ad {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int duration;
    private int days;
    private String fileUrl;
    private double amount;
    private String paymentStatus;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Place> places;

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public int getDuration(){return duration;}
    public void setDuration(int duration){this.duration=duration;}

    public int getDays(){return days;}
    public void setDays(int days){this.days=days;}

    public String getFileUrl(){return fileUrl;}
    public void setFileUrl(String fileUrl){this.fileUrl=fileUrl;}

    public double getAmount(){return amount;}
    public void setAmount(double amount){this.amount=amount;}

    public String getPaymentStatus(){return paymentStatus;}
    public void setPaymentStatus(String paymentStatus){this.paymentStatus=paymentStatus;}

    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}

    public List<Place> getPlaces(){return places;}
    public void setPlaces(List<Place> places){this.places=places;}
}