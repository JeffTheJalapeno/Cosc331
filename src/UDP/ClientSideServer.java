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
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author jthha
 */
public class ClientSideServer{
    
    
    private DatagramSocket socket;
    private InetAddress thisAddr;
    private InetAddress addr;
    
    public ClientSideServer(){
        
    }
    
    public void start(){
        String IP = "10.103.48.140";
        String hostName = "DESKTOP-4S77T69";
        connectToHost(IP, hostName);
        
    }
    
    private void connectToHost(String IP, String hostName){
        try{
            IP = IP.replace('.', ' ');
            Scanner IPScanner = new Scanner(IP);
            int port = 4005;
            socket = new DatagramSocket(port);
            byte IPv4[] = new byte[4];
            for(int i = 0; i < 4; i++){
                int j = IPScanner.nextInt();
                if(j > 127)
                    j = j-256;
                IPv4[i] = (byte)j;
            }
            addr = InetAddress.getByAddress(hostName, IPv4); 
            thisAddr = InetAddress.getLocalHost();
            String s = thisAddr.getHostName() + " has joined the room.";
            byte[] buffer = s.getBytes();
            DatagramPacket p = new DatagramPacket(buffer,buffer.length,addr,4000);
            socket.send(p);
            socket.setSoTimeout(50);
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
    
    public void sendMessage(String message){
        try{
            String s = thisAddr.getHostName() + "-->" + message;
            byte[] buffer = s.getBytes();
            DatagramPacket p = new DatagramPacket(buffer,buffer.length,addr,4000);
            socket.send(p);
        }
        catch(IOException e){
            
        }
    }
    
    public String receive() throws Exception{
        try{
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);
            String message = "";
            for(byte b : buffer)
                message += (char)b + "";
            return message;
        }
        catch(SocketTimeoutException e){
            return "";
        }
        catch(SocketException e){
            throw e;
        }
        catch(IOException e){
            throw e;
        }
    }
    
    
}
