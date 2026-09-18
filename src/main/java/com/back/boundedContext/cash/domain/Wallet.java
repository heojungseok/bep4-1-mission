package com.back.boundedContext.cash.domain;

import com.back.global.jpa.entity.BaseEntity;
import com.back.global.jpa.entity.BaseManualIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "CASH_WALLET")
@NoArgsConstructor
public class Wallet extends BaseManualIdAndTime {

    @ManyToOne(fetch = LAZY)
    private CashMember holder;
    @Getter
    private long balance;
    @OneToMany(mappedBy = "wallet", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<CashLog> cashLogs = new ArrayList<>();

    public Wallet(CashMember holder) {
        super(holder.getId());
        this.holder = holder;
    }

    public boolean hasBalance() {
        return balance > 0;
    }

    // 입금
    public void credit(long amount, CashEventType type, String relTypeCode, int relId) {
        balance += amount;

        addCashLog(amount, type, relTypeCode, relId);
    }

    public void credit(long amount, CashEventType cashEventType, BaseEntity rel) {
        credit(amount, cashEventType, rel.getModelTypeCode(), rel.getId());
    }

    public void credit(long amount, CashEventType cashEventType) {
        credit(amount, cashEventType, holder);
    }

    // 출금
    public void debit(long amount, CashEventType cashEventType, String relTypeCode, int relId) {
        balance -= amount;

        addCashLog(-amount, cashEventType, relTypeCode, relId);
    }

    public void debit(long amount, CashEventType cashEventType, BaseEntity rel) {
        debit(amount, cashEventType, rel.getModelTypeCode(), rel.getId());
    }

    public void debit(long amount, CashEventType cashEventType) {
        debit(amount, cashEventType, holder);
    }

    private CashLog addCashLog(long amount, CashEventType cashEventType, String relTypeCode, int relId) {
        CashLog cashLog = new CashLog(
                holder,
                this,
                amount,
                balance,
                cashEventType,
                relTypeCode,
                relId
        );

        cashLogs.add(cashLog);

        return cashLog;
    }
}
