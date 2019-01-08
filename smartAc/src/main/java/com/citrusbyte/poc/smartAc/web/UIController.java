package com.citrusbyte.poc.smartAc.web;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.citrusbyte.poc.smartAc.persistence.model.Device;
import com.citrusbyte.poc.smartAc.persistence.model.SensorData;
import com.citrusbyte.poc.smartAc.persistence.repo.DeviceRepository;
import com.citrusbyte.poc.smartAc.persistence.repo.SensorDataRepository;
import com.citrusbyte.poc.smartAc.web.dto.Notifications;
import com.citrusbyte.poc.smartAc.web.dto.UserDto;

@Controller
public class UIController {
	
	@Autowired
    private DeviceRepository deviceRepository;
	
	@Autowired
    private SensorDataRepository sensorDataRepository;
	
	@Autowired
    private Notifications notifications;
	
	@GetMapping("/")
    String home(HttpSession session) {
		if(session.getAttribute("userDto") == null) {
			return "views/home/homeNotSignedIn";
		} else {
			return "views/home/homeSignedIn";
		}
    }
	
	@GetMapping("/authenticate/{message}")
    String authenticateWithMessage(HttpSession session, ModelMap model, @PathVariable("message") String message) {
		if("error".equalsIgnoreCase(message)) {
			model.addAttribute("loginError", true);
			return "views/home/homeNotSignedIn";
		} else {
			return "views/screens/showDevices";
		}
	}
	
	@GetMapping("/authenticate")
    String authenticate(ModelMap modelMap, HttpSession session) {
		return "views/screens/showDevices";
    }
	
	@PostMapping("/authenticate")
    String authenticatePost(ModelMap modelMap, HttpSession session) {
		return "views/screens/showDevices";
    }

	@GetMapping("/logout")
    String logout(HttpSession session) {
		session.setAttribute("userDto", null);
		return "views/home/homeNotSignedIn";
    }
	
	@GetMapping("/showDevices")
    String showDevices(HttpSession session, ModelMap model, @RequestParam(value = "searchDeviceName", required=false, defaultValue = "") String searchDeviceName) {
		//if(!authorizedNow(session)) {
		//	return "views/home/homeNotSignedIn";
		//}
		getNotificationMessages(model);
		Iterable<Device> devicesList = null;
		if(searchDeviceName != null && searchDeviceName.length() > 0) {
			devicesList = deviceRepository.findByDeviceSerialContaining(searchDeviceName);
		} else {
			devicesList = deviceRepository.findAll();
		}
		model.addAttribute("devicesList", devicesList);
		return "views/screens/showDevices";
    }
	
	@GetMapping("/showDeviceDetail/{deviceNbr}")
    String showDeviceDetail(HttpSession session, ModelMap model, @PathVariable("deviceNbr") long deviceNbr, @RequestParam(value = "searchDateRange", required=false, defaultValue = "") String searchDateRange) {
		getNotificationMessages(model);
		session.setAttribute("deviceNbr", deviceNbr);
		return showDeviceDetail(session,model,searchDateRange);
	}
	
	@GetMapping("/showDeviceDetail/")
    String showDeviceDetail(HttpSession session, ModelMap model, @RequestParam(value = "searchDateRange", required=false, defaultValue = "") String searchDateRange) {
		getNotificationMessages(model);
		String deviceNbrStr = session.getAttribute("deviceNbr").toString();
		long deviceNbr = 1L;
		if(deviceNbrStr != null && deviceNbrStr.length() >= 1) {
			deviceNbr = new Long(deviceNbrStr);
		}
		
		Optional<Device> device = deviceRepository.findById(deviceNbr);
		if(device.isPresent()) {
			model.addAttribute("device", device.get());
			Iterable<SensorData> sensorDataList = null;
			if(searchDateRange != null && searchDateRange.length() > 0) {
				Date fromDate = null;
				Calendar cal = Calendar.getInstance();
				if("TODAY".equalsIgnoreCase(searchDateRange)) {				
					cal.set(Calendar.HOUR_OF_DAY, 
					cal.getActualMinimum(Calendar.HOUR_OF_DAY));
					fromDate = cal.getTime();
				} else if("THIS_WEEK".equalsIgnoreCase(searchDateRange)) {
					cal.set(Calendar.DAY_OF_WEEK, 
					cal.getActualMinimum(Calendar.DAY_OF_WEEK));
					fromDate = cal.getTime();
				} else if("THIS_MONTH".equalsIgnoreCase(searchDateRange)) {
					cal.set(Calendar.DAY_OF_MONTH, 
					cal.getActualMinimum(Calendar.DAY_OF_MONTH));
					fromDate = cal.getTime();
				} else if("THIS_YEAR".equalsIgnoreCase(searchDateRange)) {
					cal.set(Calendar.DAY_OF_YEAR,
					cal.getActualMinimum(Calendar.DAY_OF_YEAR));
					fromDate = cal.getTime();
				}
				if(fromDate != null) {
					sensorDataList = sensorDataRepository.findByGreaterThanDateForDevice(deviceNbr, fromDate);
				} else {
					sensorDataList = sensorDataRepository.findAllForDevice(deviceNbr);
				}
			} else {
				sensorDataList = sensorDataRepository.findAll();
			}		
			sensorDataRepository.findAll();
			model.addAttribute("sensorDataList", sensorDataList);
			model.addAttribute("chartData", getChartData(sensorDataList));
		}		
		return "views/screens/showDeviceDetail";
    }
	
	private void getNotificationMessages(ModelMap model) {
		model.addAttribute("notificationMessages", notifications.getNotifications());
	}
	
	@GetMapping("/notifications/{from}/{id}")
    public void manageNotification(HttpServletRequest request, HttpServletResponse response, @PathVariable String from, @PathVariable long id) throws IOException {
    	notifications.removeNotification(id);
    	if("d".equalsIgnoreCase(from)) {
    		response.sendRedirect(request.getContextPath() + "/showDevices/");
    	} else {
    		response.sendRedirect(request.getContextPath() + "/showDeviceDetail/");
    	}
    }

	/*private boolean authorizedNow(HttpSession session) {
		if(session.getAttribute("userDto") == null) {
			return false;
		} else {
			return true;
		}
    }*/
	
	private String getChartData(Iterable<SensorData> sensorDataList) {				
		Iterator<SensorData> dataIterator = sensorDataList.iterator();
		Map<String, SensorDataTuple> consolidatedData = new TreeMap<String, SensorDataTuple>();
		while(dataIterator.hasNext()){
			SensorData data = dataIterator.next();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");			
			Date date = data.getSensorTimestamp();
			String key = sdf.format(date);
			
			SensorDataTuple tmpSd = consolidatedData.get(key);
			if(tmpSd == null) {
				tmpSd = new SensorDataTuple();
			}
			
			if("TEMPERATURE".equalsIgnoreCase(data.getSensorType())){
				tmpSd.setTemperature(data.getSensorValueDigit());
			} else if("HUMIDITY".equalsIgnoreCase(data.getSensorType())){
				tmpSd.setHumidity(data.getSensorValueDigit());
			} else if("CARBON_MONOXIDE".equalsIgnoreCase(data.getSensorType())){
				tmpSd.setCarbonMonoxide(data.getSensorValueDigit());
			}
			consolidatedData.put(key, tmpSd);
		}
		
		String rtn = "Date, Temperature, Humidity, Carbon Monoxide;";
		for (String key : consolidatedData.keySet()) {
			rtn = rtn + key + "," +
					consolidatedData.get(key).getTemperature() + "," +
					consolidatedData.get(key).getHumidity() + "," +
					consolidatedData.get(key).getCarbonMonoxide() + ";";
		}
		return rtn;
	}
	
	private class SensorDataTuple{
		BigDecimal temperature = new BigDecimal(0);
		BigDecimal humidity = new BigDecimal(0);
		BigDecimal carbonMonoxide = new BigDecimal(0);
		
		public BigDecimal getTemperature() {
			return temperature;
		}
		public void setTemperature(BigDecimal temperature) {
			this.temperature = temperature;
		}
		public BigDecimal getHumidity() {
			return humidity;
		}
		public void setHumidity(BigDecimal humidity) {
			this.humidity = humidity;
		}
		public BigDecimal getCarbonMonoxide() {
			return carbonMonoxide;
		}
		public void setCarbonMonoxide(BigDecimal carbonMonoxide) {
			this.carbonMonoxide = carbonMonoxide;
		}
	}
}