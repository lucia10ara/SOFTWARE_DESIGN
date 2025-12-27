package ecoembes.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ecoembes.entity.Assignment;
import ecoembes.entity.RecyclingPlant;

@Component
public class ContSocketGateway implements IRecyclingPlantGateway {
    
    @Value("${socket.messages.get-capacity:GET_CAPACITY}")
    private String getCapacityCommand;
    
    @Value("${socket.messages.assign-dumpster:ASSIGN_DUMPSTER}")
    private String assignDumpsterCommand;
    
    @Value("${socket.response.ok:OK}")
    private String okResponse;
    
    @Override
    public int getCapacity(RecyclingPlant plant, String date) {
        String ip = plant.getIp(); 
        int port = plant.getPort();
        String message = getCapacityCommand + ":" + date;
        
        try (Socket socket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println(message); 
            String response = in.readLine(); 
            return Integer.parseInt(response.trim()); 
            
        } catch (IOException e) {
            System.err.println("Communication error via Sockets with ContSocket: " + e.getMessage());
            return 0;
        } catch (NumberFormatException e) {
            System.err.println("Invalid response from ContSocket Server.");
            return 0;
        }
    }
    
    @Override
    public boolean notifyAssignment(RecyclingPlant plant, Assignment assignment) {
        String ip = plant.getIp();
        int port = plant.getPort();
        String message = assignDumpsterCommand + ":" + assignment.getDumpster().getDumpster_id() +
                         "," + assignment.getDate().toString();
        
        try (Socket socket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println(message);
            String response = in.readLine();
            boolean success = okResponse.equalsIgnoreCase(response.trim());
            
            if(success) {
                System.out.println("✓ ContSocket Plant notified for assignment: Dumpster " + assignment.getDumpster().getDumpster_id());
            } else {
                System.err.println("ContSocket Plant did not acknowledge assignment");
            }
            return success;
        } catch (IOException e) {
            System.err.println("Failed to notify ContSocket Plant: " + e.getMessage());
            return false;
        }
    }
}