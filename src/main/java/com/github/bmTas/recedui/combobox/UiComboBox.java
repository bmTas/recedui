package com.github.bmTas.recedui.combobox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import com.github.bmTas.recedui.common.FieldChangeAdapter;
import com.github.bmTas.recedui.common.UiFocusListnerAdater;
import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.common.IFieldChangedListner;
import com.github.bmTas.recedui.def.component.IUiComponent;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class UiComboBox<E> extends UiStdComponent<JComboBox<E>> {

	public static <Entry> ComboBoxParam<Entry> newParam(List<Entry> data) { 
		return new ComboBoxParam<Entry>().setModel(data);
	}
	
	public static <Entry> ComboBoxParam<Entry> newParam(Class<Entry> classDtls) { 
		return new ComboBoxParam<Entry>();
	}

//	public UiComboBox(IStyle style) {
//		this(style, new JComboBox<>());
//	}
//	public UiComboBox(IStyle style, E[] collection) {
//		this(style, new JComboBox<>(new DefaultComboBoxModel<>(collection)));
//	}
//
//	public UiComboBox(IStyle style, Collection<E> collection) {
//		this(style, new JComboBox<>(new DefaultComboBoxModel<>(new Vector<>(collection))));
//	}
	
	private FieldChangeAdapter<UiComboBox<E>, E> changeAdapter = new FieldChangeAdapter<UiComboBox<E>, E>(this) {

		@SuppressWarnings("unchecked")
		@Override protected E getValue() {
			return (E) getMainComponent().getSelectedItem();
		}
		
	};

	public UiComboBox(IComboBoxParam<E> param) {
		this(param.getStyle(), new JComboBox<>(param.getModel()));
	}

	public UiComboBox(ComboBoxModel<E> model) {
		this(StyleManager.styles().comboBox(), new JComboBox<>(model));
	}

	public UiComboBox(IStyle style, JComboBox<E> combo) {
		super(style, combo, combo);
	}

	//protected JComboBox<E> component;
	public void setModel(ComboBoxModel<E> aModel) {
		component.setModel(aModel);
	}
	
	public E getSelectedItem() {
		return (E) component.getSelectedItem();
	}
	
	public void setSelectedItem(E item) {
		component.setSelectedItem(item);
	}
	
	public int getSelectedIndex() {
		return component.getSelectedIndex();
	}
	
	
	public void setSelectedIndex(int index) {
		component.setSelectedIndex(index);
	}

	
	public E[] getSelectedObjects() {
		return (E[]) component.getSelectedObjects();
	}
	
	
	public int getItemCount() {
		return component.getItemCount();
	}
	
	
	public E getItemAt(int index) {
		
		return (E) component.getItemAt(index);
	}
	
	public void addChangeListner(IFieldChangedListner<UiComboBox<E>, E> listner) {
		changeAdapter.addListner(listner);
	}
}
 