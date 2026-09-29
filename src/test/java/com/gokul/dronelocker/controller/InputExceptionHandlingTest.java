package com.gokul.dronelocker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.gokul.dronelocker.config.ApiInputConfiguration;
import com.gokul.dronelocker.entity.CourierService;
import com.gokul.dronelocker.exception.GlobalExceptionHandler;
import com.gokul.dronelocker.service.CourierServiceManager;
import com.gokul.dronelocker.service.DroneLandingService;
import com.gokul.dronelocker.service.LockerBayService;
import com.gokul.dronelocker.service.PackageService;

import tools.jackson.databind.json.JsonMapper;

class InputExceptionHandlingTest {

    private MockMvc courierMvc;
    private MockMvc landingMvc;
    private MockMvc packageMvc;

    @BeforeEach
    void setUp() {
        CourierServiceManager courierServiceManager = mock(CourierServiceManager.class);
        when(courierServiceManager.createCourierService(any(CourierService.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        courierMvc = standaloneSetup(new CourierServiceController(courierServiceManager))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        JsonMapper.Builder jsonMapperBuilder = JsonMapper.builder();
        new ApiInputConfiguration().strictIntegerInputCustomizer().customize(jsonMapperBuilder);
        JacksonJsonHttpMessageConverter jsonConverter = new JacksonJsonHttpMessageConverter(jsonMapperBuilder);
        landingMvc = standaloneSetup(new DroneLandingController(mock(DroneLandingService.class)))
                .setMessageConverters(jsonConverter)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        packageMvc = standaloneSetup(new PackageController(mock(PackageService.class), mock(LockerBayService.class)))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void validCourierServiceRequestStillWorks() throws Exception {
        courierMvc.perform(post("/api/courier-services")
                        .contentType("application/json")
                        .content("""
                                {"serviceCode":"C-1","companyName":"Courier One","active":true}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceCode").value("C-1"));
    }

    @Test
    void textInNumericIdReturnsReadableError() throws Exception {
        courierMvc.perform(get("/api/courier-services/not-a-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid ID. Please enter a valid number."));
    }

    @Test
    void decimalIdReturnsReadableError() throws Exception {
        courierMvc.perform(get("/api/courier-services/1.5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid ID. Please enter a valid number."));
    }

    @Test
    void malformedJsonReturnsReadableError() throws Exception {
        courierMvc.perform(post("/api/courier-services")
                        .contentType("application/json")
                        .content("{\"serviceCode\":"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid request data."));
    }

    @Test
    void blankPickupPasscodeReturnsReadableError() throws Exception {
        packageMvc.perform(post("/api/packages/P-1/pickup")
                        .contentType("application/json")
                        .content("{\"passcode\":\" \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Required field 'passcode' must not be null or empty."));
    }

    @Test
    void decimalLockerPadIdReturnsReadableError() throws Exception {
        landingMvc.perform(post("/api/lockers/landings/process")
                        .contentType("application/json")
                        .content("""
                                {"droneCode":"D-1","packageCode":"P-1","lockerPadId":1.5}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid request data."));
    }

    @Test
    void missingRequestParameterReturnsReadableError() {
        var error = new GlobalExceptionHandler().handleMissingParameter(
                new MissingServletRequestParameterException("page", "Integer"));

        assertEquals(400, error.getStatusCode().value());
        assertEquals("Missing required request parameter: page.", error.getBody().get("message"));
    }
}
