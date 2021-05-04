package com.trueaccord.internal.admin.module.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Payment {
    @JsonProperty("payment_plan_id")
    private Integer paymentPlanId;
    private Double amount;
    private String date;
    private Debt debtId;
}
