package com.back.boundedContext.cash.domain;

import com.back.global.jpa.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "CASH_CASH_LOG")
@NoArgsConstructor
@Getter
public class CashLog extends BaseIdAndTime {
    @ManyToOne(fetch = LAZY)
    private CashMember holder;
    @ManyToOne(fetch = LAZY)
    private Wallet wallet;
    private long amount;
    private long balance;

    @Enumerated(EnumType.STRING)
    private CashEventType cashEventType;
    private String relTypeCode;
    private int relId;

    public CashLog(CashMember holder, Wallet wallet, long amount, long balance, CashEventType cashEventType, String relTypeCode, int relId) {
        this.holder = holder;
        this.wallet = wallet;
        this.amount = amount;
        this.balance = balance;
        this.cashEventType = cashEventType;
        this.relTypeCode = relTypeCode;
        this.relId = relId;
    }
}
