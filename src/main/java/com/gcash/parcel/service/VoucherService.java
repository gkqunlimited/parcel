package com.gcash.parcel.service;

import com.gcash.parcel.model.VoucherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class VoucherService {

    private final WebClient webClient;
    private final  Logger logger = Logger.getLogger(getClass().getName());

    @Value("${voucher.api.url}")
    private String voucherApiUrl;

    public VoucherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(voucherApiUrl).build();
    }

    public double getVoucherDiscount(String voucherCode, String apikey) {
        try {
            // Make the API call to the voucher service
            VoucherResponse response = webClient
                    .get()
                    .uri("/" + voucherCode + "?key=" + apikey)
                    .retrieve()
                    .bodyToMono(VoucherResponse.class)
                    .block(); // Blocking for simplicity, can be non-blocking if needed

            assert response != null;
            return response.getDiscount(); // Assuming the response contains a discount field
        } catch (WebClientResponseException e) {
            // Log error and return 0 if the voucher is invalid or there's an error
            logger.log(Level.SEVERE,"Error calling voucher API: " + e.getMessage());
            return 0;
        }
    }

}
