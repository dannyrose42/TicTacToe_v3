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
    
    public StartMenuObserver(TicTacToeGame game, StartMenu menu){
        this.game = game;
        this.menu = menu;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        menu.setVisible(false);
        game.DisplayBoard();
    }
}
    

