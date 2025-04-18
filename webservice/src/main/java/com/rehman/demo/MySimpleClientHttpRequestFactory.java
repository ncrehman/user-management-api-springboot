/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rehman.demo;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 *
 * @author Rehman
 */
public class MySimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

    private final HostnameVerifier verifier;

    public MySimpleClientHttpRequestFactory(HostnameVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (connection instanceof HttpsURLConnection httpsURLConnection) {
            httpsURLConnection.setHostnameVerifier(verifier);
        }
        super.prepareConnection(connection, httpMethod);
    }

}
