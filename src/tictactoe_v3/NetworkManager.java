/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_v3;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author drose
 */
public class NetworkManager implements Runnable{
     
    private NetworkThread clientThread, serverThread;
    private final String hostName;
    private final int port;
    private ArrayList<Integer> oppMoveHistory;
    
    
    
     
    NetworkManager(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
        
        this.serverThread = new NetworkThread(hostName, port, "server");
        this.clientThread = new NetworkThread(hostName, port, "client");
        
        serverThread.Start();
    }
    @Override
    public void run(){
        while (true){

        }
    }
    //Returns true if this player goes first
    public boolean GetFirstPlayer(){
        int firstPlayer = -1;
        firstPlayer = DetermineFirstPlayer();
        while (firstPlayer == -1){firstPlayer = DetermineFirstPlayer();}
        return (firstPlayer == 1);
    }
    public boolean IsConnected(){
//        return (clientThread.connected && serverThread.connected);
        return (serverThread.connected);
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

    }

    private int DetermineFirstPlayer(){
        int theirNum, firstPlayer;
        
        //Get Random Number from 1-100
        Random random = new Random();
        Integer myNum = random.nextInt(100 - 1 + 1) + 1;
        System.out.println("Our Number is " + myNum);
        
        //Get their num
        theirNum = serverThread.ReadInt();
        System.out.println("Thier number is " + theirNum);
        
        //Compare
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
}
