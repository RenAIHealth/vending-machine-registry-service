package com.stardust.machine.registry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="receipt_details")
public class ReceiptDetail extends DataModel {
    private Receipt receipt;

    private String itemName;

    private Double amount;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public long getId() {
        return id;
    }

    @Column(nullable = false, name = "item_name", length = 100)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "receipt_id")
    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
