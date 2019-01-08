package com.citrusbyte.poc.smartAc;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

//import com.citrusbyte.poc.smartAc.persistence.model.Device;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SmartAcApplication.class })
//@SpringBootTest(classes = { SmartAcApplication.class }, webEnvironment 
//= WebEnvironment.DEFINED_PORT)
public class ApiTests {

    private static final String API_ROOT = "http://localhost:8080/api/device";
    
    @Test
	public void contextLoads() {    	
    	/*try {
			TimeUnit.SECONDS.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

    /*@Test
    public void whenGetAllDevices_thenOK() {
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetDevicesByDeviceSerialNumber_thenOK() {
    	Device device = new Device();
    	device.setDeviceSerialNumber(randomAlphabetic(10));
    	device.setFirmwareVersion(randomAlphabetic(4));
    	//com.citrusbyte.poc.smartAc.persistence.model.Device device = createRandomDevice();
        //createDeviceAsUri(device);

        final Response response = RestAssured.get(API_ROOT + "/title/" + device.getDeviceSerialNumber());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
            .size() > 0);
    }

    @Test
    public void whenGetCreatedDeviceById_thenOK() {
        final Device device = createRandomDevice();
        final String location = createDeviceAsUri(device);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(device.getDeviceSerialNumber(), response.jsonPath()
            .get("title"));
    }

    @Test
    public void whenGetNotExistDeviceById_thenNotFound() {
        final Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewDevice_thenCreated() {
        final Device device = createRandomDevice();

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(device)
            .post(API_ROOT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidDevice_thenError() {
        final Device device = createRandomDevice();
        device.setDeviceSerialNumber(null);

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(device)
            .post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedDevice_thenUpdated() {
        final Device device = createRandomDevice();
        final String location = createDeviceAsUri(device);

        device.setId(Long.parseLong(location.split("api/devices/")[1]));
        device.setFirmwareVersion("newFirmwareVersion");
        device.setDeviceSerialNumber("newDeviceSerialNumber");
        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(device)
            .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newFirmwareVersion", response.jsonPath()
            .get("author"));
        assertEquals("newDeviceSerialNumber", response.jsonPath()
                .get("author"));
    }

    @Test
    public void whenDeleteCreatedDevice_thenOk() {
        final Device device = createRandomDevice();
        final String location = createDeviceAsUri(device);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }*/

    // ===============================

    /*public Device createRandomDevice() {
        Device device = new Device();
        device.setDeviceSerialNumber(randomAlphabetic(10));
        device.setFirmwareVersion(randomAlphabetic(15));
        return device;
    }

    public String createDeviceAsUri(Device device) {
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(device)
            .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath()
            .get("id");
    }*/

}
