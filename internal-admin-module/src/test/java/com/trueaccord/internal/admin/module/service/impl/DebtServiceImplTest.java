package com.trueaccord.internal.admin.module.service.impl;

import com.trueaccord.internal.admin.module.model.Debt;
import com.trueaccord.internal.admin.module.model.Payment;
import com.trueaccord.internal.admin.module.model.PaymentFrequency;
import com.trueaccord.internal.admin.module.model.PaymentPlan;
import com.trueaccord.internal.admin.module.service.DebtService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DebtServiceImplTest {

    Debt[] debts;
    Payment[] payments;
    PaymentPlan[] paymentPlans;

    private DebtService debtService;

    @Before
    public void initializeData(){
        debtService = new DebtServiceImpl();
        Debt debt1 = new Debt();
        debt1.setId(1); debt1.setRemainingAmount(500d);
        debt1.setIsInPaymentPlan(true); debt1.setNextPaymentDueDate("2020-09-28");
        debt1.setAmount(1000d);
        debts = new Debt[]{debt1};

        Payment payment1 = new Payment();
        payment1.setAmount(90d); payment1.setDate(null);
        payment1.setPaymentPlanId(1);
        payments = new Payment[]{payment1};

        PaymentPlan paymentPlan = new PaymentPlan();
        paymentPlan.setDebtId(1); paymentPlan.setAmountToPay(400d);
        paymentPlan.setId(1); paymentPlan.setInstallmentAmount(440d);
        paymentPlan.setInstallmentFrequency(PaymentFrequency.WEEKLY);
        paymentPlan.setStartDate("2020-09-28");
        paymentPlans = new PaymentPlan[]{paymentPlan};
    }

    @Test
    public void testHaveAPaymentPlanWithCorrectData() {
        boolean haveAPaymentPlan = debtService.haveAPaymentPlan(1, paymentPlans);
        assertTrue(haveAPaymentPlan);
    }

    @Test
    public void testHaveAPaymentPlanWithInCorrectData() {
        boolean haveAPaymentPlan = debtService.haveAPaymentPlan(0, paymentPlans);
        assertFalse(haveAPaymentPlan);
    }

    @Test
    public void testHaveAPaymentPlanWithNullData() {
        boolean haveAPaymentPlan = debtService.haveAPaymentPlan(null, null);
        assertFalse(haveAPaymentPlan);
    }

    @Test
    public void testIsPaymentCompletedWithCorrectData() {
        boolean isPaymentCompleted = debtService.isPaymentCompleted(1, payments, debts);
        assertFalse(isPaymentCompleted);
    }

    @Test
    public void testIsPaymentCompletedWithInCorrectData() {
        boolean isPaymentCompleted = debtService.isPaymentCompleted(-1, payments, debts);
        assertFalse(isPaymentCompleted);
    }

    @Test
    public void testIsPaymentCompletedWithNullData() {
        boolean isPaymentCompleted = debtService.isPaymentCompleted(null, null, null);
        assertFalse(isPaymentCompleted);
    }

    @Test
    public void testIsPaidDebtWithCorrectData() {
        double paidDebt = debtService.paidDebt(1, payments);
        assertEquals(90, paidDebt, 0);
    }

    @Test
    public void testIsPaidDebtWithInCorrectData() {
        double paidDebt = debtService.paidDebt(0, payments);
        assertEquals(0, paidDebt, 0);
    }

    @Test
    public void testIsPaidDebtWithNullData() {
        double paidDebt = debtService.paidDebt(null, null);
        assertEquals(0, paidDebt, 0);
    }

    @Test
    public void testUpdatePaymentDueDateWithCorrectData() {
        String setPaymentDueDate = debtService.setPaymentDueDate(paymentPlans, debts[0]);
        assertEquals("2020-10-05", setPaymentDueDate);
    }

    @Test
    public void testUpdatePaymentDueDateWithInCorrectData() {
        debts[0].setId(0);
        String setPaymentDueDate = debtService.setPaymentDueDate(paymentPlans, debts[0]);
        assertEquals(null, setPaymentDueDate);
    }

    @Test
    public void testUpdatePaymentDueDateWithNullData() {
        String setPaymentDueDate = debtService.setPaymentDueDate(null, null);
        assertEquals(null, setPaymentDueDate);
    }
}