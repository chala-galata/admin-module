package com.trueaccord.internal.admin.module.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trueaccord.internal.admin.module.model.Debt;
import com.trueaccord.internal.admin.module.model.Payment;
import com.trueaccord.internal.admin.module.model.PaymentPlan;
import com.trueaccord.internal.admin.module.service.DebtService;
import com.trueaccord.internal.admin.module.service.DataSource;
import com.trueaccord.internal.admin.module.service.impl.DebtServiceImpl;
import com.trueaccord.internal.admin.module.service.impl.DataSourceImpl;

import java.io.IOException;
import java.util.Arrays;

public class InternalAdminDriver {

    private DebtService debtService;
    private DataSource dataSource;

    public InternalAdminDriver() {
        this.debtService = new DebtServiceImpl();
        this.dataSource = new DataSourceImpl();
    }

    public Debt[] getDeptInformation() throws IOException, InterruptedException {
        Debt[] listOfDept = dataSource.getListOfDebt();
        PaymentPlan[] paymentPlans = dataSource.getListOfPaymentPlan();
        Payment[] payments = dataSource.getListOfPayment();

        for (Debt debt : listOfDept) {
            boolean haveAPaymentPlan = debtService.haveAPaymentPlan(debt.getId(), paymentPlans);
            boolean isPaymentCompleted = debtService.isPaymentCompleted(debt.getId(), payments, listOfDept);

            debtService.setPaymentDueDate(paymentPlans, debt);

            if (isPaymentCompleted) {
                debt.setIsInPaymentPlan(false);
                debt.setRemainingAmount(0.0);
            }else if (!haveAPaymentPlan){
                debt.setIsInPaymentPlan(false);
                debt.setRemainingAmount(debt.getAmount());
            }else {
                double paidAmount = debtService.paidDebt(debt.getId(), payments);
                debt.setIsInPaymentPlan(true);
                setRemainingPaymentAmount(debt, paidAmount, paymentPlans);
            }

        }

        return listOfDept;
    }


    private void setRemainingPaymentAmount(Debt debt, double amount, PaymentPlan[] paymentPlans) {
        double amountToPay = Arrays.stream(paymentPlans).filter(paymentPlan -> debt.getId().equals(paymentPlan.getDebtId()))
                                   .mapToDouble(paymentPlan -> paymentPlan.getAmountToPay())
                                   .findFirst().getAsDouble();

        debt.setRemainingAmount(amountToPay - amount);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        InternalAdminDriver driver = new InternalAdminDriver();
        Debt[] debts = driver.getDeptInformation();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(debts));
    }
}
