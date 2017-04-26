/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_v3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author drose
 */
public class StartMenuObserver implements ActionListener{
    
    private TicTacToeGame game;
    private StartMenu menu;
    private GameController controller;
    
    public StartMenuObserver(TicTacToeGame g, StartMenu m, GameController gc ){
        this.game = g;
        this.menu = m;
        this.controller = gc;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        menu.setVisible(false);
        controller.SetGameMode(menu.GetSelectedMode());
//        controller.SetHost(menu.GetHost());
//        controller.SetPort(menu.GetPort());
        game.DisplayBoard();
        controller.Start();
    }
}
    

