/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
/**
 *
 * @author jthha
 */
public class HostServer {

    private static DatagramSocket socket;
    private static HashSet<SocketAddress> group;
    private static InetAddress addr;
    private static boolean firstTime;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        run();
    }
    public static void run(){
        try{
            firstTime = true;
        
            group = new HashSet<>();
            int port = 4000;
            socket = new DatagramSocket(port);
        
            addr = InetAddress.getLocalHost();
            String hostName = addr.getHostName();
            String hostAddress = addr.getHostAddress();
            for(byte b : addr.getAddress())
                System.out.print(b + " ");
            System.out.println();
            
            System.out.println("IP Address = " + hostAddress);
            System.out.println("Port# = " + port);
            System.out.println("Host name: " + hostName);
            
                    
        
            System.out.println("\nClient Activity\n");
       
            while(true){
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
                socket.receive(packet);
                firstTime = group.add(packet.getSocketAddress());
                System.out.println("Receiving-->" + packet.getSocketAddress());
                for(SocketAddress s: group){
                    if(firstTime){
                        String greeting = "Hi all";
                        //DatagramPacket gr = new DatagramPacket(greeting.getBytes(),greeting.getBytes().length,s);
                        //socket.send(gr);
                    }
                    System.out.println("Sending-->" + s);
                    DatagramPacket p = new DatagramPacket(buffer,1024,s);
                    socket.send(p);
                }
            }
        }
        catch(SocketException e){
            System.out.println("we fucked up1");
        }
        catch(UnknownHostException e){
            System.out.println("we fucked up2");
        }
        catch(IOException e){
            System.out.println("we fucked up3");
            
        }
    }
    
    
    
}
