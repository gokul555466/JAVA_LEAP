package com.gokul.dronelocker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import com.gokul.dronelocker.config.ApiInputConfiguration;
import com.gokul.dronelocker.entity.CourierService;
import com.gokul.dronelocker.entity.LockerBay;
import com.gokul.dronelocker.entity.LockerPad;
import com.gokul.dronelocker.entity.Package;
import com.gokul.dronelocker.entity.Vendor;
import com.gokul.dronelocker.entity.Invoice;
import com.gokul.dronelocker.entity.Payment;
import com.gokul.dronelocker.entity.PurchaseOrder;
import com.gokul.dronelocker.entity.LedgerEntry;
import com.gokul.dronelocker.exception.GlobalExceptionHandler;
import com.gokul.dronelocker.service.CourierServiceManager;
import com.gokul.dronelocker.service.DroneLandingService;
import com.gokul.dronelocker.service.InvoiceService;
import com.gokul.dronelocker.service.LedgerEntryService;
import com.gokul.dronelocker.service.LockerBayService;
import com.gokul.dronelocker.service.LockerPadService;
import com.gokul.dronelocker.service.PackageService;
import com.gokul.dronelocker.service.PaymentService;
import com.gokul.dronelocker.service.PurchaseOrderService;
import com.gokul.dronelocker.service.VendorService;

import tools.jackson.databind.json.JsonMapper;

class ValidationSystemTest {

    private MockMvc padMvc;
    private MockMvc bayMvc;
    private MockMvc courierMvc;
    private MockMvc packageMvc;
    private MockMvc landingMvc;
    private MockMvc invoiceMvc;
    private MockMvc paymentMvc;
    private MockMvc purchaseOrderMvc;
    private MockMvc ledgerMvc;
    private MockMvc vendorMvc;

    @BeforeEach
    void setUp() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        JsonMapper.Builder jsonMapperBuilder = JsonMapper.builder();
        new ApiInputConfiguration().strictIntegerInputCustomizer().customize(jsonMapperBuilder);
        JacksonJsonHttpMessageConverter jsonConverter = new JacksonJsonHttpMessageConverter(jsonMapperBuilder);

        LockerPadService padService = mock(LockerPadService.class);
        when(padService.createPad(any(LockerPad.class))).thenAnswer(i -> i.getArgument(0));
        padMvc = standaloneSetup(new LockerPadController(padService))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();

        LockerBayService bayService = mock(LockerBayService.class);
        when(bayService.createBay(any(LockerBay.class))).thenAnswer(i -> i.getArgument(0));
        when(bayService.updateBay(any(Long.class), any(LockerBay.class))).thenAnswer(i -> i.getArgument(1));
        bayMvc = standaloneSetup(new LockerBayController(bayService))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();

        CourierServiceManager courierManager = mock(CourierServiceManager.class);
        when(courierManager.createCourierService(any(CourierService.class))).thenAnswer(i -> i.getArgument(0));
        when(courierManager.updateCourierService(any(Long.class), any(CourierService.class))).thenAnswer(i -> i.getArgument(1));
        courierMvc = standaloneSetup(new CourierServiceController(courierManager))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();

        PackageService pkgService = mock(PackageService.class);
        when(pkgService.createPackage(any(Package.class))).thenAnswer(i -> i.getArgument(0));
        when(pkgService.updatePackage(any(Long.class), any(Package.class))).thenAnswer(i -> i.getArgument(1));
        packageMvc = standaloneSetup(new PackageController(pkgService, bayService))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();

        DroneLandingService landingService = mock(DroneLandingService.class);
        landingMvc = standaloneSetup(new DroneLandingController(landingService))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();

        InvoiceService invoiceService = mock(InvoiceService.class);
        when(invoiceService.createInvoice(any(Invoice.class))).thenAnswer(i -> i.getArgument(0));
        invoiceMvc = standaloneSetup(new InvoiceController(invoiceService))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();

        PaymentService paymentService = mock(PaymentService.class);
        when(paymentService.recordPayment(any(Payment.class))).thenAnswer(i -> i.getArgument(0));
        paymentMvc = standaloneSetup(new PaymentController(paymentService))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();

        PurchaseOrderService poService = mock(PurchaseOrderService.class);
        when(poService.createPurchaseOrder(any(PurchaseOrder.class))).thenAnswer(i -> i.getArgument(0));
        when(poService.updatePurchaseOrder(any(Long.class), any(PurchaseOrder.class))).thenAnswer(i -> i.getArgument(1));
        purchaseOrderMvc = standaloneSetup(new PurchaseOrderController(poService))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();

        LedgerEntryService ledgerService = mock(LedgerEntryService.class);
        when(ledgerService.createEntry(any(LedgerEntry.class))).thenAnswer(i -> i.getArgument(0));
        ledgerMvc = standaloneSetup(new LedgerEntryController(ledgerService))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();

        VendorService vendorService = mock(VendorService.class);
        when(vendorService.createVendor(any(Vendor.class))).thenAnswer(i -> i.getArgument(0));
        when(vendorService.updateVendor(any(Long.class), any(Vendor.class))).thenAnswer(i -> i.getArgument(1));
        vendorMvc = standaloneSetup(new VendorController(vendorService))
                .setControllerAdvice(handler).setMessageConverters(jsonConverter).build();
    }

    /* ═══════════ LOCKER PAD TESTS ═══════════ */

    @Nested
    @DisplayName("Locker Pad Validation")
    class LockerPadTests {

        @Test
        @DisplayName("Valid pad creation succeeds")
        void validPadCreation() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"LP-001","location":"Coimbatore North Hub","totalBays":10,"availableBays":5,"landingTariff":250.50,"active":true}
                                    """))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.padCode").value("LP-001"));
        }

        @Test
        @DisplayName("Blank pad code is rejected")
        void blankPadCode() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"","location":"Coimbatore","totalBays":5,"availableBays":3,"landingTariff":100,"active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Numbers-only location is rejected")
        void numbersOnlyLocation() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"LP001","location":"12345","totalBays":5,"availableBays":3,"landingTariff":100,"active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Special characters in location rejected")
        void specialCharsLocation() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"LP001","location":"@@@@","totalBays":5,"availableBays":3,"landingTariff":100,"active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Whitespace-only location is rejected")
        void whitespaceLocation() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"LP001","location":"   ","totalBays":5,"availableBays":3,"landingTariff":100,"active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Negative tariff is rejected")
        void negativeTariff() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"LP001","location":"Coimbatore","totalBays":5,"availableBays":3,"landingTariff":-50,"active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Negative total bays is rejected")
        void negativeTotalBays() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"LP001","location":"Coimbatore","totalBays":-5,"availableBays":0,"landingTariff":100,"active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Valid location with spaces and hyphens accepted")
        void validLocationWithFormats() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"LP002","location":"Chennai Central Hub","totalBays":8,"availableBays":4,"landingTariff":200,"active":true}
                                    """))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Text ID returns clean error")
        void textIdReturnsError() throws Exception {
            padMvc.perform(get("/api/lockers/pads/abc"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Invalid ID. Please enter a valid number."));
        }

        @Test
        @DisplayName("Decimal ID returns clean error")
        void decimalIdReturnsError() throws Exception {
            padMvc.perform(get("/api/lockers/pads/1.5"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    /* ═══════════ LOCKER BAY TESTS ═══════════ */

    @Nested
    @DisplayName("Locker Bay Validation")
    class LockerBayTests {

        @Test
        @DisplayName("Valid bay creation succeeds")
        void validBayCreation() throws Exception {
            bayMvc.perform(post("/api/lockers/bays")
                            .contentType("application/json")
                            .content("""
                                    {"bayCode":"B-01","status":"AVAILABLE","lockerPadId":1}
                                    """))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Invalid status rejected")
        void invalidStatus() throws Exception {
            bayMvc.perform(post("/api/lockers/bays")
                            .contentType("application/json")
                            .content("""
                                    {"bayCode":"B-01","status":"BROKEN","lockerPadId":1}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Blank bay code rejected")
        void blankBayCode() throws Exception {
            bayMvc.perform(post("/api/lockers/bays")
                            .contentType("application/json")
                            .content("""
                                    {"bayCode":"","status":"AVAILABLE","lockerPadId":1}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Missing locker pad ID rejected")
        void missingLockerPadId() throws Exception {
            bayMvc.perform(post("/api/lockers/bays")
                            .contentType("application/json")
                            .content("""
                                    {"bayCode":"B-01","status":"AVAILABLE"}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Negative locker pad ID rejected")
        void negativeLockerPadId() throws Exception {
            bayMvc.perform(post("/api/lockers/bays")
                            .contentType("application/json")
                            .content("""
                                    {"bayCode":"B-01","status":"AVAILABLE","lockerPadId":-1}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    /* ═══════════ COURIER SERVICE TESTS ═══════════ */

    @Nested
    @DisplayName("Courier Service Validation")
    class CourierServiceTests {

        @Test
        @DisplayName("Valid courier creation succeeds")
        void validCourierCreation() throws Exception {
            courierMvc.perform(post("/api/courier-services")
                            .contentType("application/json")
                            .content("""
                                    {"serviceCode":"CS-001","companyName":"Fast Delivery","contactPerson":"Ravi Kumar","email":"ravi@fast.com","phone":"9876543210","active":true}
                                    """))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.serviceCode").value("CS-001"));
        }

        @Test
        @DisplayName("Invalid email rejected")
        void invalidEmail() throws Exception {
            courierMvc.perform(post("/api/courier-services")
                            .contentType("application/json")
                            .content("""
                                    {"serviceCode":"CS-001","companyName":"Fast Delivery","email":"not-an-email","active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Invalid phone number rejected")
        void invalidPhone() throws Exception {
            courierMvc.perform(post("/api/courier-services")
                            .contentType("application/json")
                            .content("""
                                    {"serviceCode":"CS-001","companyName":"Fast Delivery","phone":"12345","active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Numbers-only company name rejected")
        void numbersOnlyCompanyName() throws Exception {
            courierMvc.perform(post("/api/courier-services")
                            .contentType("application/json")
                            .content("""
                                    {"serviceCode":"CS-001","companyName":"12345","active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Special chars in service code rejected")
        void specialCharsServiceCode() throws Exception {
            courierMvc.perform(post("/api/courier-services")
                            .contentType("application/json")
                            .content("""
                                    {"serviceCode":"CS@#$","companyName":"Fast Delivery","active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Blank service code rejected")
        void blankServiceCode() throws Exception {
            courierMvc.perform(post("/api/courier-services")
                            .contentType("application/json")
                            .content("""
                                    {"serviceCode":"","companyName":"Fast Delivery","active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Text ID returns clean error")
        void textIdError() throws Exception {
            courierMvc.perform(get("/api/courier-services/not-a-number"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Invalid ID. Please enter a valid number."));
        }
    }

    /* ═══════════ PACKAGE TESTS ═══════════ */

    @Nested
    @DisplayName("Package Validation")
    class PackageTests {

        @Test
        @DisplayName("Valid package creation succeeds")
        void validPackageCreation() throws Exception {
            packageMvc.perform(post("/api/packages")
                            .contentType("application/json")
                            .content("""
                                    {"packageCode":"PKG-001","recipientName":"Rahul Kumar","recipientPhone":"9876543210","recipientEmail":"rahul@mail.com","deliveryAddress":"123 Main St","status":"REGISTERED","storageFee":0}
                                    """))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Blank package code rejected")
        void blankPackageCode() throws Exception {
            packageMvc.perform(post("/api/packages")
                            .contentType("application/json")
                            .content("""
                                    {"packageCode":"","recipientName":"Rahul","status":"REGISTERED","storageFee":0}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Numbers-only recipient name rejected")
        void numbersOnlyRecipientName() throws Exception {
            packageMvc.perform(post("/api/packages")
                            .contentType("application/json")
                            .content("""
                                    {"packageCode":"PKG001","recipientName":"123456","status":"REGISTERED","storageFee":0}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Invalid phone number rejected")
        void invalidRecipientPhone() throws Exception {
            packageMvc.perform(post("/api/packages")
                            .contentType("application/json")
                            .content("""
                                    {"packageCode":"PKG001","recipientName":"Rahul","recipientPhone":"abcdef","status":"REGISTERED","storageFee":0}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Invalid status rejected")
        void invalidStatus() throws Exception {
            packageMvc.perform(post("/api/packages")
                            .contentType("application/json")
                            .content("""
                                    {"packageCode":"PKG001","recipientName":"Rahul","status":"INVALID_STATUS","storageFee":0}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Negative storage fee rejected")
        void negativeStorageFee() throws Exception {
            packageMvc.perform(post("/api/packages")
                            .contentType("application/json")
                            .content("""
                                    {"packageCode":"PKG001","recipientName":"Rahul","status":"REGISTERED","storageFee":-10}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Pickup with blank passcode rejected")
        void blankPickupPasscode() throws Exception {
            packageMvc.perform(post("/api/packages/PKG001/pickup")
                            .contentType("application/json")
                            .content("""
                                    {"passcode":"  "}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    /* ═══════════ DRONE LANDING TESTS ═══════════ */

    @Nested
    @DisplayName("Drone Landing Validation")
    class DroneLandingTests {

        @Test
        @DisplayName("Missing drone code rejected")
        void missingDroneCode() throws Exception {
            landingMvc.perform(post("/api/lockers/landings/process")
                            .contentType("application/json")
                            .content("""
                                    {"droneCode":"","packageCode":"PKG001","lockerPadId":1}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Missing package code rejected")
        void missingPackageCode() throws Exception {
            landingMvc.perform(post("/api/lockers/landings/process")
                            .contentType("application/json")
                            .content("""
                                    {"droneCode":"D-01","packageCode":"","lockerPadId":1}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Missing locker pad ID rejected")
        void missingLockerPadId() throws Exception {
            landingMvc.perform(post("/api/lockers/landings/process")
                            .contentType("application/json")
                            .content("""
                                    {"droneCode":"D-01","packageCode":"PKG001"}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Decimal locker pad ID rejected")
        void decimalLockerPadId() throws Exception {
            landingMvc.perform(post("/api/lockers/landings/process")
                            .contentType("application/json")
                            .content("""
                                    {"droneCode":"D-01","packageCode":"PKG001","lockerPadId":1.5}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Special chars in drone code rejected")
        void specialCharsDroneCode() throws Exception {
            landingMvc.perform(post("/api/lockers/landings/process")
                            .contentType("application/json")
                            .content("""
                                    {"droneCode":"D@#$","packageCode":"PKG001","lockerPadId":1}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    /* ═══════════ INVOICE TESTS ═══════════ */

    @Nested
    @DisplayName("Invoice Validation")
    class InvoiceTests {

        @Test
        @DisplayName("Valid invoice creation succeeds")
        void validInvoice() throws Exception {
            invoiceMvc.perform(post("/api/invoices")
                            .contentType("application/json")
                            .content("""
                                    {"invoiceNumber":"INV-001","customerName":"Rahul Kumar","landingFee":250,"storageFee":100,"status":"UNPAID"}
                                    """))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Blank invoice number rejected")
        void blankInvoiceNumber() throws Exception {
            invoiceMvc.perform(post("/api/invoices")
                            .contentType("application/json")
                            .content("""
                                    {"invoiceNumber":"","customerName":"Rahul","landingFee":250,"storageFee":100}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Negative landing fee rejected")
        void negativeLandingFee() throws Exception {
            invoiceMvc.perform(post("/api/invoices")
                            .contentType("application/json")
                            .content("""
                                    {"invoiceNumber":"INV001","customerName":"Rahul","landingFee":-50,"storageFee":100}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Numbers-only customer name rejected")
        void numbersOnlyCustomerName() throws Exception {
            invoiceMvc.perform(post("/api/invoices")
                            .contentType("application/json")
                            .content("""
                                    {"invoiceNumber":"INV001","customerName":"12345","landingFee":250,"storageFee":100}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    /* ═══════════ PAYMENT TESTS ═══════════ */

    @Nested
    @DisplayName("Payment Validation")
    class PaymentTests {

        @Test
        @DisplayName("Valid payment creation succeeds")
        void validPayment() throws Exception {
            paymentMvc.perform(post("/api/payments")
                            .contentType("application/json")
                            .content("""
                                    {"paymentReference":"PAY-001","invoiceId":1,"amount":500,"paymentMethod":"UPI","status":"SUCCESS"}
                                    """))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Blank payment reference rejected")
        void blankPaymentReference() throws Exception {
            paymentMvc.perform(post("/api/payments")
                            .contentType("application/json")
                            .content("""
                                    {"paymentReference":"","invoiceId":1,"amount":500,"paymentMethod":"UPI","status":"SUCCESS"}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Invalid payment method rejected")
        void invalidPaymentMethod() throws Exception {
            paymentMvc.perform(post("/api/payments")
                            .contentType("application/json")
                            .content("""
                                    {"paymentReference":"PAY001","invoiceId":1,"amount":500,"paymentMethod":"BITCOIN","status":"SUCCESS"}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Missing invoice ID rejected")
        void missingInvoiceId() throws Exception {
            paymentMvc.perform(post("/api/payments")
                            .contentType("application/json")
                            .content("""
                                    {"paymentReference":"PAY001","amount":500,"paymentMethod":"UPI","status":"SUCCESS"}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Negative amount rejected")
        void negativeAmount() throws Exception {
            paymentMvc.perform(post("/api/payments")
                            .contentType("application/json")
                            .content("""
                                    {"paymentReference":"PAY001","invoiceId":1,"amount":-100,"paymentMethod":"UPI","status":"SUCCESS"}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    /* ═══════════ VENDOR TESTS ═══════════ */

    @Nested
    @DisplayName("Vendor Validation")
    class VendorTests {

        @Test
        @DisplayName("Valid vendor creation succeeds")
        void validVendor() throws Exception {
            vendorMvc.perform(post("/api/vendors")
                            .contentType("application/json")
                            .content("""
                                    {"vendorCode":"V-001","vendorName":"Tech Parts","contactPerson":"Suresh","email":"suresh@parts.com","phone":"9123456789","active":true}
                                    """))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Blank vendor code rejected")
        void blankVendorCode() throws Exception {
            vendorMvc.perform(post("/api/vendors")
                            .contentType("application/json")
                            .content("""
                                    {"vendorCode":"","vendorName":"Tech Parts","active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Numbers-only vendor name rejected")
        void numbersOnlyVendorName() throws Exception {
            vendorMvc.perform(post("/api/vendors")
                            .contentType("application/json")
                            .content("""
                                    {"vendorCode":"V001","vendorName":"12345","active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Invalid phone rejected")
        void invalidPhone() throws Exception {
            vendorMvc.perform(post("/api/vendors")
                            .contentType("application/json")
                            .content("""
                                    {"vendorCode":"V001","vendorName":"Tech Parts","phone":"abc","active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    /* ═══════════ PURCHASE ORDER TESTS ═══════════ */

    @Nested
    @DisplayName("Purchase Order Validation")
    class PurchaseOrderTests {

        @Test
        @DisplayName("Valid purchase order creation succeeds")
        void validPO() throws Exception {
            purchaseOrderMvc.perform(post("/api/purchase-orders")
                            .contentType("application/json")
                            .content("""
                                    {"purchaseOrderNumber":"PO-001","vendorId":1,"itemName":"Locker Door","quantity":5,"unitPrice":1500,"totalAmount":7500,"status":"CREATED"}
                                    """))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Blank PO number rejected")
        void blankPONumber() throws Exception {
            purchaseOrderMvc.perform(post("/api/purchase-orders")
                            .contentType("application/json")
                            .content("""
                                    {"purchaseOrderNumber":"","vendorId":1,"itemName":"Locker Door","quantity":5,"unitPrice":1500}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Zero quantity rejected")
        void zeroQuantity() throws Exception {
            purchaseOrderMvc.perform(post("/api/purchase-orders")
                            .contentType("application/json")
                            .content("""
                                    {"purchaseOrderNumber":"PO001","vendorId":1,"itemName":"Locker Door","quantity":0,"unitPrice":1500}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Negative unit price rejected")
        void negativeUnitPrice() throws Exception {
            purchaseOrderMvc.perform(post("/api/purchase-orders")
                            .contentType("application/json")
                            .content("""
                                    {"purchaseOrderNumber":"PO001","vendorId":1,"itemName":"Locker Door","quantity":5,"unitPrice":-100}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    /* ═══════════ LEDGER ENTRY TESTS ═══════════ */

    @Nested
    @DisplayName("Ledger Entry Validation")
    class LedgerEntryTests {

        @Test
        @DisplayName("Valid ledger entry creation succeeds")
        void validLedgerEntry() throws Exception {
            ledgerMvc.perform(post("/api/ledger")
                            .contentType("application/json")
                            .content("""
                                    {"transactionReference":"TXN-001","accountName":"Landing Fee Revenue","accountType":"INCOME","debit":0,"credit":250,"description":"Landing fee"}
                                    """))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Invalid account type rejected")
        void invalidAccountType() throws Exception {
            ledgerMvc.perform(post("/api/ledger")
                            .contentType("application/json")
                            .content("""
                                    {"transactionReference":"TXN-001","accountName":"Revenue","accountType":"INVALID","debit":0,"credit":250}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Negative debit rejected")
        void negativeDebit() throws Exception {
            ledgerMvc.perform(post("/api/ledger")
                            .contentType("application/json")
                            .content("""
                                    {"transactionReference":"TXN-001","accountName":"Expense","accountType":"EXPENSE","debit":-100,"credit":0}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Blank transaction reference rejected")
        void blankTransactionReference() throws Exception {
            ledgerMvc.perform(post("/api/ledger")
                            .contentType("application/json")
                            .content("""
                                    {"transactionReference":"","accountName":"Revenue","accountType":"INCOME","debit":0,"credit":250}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }

    /* ═══════════ MALFORMED JSON TESTS ═══════════ */

    @Nested
    @DisplayName("Malformed Request Handling")
    class MalformedRequestTests {

        @Test
        @DisplayName("Malformed JSON returns clean 400")
        void malformedJson() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("{\"padCode\":"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false))
                    .andExpect(jsonPath("$.message").value("Invalid request data."));
        }

        @Test
        @DisplayName("Wrong data type in JSON returns clean error")
        void wrongDataType() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"LP001","location":"Coimbatore","totalBays":"not-a-number","availableBays":0,"landingTariff":100,"active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Decimal in integer field returns error")
        void decimalInIntegerField() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content("""
                                    {"padCode":"LP001","location":"Coimbatore","totalBays":1.5,"availableBays":0,"landingTariff":100,"active":true}
                                    """))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }

        @Test
        @DisplayName("Empty request body returns error")
        void emptyBody() throws Exception {
            padMvc.perform(post("/api/lockers/pads")
                            .contentType("application/json")
                            .content(""))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.success").value(false));
        }
    }
}
