package com.citrusbyte.poc.smartAc.persistence.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.citrusbyte.poc.smartAc.persistence.model.SensorData;

public interface SensorDataRepository extends CrudRepository<SensorData, Long> {
	@Query("Select s from SensorData s where s.sensorTimestamp > :sensorTimestamp and s.deviceId = :deviceId order by s.sensorTimestamp desc")
    List<SensorData> findByGreaterThanDateForDevice(long deviceId, Date sensorTimestamp);
	@Query("Select s from SensorData s where s.deviceId = :deviceId order by s.sensorTimestamp desc")
    List<SensorData> findAllForDevice(long deviceId);
}