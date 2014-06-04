package com.tennissetapp.ui;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MultiSpinner extends Spinner implements
		OnMultiChoiceClickListener, OnCancelListener {

	private List<MultiSpinnerItem> items;
	private boolean[] selected;
	private String defaultText;
	private MultiSpinnerListener listener;
	private String dialogTitle;

	public static class MultiSpinnerItem implements CharSequence{
		public String name;

		public MultiSpinnerItem(){}
		public MultiSpinnerItem(String name){
			this.name = name;
		}
		@Override
		public String toString() {
			return name;
		}
		@Override
		public char charAt(int index) {
			if(this.name != null){
				return this.name.charAt(index);
			}
			else{
				return 0;
			}
		}
		@Override
		public int length() {
			if(this.name != null){
				return this.name.length();
			}
			else return 0;
		}
		@Override
		public CharSequence subSequence(int start, int end) {
			if(this.name != null){
				return this.name.subSequence(start, end);
			}
			else{
				return "";
			}
		}
	}
	
	
	public MultiSpinner(Context context) {
		super(context);
	}

	public MultiSpinner(Context context, AttributeSet atts) {
		super(context, atts);
	}

	public MultiSpinner(Context context, AttributeSet atts, int arg2) {
		super(context, atts, arg2);
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) {
		if (isChecked)
			selected[which] = true;
		else
			selected[which] = false;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// refresh text on spinner
		StringBuffer spinnerBuffer = new StringBuffer();
//		boolean someUnselected = false;
		for (int i = 0; i < items.size(); i++) {
			if (selected[i] == true) {
				spinnerBuffer.append(items.get(i));
				spinnerBuffer.append(", ");
			} 
//			else {
//				someUnselected = true;
//			}
		}
		String spinnerText;
//		if (someUnselected) {
			spinnerText = spinnerBuffer.toString();
			if (spinnerText.length() > 2){
				spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
			}
				
//		} else {
//			spinnerText = defaultText;
//		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,new String[] { spinnerText });
		setAdapter(adapter);
		if(listener != null){
			listener.onItemsSelected(selected);
		}
		
	}

	@Override
	public boolean performClick() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMultiChoiceItems(items.toArray(new CharSequence[items.size()]), selected, this);
		builder.setPositiveButton(android.R.string.ok,
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
		if(this.dialogTitle != null){
			builder.setTitle(this.dialogTitle);
		}
		
		builder.setOnCancelListener(this);
		builder.show();
		return true;
	}
	
	public List<MultiSpinnerItem> getSelectedItems(){
		List<MultiSpinnerItem> list = new ArrayList<MultiSpinnerItem>();
		for(int i = 0 ; i < selected.length ; i++){
			if(selected[i]){
				list.add(this.items.get(i));
			}
		}
		return list;
	}

//	public void setItems(List<MultiSpinnerItem> items, String allText,MultiSpinnerListener listener) {
//		this.items = items;
//		this.defaultText = allText;
//		this.listener = listener;
//
//		// all selected by default
//		selected = new boolean[items.size()];
//		for (int i = 0; i < selected.length; i++){
//			selected[i] = false;
//		}
//
//		// all text on the spinner
//		ArrayAdapter<MultiSpinnerItem> adapter = new ArrayAdapter<MultiSpinnerItem>(getContext(),
//				android.R.layout.simple_spinner_item, new MultiSpinnerItem[] { new MultiSpinnerItem(allText) });
//		setAdapter(adapter);
//	}
	
	public void setItems(List<MultiSpinnerItem> items, String defaultText) {
		
		this.setItems_(items,defaultText,null);
	}
	
	public void setItems(List<MultiSpinnerItem> items) {
		this.setItems_(items,null,null);
	}
	
	public void setItems(List<MultiSpinnerItem> items, String defaultText,String dialogTitle) {
		this.setItems_(items,defaultText,dialogTitle);
	}
	
	private void setItems_(List<MultiSpinnerItem> items, String defaultText,String dialogTitle) {
		
		this.items = items;
		this.defaultText = defaultText != null ? defaultText : items.get(0).name;
		this.dialogTitle = dialogTitle;

		// all selected by default
		selected = new boolean[items.size()];
		if(defaultText == null){
			selected[0] = true;
			for (int i = 1; i < selected.length; i++){
				selected[i] = false;
			}
		}
		else{
			for (int i = 0; i < selected.length; i++){
				selected[i] = false;
			}
		}
		

		// all text on the spinner
		ArrayAdapter<MultiSpinnerItem> adapter = new ArrayAdapter<MultiSpinnerItem>(getContext(),
				android.R.layout.simple_spinner_item, new MultiSpinnerItem[] { new MultiSpinnerItem(this.defaultText) });
		setAdapter(adapter);
	}

	public interface MultiSpinnerListener {
		public void onItemsSelected(boolean[] selected);
	}
}