package plasSB;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import plasSB.dao.CapacityRepository;
import plasSB.entity.CapacityRecord;

@Configuration
public class DataInitializer {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Bean
    CommandLineRunner initPlasSBData(CapacityRepository capacityRepository) {
        return args -> {
            logger.info("--- Inicializando datos de capacidad en PlasSB ---");
            
            // Crear registros de capacidad para varios días
            CapacityRecord today = new CapacityRecord(LocalDate.now(), 1000);
            CapacityRecord tomorrow = new CapacityRecord(LocalDate.now().plusDays(1), 950);
            CapacityRecord dayAfter = new CapacityRecord(LocalDate.now().plusDays(2), 800);
            
            // Agregar capacidad para la fecha que estás probando (2025-11-23)
            CapacityRecord testDate = new CapacityRecord(LocalDate.of(2025, 11, 23), 1000);
            
            // Guardar en la base de datos
            capacityRepository.save(today);
            capacityRepository.save(tomorrow);
            capacityRepository.save(dayAfter);
            capacityRepository.save(testDate);
            
            LocalDate today_date = LocalDate.now();
            if (!capacityRepository.findByDate(today_date).isPresent()) {
                capacityRepository.save(new CapacityRecord(today_date, 1000));
            }

            
            logger.info("Capacidad guardada para:");
            logger.info("  - " + LocalDate.now() + ": 1000 tons");
            logger.info("  - " + LocalDate.now().plusDays(1) + ": 950 tons");
            logger.info("  - " + LocalDate.now().plusDays(2) + ": 800 tons");
            logger.info("  - 2025-11-23: 1000 tons");
            logger.info("--- Inicialización de PlasSB completada ---");
        };
    }
}