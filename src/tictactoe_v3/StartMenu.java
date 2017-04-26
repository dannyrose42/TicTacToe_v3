/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_v3;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author drose
 */
public class StartMenu extends JFrame {
   
    private TicTacToeGame game;
    private JTextField textField, hostTextField, portTextField;
    private JLabel hostLabel, portLabel;
    private JButton startButton;
    private JRadioButton standAloneModeRadioButton, networkModeRadioButton;
    private ButtonGroup gameModeButtons;
    private JPanel modeSelectionPanel, radioPanel, networkSettingsPanel;
    private Container pane;
    private StartMenuObserver smo;
    
    public StartMenu(TicTacToeGame g, GameController gc){
        game = g;
        smo = new StartMenuObserver(game, this, gc);
        //Build and assemble swing components
        textField = new JTextField("Please select game mode.");
        textField.setEditable(false);
        
        //Buttons
        gameModeButtons = new ButtonGroup();
        standAloneModeRadioButton = new JRadioButton();
        standAloneModeRadioButton.setText("Stand-Alone Mode");
        standAloneModeRadioButton.setSelected(true);
        networkModeRadioButton = new JRadioButton();
        networkModeRadioButton.setText("Network Mode");
        gameModeButtons.add(standAloneModeRadioButton);
        gameModeButtons.add(networkModeRadioButton);
        startButton = new JButton();
        startButton.setText("Start");
        startButton.addActionListener(smo);
        
        //SubPanel For Radio Buttons
        radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(0, 1));
        radioPanel.add(standAloneModeRadioButton);
        radioPanel.add(networkModeRadioButton);  
        
        //Mode Selection Panel (Contains sub panel for buttons)
        modeSelectionPanel = new JPanel();
        modeSelectionPanel.setLayout(new GridLayout(1,2)); 
        modeSelectionPanel.add(radioPanel);
        modeSelectionPanel.add(startButton);
        
        //Network Settings Panel
        hostTextField = new JTextField("Host");
        portTextField = new JTextField("Port");
        hostLabel = new JLabel("Host Name:");
        portLabel = new JLabel("Port:");
        
        networkSettingsPanel = new JPanel();
        networkSettingsPanel.setLayout(new GridLayout(2, 2));
        networkSettingsPanel.add(hostLabel);
        networkSettingsPanel.add(hostTextField);
        networkSettingsPanel.add(portLabel);
        networkSettingsPanel.add(portTextField);
        
        //Main Pane
        pane = getContentPane();
        pane.add(textField, "North");
        pane.add(modeSelectionPanel, "Center");
        pane.add(networkSettingsPanel, "South");
        
        //Set Frame Attributes
        this.setSize(500, 500);
        this.setTitle("Tic Tac Toe Start Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.setVisible(true);
    }
    public int GetSelectedMode(){
        int mode = -1;
        if(standAloneModeRadioButton.isSelected()){
            mode = 1;
        }else if(networkModeRadioButton.isSelected()){
            mode = 2;
        }else{
            System.out.println("Invalid Game Mode Selected");
        }
        return mode;
    }
    public String GetHost(){
        return hostTextField.getText();
    }
    public int GetPort(){
        return Integer.parseInt(portTextField.getText());
    }
}
