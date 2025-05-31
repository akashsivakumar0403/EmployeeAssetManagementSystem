package com.bridgelabz.EAMS.controller;

import com.bridgelabz.EAMS.dto.AssetDTO;
import com.bridgelabz.EAMS.service.AssetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssetController.class)
class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssetService assetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAsset() throws Exception {
        AssetDTO dto = new AssetDTO();
        dto.setName("Sensor A");
        dto.setType("Temperature");
        dto.setLocation("Plant 1");
        dto.setThresholdTemp(60.0);
        dto.setThresholdPressure(1.2);
        dto.setAssignedToUserId(1L);

        Mockito.when(assetService.createAsset(any(AssetDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sensor A"));
    }

    @Test
    void testGetAllAssets() throws Exception {
        AssetDTO dto1 = new AssetDTO();
        dto1.setId(1L);
        dto1.setName("Sensor A");

        AssetDTO dto2 = new AssetDTO();
        dto2.setId(2L);
        dto2.setName("Sensor B");

        List<AssetDTO> list = Arrays.asList(dto1, dto2);

        Mockito.when(assetService.getAllAssets()).thenReturn(list);

        mockMvc.perform(get("/api/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Sensor A"))
                .andExpect(jsonPath("$[1].name").value("Sensor B"));
    }

    @Test
    void testUpdateAsset() throws Exception {
        AssetDTO dto = new AssetDTO();
        dto.setId(1L);
        dto.setName("Updated Asset");

        Mockito.when(assetService.updateAsset(Mockito.eq(1L), any(AssetDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/api/assets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Asset"));
    }

    @Test
    void testDeleteAsset() throws Exception {
        mockMvc.perform(delete("/api/assets/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(assetService).deleteAsset(1L);
    }
    
    @Test
    void testCreateAsset_userNotFound_shouldReturn500() throws Exception {
        AssetDTO dto = new AssetDTO();
        dto.setName("Sensor X");
        dto.setAssignedToUserId(999L);

        Mockito.when(assetService.createAsset(any(AssetDTO.class)))
               .thenThrow(new RuntimeException("User not found with id 999"));

        mockMvc.perform(post("/api/assets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("User not found with id 999")));
    }

    @Test
    void testUpdateAsset_assetNotFound_shouldReturn500() throws Exception {
        AssetDTO dto = new AssetDTO();
        dto.setName("Updated Asset");

        Mockito.when(assetService.updateAsset(Mockito.eq(404L), any(AssetDTO.class)))
               .thenThrow(new RuntimeException("Asset not found with id 404"));

        mockMvc.perform(put("/api/assets/404")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Asset not found with id 404")));
    }
    
    
}
