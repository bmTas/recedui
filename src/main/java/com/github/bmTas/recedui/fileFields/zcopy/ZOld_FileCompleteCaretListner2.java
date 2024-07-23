/*
 * Author Bruce Martin
 * 
 * License: either    (Apache 2.0) or (LGPL 3 or later) 
 */
package com.github.bmTas.recedui.fileFields.zcopy;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.xfiles.FileUtils;



/**
 * Auto Complete Caret listner
 * 
 * @author Bruce Martin
 *
 */
public class ZOld_FileCompleteCaretListner2 implements CaretListener {
	public boolean autocompleteEnabled = true, skip=false;
	private JWindow filesListDialog;

	private JList<Path> list = null;
	private JScrollPane listScroll;

	private final JTextField fileField;
	private final JComponent parentComponent;
	private final IFileCompleteActions completeActions;
	private final AWTEventListener awtListner = new AWTEventListener() {
		@Override public void eventDispatched(AWTEvent event) {
			Object source = event.getSource();
			if (source == parentComponent || source == listScroll
			||  source == list ||  source == filesListDialog) {
				
			} else 	if (event.getID() == MouseEvent.MOUSE_RELEASED) {
				hideDialog();
				completeActions.fieldExit();
			}
		}
	};
	protected DirectoryStream.Filter<Path> fileFilter;
	
	private MouseAdapter dialogMonitor = new MouseAdapter() {
		@Override public void mousePressed(final MouseEvent e) {
			final int index = list.getUI().locationToIndex(list, e.getPoint());
			if (SwingUtilities.isLeftMouseButton(e) && index > -1) {
				setSelectedPath(list.getModel().getElementAt(index));
			}
		}
	};
//	private MouseMonitor fieldMonitor = new MouseMonitor(true);
//	private MouseMonitor parentMonitor = new MouseMonitor(false);
//	private MouseMonitor m2 = new MouseMonitor(true);


	public ZOld_FileCompleteCaretListner2(
			JTextField fileField, JComponent parentComponent,
			DirectoryStream.Filter<Path> fileFilter,
			IFileCompleteActions completeFileActions) {
		super();
		this.fileField  = fileField;
		this.fileFilter = fileFilter;
		this.parentComponent = parentComponent;
		this.completeActions = completeFileActions;
//		parentComponent.addMouseListener(parentMonitor);
		
		fileField.addFocusListener(new FocusAdapter() {			
			@Override public void focusLost(final FocusEvent e) {
				hideDialog();
				completeActions.fieldExit();
			}
		});
//		fileField.addMouseListener(fieldMonitor);
	}

	@Override
	public void caretUpdate(final CaretEvent e) {
		if (skip) { return; }
		if (!autocompleteEnabled || !fileField.isVisible() || !fileField.isShowing()) {
			if (filesListDialog != null && filesListDialog.isVisible()) {
				hideDialog();
			}
			return;
		}

		if (filesListDialog == null) {
			filesListDialog = new JWindow(SwingUtilities.getWindowAncestor(parentComponent));
			filesListDialog.getContentPane().setLayout(new BorderLayout());
			filesListDialog.setFocusable(false);
//			filesListDialog.addMouseListener(parentMonitor);
			

			SwingUtilities.getWindowAncestor(parentComponent).addComponentListener(new ComponentAdapter() {
				@Override public void componentMoved(final ComponentEvent e) {
					hideDialog();
				}

				@Override public void componentResized(final ComponentEvent e) {
					hideDialog();
				}
			});

			list = new JList<Path>();
			list.setFocusable(false);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setCellRenderer(new ListCellRenderer<Path>() {
				@Override 
				public Component getListCellRendererComponent(JList<? extends Path> list, Path value, int index,
						boolean isSelected, boolean cellHasFocus) {
					return new JLabel(
								value.getFileName().toString(),
								IconManager.ICONS.iconForFile(value), 
								JLabel.LEADING);
				}
			});
//			list.addMouseListener(new MouseAdapter() {
//				@Override public void mousePressed(final MouseEvent e) {
//					final int index = list.getUI().locationToIndex(list, e.getPoint());
//					if (SwingUtilities.isLeftMouseButton(e) && index > -1) {
//						setSelectedPath(list.getModel().getElementAt(index));
//					}
//				}
//			});
			list.addMouseListener(dialogMonitor);
			list.addKeyListener(new KeyAdapter() {
				@Override public void keyPressed(final KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER & e.getModifiers() == 0) {
						setSelectedPath( list.getSelectedValue());
					}
				}
			});
			listScroll = new JScrollPane(list);
//			listScroll.addMouseListener(m2);

			filesListDialog.getContentPane().add(listScroll, BorderLayout.CENTER);

			fileField.addKeyListener(new KeyAdapter() {
				@Override public void keyPressed(final KeyEvent e) {
					if (filesListDialog.isShowing() && list.getModel().getSize() > 0) {
						if (list.getSelectedIndex() != -1) {
							if (e.getModifiers() == KeyEvent.CTRL_MASK) {
								if (e.getKeyCode() == KeyEvent.VK_ENTER) {
									hideDialog();
								}
							} else if (e.getModifiers() == 0) {
								switch (e.getKeyCode()) {
								case KeyEvent.VK_ESCAPE:
									hideDialog();
									break;
								case KeyEvent.VK_ENTER:
									setSelectedPath(list.getSelectedValue());
									break;
								case KeyEvent.VK_UP:
									int idx1 = list.getModel().getSize() - 1;
									if (list.getSelectedIndex() > 0) {
										idx1 = list.getSelectedIndex() - 1;
									}
									scrollToSelected(idx1);
									break;
								case KeyEvent.VK_DOWN:
									if (list.getSelectedIndex() == list.getModel().getSize() - 1) {
										scrollToSelected(0);
									} else {
										scrollToSelected(list.getSelectedIndex() + 1);
									}
									break;
								}
							}
						} else if (e.getModifiers() != KeyEvent.CTRL_MASK && e.getModifiers() != KeyEvent.ALT_MASK) {
							scrollToSelected(0);
						}
					}
				}

				private void scrollToSelected(int idx) {
					list.setSelectedIndex(idx);
					list.scrollRectToVisible(list.getUI().getCellBounds(list, idx, idx));
				}
			});


//			fileField.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseExited(MouseEvent e) {
//					System.out.print('*' + e.);
//					//hideDialog();
//				}
//				
//			});
			
			filesListDialog.addComponentListener(new ComponentAdapter() {
				@Override public void componentShown ( ComponentEvent e ) {
					Toolkit.getDefaultToolkit().addAWTEventListener(
							awtListner, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
				}

				@Override public void componentHidden(ComponentEvent e) {
					Toolkit.getDefaultToolkit().removeAWTEventListener(awtListner);
				}
			});
			
		}


		final String t = fileField.getText().substring(0, fileField.getCaretPosition());

		int beginIndex = t.lastIndexOf(File.separator);
		beginIndex = beginIndex != -1 ? beginIndex + 1 : 0;

		// Parent file
		final String parentPath = t.substring(0, beginIndex);
		final File parent = parentPath.trim().equals("") ? null : new File(parentPath);

		if (parent != null && parent.exists()) {
			final List<Path> similar = getSimilarFiles(parent, t.substring(beginIndex));
			if (similar != null && similar.size() > 0) {
				updateList(similar);
			} else {
				hideDialog();
			}
		}
		// }
		// } ).start ();

	}

	private void updateList(final List<Path> similarFiles) {
		if (! skip) {
			SwingUtilities.invokeLater(new Runnable() {
				@SuppressWarnings("serial")
				@Override public void run() {
					list.setModel(new AbstractListModel<Path>() {
						@Override public int getSize() {
							return similarFiles.size();
						}
	
						@Override public Path getElementAt(final int i) {
							return similarFiles.get(i);
						}
					});
					list.setVisibleRowCount(Math.min(similarFiles.size(), 15));
					list.updateUI();
					if (similarFiles.size() > 0) {
						list.setSelectedIndex(0);
					}
	
					// Fixing window bounds
					final Point pos = fileField.getLocationOnScreen();
					filesListDialog.setSize(fileField.getWidth(), listScroll.getPreferredSize().height);
					filesListDialog.setLocation(
							fileField.getComponentOrientation().isLeftToRight() 
									? pos.x
									: pos.x + fileField.getWidth() - filesListDialog.getWidth(),
									parentComponent.getLocationOnScreen().y + parentComponent.getHeight());
	
					// Showing dialog if needed
					if (!filesListDialog.isShowing()) {
						filesListDialog.setVisible(true);
//						Toolkit.getDefaultToolkit().addAWTEventListener(
//						          new Listener(), AWTEvent.MOUSE_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
//						        

					}
				}
			});
		}
	}

	private void hideDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				filesListDialog.setVisible(false);
			}
		});
	}

	private void setSelectedPath(final Path path) {
		String text = path.toString();
		text = text.endsWith(File.separator) || ! Files.isDirectory(path) ? text : text + File.separator;
		try {
			skip = true;
			fileField.setText(text);
			fileField.setCaretPosition(text.length());
			
			File f = new File(text);
			if (f != null && f.exists()) {
				completeActions.fireFileChanged(f);
			}
		} finally {
			skip = false;
		}
		filesListDialog.setVisible(false);
	}

	private List<Path> getSimilarFiles(final File file, final String namePart) {
		DirectoryStream.Filter<Path> filter = fileFilter;
		if (namePart != null && namePart.length() != 0) {
			final String searchText = namePart.toLowerCase();
			filter = new DirectoryStream.Filter<Path>() {
				@Override public boolean accept(Path entry) throws IOException {
					return entry.getFileName().toString().toLowerCase().contains(searchText)
						&& fileFilter.accept(entry);
				}
			};
		}
		
		List<Path> readDirectory = FileUtils.readDirectory(file.toPath(), filter, 90);
		Collections.sort(readDirectory);
		return readDirectory;
	}

	public boolean isDialogVisible() {
		return filesListDialog != null && filesListDialog.isVisible();
	}
	

//	TimerTask lastCheckPosition;
//	private class MouseMonitor extends MouseAdapter {
//		boolean inField = false;
//		final boolean doHide;
//		public MouseMonitor(boolean doHide) {
//			this.doHide = doHide;
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
////			System.out.println(">>" + e.getSource().getClass());
//			cancelCheck();
//			inField = true;
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
////			System.out.println("<<" + e.getSource().getClass());
//			inField = false;
//			//Point locationOnScreen = e.getLocationOnScreen();
//			if (doHide && isDialogVisible()) {
//				cancelCheck();
//				TimerTask checkPosition = new TimerTask() {
//					@Override public void run() {
//						if (!(dialogMonitor.inField || fieldMonitor.inField || parentMonitor.inField 
//						||  (listScroll.getMousePosition() != null))) {
//							hideDialog();
//							completeActions.fieldExit();
//						}
//						//}
//					}
//				};
//				
//				new Timer().schedule(checkPosition, 750);
//				lastCheckPosition = checkPosition;
//			}
//		}
//		@Override
//		public void mouseExited(MouseEvent e) {
//			System.out.println("<<" + e.getSource().getClass());
//			inField = false;
//			//Point locationOnScreen = e.getLocationOnScreen();
//			if (doHide && isDialogVisible()) {
//				TimerTask checkPosition = new TimerTask() {
//					@Override public void run() {
//						if (!(dialogMonitor.inField || fieldMonitor.inField || parentMonitor.inField 
//						||  (listScroll.getMousePosition() != null))) {
//								hideDialog();
//							}
//						//}
//					}
//				};
//				
//				new Timer().schedule(checkPosition, 750);
//			}
//		}

//		private void cancelCheck() {
//			if (lastCheckPosition != null) {
//				lastCheckPosition.cancel();
//			}
//		}
//	}
}
