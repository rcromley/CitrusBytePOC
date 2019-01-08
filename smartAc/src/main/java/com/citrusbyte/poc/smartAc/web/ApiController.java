package com.citrusbyte.poc.smartAc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.citrusbyte.poc.smartAc.persistence.model.Device;
import com.citrusbyte.poc.smartAc.persistence.model.SensorData;
import com.citrusbyte.poc.smartAc.persistence.repo.DeviceRepository;
import com.citrusbyte.poc.smartAc.persistence.repo.SensorDataRepository;
import com.citrusbyte.poc.smartAc.web.dto.DeviceRegistration;
import com.citrusbyte.poc.smartAc.web.dto.Notifications;
import com.citrusbyte.poc.smartAc.web.dto.ReturnObject;
import com.citrusbyte.poc.smartAc.web.dto.SensorDataDto;
import com.citrusbyte.poc.smartAc.web.dto.SensorValue;
import com.citrusbyte.poc.smartAc.web.exception.DeviceIdMismatchException;
import com.citrusbyte.poc.smartAc.web.exception.DeviceNotFoundException;
import com.citrusbyte.poc.smartAc.web.exception.ExceptionConstants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
	private BigDecimal maxAllowedPPM = new BigDecimal(9);

    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
    private SensorDataRepository sensorDataRepository;
    
    @Autowired
    private Notifications notifications;

    @GetMapping
    public Iterable<Device> findAll() {
        return deviceRepository.findAll();
    }

	@GetMapping("/deviceSerialNumber/{deviceSerialNumber}")
    public List<Device> findByDeviceSerialNumber(@PathVariable String deviceSerialNumber) {
        return deviceRepository.findByDeviceSerialNumber(deviceSerialNumber);
    }

    @GetMapping("/{id}")
    public Device findOne(@PathVariable long id) {
        return deviceRepository.findById(id)
          .orElseThrow(DeviceNotFoundException::new);
    }
    
    @GetMapping("/initializeTestData")
    public void initializeTestData() {
    	for(int i=0; i<12; i++) {
    		Device device = new Device();
        	device.setDeviceSerialNumber("A"+i+"-SerialNumber");
        	device.setFirmwareVersion("FirmwareVersion-"+i);
        	device.setRegistrationDate(new Date());
            Device savedDevice = deviceRepository.save(device);
            for(int j=0; j<100; j++) {
            	LocalDateTime ldt = LocalDateTime.now().minusHours(j*12);
            	Date sensorDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            	SensorData sensorData = new SensorData();
            	sensorData.setDeviceId(savedDevice.getId());
            	sensorData.setSensorTimestamp(sensorDate);
            	sensorData.setSensorType("TEMPERATURE");
            	sensorData.setSensorValueDigit(new BigDecimal(j-i));
            	sensorDataRepository.save(sensorData);
            	
            	sensorData = new SensorData();
            	sensorData.setDeviceId(savedDevice.getId());
            	sensorData.setSensorTimestamp(sensorDate);
            	sensorData.setSensorType("HUMIDITY");
            	sensorData.setSensorValueDigit(new BigDecimal((j-i)*.75));
            	sensorDataRepository.save(sensorData);
            	
            	sensorData = new SensorData();
            	sensorData.setDeviceId(savedDevice.getId());
            	sensorData.setSensorTimestamp(sensorDate);
            	sensorData.setSensorType("CARBON_MONOXIDE");
            	sensorData.setSensorValueDigit(new BigDecimal((j-i)/3));
            	sensorDataRepository.save(sensorData);
            	
            	sensorData = new SensorData();
            	sensorData.setDeviceId(savedDevice.getId());
            	sensorData.setSensorTimestamp(sensorDate);
            	sensorData.setSensorType("DEVICE_HEALTH");
            	sensorData.setSensorValueString("OK");
            	sensorDataRepository.save(sensorData);   
            }
    	}  
    	addNotification("PPM for A2-SerialNumber has a value of 12 at "+LocalDateTime.now().minusHours(3));
    	addNotification("Health status of needs_service reported on "+LocalDateTime.now().minusHours(2)+" at A4-SerialNumber");
    	addNotification("Health status of needs_new_filter reported on "+LocalDateTime.now().minusHours(1)+" at A5-SerialNumber");
    	addNotification("Health status of gas_leak reported on "+LocalDateTime.now().minusHours(4)+" at A6-SerialNumber");
    }

    @PostMapping("/device")
    @ResponseStatus(HttpStatus.CREATED)    	    
    public ReturnObject createDevice(@RequestBody String deviceRegistrationJson) {
    	ObjectMapper mapper = new ObjectMapper();
    	DeviceRegistration deviceRegistration;
    	ReturnObject rtn = new ReturnObject();
		try {
			deviceRegistration = mapper.readValue(deviceRegistrationJson, DeviceRegistration.class);
			Device device = new Device();
	    	device.setDeviceSerialNumber(deviceRegistration.getDevice_serial_number());
	    	device.setFirmwareVersion(deviceRegistration.getFirmware_version());
	    	device.setRegistrationDate(deviceRegistration.getTimestampAsDate());
	        Device device1 = deviceRepository.save(device);
	        rtn.setOk(true);
	        return rtn;
		} catch (JsonParseException e) {
			rtn.setOk(false);
			rtn.addMessage(ExceptionConstants.JSON_PARSE_EXCEPTION, e.getMessage());
	        return rtn;
		} catch (JsonMappingException e) {
			rtn.setOk(false);
			rtn.addMessage(ExceptionConstants.JSON_MAPPING_EXCEPTION, e.getMessage());
	        return rtn;
		} catch (IOException e) {
			rtn.setOk(false);
			rtn.addMessage(ExceptionConstants.IO_EXCEPTION, e.getMessage());
	        return rtn;
		}           
    }
    
    @PostMapping("/sensorData")
    @ResponseStatus(HttpStatus.CREATED)    	    
    public ReturnObject sensorData(@RequestBody String sensorDataJson) {
    	ObjectMapper mapper = new ObjectMapper();
    	SensorDataDto sensorDataDto;
    	ReturnObject rtn = new ReturnObject();
		try {
			sensorDataDto = mapper.readValue(sensorDataJson, SensorDataDto.class);			
			List<Device> deviceRetrievedList = deviceRepository.findByDeviceSerialNumber(sensorDataDto.getDevice_serial_number());
			if(deviceRetrievedList == null || deviceRetrievedList.size() <= 0) {
				rtn.setOk(false);
				rtn.addMessage(ExceptionConstants.DEVICE_NOT_FOUND, "Device Serial Number "+sensorDataDto.getDevice_serial_number()+" not found.");
		        return rtn;
			}
			long deviceId = deviceRetrievedList.get(0).getId();
			
			SensorData sensorData = new SensorData();
			sensorData.setDeviceId(deviceId);
			sensorData.setSensorTimestamp(sensorDataDto.getTimestampAsDate());
			sensorData.setSensorType("DEVICE_HEALTH");
			sensorData.setSensorValueString(sensorDataDto.getDevice_health_status());
			sensorDataRepository.save(sensorData);
			if("needs_service".equalsIgnoreCase(sensorDataDto.getDevice_health_status())
				|| "needs_new_filter".equalsIgnoreCase(sensorDataDto.getDevice_health_status())
				|| "gas_leak".equalsIgnoreCase(sensorDataDto.getDevice_health_status())) {
				addNotification("Health status of "+sensorDataDto.getDevice_health_status()+" reported on "
						+sensorDataDto.getDevice_serial_number()+" at "+sensorDataDto.getTimestampAsDate());
			}
			
			for (SensorValue oneSensorValue : sensorDataDto.getSensor_values()) {
				sensorData = new SensorData();
				sensorData.setDeviceId(deviceId);
				sensorData.setSensorTimestamp(oneSensorValue.getTimestampAsDate());
				sensorData.setSensorType(oneSensorValue.getSensor_type());
				BigDecimal value = new BigDecimal(oneSensorValue.getSensor_value());
				sensorData.setSensorValueDigit(value);
				sensorDataRepository.save(sensorData);
				if("CARBON_MONOXIDE".equalsIgnoreCase(oneSensorValue.getSensor_type())) {
					if(value.compareTo(maxAllowedPPM) == 1) {
						addNotification("PPM for "+sensorDataDto.getDevice_serial_number()+" has a value of "+value+" at "+oneSensorValue.getTimestampAsDate());
					}
				}
			}
	        rtn.setOk(true);
	        return rtn;
		} catch (JsonParseException e) {
			rtn.setOk(false);
			rtn.addMessage(ExceptionConstants.JSON_PARSE_EXCEPTION, e.getMessage());
	        return rtn;
		} catch (JsonMappingException e) {
			rtn.setOk(false);
			rtn.addMessage(ExceptionConstants.JSON_MAPPING_EXCEPTION, e.getMessage());
	        return rtn;
		} catch (IOException e) {
			rtn.setOk(false);
			rtn.addMessage(ExceptionConstants.IO_EXCEPTION, e.getMessage());
	        return rtn;
		}           
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        deviceRepository.findById(id)
          .orElseThrow(DeviceNotFoundException::new);
        deviceRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Device updateDevice(@RequestBody Device device, @PathVariable long id) {
        if (device.getId() != id) {
            throw new DeviceIdMismatchException();
        }
        deviceRepository.findById(id)
          .orElseThrow(DeviceNotFoundException::new);
        return deviceRepository.save(device);
    } 
    
    private void addNotification(String notification) {
    	notifications.addNotification(notification);
    }
}
