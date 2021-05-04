package com.trueaccord.internal.admin.module.service.impl;

import com.trueaccord.internal.admin.module.model.Debt;
import com.trueaccord.internal.admin.module.model.Payment;
import com.trueaccord.internal.admin.module.model.PaymentFrequency;
import com.trueaccord.internal.admin.module.model.PaymentPlan;
import com.trueaccord.internal.admin.module.service.DataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DataSourceImplTest {
    private DataSource dataSourceImpl;

    @Before
    public void setUp() {
        dataSourceImpl = new DataSourceImpl();
    }

    @Test
    public void testGetListOfDebt() throws IOException, InterruptedException {
        Debt[] debtArray = dataSourceImpl.getListOfDebt();
        assertNotNull(debtArray);
        assertTrue(debtArray[0].getId() instanceof Integer);
        assertTrue(debtArray[0].getAmount() instanceof Double);
    }

    @Test
    public void testGetListOfPayment() throws IOException, InterruptedException {
        Payment[] payments = dataSourceImpl.getListOfPayment();
        assertNotNull(payments);
        assertTrue(payments[0].getPaymentPlanId() instanceof Integer);
        assertTrue(payments[0].getAmount() instanceof Double);
        assertTrue(payments[0].getDate() instanceof String);
    }

    @Test
    public void testGetListOfPaymentPlan() throws IOException, InterruptedException {
        PaymentPlan[] paymentPlans = dataSourceImpl.getListOfPaymentPlan();
        assertNotNull(paymentPlans);
        assertTrue(paymentPlans[0].getStartDate() instanceof String);
        assertTrue(paymentPlans[0].getDebtId() instanceof Integer);
        assertTrue(paymentPlans[0].getInstallmentAmount() instanceof Double);
        assertTrue(paymentPlans[0].getInstallmentFrequency() instanceof PaymentFrequency);
        assertTrue(paymentPlans[0].getId() instanceof Integer);
        assertTrue(paymentPlans[0].getAmountToPay() instanceof Double);
    }

}