package com.infosetgroup.flexevent.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;

public class FlexEventUtils {
    public static String sendSms(String token, String from, String to, String text) {
        System.out.println("Token : " + token);
        System.out.println("From : " + from);
        System.out.println("To : " + to);
        System.out.println("Text : " + text);

        try {
            text = URLEncoder.encode(text, "UTF-8");
            String url = "https://api.keccel.com/sms/v1/message.asp?token=%s&from=%s&to=%s&message=%s";
            String urlRequest = String.format(url, token, from, to, text);
            System.out.println("URL : " + urlRequest);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(urlRequest);
            HttpResponse response = null;
            response = httpClient.execute(request);
            int responseCode = response.getStatusLine().getStatusCode();
            System.out.println("Response code : " + responseCode);
            if (responseCode == 200 || responseCode == 201) {
                return EntityUtils.toString(response.getEntity());
            }else {
                System.out.println("sendSmsApi2 not 200 or 201");
                return null;
            }
        }catch (Exception e) {
            System.out.println("Exception sendSmsApi2");
            e.printStackTrace();
            return null;
        }
    }
}
