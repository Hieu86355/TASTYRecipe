package com.h86355.tastyrecipe.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.h86355.tastyrecipe.models.FeedCollection;
import com.h86355.tastyrecipe.models.RecipeCollection;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.h86355.tastyrecipe.utils.Constants.BASE_URL;

public class ServiceGenerator {
    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    private static OkHttpClient okHttpClient = okHttpClientBuilder
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request.Builder newRequest = request.newBuilder()
                            .addHeader("x-rapidapi-key", "71cc822456mshf3db7c3df9c17cfp17e305jsnaa8b3ad9f251")
                            .addHeader("x-rapidapi-host", "tasty.p.rapidapi.com");
                    return chain.proceed(newRequest.build());
                }
            }).build();

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RequestAPI requestAPI = retrofit.create(RequestAPI.class);

    public static RequestAPI getRequestAPI() {
        return requestAPI;
    }
}
