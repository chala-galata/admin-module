package com.trueaccord.internal.admin.module.service;

import com.trueaccord.internal.admin.module.model.Debt;
import com.trueaccord.internal.admin.module.model.Payment;
import com.trueaccord.internal.admin.module.model.PaymentPlan;

public interface DebtService {
     boolean haveAPaymentPlan(Integer debtId, PaymentPlan[] plans);
     boolean isPaymentCompleted(Integer debtId, Payment[] payments, Debt[] debts);
     double paidDebt(Integer debtId, Payment[] payments);
     String setPaymentDueDate(PaymentPlan[] plans, Debt debt);
}
