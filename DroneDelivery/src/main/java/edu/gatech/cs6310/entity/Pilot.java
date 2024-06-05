package edu.gatech.cs6310.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;


//@Data
@Entity
@Table(name = "pilot")
public class Pilot extends User {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drone_id", referencedColumnName = "id")
    private Drone drone;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "license_id")
    private String licenseId;

    @Column(name = "exp_level")
    private int expLevel = 0;

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }
}
