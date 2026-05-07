package com.chirag.flex.controller;

import com.chirag.flex.dto.AdRequest;
import com.chirag.flex.entity.Ad;
import com.chirag.flex.service.AdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // ✅ IMPORTANT

import java.util.List;

@RestController
@RequestMapping("/ads")
@CrossOrigin
public class AdController {

    @Autowired
    private AdService service;

    // ✅ STEP 1: Create Ad (NO FILE)
    @PostMapping("/create")
    public Ad createAd(
            @RequestParam String email,
            @RequestBody AdRequest req){

        return service.createAd(
                email,
                req.getName(),
                req.getDuration(),
                req.getDays(),
                req.getPlaces(),
                null
        );
    }

    // ✅ STEP 2: Upload AFTER payment
    @PostMapping("/upload-after-payment")
    public String uploadAfterPayment(
            @RequestParam Long adId,
            @RequestParam MultipartFile file) throws Exception {

        return service.uploadFileAfterPayment(adId, file);
    }

    // ✅ STEP 3: Get user ads
    @GetMapping("/my")
    public List<Ad> getMyAds(@RequestParam String email){
        return service.getUserAds(email);
    }

    // ✅ STEP 4: Delete ad
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.deleteAd(id);
        return "Deleted";
    }
}