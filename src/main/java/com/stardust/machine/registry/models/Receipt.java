package com.stardust.machine.registry.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="receipts")
public class Receipt extends DataModel {
    private Date date;

    private String operator;

    private Double totalAmount;

    private Double paidAmount;

    private List<ReceiptDetail> details = new ArrayList();

    private String orderNumber;

    private String externalTradeNumber;

    private TransactionType transactionType;

    private PaymentType paymentType;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public long getId() {
        return id;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "operator", length = 30)
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Column(name = "total_amount")
    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @OneToMany(mappedBy="receipt", cascade = CascadeType.ALL)
    public List<ReceiptDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ReceiptDetail> details) {
        this.details = details;
        this.details.stream().forEach(receiptDetail -> {receiptDetail.setReceipt(this);});
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Column(nullable = true, name = "external_trade_number")
    public String getExternalTradeNumber() {
        return externalTradeNumber;
    }

    public void setExternalTradeNumber(String externalTradeNumber) {
        this.externalTradeNumber = externalTradeNumber;
    }

    @Enumerated
    @Column(name = "transaction_type")
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Enumerated
    @Column(name = "payment_type")
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name = "paid_amount")
    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }
}

