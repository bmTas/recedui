package com.github.bmTas.recedui.fileFields.zcopy;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.github.bmTas.recedui.buttons.ArrowButton;
import com.github.bmTas.recedui.buttons.UiButton;
import com.github.bmTas.recedui.common.ShapePainters;
import com.github.bmTas.recedui.def.common.IGetSelectedFile;
import com.github.bmTas.recedui.def.common.ISetSelectedFile;
import com.github.bmTas.recedui.def.file.IFileChangedListner;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.standard.component.BmPanel;
import com.github.bmTas.recedui.styles.StyleAction;
import com.github.bmTas.recedui.styles.StyleManager;

@SuppressWarnings("serial")
public class ZOld_UiBasicBreadCrumbFileField extends BmPanel implements IGetSelectedFile, ISetSelectedFile, IFileCompleteActions {

	private final boolean isDirectory;
	private FileCrumb currentCrumb;
	private final ArrayList<FileCrumb> fileCrumbs = new ArrayList<FileCrumb>();
	private final UiBasicFileTextField fileTxt;
	
	private int width = -1;
	
	private File currentFile;
	private boolean beenShortend = false, showingText = false;
	
	private FileChangedListnerList changeListners = new FileChangedListnerList();
	
	
	
//	MouseAdapter ma = new MouseAdapter() {
//		@Override public void mouseEntered(MouseEvent e) {
//			Object source = e.getSource();
//			if (isCrumbPopupVisible()
//			&& currentCrumb.fileBtn != source && currentCrumb.arrowBtn != source ) {
//				for (FileCrumb name: names) {
//					if (name.arrowBtn == source || name.fileBtn == source) {						
//						name.arrowBtn.getStyleId().apply(StyleAction.mouseOver, name.arrowBtn);
//						name.fileBtn.getStyleId().apply(StyleAction.mouseOver, name.fileBtn);
//						name.showPopup();
//						break;
//					}
//				}
//			}
//		}
//		@Override public void mouseExited(MouseEvent e) {
//			//setDirection(ShapePainters.LEFT);
//		}
//	};

	private FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 2, 0);
	private BorderLayout borderLayout = new BorderLayout();
	public ZOld_UiBasicBreadCrumbFileField(File f, boolean isDirectory, boolean fileComplete) {
		super(StyleManager.styles().fileBreadCrumbField(), new FlowLayout(FlowLayout.LEFT));

		this.isDirectory = isDirectory;
		currentFile = f;
		layoutCrumbs();
		
		this.addMouseListener(new MouseAdapter() {
			@Override public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					showTextField();
				}
			}			
		});
		
		this.fileTxt = new UiBasicFileTextField(null, super.getMainComponent(), isDirectory, fileComplete, this);
		this.fileTxt.getMainComponent().addFocusListener(new FocusAdapter() {
			@Override public void focusLost(FocusEvent e) {
				//System.out.println(fileTxt.getText());
				setSelectedFile(new File(fileTxt.getText()), true);
			}
		});

		super.setName("FileBreadCrumbField");
	}
	
	private boolean isCrumbPopupVisible() {
		return currentCrumb != null && (! currentCrumb.popupHidden());
	}


	
	public File getSelectedFile() {
		return currentFile;
	}


	public void setSelectedFile(File file, boolean notifyUsers) {
		if (file == null) { return; }
		File old = currentFile;
		if (isDirectory && ! file.isDirectory()) {
			file = file.getParentFile();
		}
		currentFile = file;
		layoutCrumbs();
		JPanel p = this.getMainComponent();
		p.revalidate();
		p.repaint();
	
		if (notifyUsers) {
			notifyUsersOfNewFile(old, file);
		}
	}

	private void layoutCrumbs() {
		File f = currentFile;
		
		if (isCrumbPopupVisible()) {
			currentCrumb.popup.setVisible(false);
		}
		fileCrumbs.clear();
		
		while (f != null) {
			fileCrumbs.add(new FileCrumb(f));
			f = f.getParentFile();
		}
		
		JPanel p = this.getMainComponent();
		p.removeAll();
	
		p.setLayout(flowLayout);
		beenShortend = false;
		for (int i = fileCrumbs.size() - 1; i >= 0; i--) {
			p.add(fileCrumbs.get(i).fileBtn.getMainComponent());
			if (i > 0 || fileCrumbs.get(i).file.isDirectory()) {
				p.add(fileCrumbs.get(i).arrowBtn);
			}
			shorten(p);
		}
		
		showingText = false;
	}

	private void shorten(JPanel p) {
		//System.out.println("~~~>> " + this.getWidth() + " " + this.getPreferredSize().width + " " + width);
		width = this.getWidth();
		while (width > 0 && fileCrumbs.size() > 1 && this.getPreferredSize().width > width) {
			int index = fileCrumbs.size() - 1;
			
			p.remove(fileCrumbs.get(index).fileBtn.getMainComponent());
			p.remove(fileCrumbs.get(index).arrowBtn);
			fileCrumbs.remove(index);
			beenShortend = true;
		}
	}
	
	private void showTextField() {
		if (! showingText) {
			fileTxt.setSelectedFile(currentFile);
			JPanel p = this.getMainComponent();
			p.removeAll();
			
			p.setLayout(borderLayout);
			p.add(fileTxt.getMainComponent(), BorderLayout.CENTER);
			fileTxt.getMainComponent().requestFocus();
			fileTxt.getMainComponent().setCaretPosition(currentFile.toString().length());

			showingText = true;
			p.revalidate();
			p.repaint();
		}
	}
	
	@Override
	public void fireFileChanged(File f) {
		File old  = currentFile;
		currentFile = f;
		
		notifyUsersOfNewFile(old, f);
	}


	@Override
	public void fieldExit() {
		setSelectedFile(new File(fileTxt.getFileName()), true);
	}

	private final void notifyUsersOfNewFile(File old , File f) {
		changeListners.notifyUsersOfNewFile(isDirectory, old, f);
	}
	
	public void addFileChangedListner(IFileChangedListner listner) {
		changeListners.listners.add(listner);
	}

	private class FileCrumb implements ActionListener, PopupMenuListener {
		final File file;
		final UiButton fileBtn;
		final ArrowButton arrowBtn = new ArrowButton(StyleManager.styles().fileBreadCrumbArrow(), ShapePainters.RIGHT);
		ZOld_FileListPopup popup;
		private long popupBecameInvisibleAt = System.currentTimeMillis();
		
		boolean highlighted = false;
		
		FileCrumb(File f) {
			file = f;
			
			if (f.getParentFile() == null) {
				fileBtn = new UiButton(
						StyleManager.styles().fileBreadCrumbItem(), 
						IconManager.ICONS.monitor.icon());
				String name = f.getPath();
				if (! "/".equals(name)) {
					while (name.endsWith("/") || name.endsWith("\\")) {
						name = name.substring(0, name.length()-1);
					}
					fileBtn.getMainComponent().setText(name);
				}
			} else {
				fileBtn = new UiButton(StyleManager.styles().fileBreadCrumbItem(), f.getName());
			}
			
//			fileBtn.getJComponent().setContentAreaFilled(false);
//			arrowBtn.setContentAreaFilled(false);
//			fileBtn.setBorder(BorderFactory.createEmptyBorder());
//			fileBtn.setBackground(Color.WHITE);
			//fileBtn.setOpaque(false);
			MouseAdapter ma = new MouseAdapter() {
				@Override public void mouseEntered(MouseEvent e) {
					if (! highlighted) {
						synchronized (arrowBtn) {
							arrowBtn.getStyleId().apply(StyleAction.mouseOver, arrowBtn);
							fileBtn.getStyleId().apply(StyleAction.mouseOver, fileBtn);
							highlighted = true;
						}
						
						for (FileCrumb crumb : fileCrumbs) {
							if (crumb != FileCrumb.this) {
								crumb.doReset();
							}
						}

					}

					if (isCrumbPopupVisible()
					&& currentCrumb != FileCrumb.this) {	
						showPopup();
					}
				}
				@Override public void mouseExited(MouseEvent e) {
					if (popupHidden()) {
						resetStyle();
					}
				}
			};

			
			Dimension preferredSize2 = fileBtn.getMainComponent().getPreferredSize();
			int height2 = preferredSize2.height+2;
			fileBtn.getMainComponent().setPreferredSize(new Dimension(preferredSize2.width, height2));
			arrowBtn.setPreferredSize(new Dimension((height2 + 1) * 3 / 4, height2));
			
			fileBtn.getMainComponent().addMouseListener(ma);
			arrowBtn.addMouseListener(ma);
			fileBtn.getMainComponent().addActionListener(this);
			arrowBtn.addActionListener(this);
		}
		
		private void resetStyle() {
			if (highlighted && (! arrowBtn.isFocusOwner()) && (! fileBtn.getMainComponent().isFocusOwner())) {
				doReset();
			}
		}
		
		boolean popupHidden() {
			return popup == null || ! popup.isVisible();
		}

		private void doReset() {
			if (highlighted) {
				synchronized (arrowBtn) {
					if (highlighted) {
						arrowBtn.getStyleId().apply(StyleAction.mouseExit, arrowBtn);
						fileBtn.getStyleId().apply(StyleAction.mouseExit, fileBtn);
						highlighted = false;
					}
				}
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println("ap " + (e.getSource() == fileBtn.getJComponent()) + " " );
			if (e.getSource() == fileBtn.getMainComponent()) {
				setSelectedFile(file, true);
			} else {
				long timeDiff = System.currentTimeMillis() - popupBecameInvisibleAt;

				if ((popup != null && (popup.isVisible()) || (timeDiff > 0 && timeDiff < 650)) ) {
					//System.out.println(" Hide popup " +  file.getName());
					popup.setVisible(false);
				} else {
					showPopup();
				}
			}
		}

		private void showPopup() {
			if (popup == null) {
				popup = new ZOld_FileListPopup(this.arrowBtn, null, ZOld_UiBasicBreadCrumbFileField.this, file.toPath(), isDirectory, 0, 0);
				popup.addPopupMenuListener(this);
			}
			
			popup.showPopup();
			synchronized (arrowBtn) {
				arrowBtn.setDirection(ShapePainters.DOWN);
			}
			if (currentCrumb != null && currentCrumb != this) {
				if (isCrumbPopupVisible()) {
					//System.out.println("x Hide popup " + currentCrumb.file.getName());
					currentCrumb.popup.setVisible(false);
				}
				currentCrumb.resetStyle();
			}
			currentCrumb = this;
		}
		
		@Override 
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			resetArrowDirection();
		}

		private void resetArrowDirection() {
			synchronized (arrowBtn) {
				System.out.println("Reset Direction \t" +  file.getName());

				popupBecameInvisibleAt = System.currentTimeMillis();
				arrowBtn.setDirection(ShapePainters.RIGHT);
			}
		}

		@Override
		public void popupMenuCanceled(PopupMenuEvent e) {
			resetArrowDirection();
		}

	}
//	
//	public static void main(String[] a) {
//    	JFrame f = new JFrame();
//    	JPanel p = new JPanel(new BorderLayout());
//    	
//    	p.add(new UiBasicBreadCrumbFileField(new File("/home/bruce/work/src/fontchooser-master/"), false, true), BorderLayout.CENTER);
//    	//p.add(new JTextField(25));
//
//    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	f.getContentPane().add(p);
//		f.pack();
//		f.setVisible(true);
//	}
}
