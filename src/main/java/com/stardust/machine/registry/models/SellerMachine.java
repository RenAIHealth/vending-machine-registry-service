package com.stardust.machine.registry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="machines")
public class SellerMachine extends DataModel implements SNIdentityModel {
    private String province;
    private String city;
    private String district;
    private String location;
    private double longitude;
    private double latitude;
    private String sn;
    private List<Package> packages;
    private MachineRegistry registry;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    public long getId() {
        return id;
    }

    @Column(nullable = false, length = 100, unique = true)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(nullable = false, length = 30)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(nullable = false, length = 30)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(nullable = false, length = 30)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(nullable = false, length = 100)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(nullable = true)
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Column(nullable = true)
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "machine", fetch=FetchType.LAZY)
    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    @JsonIgnore
    @OneToOne(mappedBy = "machine",
              fetch = FetchType.LAZY,
              cascade = CascadeType.ALL)
    public MachineRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(MachineRegistry registry) {
        this.registry = registry;
    }
}
