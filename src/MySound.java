import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.midi.*;
import javax.swing.JCheckBox;

public class MySound{
    //Attributes:
    public ArrayList<JCheckBox> checkboxList;
    public boolean isLoaded = false;
    private SavedObject savedObject;
    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;
    private int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
    private int seqId;
    private Scanner sc = new Scanner(System.in);

    //Constructor:
    public MySound(){
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        }catch(Exception e){e.printStackTrace();}
    }

    //Methods:
    public void buildTrack(){
        int[] trackList = null;
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for(int i=0; i<16; i++){
            trackList = new int[16];
            int key = instruments[i];
            for(int j=0; j<16; j++){
                JCheckBox jc = (JCheckBox) this.checkboxList.get(j+(16*i));
                trackList[j] = (jc.isSelected())?key:0;
            }
            makeTracks(trackList);
            track.add(makeEvent(176,1,127,0,16));
        }
        track.add(makeEvent(192,9,1,0,15));
        try{
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        }catch(Exception e){e.printStackTrace();}
    }
    public void makeTracks(int[] list){
        for(int i=0; i<16; i++){
            int key = list[i];
            if(key!=0){
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i+1));
            }
        }
    }
    public MidiEvent makeEvent(int cmnd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try{
            ShortMessage a = new ShortMessage(cmnd, chan, one, two);
            event = new MidiEvent(a, tick);
        }catch(Exception e){e.printStackTrace();}
        return event;
    }
    public void stop(){
        sequencer.stop();
    }
    public void tempoUp(){
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor((float) (tempoFactor*1.3));
    }
    public void tempoDown(){
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor((float) (tempoFactor*.97));
        System.out.println(sequencer);
    }
    public void save(){
        savedObject = new SavedObject(this.checkboxList, this.seqId);
        ReaderWriter.write(savedObject);
        this.seqId++;
    }
    public void load(){
        System.out.print("Type an ID to load: ");
        int in = sc.nextInt();
        this.checkboxList = ReaderWriter.read(in);
        this.isLoaded = true;
    }
    public ArrayList<JCheckBox> getCheckboxList(){
        return this.checkboxList;
    }
}