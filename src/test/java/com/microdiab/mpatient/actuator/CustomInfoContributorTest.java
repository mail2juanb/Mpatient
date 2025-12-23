package com.microdiab.mpatient.actuator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.info.Info;
import org.springframework.core.env.Environment;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomInfoContributorTest {

    @Mock
    private Environment environment;

    @InjectMocks
    private CustomInfoContributor customInfoContributor;

    @Test
    void contribute_ShouldAddAppInfoToBuilder() {
        // Arrange
        when(environment.getProperty("info.app.version", "mpatient - Version non définie")).thenReturn("1.0.0");
        when(environment.getProperty("info.app.description", "mpatient - Description non définie")).thenReturn("Gestion des patients");
        when(environment.getProperty("info.app.documentation", "mpatient - Documentation non définie")).thenReturn("https://docs.microdiab.com/mpatient");
        when(environment.getProperty("info.app.information", "mpatient - Informations non définies")).thenReturn("Microservice de gestion des patients");

        Info.Builder builder = new Info.Builder();

        // Act
        customInfoContributor.contribute(builder);

        // Assert
        Info info = builder.build();
        Map<String, Object> details = info.getDetails();

        assertNotNull(details);
        assertTrue(details.containsKey("app"));

        @SuppressWarnings("unchecked")
        Map<String, Object> appInfo = (Map<String, Object>) details.get("app");

        assertEquals("1.0.0", appInfo.get("version"));
        assertEquals("Gestion des patients", appInfo.get("description"));
        assertEquals("https://docs.microdiab.com/mpatient", appInfo.get("documentation"));
        assertEquals("Microservice de gestion des patients", appInfo.get("information"));
        assertNotNull(appInfo.get("lastUpdated"));
    }

    @Test
    void contribute_ShouldUseDefaultValuesIfPropertiesAreMissing() {
        // Arrange
        when(environment.getProperty("info.app.version", "mpatient - Version non définie")).thenReturn("mpatient - Version non définie");
        when(environment.getProperty("info.app.description", "mpatient - Description non définie")).thenReturn("mpatient - Description non définie");
        when(environment.getProperty("info.app.documentation", "mpatient - Documentation non définie")).thenReturn("mpatient - Documentation non définie");
        when(environment.getProperty("info.app.information", "mpatient - Informations non définies")).thenReturn("mpatient - Informations non définies");

        Info.Builder builder = new Info.Builder();

        // Act
        customInfoContributor.contribute(builder);

        // Assert
        Info info = builder.build();
        Map<String, Object> details = info.getDetails();

        assertNotNull(details);
        assertTrue(details.containsKey("app"));

        @SuppressWarnings("unchecked")
        Map<String, Object> appInfo = (Map<String, Object>) details.get("app");

        assertEquals("mpatient - Version non définie", appInfo.get("version"));
        assertEquals("mpatient - Description non définie", appInfo.get("description"));
        assertEquals("mpatient - Documentation non définie", appInfo.get("documentation"));
        assertEquals("mpatient - Informations non définies", appInfo.get("information"));
        assertNotNull(appInfo.get("lastUpdated"));
    }
}