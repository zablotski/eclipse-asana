package customControls;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Combo;

public class ValueBasedCombo{
	
	private Combo comboBox;
	private ArrayList<SimpleEntry<String, String>> keyValItemList;
	
	public ValueBasedCombo(Combo combo, ArrayList<SimpleEntry<String, String>> keyValItemList){
		this.comboBox = combo;
		this.keyValItemList = keyValListDeepCopy(keyValItemList);
		comboBox.setItems(getValuesArrayFromEntryList(this.keyValItemList));
	}
	
	public ValueBasedCombo(Combo combo){
		this.comboBox = combo;
	}
	
	public void setComboKeyValItemList(ArrayList<SimpleEntry<String, String>> keyValItemList){
		this.keyValItemList = keyValListDeepCopy(keyValItemList);
	}
	
	public String getSelectedItemValue(){
		int selectedIndex = this.comboBox.getSelectionIndex();
		return this.keyValItemList.get(selectedIndex).getKey();
	}
	
	public Combo getComboBox(){
		return this.comboBox;
	}
	
	public void removeAll(){
		this.comboBox.removeAll();
		this.keyValItemList = null;
	}
	
	private ArrayList<SimpleEntry<String, String>> keyValListDeepCopy(ArrayList<SimpleEntry<String, String>> keyValItemList){
		ArrayList<SimpleEntry<String, String>> result = new ArrayList<>();
		for(SimpleEntry<String, String> item : keyValItemList){
			SimpleEntry<String, String> itemCopy = new SimpleEntry<>(item.getKey(), item.getValue());
			result.add(itemCopy);
		}
		return result;
	}
	
	private String[] getValuesArrayFromEntryList(ArrayList<SimpleEntry<String, String>> keyValItemList){
		String[] result = new String[keyValItemList.size()];
		
		int i = 0;
		for(SimpleEntry<String, String> item : keyValItemList){
			result[i] = item.getValue();
		}
		
		return result;
	}
}
