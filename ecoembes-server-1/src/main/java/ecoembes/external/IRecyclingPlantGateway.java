	package ecoembes.external;

import ecoembes.entity.Assignment;
import ecoembes.entity.RecyclingPlant;
import ecoembes.dto.*;

//la interfaz define lacomunicación con cualquier recycling plant externa
public interface IRecyclingPlantGateway {

    /**
     * Consulta la capacidad disponible de la planta de reciclaje para una fecha específica.
     * @param plant La planta de reciclaje de la que se requiere la capacidad (contiene la URL/IP).
     * @param date La fecha para la cual se consulta la capacidad (ej: "2025-01-01").
     * @return La capacidad disponible en toneladas (int).
     */
    int getCapacity(RecyclingPlant plant, String date);

    boolean notifyAssignment(RecyclingPlant plant, Assignment assignment);

}