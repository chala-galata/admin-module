package com.trueaccord.internal.admin.module.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueaccord.internal.admin.module.model.Debt;
import com.trueaccord.internal.admin.module.model.Payment;
import com.trueaccord.internal.admin.module.model.PaymentPlan;
import com.trueaccord.internal.admin.module.service.DataSource;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class DataSourceImpl implements DataSource {


    private String DEBT_URL = "https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/debts";
    private String PAYMENT_URL = "https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/payments";
    private String PAYMENT_PLAN_URL = "https://my-json-server.typicode.com/druska/trueaccord-mock-payments-api/payment_plans";

    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public Debt[] getListOfDebt() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .header("accept", "application/json")
                .uri(URI.create(DEBT_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Debt[] debtArray = mapper.readValue(response.body(), Debt[].class);
        return debtArray;
    }

    @Override
    public Payment[] getListOfPayment() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .header("accept", "application/json")
                .uri(URI.create(PAYMENT_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Payment[] payments = mapper.readValue(response.body(), Payment[].class);
        return payments;
    }

    @Override
    public PaymentPlan[] getListOfPaymentPlan() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .header("accept", "application/json")
                .uri(URI.create(PAYMENT_PLAN_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        PaymentPlan[] paymentPlans = mapper.readValue(response.body(), PaymentPlan[].class);
        return paymentPlans;
    }
}
