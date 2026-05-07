package com.chirag.flex.controller;

import com.chirag.flex.entity.Ad;
import com.chirag.flex.repository.AdRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdRepository adRepo;

    // ✅ Search ads by city/place
    @GetMapping("/search")
    public List<Ad> searchByCity(@RequestParam String city){

        return adRepo.findAll().stream()
                .filter(ad -> ad.getPlaces().stream()
                        .anyMatch(p -> p.getName().equalsIgnoreCase(city)))
                .collect(Collectors.toList());
    }
}