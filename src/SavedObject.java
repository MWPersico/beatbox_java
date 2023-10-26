import java.io.Serializable;
import javax.swing.JCheckBox;
import java.util.ArrayList;

public class SavedObject implements Serializable{
    private boolean[] checkboxList;
    private int id;
    public SavedObject(ArrayList<JCheckBox> list, int id){
        checkboxList = new boolean[256];
        for(int i=0; i<256; i++){
            JCheckBox check = list.get(i);
            if(check.isSelected()){
                checkboxList[i] = true;
            }
        }
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public boolean[] getCheckboxList(){
        return this.checkboxList;
    }
}