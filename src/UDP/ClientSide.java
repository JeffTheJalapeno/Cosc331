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
import java.util.Scanner;

/**
 *
 * @author jthha
 */
public class ClientSide {

    private static DatagramSocket socket;
    private static HashSet<SocketAddress> group;
    private static InetAddress addr;
    private static boolean firstTime;
    private static String messageToSend;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //String IP = (String)JOptionPane.showInputDialog("Please enter the IP address of the host computer\nusing . separating the numbers.");
        //String hostName = (String)JOptionPane.showInputDialog("Please enter name of the host computer exactly.");
        
        String IP = "10.103.48.140";
        String hostName = "DESKTOP-4S77T69";
        connectToHost(IP, hostName);
        try{
            sendAndRecieve();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    private static void connectToHost(String IP, String hostName){
        try{
            System.out.println(IP);
            IP = IP.replace('.', ' ');
            Scanner IPScanner = new Scanner(IP);
            System.out.println(IP);
            int port = 4005;
            socket = new DatagramSocket(port);
            byte IPv4[] = new byte[4];
            System.out.println(IP);
            for(int i = 0; i < 4; i++){
                int j = IPScanner.nextInt();
                if(j > 127)
                    j = j-256;
                IPv4[i] = (byte)j;
            }
            addr = InetAddress.getByAddress(hostName, IPv4); 
            
            System.out.println(addr.isReachable(10000));
            byte[] buffer = new byte[1024];
            DatagramPacket p = new DatagramPacket(buffer,1024,addr,4000);
            socket.send(p);
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
    private static void sendAndRecieve() throws Exception{
        Scanner in = new Scanner(System.in);
        while(true){
            
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);
            String message = "";
            for(byte b : buffer)
                message += (char)b + "";
            System.out.println(message);
            
            if(in.hasNext()){
                messageToSend = in.nextLine();
                buffer = messageToSend.getBytes();
                DatagramPacket p = new DatagramPacket(buffer,buffer.length,addr,4000);
                socket.send(p);
                messageToSend = "";
            }
        }
    }
}
