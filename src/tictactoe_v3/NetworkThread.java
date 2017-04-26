/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_v3;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author drose
 */
public class NetworkThread implements Runnable{
     
    public boolean connected, running;
    private final String hostName, role; 
    private final int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ArrayList<Integer> oppMoveHistory;
    private Thread thread;
    
     
    NetworkThread(String hostName, int port, String role) {
        this.hostName = hostName;
        this.port = port;
        this.role = role;
        connected = false;
        this.thread = new Thread(this, role + "Thread");
    }
    public void Start(){
        thread.start();
    }
    @Override
    public void run(){
        running = true;
        if (connected == false)Connect();
        while (running){
            if(role.equals("server"))ListenForOppMove();
        }
    }
    public void Connect(){
        switch (this.role){
            case "server":
                connected = ConnectAsServer();
                break;
            case "client":
                connected = ConnectAsClient();
                break;
            default:
                System.out.println("Network thread was given invalid role of '" + this.role + "");
        }  
    }
    private void ListenForOppMove(){
        Integer oppMove = ReadInt();
        oppMoveHistory.add(oppMove);
        System.out.println("Got Opp Move: " + oppMove);
    }
    public void CloseSockets(){
        try {
            serverSocket.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //Establish Connection as client
    private boolean ConnectAsClient(){
        int errorCount = 0; 
        boolean result = false; 
        while (errorCount < 10){
            try {
                socket = new Socket(this.hostName, this.port);
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unable to connect to the server address: " + this.hostName + ":" + port + ".");
                result = false;
                errorCount++;
            }
            result = true;
            System.out.println("Successfully connected to the server.");
        }
        return result;
    }
    //Establish connection as Server
    private boolean ConnectAsServer(){
        System.out.println("A");
        boolean result = InitializeServer();
        System.out.println("B");
        if (result) result = ListenForClient();
        
        if (role.equals("server")){                  
            try {
                System.out.println("A");
                result = InitializeServer();
                System.out.println("B");
                if (result) result = ListenForClient();
            } catch (Exception e) {
                result = false; 
                e.printStackTrace();
            }
        }else{
            result = false;
            System.out.println("Bad call to ConnectAsServer");
        }
        
        return result;
    }
    private boolean InitializeServer(){ 
        boolean result = true;
        if (role.equals("server")){                  
            try {
    //            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(this.hostName));
                serverSocket = new ServerSocket(port);
            } catch (Exception e) {
                result = false; 
                e.printStackTrace();
            }
        }else{
            result = false;
            System.out.println("Bad call to InitializeServer");
        }
        return result; 
    }
    private boolean ListenForClient(){
        
        boolean result = true;
        if (role.equals("server")){
            try {
                System.out.println("C");
                socket = serverSocket.accept();
                System.out.println("D");
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
                System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
            } catch (IOException e) {
                result = false;
                e.printStackTrace();
            }
        }else{
            result = false;
            System.out.println("Bad call to ListenForClient");
        }
        return result;
    }
    public int ReadInt(){
        int i = -1;
        try{
            i = in.readInt();
        }catch(IOException e){
            e.printStackTrace();
        }
        return i;
    }
    public boolean SendRandomNumber(){
        boolean result = false;
        
        //Get Random Number from 1-100
        Random random = new Random();
        Integer myNum = random.nextInt(100 - 1 + 1) + 1;
        
        result = SendInt(myNum);   
        return result;
    }
    public boolean SendInt(int i){
        boolean result = false;
        try{
            out.writeInt(i);
            out.flush();
            result = true;
            System.out.println("");
        }catch(IOException e){
            e.printStackTrace();
        }       
        return result;      
    }
}
