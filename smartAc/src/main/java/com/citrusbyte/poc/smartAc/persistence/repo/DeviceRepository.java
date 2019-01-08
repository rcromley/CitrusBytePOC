package com.citrusbyte.poc.smartAc.persistence.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.citrusbyte.poc.smartAc.persistence.model.Device;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, Long> {
    List<Device> findByDeviceSerialNumber(String deviceSerialNumber);
    
    @Query("Select d from Device d where d.deviceSerialNumber like %:deviceSearch% order by d.deviceSerialNumber")
    List<Device> findByDeviceSerialContaining(String deviceSearch);
}