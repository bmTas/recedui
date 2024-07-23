package com.github.bmTas.recedui.common;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.github.bmTas.recedui.def.common.IListPopupActions;
import com.github.bmTas.recedui.styles.IStyle;


/**
 * Generic dropdown list
 * 
 * @author Bruce Martin
 *
 * @param <What>
 */
public class UiSelectionList<What> extends UiStdComponent2<JWindow, JList<What>> {

	private final JComponent parentComponent;
	protected final JList<What> list;
	private JScrollPane listScroll;
	private final IListPopupActions<What> completeActions;
	
	private boolean toInit = true;

	private final AWTEventListener awtListner = new AWTEventListener() {
		@Override public void eventDispatched(AWTEvent event) {
			Object source = event.getSource();
			
			if (source == parentComponent || source == listScroll
			||  source == list ||  source == container
			|| listScroll.getMousePosition() != null) {
				
			} else if (event.getID() == MouseEvent.MOUSE_RELEASED) {
				hideDialog();
			}
		}
	};

//	public UiSelectionList(
//			IStyle style, JComponent parentComponent, boolean focusable, 
//			IListPopupActions<What> completeActions) {
//		this(style, parentComponent, focusable, null, completeActions);
//	}


	public UiSelectionList(
			IStyle style, JComponent parentComponent, boolean focusable, 
			JPanel favouritePnls,
			IListPopupActions<What> completeActions) {
		super(style, new JWindow(SwingUtilities.getWindowAncestor(parentComponent)), new JList<What>());
		this.parentComponent = parentComponent;
		this.completeActions = completeActions;

		JWindow window = super.container;
		list = super.getMainComponent();
		setupList(window);
		
		window.setFocusable(focusable);

		Container contentPane = window.getContentPane();
		contentPane.setLayout(new BorderLayout());

		if (favouritePnls == null) {
			contentPane.add(listScroll, BorderLayout.CENTER);
		} else {
			JPanel pnl = new JPanel(new BorderLayout());
			pnl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			
			pnl.add(favouritePnls, BorderLayout.NORTH);
			pnl.add(listScroll, BorderLayout.CENTER);
			
			contentPane.add(pnl, BorderLayout.CENTER);
		}
	}


	protected void setupList(JWindow window) {
		

		list.setFocusable(false);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.addMouseListener(new MouseAdapter() {
			@Override public void mousePressed(final MouseEvent e) {
				final int index = list.getUI().locationToIndex(list, e.getPoint());
				if (SwingUtilities.isLeftMouseButton(e) && index > -1) {
					setSelectedPath( list.getModel().getElementAt(index));
				}
			}
		});
		list.addKeyListener(new KeyAdapter() {
			@Override public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER & e.getModifiers() == 0) {
					setSelectedPath(list.getSelectedValue());
				}
			}
		});
		listScroll = new JScrollPane(list);
		

		window.addComponentListener(new ComponentAdapter() {
			@Override public void componentShown ( ComponentEvent e ) {
				Toolkit.getDefaultToolkit().addAWTEventListener(
						awtListner, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
			}

			@Override public void componentHidden(ComponentEvent e) {
				Toolkit.getDefaultToolkit().removeAWTEventListener(awtListner);
			}
		});
	}


	private void setSelectedPath(final What path) {
		completeActions.fireFileChanged(path);
		container.setVisible(false);
	}


	public void hideDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				container.setVisible(false);
				completeActions.popupHidden();
			}
		});
	}


	public void scrollToSelected(int idx) {
		list.setSelectedIndex(idx);
		list.scrollRectToVisible(list.getUI().getCellBounds(list, idx, idx));
	}
	
	public void setPosition(int x, int y, int width) {
		setPosition(x, y, width, listScroll.getPreferredSize().height);
	}
	
	public void setPosition(int x, int y, int width, int height) {
		container.setSize(width, height);
		super.container.setLocation(x, y);

		// Showing dialog if needed
		if (!this.isShowing()) {
			super.container.setVisible(true);  
		}
	}

	public int getSelectedIndex() {
		return list.getSelectedIndex();
	}


	public What getSelectedValue() {
		return list.getSelectedValue();
	}

	public int getListSize() {
		return list.getModel().getSize();
	}


	public boolean isShowing() {
		return super.container.isShowing();
	}
	

	public void setVisible(boolean b) {
		super.container.setVisible(b);
		
		if (b && toInit) {
			SwingUtilities.getWindowAncestor(parentComponent).addComponentListener(new ComponentAdapter() {
				@Override public void componentMoved(final ComponentEvent e) {
					hideDialog();
				}

				@Override public void componentResized(final ComponentEvent e) {
					hideDialog();
				}
			});
			toInit = false;
		}
	}
	
	

	@SuppressWarnings("serial")
	public void updateFileList(final List<? extends What> similarFiles) {
		list.setModel(new AbstractListModel<What>() {
			@Override public int getSize() {
				return similarFiles.size();
			}

			@Override public What getElementAt(final int i) {
				return similarFiles.get(i);
			}
		});
		list.setVisibleRowCount(Math.min(similarFiles.size(), 15));
		list.updateUI();
		if (similarFiles.size() > 0) {
			list.setSelectedIndex(0);
		}

	}

}
