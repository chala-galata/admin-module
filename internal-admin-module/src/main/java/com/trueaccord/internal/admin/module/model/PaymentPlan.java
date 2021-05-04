package com.trueaccord.internal.admin.module.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentPlan {
    private Integer id;
    @JsonProperty("debt_id")
    private Integer debtId;
    @JsonProperty("amount_to_pay")
    private Double amountToPay;
    @JsonProperty("installment_frequency")
    private PaymentFrequency installmentFrequency;
    @JsonProperty("installment_amount")
    private Double installmentAmount;
    @JsonProperty("start_date")
    private String startDate;
}
