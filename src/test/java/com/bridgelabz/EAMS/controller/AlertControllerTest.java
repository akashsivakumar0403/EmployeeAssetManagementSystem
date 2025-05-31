//package com.bridgelabz.EAMS.controller;
//
//import com.bridgelabz.EAMS.dto.AlertResponse;
//import com.bridgelabz.EAMS.entity.AlertStatus;
//import com.bridgelabz.EAMS.entity.AlertType;
//import com.bridgelabz.EAMS.service.AlertService;
//import com.bridgelabz.EAMS.service.MailService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class AlertControllerTest {
//
//    @InjectMocks
//    private AlertController alertController;
//
//    @Mock
//    private AlertService alertService;
//
//    @Mock
//    private MailService mailService;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(alertController).build();
//    }
//
//    @Test
//    void getActiveAlerts_success() throws Exception {
//        AlertResponse alert1 = createMockAlert(1L);
//        AlertResponse alert2 = createMockAlert(2L);
//
//        when(alertService.getActiveAlerts()).thenReturn(Arrays.asList(alert1, alert2));
//
//        mockMvc.perform(get("/api/alerts/active"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2));
//
//        verify(alertService).getActiveAlerts();
//    }
//
//    @Test
//    void getAllAlerts_success() throws Exception {
//        AlertResponse alert1 = createMockAlert(1L);
//        AlertResponse alert2 = createMockAlert(2L);
//
//        when(alertService.getAllAlerts()).thenReturn(Arrays.asList(alert1, alert2));
//
//        mockMvc.perform(get("/api/alerts/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2));
//
//        verify(alertService).getAllAlerts();
//    }
//
//    @Test
//    void resolveAlert_success() throws Exception {
//        Long alertId = 1L;
//
//        mockMvc.perform(put("/api/alerts/resolve/{id}", alertId))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Alert resolved."));
//
//        verify(alertService).resolveAlert(alertId);
//    }
//
//    @Test
//    void sendMail_success() throws Exception {
//        doNothing().when(mailService).sendMail(anyString(), anyString(), anyString());
//
//        mockMvc.perform(get("/api/alerts/send-mail"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Mail Sent!"));
//
//        verify(mailService).sendMail(anyString(), anyString(), anyString());
//    }
//
//    private AlertResponse createMockAlert(Long id) {
//        AlertResponse response = new AlertResponse();
//        response.setId(id);
//        response.setAssetId(101L);
//        response.setType(AlertType.TEMP_HIGH);
//        response.setMessage("Temp too high");
//        response.setStatus(AlertStatus.ACTIVE);
//        response.setTriggeredAt(LocalDateTime.now());
//        return response;
//    }
//}
