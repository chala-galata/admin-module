package com.trueaccord.internal.admin.module.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Debt implements Serializable {

    private Integer id;
    private Double amount;
    @JsonProperty("is_in_payment_plan")
    private Boolean isInPaymentPlan;
    @JsonProperty("remaining_amount")
    private Double remainingAmount;
    @JsonProperty("next_payment_due_date")
    private String nextPaymentDueDate;

}
