package com.sayan.sample.paytmpgsdksample.PatmPGsdk;

import com.sayan.sample.paytmpgsdksample.models.GeneratePaytmChecksomeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


/**
 * Created by Admin on 23-10-2017.
 */

public interface Service {

    @POST("generatePaytmChecksome.php")
    @FormUrlEncoded
    Call<GeneratePaytmChecksomeResponse> generatePaytmChecksome(@Field("ORDER_ID") String orderID, @Field("TXN_AMOUNT") String amount, @Field("CUST_ID") String userID);

    /*@GET("payment.php")
    Call<PaymentSuccessDetailsResponse> getPaymentSuccessDetails(@Query("paymentId") String paymentId);*/
}

