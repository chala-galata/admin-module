package com.trueaccord.internal.admin.module.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trueaccord.internal.admin.module.model.Debt;
import com.trueaccord.internal.admin.module.model.Payment;
import com.trueaccord.internal.admin.module.model.PaymentPlan;

import java.io.IOException;
import java.net.MalformedURLException;

public interface DataSource {
    Debt[] getListOfDebt() throws IOException, InterruptedException;
    Payment[] getListOfPayment() throws IOException, InterruptedException;
    PaymentPlan[] getListOfPaymentPlan() throws IOException, InterruptedException;
}
