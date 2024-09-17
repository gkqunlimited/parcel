package com.gcash.parcel.service;

import com.gcash.parcel.model.ParcelRequest;
import com.gcash.parcel.model.ParcelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ParcelService {
    @Value("${pricing.heavy-parcel-rate}")
    private double heavyParcelRate;

    @Value("${pricing.small-parcel-rate}")
    private double smallParcelRate;

    @Value("${pricing.medium-parcel-rate}")
    private double mediumParcelRate;

    @Value("${pricing.large-parcel-rate}")
    private double largeParcelRate;

    @Value("${max.weight}")
    private double maxWeight;

    @Value("${max.heavy-weight}")
    private double maxHeavyWeight;

    @Value("${max.small-volume}")
    private double maxSmallVolume;

    @Value("${max.medium-volume}")
    private double maxMediumVolume;

    private final VoucherService voucherService;

    @Autowired
    public ParcelService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    public ParcelResponse calculateDeliveryCost(ParcelRequest request){
        double volume = request.getHeight() * request.getWidth() * request.getLength();
        double cost = 0;
        String message = "Success";

        // 1. Reject if weight > 50kg
        if (request.getWeight() > maxWeight) {
            message = "Parcel weight exceeds 50kg, delivery rejected.";
            return new ParcelResponse(0, 0, 0, message);
        }

        // 2. Heavy Parcel (Weight > 10kg)
        if (request.getWeight() > maxHeavyWeight) {
            cost = heavyParcelRate * request.getWeight();
        }
        // 3. Small Parcel (Volume < 1500 cm³)
        else if (volume < maxSmallVolume) {
            cost = smallParcelRate * volume;
        }
        // 4. Medium Parcel (Volume < 2500 cm³)
        else if (volume < maxMediumVolume) {
            cost = mediumParcelRate * volume;
        }
        // 5. Large Parcel (Any other case)
        else {
            cost = largeParcelRate * volume;
        }

        // Integrate voucher discount service
        double discount = 0;
        if (request.getVoucherCode() != null && !request.getVoucherCode().isEmpty()) {
            discount = voucherService.getVoucherDiscount(request.getVoucherCode(), request.getApikey());
        }

        double totalCost = cost - discount;

        return new ParcelResponse(cost, discount, totalCost, message);

    }


}
