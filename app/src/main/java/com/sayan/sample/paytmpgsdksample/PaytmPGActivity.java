package com.sayan.sample.paytmpgsdksample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.sayan.sample.paytmpgsdksample.PatmPGsdk.InterceptorHTTPClientCreator;
import com.sayan.sample.paytmpgsdksample.PatmPGsdk.PatmPGsdk;
import com.sayan.sample.paytmpgsdksample.PatmPGsdk.Service;
import com.sayan.sample.paytmpgsdksample.models.GeneratePaytmChecksomeResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sayan.sample.paytmpgsdksample.Constants.SMS_REQUEST_CODE;

//Paytm doc: https://developer.paytm.com/docs/v1/android-sdk
public class PaytmPGActivity extends AppCompatActivity {

    //TODO change it to PRODUCTION for live
    Constants.ENVIRONMENT environment = Constants.ENVIRONMENT.SANDBOX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm_pg);
    }

    public void onClickPay(View view) {
        InterceptorHTTPClientCreator.createInterceptorHTTPClient(getApplicationContext());
        Service service = new PatmPGsdk.Builder().build(this).getService();
        service.generatePaytmChecksome("order1", "100.00", "cust_1001")
                .enqueue(new Callback<GeneratePaytmChecksomeResponse>() {
                    @Override
                    public void onResponse(Call<GeneratePaytmChecksomeResponse> call, Response<GeneratePaytmChecksomeResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getResult().equalsIgnoreCase("success")) {
                                initiatePayment(response.body());
                            } else {
                                Toast.makeText(PaytmPGActivity.this, "Something is not right", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PaytmPGActivity.this, "Something is not right", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GeneratePaytmChecksomeResponse> call, Throwable t) {
                        Toast.makeText(PaytmPGActivity.this, "Something is not right", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initiatePayment(GeneratePaytmChecksomeResponse responseBody) {
        PaytmPGService paytmPGService = initializePaytmPGService();
        PaytmOrder newOrder = createNewOrder(responseBody);
      /*  //TODO Optional
        generatePaytmClientCentificate();   //for handshaking purpose use this with server's SSL certificate*/
        paytmPGService.initialize(newOrder, null);  //pass the PaytmClientCentificate here for using handshaking
        startPaymentTransaction(paytmPGService);
    }

    private void startPaymentTransaction(PaytmPGService paytmPGService) {
        paytmPGService.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                ViewUtil.showAlertDialog(PaytmPGActivity.this, inErrorMessage);
            }

            public void onTransactionResponse(Bundle inResponse) {
                ViewUtil.showAlertDialog(PaytmPGActivity.this, "onTransactionResponse:=> " + inResponse.toString());
            }

            public void networkNotAvailable() {
                ViewUtil.showAlertDialog(PaytmPGActivity.this, "networkNotAvailable");
            }

            public void clientAuthenticationFailed(String inErrorMessage) {
                ViewUtil.showAlertDialog(PaytmPGActivity.this, inErrorMessage);
            }

            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                ViewUtil.showAlertDialog(PaytmPGActivity.this, inErrorMessage + "\nFailingURL:=> " + inFailingUrl);
            }

            public void onBackPressedCancelTransaction() {
                ViewUtil.showAlertDialog(PaytmPGActivity.this, "canceled by back pressed");
            }

            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                ViewUtil.showAlertDialog(PaytmPGActivity.this, "onTransactionCancel:=> " + inErrorMessage);
            }
        });
    }

    private PaytmClientCertificate generatePaytmClientCentificate() {
        // inPassword is the password for client side certificate
        //inFileName is the file name of client side certificate
        return new PaytmClientCertificate("String inPassword", "String inFileName");
    }

    private PaytmPGService initializePaytmPGService() {
        PaytmPGService service = null;
        if (environment == Constants.ENVIRONMENT.SANDBOX) {
            service = PaytmPGService.getStagingService();
        } else if (environment == Constants.ENVIRONMENT.PRODUCTION) {
            service = PaytmPGService.getProductionService();
        }
        return service;
    }

    //All the values are static and hard coded here. pass the values through method params using the HashMap
    private PaytmOrder createNewOrder(GeneratePaytmChecksomeResponse responseBody) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        // Key in your staging and production MID available in your dashboard
        paramMap.put("MID", responseBody.getData().getMID());
        paramMap.put("ORDER_ID", responseBody.getData().getORDERID());
        paramMap.put("CUST_ID", responseBody.getData().getCUSTID());
        paramMap.put("MOBILE_NO", responseBody.getData().getMOBILENO());
        paramMap.put("EMAIL", responseBody.getData().getEMAIL());
        paramMap.put("CHANNEL_ID", responseBody.getData().getCHANNELID());
        paramMap.put("TXN_AMOUNT", responseBody.getData().getTXNAMOUNT());
        // This is the staging value. Production value is available in your dashboard
        paramMap.put("WEBSITE", responseBody.getData().getWEBSITE());
        // This is the staging value. Production value is available in your dashboard
        paramMap.put("INDUSTRY_TYPE_ID", responseBody.getData().getINDUSTRYTYPEID());
        paramMap.put("CALLBACK_URL", responseBody.getData().getCALLBACKURL());
        paramMap.put("CHECKSUMHASH", responseBody.getChecksomeHASH());
        return new PaytmOrder(paramMap);
    }

}
