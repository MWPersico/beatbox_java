import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MyScreen extends JFrame{
    //Attributes:
    private MySound mySound;
    private JPanel mainPanel;
    private Box labelsBox, buttonsBox;
    private String[] labelNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Scoustic Snare", "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"};
    private JButton[] buttonsList = new JButton[6];
    public ArrayList<JCheckBox> checkboxList = new ArrayList<JCheckBox>();
    
    //Constructor:
    public MyScreen(MySound mySound){
        super();
        setComponents();
        buildGUI();
        this.mySound = mySound;
    }

    //Methods:
    public void setComponents(){
        buttonsBox = new Box(BoxLayout.Y_AXIS);
        labelsBox = new Box(BoxLayout.Y_AXIS);
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);

        buttonsList[0] = new PlayBtn();
        buttonsList[1] = new StopBtn();
        buttonsList[2] = new UpBtn();
        buttonsList[3] = new DownBtn();
        buttonsList[4] = new SaveBtn();
        buttonsList[5] = new LoadBtn();

        for(int i=0; i<buttonsList.length; i++){
            buttonsBox.add(buttonsList[i]);
        }
        for(int i=0; i<16; i++){
            JLabel jl = new JLabel(labelNames[i]);
            jl.setFont(new Font(jl.getFont().getFamily(), jl.getFont().getStyle(), 20));
            labelsBox.add(jl);
        }
        if(checkboxList.size()>0){
            for(JCheckBox element : checkboxList){
                mainPanel.add(element);
            }
        }else{
            for(int i=0; i<256; i++){
            JCheckBox cb = new JCheckBox();
            cb.setSelected(false);
            checkboxList.add(cb);
            mainPanel.add(cb);
        }
        }
    }
    public void buildGUI(){
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        setTitle("BeatBox");
        setSize(800,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        background.add(BorderLayout.WEST, labelsBox);
        background.add(BorderLayout.EAST, buttonsBox);
        background.add(BorderLayout.CENTER, mainPanel);

        getContentPane().add(background);
        setVisible(true);
    }
    
    //Components and listeners:
    class PlayBtn extends JButton implements ActionListener{
        PlayBtn(){
            super("Play");
            addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e){
            if(mySound.isLoaded){
                checkboxList = mySound.getCheckboxList();
                setComponents();
                buildGUI();
            }else{
                mySound.checkboxList = checkboxList;
            }

            mySound.buildTrack();
            mySound.isLoaded = false;
        }
    }
    class StopBtn extends JButton implements ActionListener{
        StopBtn(){
            super("Stop");
            addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e){
            mySound.stop();
        }
    }
    class UpBtn extends JButton implements ActionListener{
        UpBtn(){
            super("Tempo up");
            addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e){
            mySound.tempoUp();
        }
    }
    class DownBtn extends JButton implements ActionListener{
        DownBtn(){
            super("Tempo down");
            addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e){
            mySound.tempoDown();
        }
    }
    class SaveBtn extends JButton implements ActionListener{
        SaveBtn(){
            super("Save");
            addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e){
            mySound.save();
        }
    }
    class LoadBtn extends JButton implements ActionListener{
        LoadBtn(){
            super("Load");
            addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e){
            mySound.load();
        }
    }
}