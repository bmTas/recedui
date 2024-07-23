package com.github.bmTas.recedui.combobox;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;

public class ComboBoxParam<ComboItem> implements IComboBoxParam<ComboItem>, IComboBoxParamBuilder<ComboItem> {

	public static <Entry> ComboBoxParam<Entry> newParam(List<Entry> data) { 
		return new ComboBoxParam<Entry>().setModel(data);
	}
	public static <Entry> ComboBoxParam<Entry> newParam(Entry[] data) { 
		return new ComboBoxParam<Entry>().setModel(data);
	}
	
	public static <Entry> ComboBoxParam<Entry> newParam(Class<Entry> classDtls) { 
		return new ComboBoxParam<Entry>();
	}

	private IStyle style;
	private ComboBoxModel<ComboItem> model;
	
	@Override
	public ComboBoxParam<ComboItem> setStyle(IStyle style) {
		this.style = style;
		return this;
	}

	@Override
	public ComboBoxParam<ComboItem> setModel(ComboItem[] collection) {
		this.model = new DefaultComboBoxModel<>(collection);
		return this;
	}

	@Override
	public ComboBoxParam<ComboItem> setModel(Collection<ComboItem> collection) {
		this.model = new DefaultComboBoxModel<>(new Vector<>(collection));
		return this;
	}

	@Override
	public ComboBoxParam<ComboItem> setModel(ComboBoxModel<ComboItem> model) {
		this.model = model;
		return this;
	}

	@Override
	public IComboBoxParam<ComboItem> asParam() {
		return this;
	}

	@Override
	public IStyle getStyle() {
		return style == null ? standardComboStyle() : style;
	}



	@Override
	public ComboBoxModel<ComboItem> getModel() {
		return model;
	}
	

	public static final IStyle standardComboStyle() {
		return StyleManager.styles().comboBox();
	}


}
