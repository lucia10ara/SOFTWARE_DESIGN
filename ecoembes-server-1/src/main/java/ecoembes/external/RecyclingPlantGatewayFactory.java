package ecoembes.external;

import ecoembes.entity.RecyclingPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecyclingPlantGatewayFactory {

    @Autowired
    private PlasSBGateway plasSBGateway;

    @Autowired
    private ContSocketGateway contSocketGateway;

    public IRecyclingPlantGateway createGateway(RecyclingPlant plant) {
        
        if (plant.getUrl() != null && !plant.getUrl().isEmpty()) {
            //usa URL --> REST para el PlasSB
            return plasSBGateway;
            
        } else if (plant.getIp() != null && plant.getPort() > 0) {
            // usa la IP y el puerto --> Sockets para ContSocket
            return contSocketGateway;
            
        } else {
            throw new IllegalArgumentException(
                "Protocolo de comunicación no definido para la planta: " + plant.getRp_id()
            );
        }
    }
}