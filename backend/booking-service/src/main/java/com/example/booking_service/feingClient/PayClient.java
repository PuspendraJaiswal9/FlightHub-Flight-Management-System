package com.example.booking_service.feingClient;

import com.example.booking_service.dto.PaymentRequest;
import com.example.booking_service.dto.StripeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name="payment-service",url="http://localhost:8084")
@FeignClient(name="payment-service")
public interface PayClient {
    @PostMapping("/payment/pay")
    public StripeResponse makePayment(@RequestBody PaymentRequest paymentRequest);
}
