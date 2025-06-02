package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.SensorDataRequest;
import com.bridgelabz.EAMS.dto.sensor.SensorDataResponse;
import com.bridgelabz.EAMS.entity.Asset;
import com.bridgelabz.EAMS.entity.SensorData;
import com.bridgelabz.EAMS.repository.AssetRepository;
import com.bridgelabz.EAMS.repository.SensorDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SensorServiceImplTest {

    @InjectMocks
    private SensorServiceImpl sensorService;

    @Mock
    private SensorDataRepository sensorDataRepository;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private IAlertService alertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSensorData_ShouldSaveAndTriggerAlert() {
        SensorDataRequest request = new SensorDataRequest();
        request.setAssetId(1L);
        request.setTemperature(80.0);
        request.setPressure(120.0);

        Asset asset = new Asset();
        asset.setId(1L);

        SensorData savedData = new SensorData();
        savedData.setAsset(asset);
        savedData.setTemperature(80.0);
        savedData.setPressure(120.0);
        savedData.setTimestamp(LocalDateTime.now());

        when(assetRepository.findById(1L)).thenReturn(Optional.of(asset));
        when(sensorDataRepository.save(any(SensorData.class))).thenReturn(savedData);

        SensorDataResponse response = sensorService.saveSensorData(request);

        assertEquals(80.0, response.getTemperature());
        assertEquals(120.0, response.getPressure());
        assertEquals(1L, response.getAssetId());
        assertNotNull(response.getTimestamp());

        verify(alertService, times(1)).checkAndCreateAlert(request);
        verify(sensorDataRepository, times(1)).save(any(SensorData.class));
    }

    @Test
    void testGetSensorDataByAssetId_ShouldReturnList() {
        Long assetId = 1L;
        Asset asset = new Asset();
        asset.setId(assetId);

        SensorData data = new SensorData();
        data.setAsset(asset);
        data.setTemperature(60.0);
        data.setPressure(90.0);
        data.setTimestamp(LocalDateTime.now());

        when(sensorDataRepository.findByAssetId(assetId)).thenReturn(List.of(data));

        List<SensorDataResponse> responses = sensorService.getSensorDataByAssetId(assetId);

        assertEquals(1, responses.size());
        SensorDataResponse res = responses.get(0);
        assertEquals(60.0, res.getTemperature());
        assertEquals(90.0, res.getPressure());
        assertEquals(assetId, res.getAssetId());
    }

    @Test
    void testSaveSensorData_WhenAssetNotFound_ShouldThrowException() {
        SensorDataRequest request = new SensorDataRequest();
        request.setAssetId(999L);
        when(assetRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sensorService.saveSensorData(request);
        });

        assertEquals("Asset not found", exception.getMessage());
    }
}
