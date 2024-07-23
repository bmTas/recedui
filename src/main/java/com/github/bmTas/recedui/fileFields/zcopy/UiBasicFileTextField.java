package com.github.bmTas.recedui.fileFields.zcopy;

import java.io.File;

import javax.swing.JComponent;

import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.text.UiTextField;
import com.github.bmTas.recedui.xEnvironment.Env;
import com.github.bmTas.recedui.xfiles.FileUtils;

public class UiBasicFileTextField extends UiTextField {

	                 File parent;
	private final boolean showDirectory;
	private boolean isFileSelecton;
	private FileCompleteCaretListner caretListener;
	UiBasicFileTextField(
			File file,
			JComponent parentComponent, boolean isDirectory, boolean completeFile,
			IFileCompleteActions actions) {
		this(StyleManager.styles().basicFileField(), file, parentComponent, isDirectory, completeFile, true, actions);
	}
	UiBasicFileTextField(IStyle style,
			File file,
			JComponent parentComponent,
			boolean isDirectory, boolean completeFile, boolean showDirectory,
			IFileCompleteActions actions) {
		super(style);
		
		this.showDirectory = showDirectory;
		this.isFileSelecton = ! isDirectory;
		
		setSelectedFile(file);
	
		
		if (completeFile) {
			caretListener = new FileCompleteCaretListner(
					super.component, 
					parentComponent, 
					FileUtils.getFileOrDirectiryFilter(isDirectory),
					actions);
			super.component.addCaretListener(caretListener);
		}
	}
	
	public String getFileName() {
		if (showDirectory) {
			return super.component.getText();
		}
		return getSelectedFile().getAbsolutePath();
	}
	
	public void setFileName(String filename) {
		if (showDirectory) {
			super.component.setText(filename);
		} else {
			setSelectedFile(new File(filename));
		}
	}
	
	public File getSelectedFile() {
		String fileName = super.component.getText();
		if (showDirectory) {
			return new File(fileName);
		} 
		File file = new File(parent, fileName);
		if ((! file.exists()) 
		&& (fileName.startsWith("/") || (Env.IS_WINDOWS && fileName.contains(":\\")))) {
			return new File(fileName);
		}
		return file;
	}

	public void setSelectedFile(File file) {
		if (file != null) {		
			String s = "";
			parent = file.getParentFile();

			if (showDirectory) {
				s = file.getAbsolutePath();
			} else if (isFileSelecton && file.isDirectory()) {
				parent = file;				
			} else {
				s = file.getName();
			}
			setFileText(s);
		}
	}
	
	public void setDirectory(File file) {
		if (showDirectory) {
			setSelectedFile(file);
		} else {
			parent = file;
			setFileText("");
		}
	}
	
	private void setFileText(String s) {
		if (caretListener == null || caretListener.isDialogVisible()) {
			super.component.setText(s);
		} else {
			try {
				super.component.removeCaretListener(caretListener);
				super.component.setText(s);
			} finally {
				super.component.addCaretListener(caretListener);
			}
		}
	}
	
	public void setDirectory(boolean isDirectory) {
		this.isFileSelecton = ! isDirectory;
		if (caretListener != null) {
			caretListener.fileFilter = FileUtils.getFileOrDirectiryFilter(isDirectory);
		}
	}
	
//	public static void main(String[] a) {
//    	JFrame f = new JFrame();
//    	JPanel p = new JPanel(new BorderLayout());
//    	FileTextField ft = new FileTextField(new File("/home/bruce/work/src/fontchooser-master/"), p, false, true);
//    	
//
//    	p.add(ft.getJComponent(), BorderLayout.CENTER);
//    	//p.add(new JTextField(25));
//
//    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	f.getContentPane().add(p);
//		f.pack();
//		f.setVisible(true);
//	}

}
