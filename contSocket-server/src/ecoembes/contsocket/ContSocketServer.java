package ecoembes.contsocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ContSocketServer {

    // Capacidad en memoria (simulación)
    private static final Map<LocalDate, Integer> capacityStore = new ConcurrentHashMap<>();
    private static final int PORT = 6000; // Puerto de conexión

    public static void main(String[] args) {
        
        // 1. Inicializar datos de capacidad en memoria (simulación de DB)
        capacityStore.put(LocalDate.now(), 500);
        capacityStore.put(LocalDate.now().plusDays(1), 450);
        System.out.println("ContSocket Server started on port " + PORT);
        System.out.println("Capacity initialized.");
        
        // 2. Abrir el ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            
            // 3. Bucle de escucha infinita (esperando conexiones)
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Bloquea hasta que llega una conexión
                
                // 4. Delegar el manejo de la conexión a un hilo o una clase Handler
                System.out.println("ContSocket: Client connected from " + clientSocket.getInetAddress());
                new SocketHandler(clientSocket, capacityStore).run();
            }
        } catch (IOException e) {
            System.err.println("ContSocket Server error: " + e.getMessage());
        }
    }
    
    public static Map<LocalDate, Integer> getCapacityStore() {
        return capacityStore;
    }
    
    
}