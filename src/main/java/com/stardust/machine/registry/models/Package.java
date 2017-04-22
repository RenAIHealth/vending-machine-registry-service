package com.stardust.machine.registry.models;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="packages")
public class Package extends DataModel implements SNIdentityModel {

    public enum PackageStatus {
        AVAILABLE, SOLD
    }

    private String sn;
    private String name;
    private SellerMachine machine;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date soldTime;
    private PackageStatus status = PackageStatus.AVAILABLE;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public long getId() {
        return id;
    }

    @Column(nullable = false, length = 100)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false,
                name = "machine_id",
                referencedColumnName="id")
    public SellerMachine getMachine() {
        return machine;
    }

    public void setMachine(SellerMachine machine) {
        this.machine = machine;
    }

    @Column(name = "sold_time", nullable = true)
    public Date getSoldTime() {
        return soldTime;
    }

    public void setSoldTime(Date soldTime) {
        this.soldTime = soldTime;
    }

    @Column(nullable = false)
    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }
}
