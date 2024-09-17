package com.gcash.parcel;

import com.gcash.parcel.model.ParcelRequest;
import com.gcash.parcel.model.ParcelResponse;
import com.gcash.parcel.service.ParcelService;
import com.gcash.parcel.service.VoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ParcelServiceTest {

    @MockBean
    private VoucherService voucherService;

    @Autowired
    private ParcelService parcelService;

    @Test
    void testCalculateCostWithHeavyParcel() {
        ParcelRequest request = new ParcelRequest();
        request.setWeight(12);
        request.setHeight(20);
        request.setWidth(10);
        request.setLength(10);

        // Mock voucher service
        when(voucherService.getVoucherDiscount(anyString(), anyString())).thenReturn(0.0);

        ParcelResponse response = parcelService.calculateDeliveryCost(request);
        assertEquals(240, response.getCost());
    }

    @Test
    void testCalculateCostWithVoucher() {
        ParcelRequest request = new ParcelRequest();
        request.setWeight(8);
        request.setHeight(10);
        request.setWidth(10);
        request.setLength(10);
        request.setVoucherCode("MYNT50");

        // Mock voucher service
        when(voucherService.getVoucherDiscount("MYNT", "apikey")).thenReturn(50.0);

        ParcelResponse response = parcelService.calculateDeliveryCost(request);
        assertEquals(30, response.getTotalCost());
    }
}
