import java.io.*;
import java.util.ArrayList;
import javax.swing.JCheckBox;

public class ReaderWriter{
    public static void write(SavedObject savedObj){
        String path = String.format("C:/Users/willi/OneDrive/Projetos-Marcos/projetos-java/beatbox/files/Object%d.ser", savedObj.getId());
        try{
            FileOutputStream fOut = new FileOutputStream(path);
            ObjectOutputStream objOut = new ObjectOutputStream(fOut);
            objOut.writeObject(savedObj);
            objOut.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static ArrayList<JCheckBox> read(int id){
        String path = String.format("C:/Users/willi/OneDrive/Projetos-Marcos/projetos-java/beatbox/files/Object%d.ser", id);
        SavedObject obj;
        try{
            FileInputStream fIn = new FileInputStream(path);
            ObjectInputStream objIn = new ObjectInputStream(fIn);
            obj = (SavedObject) objIn.readObject();
            objIn.close();
        }catch(Exception e){
            e.printStackTrace();
            obj = null;
        }
        return extractSequence(obj);
    }
    public static ArrayList<JCheckBox> extractSequence(SavedObject obj){
        ArrayList<JCheckBox> checkboxList = new ArrayList<JCheckBox>();
        boolean[] list = obj.getCheckboxList();
        for(int i=0; i<256; i++){
            if(list[i]){
                JCheckBox box = new JCheckBox();
                box.setSelected(true);
                checkboxList.add(box);
            }else{
                JCheckBox box = new JCheckBox();
                box.setSelected(false);
                checkboxList.add(box);
            }
        }
        
        return checkboxList;
    }
}