package com.github.bmTas.recedui.radioBtn;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.bmTas.recedui.common.UiStdComponent;
import com.github.bmTas.recedui.def.radioButton.IRadioBtnDetails;
import com.github.bmTas.recedui.def.radioButton.ISelectedListner;
import com.github.bmTas.recedui.grid.DataTable;
import com.github.bmTas.recedui.grid.IDataTable;
import com.github.bmTas.recedui.grid.ListTable;
import com.github.bmTas.recedui.styles.IStyle;

public class UiRadioButtonList<Btn extends IRadioBtnDetails> extends UiStdComponent<JPanel> {

	private List<JRadioButton> radioBtns = new ArrayList<JRadioButton>();
	private List<Btn> btnSource = new ArrayList<Btn>();
	private int rows, cols;
	private ButtonGroup btnGroup;
	private boolean useButtonGroup = true;
	private ArrayList<ISelectedListner<Btn>> selectedListner = new ArrayList<>();
	private ChangeListener btnChangeListener;
	
	public UiRadioButtonList(IStyle style) {
		this(style, -1, -1);
	}
	
	public UiRadioButtonList(IStyle style, int rows, int cols) {
		super(style, new JPanel());
		
		this.rows = rows;
		this.cols = cols;
	}
	
	
	public UiRadioButtonList(IStyle style, IDataTable<? extends Btn> btns) {
		super(style, new JPanel());
		
		setItems(btns);
	}

	public UiRadioButtonList<Btn> setItems(Btn[] radioBtnDetails) {
		return setItems(Arrays.asList(radioBtnDetails));
	}

	public UiRadioButtonList<Btn> setItems(List<Btn> radioBtnDetails) {
		if (cols < 0) {
			rows = 1;
			cols = radioBtnDetails.size();
		}
		return setItems(new ListTable<Btn>(rows, cols, radioBtnDetails));
	}

//	public UiRadioButtonList<Btn> setItems(Btn... radioBtnDetails) {
//		if (cols < 0) {
//			rows = 1;
//			cols = radioBtnDetails.length;
//		}
//		
//		setupLayout();
//		
//		addArray(radioBtnDetails.length, radioBtnDetails);
//		return this;
//	}
	
	public <rb extends Btn> UiRadioButtonList<Btn> setItems(rb[][] radioBtnDetails) {
		return setItems(new DataTable<rb>(radioBtnDetails));
//		if (cols < 0) {
//			rows = radioBtnDetails.length;
//			
//			int c = 1;
//			for (IRadioBtnDetails[] rowOfBtns : radioBtnDetails) {
//				c = Math.max(c, rowOfBtns.length);
//			}
//			cols = c;
//		}
//		
//		setupLayout();
//		
//		for (Btn[] rowOfBtns : radioBtnDetails) {
//			addArray(cols, rowOfBtns);
//			for (int i = rowOfBtns.length; i < cols; i++) {
//				super.component.add(new JPanel());
//			}
//		}
//		return this;
	}
	
	public UiRadioButtonList<Btn> setItems(IDataTable<? extends Btn> btns) {
		rows = btns.getRowCount();
		cols = Math.max(cols, btns.getColumnCount());
		
		setupLayout();
		
		synchronized (selectedListner) {
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					addButton(btns.get(row, col));
				}
			}
		
		}
		return this;
	}

	
	private void setupLayout() {
		if (btnChangeListener != null) {
			for (JRadioButton b : radioBtns) {
				b.removeChangeListener(btnChangeListener);
			}
		}
		radioBtns.clear();
		btnSource.clear();
		super.component.setLayout(new GridLayout(rows, cols, 1, 1));
		
		if (useButtonGroup) {
			btnGroup = new ButtonGroup();
		}
	}

	
	
//	private void addArray(int max, Btn[] radioBtnDetails) {
//		if (radioBtnDetails != null && radioBtnDetails.length > 0) {
//			int m = Math.min(radioBtnDetails.length, max);
//			synchronized (selectedListner) {
//				for (int i = 0; i < m; i++) {
//					addABtn(radioBtnDetails[i]);
//				}
//			}
//		}
//	}
	
	private void addButton(Btn dtl) {
		JRadioButton b = dtl == null ? null : dtl.getButton();
		
		if (b == null) {
			super.component.add(new JPanel());
		} else {
			radioBtns.add(b);
			btnSource.add(dtl);
			super.component.add(b);
			if (useButtonGroup) {
				btnGroup.add(b);
			}
			
			if (btnChangeListener != null) {
				b.removeChangeListener(btnChangeListener);
				b.addChangeListener(btnChangeListener);
			}
		}
	}
	
	/**
	 * get Selected Button
	 * @return Selected Button
	 */
	public Btn getSelected() {
		List<Btn> selected = getSelectedList();
		
		switch (selected.size()) {
		case 0: return null;
		case 1: return selected.get(0);
		}
		
		throw new RuntimeException(selected.size() + " button selected. This method expects 1");
	}
	
	public List<Btn> getSelectedList() {
		ArrayList<Btn> selected = new ArrayList<>();
		for (int i = 0; i < radioBtns.size(); i++) {
			if (radioBtns.get(i).isSelected()) {
				selected.add(btnSource.get(i));
			}
		}
		return selected;
	}
	
	public UiRadioButtonList<Btn> setUseButtonGroup(boolean useButtonGroup) {
		this.useButtonGroup = useButtonGroup;
		return this;
	}
	
	public void addSelectedItemListner(ISelectedListner<Btn> e) {
		if (selectedListner.size() == 0) {
			synchronized (selectedListner) {
				if (selectedListner.size() == 0) {
					setupListner();
				}
				selectedListner.add(e);
			}
		} else {
			selectedListner.add(e);
		}
	}
	
	private void setupListner() {
		btnChangeListener = new ChangeListener() {		
			Object lastSource = null;
			@Override public void stateChanged(ChangeEvent e) {
				Object source = e.getSource();
				
				if (lastSource != source
				&& source instanceof JRadioButton
				&& ((JRadioButton) source).isSelected()) {
//					System.out.println("+++>>> " + selectedListner.size());
					Btn selected = null;
					lastSource = source;
					for (int i = 0; i < radioBtns.size(); i++) {
						if (source == radioBtns.get(i)) {
							selected = btnSource.get(i);
							for (ISelectedListner<Btn> listner : selectedListner) {
								listner.changedSelection(selected);
							}
							break;
						}
					}
				}
			}
		};
		
		for (JRadioButton b : radioBtns) {
			b.addChangeListener(btnChangeListener);
		}
	}
	
	public void removeSelectedItemListner(ISelectedListner<Btn> o) {
		synchronized (selectedListner) {
			selectedListner.remove(o);
		}
	}
	protected List<Btn> getItemDetails() {
		return Collections.unmodifiableList(btnSource);
	}
	
}
