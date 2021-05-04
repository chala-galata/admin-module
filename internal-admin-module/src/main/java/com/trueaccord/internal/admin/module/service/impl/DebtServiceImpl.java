package com.trueaccord.internal.admin.module.service.impl;


import com.trueaccord.internal.admin.module.model.Debt;
import com.trueaccord.internal.admin.module.model.Payment;
import com.trueaccord.internal.admin.module.model.PaymentFrequency;
import com.trueaccord.internal.admin.module.model.PaymentPlan;
import com.trueaccord.internal.admin.module.service.DebtService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

public class DebtServiceImpl implements DebtService {

    @Override
    public boolean haveAPaymentPlan(Integer debtId, PaymentPlan[] plans) {
        if (debtId == null || plans == null) return false;
        long hasAPlan = Arrays.stream(plans).map(paymentPlan -> paymentPlan.getDebtId())
                            .filter(debt -> debtId.equals(debt))
                            .count();
        return hasAPlan == 1;
    }

    @Override
    public boolean isPaymentCompleted(Integer debtId, Payment[] payments, Debt[] debts) {
        if (debtId == null || payments == null) return false;
        double paidDebt =  paidDebt(debtId, payments);

       double debtAmount = Arrays.stream(debts).filter(debt -> debtId.equals(debt.getId()))
                                               .mapToDouble(amount -> amount.getAmount())
                                               .findFirst().orElse(Double.MIN_VALUE);
       if (debtAmount == Double.MIN_VALUE) return false;

        return debtAmount - paidDebt <= 0;
    }

    @Override
    public double paidDebt(Integer debtId, Payment[] payments){
        if (debtId == null || payments == null) return 0;
        return Arrays.stream(payments).filter(payment -> payment.getPaymentPlanId().equals(debtId))
                .mapToDouble(payment -> payment.getAmount())
                .sum();
    }

    @Override
    public String setPaymentDueDate(PaymentPlan[] plans, Debt debt) {

        if (plans == null || debt == null) return null;

        boolean hasPaymentPlan = false;

        for (PaymentPlan paymentPlan : plans) {
            if (debt.getId().equals(paymentPlan.getDebtId())){
                hasPaymentPlan = true;
                break;
            }
        }

        if (hasPaymentPlan){

            Optional<PaymentPlan> paymentPlanObject = Arrays.stream(plans).filter(paymentPlan -> debt.getId().equals(paymentPlan.getDebtId()))
                                                                   .findFirst();
            paymentPlanObject.ifPresent(paymentPlan -> {
                PaymentFrequency frequency = paymentPlan.getInstallmentFrequency();
                long days = 0;
                if (PaymentFrequency.WEEKLY == frequency){
                    days = 7;
                }else if(PaymentFrequency.BI_WEEKLY == frequency){
                    days = 14;
                }
                String startDate = paymentPlan.getStartDate();
                LocalDate startDateOfPayment = LocalDate.parse(startDate);
                LocalDate newPaymentDate =  startDateOfPayment.plusDays(days);
                debt.setNextPaymentDueDate(newPaymentDate.toString());
            });

        }else {
            debt.setNextPaymentDueDate(null);
        }
        return debt.getNextPaymentDueDate();
    }
}
















