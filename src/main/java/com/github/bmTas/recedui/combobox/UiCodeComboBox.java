package com.github.bmTas.recedui.combobox;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager; 

public class UiCodeComboBox<Code, CodeItem extends ICodeItem<Code>> extends UiComboBox<CodeItem> {

	public UiCodeComboBox(IStyle style) {
		super(style, new JComboBox<CodeItem>());
	}
	
//	public UiCodeComboBox(IStyle style, CodeItem[] collection) {
//		this(style, new JComboBox<>(new DefaultComboBoxModel<>(collection)));
//	}
//
//	public UiCodeComboBox(IStyle style, Collection<CodeItem> collection) {
//		this(style, new JComboBox<>(new DefaultComboBoxModel<>(new Vector<>(collection))));
//	}
//
	private UiCodeComboBox(IStyle style, ComboBoxModel<CodeItem> model) {
		super(style, model == null ? new JComboBox<>() : new JComboBox<>(model));
	}
//
	public UiCodeComboBox(ComboBoxModel<CodeItem> model) {
		this(StyleManager.styles().panelField(), model);
	}

	public UiCodeComboBox(IComboBoxParam<CodeItem> param) {
		this(param.getStyle(), param.getModel());
	}

//	public UiCodeComboBox(IStyle style, JComboBox<CodeItem> combo) {
//		super(style, combo);
//	}
	
	public void setCode(Code code) {
		setCodeFromObj(code);
	}
	
	
	public void setCodeFromObj(Object code) {
		ComboBoxModel<CodeItem> model = super.component.getModel();
		
		if (model != null) {
			for (int i = 0; i < model.getSize(); i++) {
				if (code.equals(model.getElementAt(i).getCode())) {
					super.component.setSelectedIndex(i);
				}
			}
		}
	}


	public Code getSelectedCode() {
		CodeItem selectedItem = super.getSelectedItem();
		return selectedItem == null ? null : selectedItem.getCode();
	}
	
	
	public List<Code> getSelectedCodes() {
		CodeItem[] selectedObjects = super.getSelectedObjects();
		ArrayList<Code> selected = new ArrayList<>();
		
		if (selectedObjects != null) {
			for (ICodeItem<Code> itm : selectedObjects) {
				selected.add(itm.getCode());
			}
		}
		
		return selected;
	}

}
