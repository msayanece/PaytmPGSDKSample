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
import android.widget.Toast;

import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;

import static com.sayan.sample.paytmpgsdksample.Constants.SMS_REQUEST_CODE;

//Paytm doc: https://developer.paytm.com/docs/v1/android-sdk
public class PaytmPGActivity extends AppCompatActivity {

    //TODO change it to PRODUCTION for live
    Constants.ENVIRONMENT environment = Constants.ENVIRONMENT.SANDBOX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm_pg);
        getSMSPermissionStatusAndPay();
    }

    private void getSMSPermissionStatusAndPay() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, SMS_REQUEST_CODE);
        } else {
            // permission already granted
            afterSMSPermissionGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case SMS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    afterSMSPermissionGranted();
                } else {
                    new AlertDialog.Builder(this).setMessage("SMS Permission not granted")
                            .setTitle("SMS Permission")
                            .setPositiveButton("Ask again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getSMSPermissionStatusAndPay();
                                }
                            })
                            .show();
                }
                break;
            }
        }
    }


    private void afterSMSPermissionGranted() {
        PaytmPGService paytmPGService = initializePaytmPGService();
        PaytmOrder newOrder = createNewOrder();
      /*  //TODO Optional
        generatePaytmClientCentificate();   //for handshaking purpose use this with server's SSL certificate*/
        paytmPGService.initialize(newOrder, null);  //pass the PaytmClientCentificate here for using handshaking
        startPaymentTransaction(paytmPGService);
    }

    private void startPaymentTransaction(PaytmPGService paytmPGService) {
        paytmPGService.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                Toast.makeText(PaytmPGActivity.this, inErrorMessage, Toast.LENGTH_LONG).show();
            }
            public void onTransactionResponse(Bundle inResponse) {
                Toast.makeText(PaytmPGActivity.this, "onTransactionResponse: " + inResponse.toString(), Toast.LENGTH_LONG).show();
            }
            public void networkNotAvailable() {
                Toast.makeText(PaytmPGActivity.this, "networkNotAvailable", Toast.LENGTH_LONG).show();
            }
            public void clientAuthenticationFailed(String inErrorMessage) {
                Toast.makeText(PaytmPGActivity.this, inErrorMessage, Toast.LENGTH_LONG).show();
            }
            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                Toast.makeText(PaytmPGActivity.this, inErrorMessage, Toast.LENGTH_LONG).show();
            }
            public void onBackPressedCancelTransaction() {
                Toast.makeText(PaytmPGActivity.this, "canceled by back pressed", Toast.LENGTH_LONG).show();
            }
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Toast.makeText(PaytmPGActivity.this, inErrorMessage, Toast.LENGTH_LONG).show();
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
    private PaytmOrder createNewOrder() {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        // Key in your staging and production MID available in your dashboard
        paramMap.put("MID", "rxazcv89315285244163");
        paramMap.put("ORDER_ID", "order1");
        paramMap.put("CUST_ID", "cust123");
        paramMap.put("MOBILE_NO", "7777777777");
        paramMap.put("EMAIL", "username@emailprovider.com");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", "100.12");
        // This is the staging value. Production value is available in your dashboard
        paramMap.put("WEBSITE", "WEBSTAGING");
        // This is the staging value. Production value is available in your dashboard
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=order1");
        paramMap.put("CHECKSUMHASH", "w2QDRMgp1234567JEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");
        return new PaytmOrder(paramMap);
    }

}
