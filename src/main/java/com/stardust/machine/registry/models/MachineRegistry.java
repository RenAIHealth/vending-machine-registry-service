package com.stardust.machine.registry.models;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="registry")
public class MachineRegistry extends DataModel {

    public enum MachineRegistryStatus {
        ACTIVATED, WITHDRAW
    }

    private String token;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date registerTime;
    private MachineRegistryStatus status = MachineRegistryStatus.ACTIVATED;
    private SellerMachine machine;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public long getId() {
        return id;
    }

    @Column(nullable = false, length = 100)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(nullable = false, name = "register_time")
    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    @Column(nullable = false)
    public MachineRegistryStatus getStatus() {
        return status;
    }

    public void setStatus(MachineRegistryStatus status) {
        this.status = status;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", referencedColumnName = "id", unique = true)
    public SellerMachine getMachine() {
        return machine;
    }

    public void setMachine(SellerMachine machine) {
        this.machine = machine;
    }
}
