/*
 * Author Bruce Martin
 * 
 * License: either    (Apache 2.0) or (LGPL 3 or later) 
 */
package com.github.bmTas.recedui.fileFields;

import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.github.bmTas.recedui.def.common.IListPopupActions;
import com.github.bmTas.recedui.def.file.IFileListItem;
import com.github.bmTas.recedui.def.file.IGetPath;
import com.github.bmTas.recedui.listManagers.FileListItem;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.xfiles.FileUtils;



/**
 * Auto Complete Caret listner
 * 
 * @author Bruce Martin
 *
 */
public class FileCompleteCaretListner implements CaretListener {
	
	private static List<Path> EMPTY_PATH_LIST = Collections.<Path>emptyList();
	public boolean autocompleteEnabled = true, skip=false;

	private final JTextField fileField;
	private final JComponent parentComponent;
	private final IFileCompleteActions completeActions;

	protected DirectoryStream.Filter<Path> fileFilter;
	
	private UiFileSelectionList filesListDialog;


	public FileCompleteCaretListner(
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
				hideFileListDialog();
				completeActions.fieldExit();
			}
		});
//		fileField.addMouseListener(fieldMonitor);
	}

	@Override
	public void caretUpdate(final CaretEvent e) {
		if (skip) { return; }
		if (!autocompleteEnabled || !fileField.isVisible() || !fileField.isShowing()) {
			hideFileListDialog();
			return;
		}

		if (filesListDialog == null) {
			filesListDialog = new UiFileSelectionList(
					StyleManager.EMPTY_STYLE, parentComponent, false,
					new IListPopupActions<IFileListItem>() {
						@Override public void popupHidden() { }
				
						@Override public void fireFileChanged(IFileListItem f) {
							setSelectedPath(f.getFile());
						}
					});

			fileField.addKeyListener(new KeyAdapter() {
				@Override public void keyPressed(final KeyEvent e) {
					int listSize;
					if (filesListDialog.isShowing() && (listSize = filesListDialog.getListSize()) > 0) {
						if (filesListDialog.getSelectedIndex() != -1) {
							if (e.getModifiers() == KeyEvent.CTRL_MASK) {
								if (e.getKeyCode() == KeyEvent.VK_ENTER) {
									filesListDialog.hideDialog();
								}
							} else if (e.getModifiers() == 0) {
								switch (e.getKeyCode()) {
								case KeyEvent.VK_ESCAPE:
									filesListDialog.hideDialog();
									break;
								case KeyEvent.VK_ENTER:
									setSelectedPath(filesListDialog.getSelectedValue().getPath().toFile());
									break;
								case KeyEvent.VK_UP:
									int idx1 = listSize - 1;
									if (filesListDialog.getSelectedIndex() > 0) {
										idx1 = filesListDialog.getSelectedIndex() - 1;
									}
									filesListDialog.scrollToSelected(idx1);
									break;
								case KeyEvent.VK_DOWN:
									if (filesListDialog.getSelectedIndex() == listSize - 1) {
										filesListDialog.scrollToSelected(0);
									} else {
										filesListDialog.scrollToSelected(filesListDialog.getSelectedIndex() + 1);
									}
									break;
								}
							}
						} else if (e.getModifiers() != KeyEvent.CTRL_MASK && e.getModifiers() != KeyEvent.ALT_MASK) {
							filesListDialog.scrollToSelected(0);
						}
					}
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
				filesListDialog.hideDialog();
			}
		}
	}

	private void hideFileListDialog() {
		if (filesListDialog != null && filesListDialog.isShowing()) {
			filesListDialog.hideDialog();
		}
	}

	private void updateList(final List<Path> fileList) {
		if (! skip) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override public void run() {
					filesListDialog.updateFileList(FileListItem.convertShortNameList(fileList));
					
					final Point pos = fileField.getLocationOnScreen();
					filesListDialog.setPosition(
							fileField.getComponentOrientation().isLeftToRight() 
									? pos.x
									: pos.x + fileField.getWidth() - filesListDialog.getGuiContainer().getWidth(),
							parentComponent.getLocationOnScreen().y + parentComponent.getHeight(),
							parentComponent.getWidth()
					);
				}
			});
		}
	}



	private void setSelectedPath(final File path) {
		String text = path.getAbsolutePath();
		text = text.endsWith(File.separator) || ! path.isDirectory() ? text : text + File.separator;
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
		
		try {
			List<Path> readDirectory = FileUtils.readDirectory(file.toPath(), filter, 90);
			Collections.sort(readDirectory);
			return readDirectory;
		} catch (Exception e) {
			return EMPTY_PATH_LIST;
		}
	}

	public boolean isDialogVisible() {
		return filesListDialog != null && filesListDialog.isShowing();
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
