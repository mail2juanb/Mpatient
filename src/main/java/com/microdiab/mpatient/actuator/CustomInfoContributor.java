package com.microdiab.mpatient.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomInfoContributor implements InfoContributor {

//    @Override
//    public void contribute(Info.Builder builder) {
//        Map<String, Object> infoMap = new HashMap<>();
//        infoMap.put("app", Map.of(
//                "version", "Version en cours de développement",
//                "description", "MicroDiab, application d'analyse du diabète des patients",
//                "documentation", "Lien vers la documentation de l'application",
//                "information", "Informations utiles"
//        ));
//        builder.withDetails(infoMap);
//    }

    private final Environment environment;

    public CustomInfoContributor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("version", environment.getProperty("info.app.version", "Version non définie"));
        appInfo.put("description", environment.getProperty("info.app.description", "Description non définie"));
        appInfo.put("documentation", environment.getProperty("info.app.documentation", "Documentation non définie"));
        appInfo.put("information", environment.getProperty("info.app.information", "Informations non définies"));

        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("app", appInfo);

        builder.withDetails(infoMap);
    }
}
