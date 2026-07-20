package com.example.payment_service.service;
import com.example.payment_service.dto.PaymentRequest;
import com.example.payment_service.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    public StripeResponse payAmount(PaymentRequest paymentRequest){
        Stripe.apiKey= secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName("Flight Ticket").build();

        SessionCreateParams.LineItem.PriceData priceData =  SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(paymentRequest.getCurrency())
                .setUnitAmount(paymentRequest.getAmount()*100)
                .setProductData(productData).build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(paymentRequest.getQuantity())
                .setPriceData(priceData).build();

//        SessionCreateParams params = SessionCreateParams.builder()
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setSuccessUrl("http://localhost:8080/success")
//                .setCancelUrl("http://localhost:8080/cancel")
//                .addLineItem(lineItem)
//                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                // ✅ Redirect user back to React app after payment success/cancel
                .setSuccessUrl("http://localhost:3000/payment-success")
                .setCancelUrl("http://localhost:3000/payment-cancel")
                .addLineItem(lineItem)
                .build();

        Session session = null;



        try {
            session = Session.create(params);
            if (session != null) {
                String sessionId = session.getId();
            } else {
                throw new RuntimeException("Stripe session creation failed");
            }
        } catch (StripeException e) {
            System.out.println("Stripe Error: " + e.getMessage());
            return StripeResponse.builder()
                    .status("Failed")
                    .message("Payment could not be processed: " + e.getMessage())
                    .build();

        }


        return StripeResponse.builder()
                .status("Success")
                .message("Payment Done")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }

}
