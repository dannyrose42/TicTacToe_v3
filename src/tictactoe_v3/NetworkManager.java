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
public class NetworkManager implements Runnable{
     
    public Thread networkThread;
    private final String hostName;
    private final int port;
    private ServerSocket serverSocket;
    private Socket socketForClientConnection, sokcetForServerConnetion;
    private DataInputStream clientIn;
    private DataOutputStream clientOut;
    private DataInputStream serverIn;
    private DataOutputStream serverOut;
    private boolean clientConnected = false; 
    private boolean serverConnected = false;
    private ArrayList<Integer> oppMoveHistory;
    
     
    NetworkManager(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
        oppMoveHistory = new ArrayList();
        clientConnected = ConnectAsClient();
        serverConnected = ConnectAsServer();      
        networkThread = new Thread(this, "networkThread");       
    }
    @Override
    public void run(){
        while (true){
            ListenForOppMove();
        }
    }
    //Returns true if this player goes first
    public boolean DoIGoFirst(){
        int firstPlayer = -1;
        while (firstPlayer == -1)firstPlayer = DetermineFirstPlayer();
        return (firstPlayer == 1);
    }
    public int GetOppMove(int turn){
        int oppMove;
        if (oppMoveHistory.size() != (turn + 1)){
            System.out.println("Turn number does not match oppMoveHistroy size!");
            oppMove = -1;
        }else{
            oppMove = oppMoveHistory.get(turn);
        }
        return oppMove;
    }
    public void SendMove(int row, int col){
        Integer myMove = (row * 5) + col + 1;
        try{
            clientOut.writeInt(myMove);
            System.out.println("Sent my move: " + myMove);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void CloseSockets(){
        try {
            serverSocket.close();
            socketForClientConnection.close();
            sokcetForServerConnetion.close();
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
                socketForClientConnection = new Socket(this.hostName, this.port);
                clientOut = new DataOutputStream(socketForClientConnection.getOutputStream());
                clientIn = new DataInputStream(socketForClientConnection.getInputStream());
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
        boolean result = InitializeServer();
        if (result) result = ListenForClientConnection();
        return result;
    }
    private boolean InitializeServer(){
        boolean result = true;
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(this.hostName));
        } catch (Exception e) {
            result = false; 
            e.printStackTrace();
        }
        return result; 
    }
    private boolean ListenForClientConnection(){
        boolean result = true;
        try {
            sokcetForServerConnetion = serverSocket.accept();
            serverOut = new DataOutputStream(sokcetForServerConnetion.getOutputStream());
            serverIn = new DataInputStream(sokcetForServerConnetion.getInputStream());
            System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    private int DetermineFirstPlayer(){
        int myNum, theirNum, firstPlayer;
        boolean gotThierNum = false, sentMyNum = false;
        Random random = new Random();
        myNum = random.nextInt(100 - 1 + 1) + 1;
        theirNum = -1;
        while ((gotThierNum == false) && (sentMyNum == false)){
            if (gotThierNum == false){                           
                try{
                    theirNum = serverIn.readInt();
                    gotThierNum = true;
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if (sentMyNum == false){             
                try{
                    clientOut.writeInt(myNum);
                    clientOut.flush();
                    sentMyNum = true;
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        if (myNum == theirNum){
            System.out.println("myNum and thierNum are the same!");
            firstPlayer = -1;
        }else if (myNum > theirNum){
            firstPlayer = 1; 
        }else{
            firstPlayer = 2;
        }
        return firstPlayer;
    }
    private void ListenForOppMove(){
        try{
            Integer oppMove = serverIn.readInt();
            oppMoveHistory.add(oppMove);
            System.out.println("Got Opp Move: " + oppMove);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
