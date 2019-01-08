package com.citrusbyte.poc.smartAc.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;

@Entity
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private long deviceId;

    @Column(nullable = true)
    private String sensorType;
    
    @Digits(integer=8, fraction=3)
    @Column
    private BigDecimal sensorValueDigit;
    
    @Column(nullable = true)
    private String sensorValueString;
    
    @Column(nullable = true)
    private Date sensorTimestamp;

    public SensorData() {
        super();
    }

    public SensorData(long deviceId, String sensorType, BigDecimal sensorValueDigit, String sensorValueString, Date sensorTimestamp) {
        super();
        this.deviceId = deviceId;
        this.sensorType = sensorType;
        this.sensorValueDigit = sensorValueDigit;
        this.sensorValueString = sensorValueString;
        this.sensorTimestamp = sensorTimestamp;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public BigDecimal getSensorValueDigit() {
		return sensorValueDigit;
	}

	public void setSensorValueDigit(BigDecimal sensorValueDigit) {
		this.sensorValueDigit = sensorValueDigit;
	}

	public String getSensorValueString() {
		return sensorValueString;
	}

	public void setSensorValueString(String sensorValueString) {
		this.sensorValueString = sensorValueString;
	}
	
	public String getSensorValue() {
		if(sensorValueString != null) {
			return sensorValueString;
		} else {
			return sensorValueDigit.toPlainString();
		}
	}

	public Date getSensorTimestamp() {
		return sensorTimestamp;
	}

	public void setSensorTimestamp(Date sensorTimestamp) {
		this.sensorTimestamp = sensorTimestamp;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sensorType == null) ? 0 : sensorType.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (deviceId ^ (deviceId >>> 32));
        result = prime * result + ((sensorValueDigit == null) ? 0 : sensorValueDigit.hashCode());
        result = prime * result + ((sensorValueString == null) ? 0 : sensorValueString.hashCode());
        result = prime * result + ((sensorTimestamp == null) ? 0 : sensorTimestamp.hashCode());
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
        
        SensorData other = (SensorData) obj;
        
        if (sensorType == null) {
            if (other.sensorType != null)
                return false;
        } else if (!sensorType.equals(other.sensorType))
            return false;
        
        if (id != other.id)
            return false;
        
        if (deviceId != other.deviceId)
            return false;
        
        if (!sensorValueDigit.equals(other.sensorValueDigit))
            return false;
        
        if (sensorValueString == null) {
            if (other.sensorValueString != null)
                return false;
        } else if (!sensorValueString.equals(other.sensorValueString))
            return false;
        
        if (sensorTimestamp == null) {
            if (other.sensorTimestamp != null)
                return false;
        } else if (!sensorTimestamp.equals(other.sensorTimestamp))
            return false;
        
        return true;
    }

    @Override
    public String toString() {
        return "Sensor Value [id=" + id + ", deviceId=" + deviceId + ", sensorType=" + sensorType + ", sensorValueDigit=" + sensorValueDigit + ", sensorValueString=" + sensorValueString + ", sensorTimestamp=" + sensorTimestamp + "]";
    }

}
