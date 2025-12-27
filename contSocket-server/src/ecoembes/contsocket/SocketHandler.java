package ecoembes.contsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class SocketHandler implements Runnable {

    private final Socket clientSocket;
    private final Map<LocalDate, Integer> capacityStore;

    
    public SocketHandler(Socket clientSocket, Map<LocalDate, Integer> capacityStore) {
        this.clientSocket = clientSocket;
        this.capacityStore = capacityStore;
    }

    @Override
    public void run() {
        try (
            // Configurar streams de entrada y salida
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;
            
            // Leer la solicitud del Servidor Central (Ecoembes Server)
            if ((inputLine = in.readLine()) != null) {
                System.out.println("ContSocket: Received command: " + inputLine);
                
                // Procesar el comando
                String response = processCommand(inputLine);
                
                // Enviar la respuesta
                out.println(response);
            }
        } catch (IOException e) {
            System.err.println("ContSocket Handler error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                 // No hacer nada
            }
        }
    }

    private String processCommand(String command) {
        String[] parts = command.split(":");
        String action = parts[0];
        
        switch (action) {
            
            case "GET_CAPACITY": //funcion 1(comando)
                if (parts.length > 1) {
                    try {
                        LocalDate date = LocalDate.parse(parts[1]);
                        // Buscar la capacidad en el mapa en memoria
                        int capacity = capacityStore.getOrDefault(date, 0); 
                        return String.valueOf(capacity);
                    } catch (Exception e) {
                        return "0"; // Error de formato de fecha
                    }
                }
                return "0"; // Formato incorrecto
                
            case "ASSIGN_DUMPSTER": //comando 1 (comando)
                // Llama al método para procesar la asignación (Requerido por el Gateway)
                if (parts.length > 1) {
                    return processAssignment(parts[1]); 
                }
                return "ERROR_MISSING_DATA";

            default:
                return "ERROR_UNKNOWN_COMMAND";
        }
    }
    
    /**
     * Procesa el comando ASSIGN_DUMPSTER. 
     * Actualiza la capacidad disponible en el capacityStore.
     * Espera el formato: <dumpsterId>,<date>
     */
    private String processAssignment(String data) {
        int loadInTons = 1; // Asumimos una carga estándar de 1 tonelada por asignación
        
        try {
            String[] assignmentData = data.split(",");
            
            if (assignmentData.length != 2) {
                return "ERROR_INVALID_DATA_FORMAT";
            }
            
            // 1. Parsear los datos
            int dumpsterId = Integer.parseInt(assignmentData[0].trim());
            LocalDate date = LocalDate.parse(assignmentData[1].trim());

            // 2. OBTENER Y ACTUALIZAR CAPACIDAD DISPONIBLE
            
            // Obtenemos la capacidad actual para esa fecha. 
            // ¡Importante! capacityStore ahora almacena la capacidad disponible restante.
            int currentAvailableCapacity = capacityStore.getOrDefault(date, 0); 

            if (currentAvailableCapacity < loadInTons) {
                System.err.println("❌ ContSocket: RECHAZADO. Capacidad insuficiente para " + date);
                // El Gateway espera "OK" o algo diferente a "OK".
                // Para indicar rechazo por capacidad, devolvemos un error que el Gateway no interpreta como éxito.
                return "REJECTED_CAPACITY_FULL"; 
            }

            // 3. ACTUALIZAR (Restar la carga)
            int newAvailableCapacity = currentAvailableCapacity - loadInTons;
            capacityStore.put(date, newAvailableCapacity);

            // 4. Confirmación
            System.out.println("✅ ContSocket: Asignación aceptada y capacidad actualizada.");
            System.out.println("   Dumpster ID: " + dumpsterId + ", Fecha: " + date);
            System.out.println("   Capacidad restante: " + newAvailableCapacity);
            
            return "OK"; // El Gateway espera "OK" para éxito.
            
        } catch (Exception e) {
            System.err.println("❌ ContSocket Error processing ASSIGNMENT: " + e.getMessage());
            return "ERROR_INVALID_DATA";
        }
    }
    

	@Override
	public int hashCode() {
		return Objects.hash(capacityStore, clientSocket);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SocketHandler other = (SocketHandler) obj;
		return Objects.equals(capacityStore, other.capacityStore) && Objects.equals(clientSocket, other.clientSocket);
	}
    
}