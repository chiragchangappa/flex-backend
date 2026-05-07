package com.chirag.flex.service;

import com.chirag.flex.entity.*;
import com.chirag.flex.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class AdService {

    @Autowired private AdRepository adRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private PlaceRepository placeRepo;

    // ✅ STEP 1: Create Ad WITHOUT file
    public Ad createAd(String email,String name,int duration,int days,
                       List<String> placeNames,String fileUrl){

        User user = userRepo.findByEmail(email).orElseThrow();

        List<Place> places = new ArrayList<>();

        for(String p : placeNames){
            Place place = placeRepo.findByName(p)
                    .orElseGet(() -> {
                        Place newPlace = new Place();
                        newPlace.setName(p);
                        return placeRepo.save(newPlace);
                    });
            places.add(place);
        }

        double amount = calculateAmount(duration, days, places.size());

        Ad ad = new Ad();
        ad.setName(name);
        ad.setDuration(duration);
        ad.setDays(days);

        // ❗ IMPORTANT: no file initially
        ad.setFileUrl(null);

        ad.setAmount(amount);
        ad.setPaymentStatus("PENDING");
        ad.setUser(user);
        ad.setPlaces(places);

        return adRepo.save(ad);
    }

    private double calculateAmount(int duration,int days,int placeCount){
        return duration * days * placeCount * 2.5;
    }

    // ✅ STEP 2: Upload file AFTER payment success
    public String uploadFileAfterPayment(Long adId, MultipartFile file) throws Exception {

        Ad ad = adRepo.findById(adId).orElseThrow();

        if (!"SUCCESS".equals(ad.getPaymentStatus())) {
            throw new RuntimeException("Payment not completed");
        }

        String dir = "uploads/";
        new File(dir).mkdirs();

        // ✅ FIX ADDED HERE
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String path = dir + fileName;

        file.transferTo(new File(path));

        ad.setFileUrl(path);
        adRepo.save(ad);

        return "File uploaded successfully";
    }

    public List<Ad> getUserAds(String email){
        User user = userRepo.findByEmail(email).orElseThrow();

        return adRepo.findAll()
                .stream()
                .filter(ad -> ad.getUser().getId().equals(user.getId()))
                .toList();
    }

    public void deleteAd(Long id){
        adRepo.deleteById(id);
    }
}