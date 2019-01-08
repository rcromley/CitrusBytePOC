package com.citrusbyte.poc.smartAc.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String deviceSerialNumber;

    @Column(nullable = true)
    private String firmwareVersion;
    
    @Column(nullable = true)
    private Date registrationDate;

    public Device() {
        super();
    }

    public Device(String deviceSerialNumber, String firmwareVersion, Date registrationDate) {
        super();
        this.deviceSerialNumber = deviceSerialNumber;
        this.firmwareVersion = firmwareVersion;
        this.registrationDate = registrationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deviceSerialNumber == null) ? 0 : deviceSerialNumber.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((firmwareVersion == null) ? 0 : firmwareVersion.hashCode());
        result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        Device other = (Device) obj;
        
        if (deviceSerialNumber == null) {
            if (other.deviceSerialNumber != null)
                return false;
        } else if (!deviceSerialNumber.equals(other.deviceSerialNumber))
            return false;
        
        if (id != other.id)
            return false;
        
        if (firmwareVersion == null) {
            if (other.firmwareVersion != null)
                return false;
        } else if (!firmwareVersion.equals(other.firmwareVersion))
            return false;
        
        if (registrationDate == null) {
            if (other.registrationDate != null)
                return false;
        } else if (!registrationDate.equals(other.registrationDate))
            return false;
        
        return true;
    }

    @Override
    public String toString() {
        return "Device [id=" + id + ", deviceSerialNumber=" + deviceSerialNumber + ", firmwareVersion=" + firmwareVersion + ", registrationDate=" + registrationDate + "]";
    }

}
