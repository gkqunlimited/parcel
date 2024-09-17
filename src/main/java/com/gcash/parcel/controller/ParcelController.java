package com.gcash.parcel.controller;

import com.gcash.parcel.model.ParcelRequest;
import com.gcash.parcel.model.ParcelResponse;
import com.gcash.parcel.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {

    private final ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ParcelResponse> calculateCost(@RequestBody ParcelRequest request) {
        ParcelResponse response = parcelService.calculateDeliveryCost(request);
        return ResponseEntity.ok(response);
    }
}
