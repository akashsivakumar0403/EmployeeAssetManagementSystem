package com.bridgelabz.EAMS.service;

import com.bridgelabz.EAMS.dto.AssetDTO;
import com.bridgelabz.EAMS.entity.Asset;
import com.bridgelabz.EAMS.entity.User;
import com.bridgelabz.EAMS.repository.AssetRepository;
import com.bridgelabz.EAMS.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetServiceImplTest {

    @InjectMocks
    private AssetServiceImpl assetService;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAsset_success() {
        AssetDTO dto = new AssetDTO();
        dto.setName("Pump");
        dto.setType("Mechanical");
        dto.setLocation("Floor 1");
        dto.setThresholdTemp(100.0);
        dto.setThresholdPressure(10.0);
        dto.setAssignedToUserId(1L);

        User user = new User();
        user.setId(1L);

        Asset savedAsset = new Asset();
        savedAsset.setId(10L);
        savedAsset.setName("Pump");
        savedAsset.setType("Mechanical");
        savedAsset.setLocation("Floor 1");
        savedAsset.setThresholdTemp(100.0);
        savedAsset.setThresholdPressure(10.0);
        savedAsset.setAssignedTo(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(assetRepository.save(any(Asset.class))).thenReturn(savedAsset);

        AssetDTO result = assetService.createAsset(dto);

        assertEquals("Pump", result.getName());
        assertEquals("Mechanical", result.getType());
        assertEquals(1L, result.getAssignedToUserId());
        assertEquals(10L, result.getId());

        verify(userRepository).findById(1L);
        verify(assetRepository).save(any(Asset.class));
    }

    @Test
    void updateAsset_success() {
        Long id = 10L;
        AssetDTO dto = new AssetDTO();
        dto.setName("Updated Pump");
        dto.setType("Hydraulic");
        dto.setLocation("Floor 2");
        dto.setThresholdTemp(120.0);
        dto.setThresholdPressure(12.0);
        dto.setAssignedToUserId(1L);

        User user = new User();
        user.setId(1L);

        Asset existing = new Asset();
        existing.setId(id);

        when(assetRepository.findById(id)).thenReturn(Optional.of(existing));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(assetRepository.save(any(Asset.class))).thenReturn(existing);

        AssetDTO result = assetService.updateAsset(id, dto);

        assertEquals("Updated Pump", result.getName());
        assertEquals("Hydraulic", result.getType());
        assertEquals("Floor 2", result.getLocation());
        assertEquals(1L, result.getAssignedToUserId());

        verify(assetRepository).findById(id);
        verify(userRepository).findById(1L);
        verify(assetRepository).save(any(Asset.class));
    }

    @Test
    void getAllAssets_success() {
        Asset a1 = new Asset();
        a1.setId(1L);
        a1.setName("Pump");
        a1.setType("Mechanical");

        Asset a2 = new Asset();
        a2.setId(2L);
        a2.setName("Motor");
        a2.setType("Electrical");

        when(assetRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<AssetDTO> result = assetService.getAllAssets();

        assertEquals(2, result.size());
        assertEquals("Pump", result.get(0).getName());
        assertEquals("Motor", result.get(1).getName());

        verify(assetRepository).findAll();
    }

    @Test
    void deleteAsset_success() {
        Long assetId = 1L;

        assetService.deleteAsset(assetId);

        verify(assetRepository).deleteById(assetId);
    }
    
    @Test
    void createAsset_userNotFound_shouldThrowException() {
        AssetDTO dto = new AssetDTO();
        dto.setName("Pump");
        dto.setAssignedToUserId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> assetService.createAsset(dto));
        assertEquals("User not found with id 99", ex.getMessage());

        verify(userRepository).findById(99L);
        verify(assetRepository, never()).save(any());
    }

    @Test
    void updateAsset_assetNotFound_shouldThrowException() {
        Long id = 100L;
        AssetDTO dto = new AssetDTO();
        dto.setAssignedToUserId(1L);

        when(assetRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> assetService.updateAsset(id, dto));
        assertEquals("Asset not found with id 100", ex.getMessage());

        verify(assetRepository).findById(id);
        verify(userRepository, never()).findById(any());
        verify(assetRepository, never()).save(any());
    }

    @Test
    void updateAsset_userNotFound_shouldThrowException() {
        Long id = 10L;
        AssetDTO dto = new AssetDTO();
        dto.setAssignedToUserId(88L);

        Asset existing = new Asset();
        existing.setId(id);

        when(assetRepository.findById(id)).thenReturn(Optional.of(existing));
        when(userRepository.findById(88L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> assetService.updateAsset(id, dto));
        assertEquals("User not found with id 88", ex.getMessage());

        verify(assetRepository).findById(id);
        verify(userRepository).findById(88L);
        verify(assetRepository, never()).save(any());
    }

}
