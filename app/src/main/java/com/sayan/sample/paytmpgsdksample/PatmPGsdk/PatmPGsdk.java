package com.sayan.sample.paytmpgsdksample.PatmPGsdk;

import android.content.Context;


import com.sayan.sample.paytmpgsdksample.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PatmPGsdk {
    private final Retrofit retrofit;
    private Service service;

    private PatmPGsdk(Retrofit retrofit) {
        this.retrofit = retrofit;
        createService();
    }

    /**
     * Builder for {@link PatmPGsdk}
     */
    public static class Builder {
        public Builder() {
        }

        /**
         * Create the {@link PatmPGsdk} to be used.
         *
         * @return {@link PatmPGsdk}
         */
        public PatmPGsdk build(Context context) {
            Retrofit retrofit = null;
            String baseUrl = null;
            baseUrl = context.getResources().getString(R.string.base_url);
            if (InterceptorHTTPClientCreator.getOkHttpClient() != null) {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(InterceptorHTTPClientCreator.getOkHttpClient())
                        .baseUrl(baseUrl)
                        .build();

                return new PatmPGsdk(retrofit);
            } else {
                retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
//                        .client(InterceptorHTTPClientCreator.getOkHttpClient())
                        .baseUrl(baseUrl)
                        .build();
            }
            return new PatmPGsdk(retrofit);
        }
    }

    private void createService() {
        service = retrofit.create(Service.class);
    }

    public Service getService(){
        return service;
    }
}
