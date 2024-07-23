package com.github.bmTas.recedui.fileChooser;


import java.awt.Component;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import com.github.bmTas.recedui.def.file.FileChooserDefs;
import com.github.bmTas.recedui.fileFields.params.FileFieldParamBldr;
import com.github.bmTas.recedui.fileFields.params.IFileChooserParam;
import com.github.bmTas.recedui.fileFields.params.IFileChooserParamBldr;


@SuppressWarnings("serial")
public class UiFileChooserDialog extends JDialog implements ActionListener {
	
	private int accepted = JFileChooser.CANCEL_OPTION;
	private UiFileChooser filePnl;

	private int fileSelectionMode = JFileChooser.FILES_ONLY;
	
	/**
	 * Create new FileChooser Parameter
	 * @return
	 */
	public static IFileChooserParamBldr newParam() {  return new FileFieldParamBldr(); }
	
	public UiFileChooserDialog(Component owner, IFileChooserParam params) {
		this(owner, new UiFileChooser(params));
	};
	
	public UiFileChooserDialog(Component owner, UiFileChooser pnl) {
		super(
				owner == null ? null : SwingUtilities.getWindowAncestor (owner), 
				pnl.getChooserType().dialogName, 
				Dialog.ModalityType.APPLICATION_MODAL);
		filePnl = pnl;
		super.getContentPane().add(pnl.getGuiContainer());
		
		super.pack();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		accepted = JFileChooser.CANCEL_OPTION;
		if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
			accepted = JFileChooser.APPROVE_OPTION;
		}
		UiFileChooserDialog.this.setVisible(false);
	}
	
//	public void setMultiSelectionEnabled(boolean multiSelectionEnabled) {
//		 filePnl.setMultiSelectionEnabled(multiSelectionEnabled);
//	}
//	 
//	public void addApproveActionListener(ActionListener l) {
//		 filePnl.approveButton.addActionListener(l);
//	}
	 
	public int showDialog(Component parent) throws HeadlessException {
		
		accepted = JFileChooser.CANCEL_OPTION;
		filePnl.addActionListner(this);
		this.setVisible(true);
		filePnl.removeActionListner(this);
		
		return accepted;
	}

	
	public File getSelectedFile() {
		return filePnl.getSelectedFile();
	}

	/**
	 * @return
	 * @see com.alee.laf.bm.filechooser.UiFileChooser#getSelectedFiles()
	 */
	public File[] getSelectedFiles() {
		return filePnl.getSelectedFiles();
	}

    public void setSelectedFile ( final File file ) {
    	filePnl.setSelectedFile(file);
    }

    public void setSelectedFiles ( final Collection<File> files ) {
    	if ( files != null && files.size() > 0) {
    		//filePnl.setCurrentFolder(files.iterator().next());
    		filePnl.setSelectedFiles(files);
    	}
    }
    
    public void setFileSelectionMode(int mode) {
    	if (fileSelectionMode == mode) { return; }
    	
    	fileSelectionMode = mode;
    	filePnl.setFileSelectionModeImp(mode);
    }
    
	public void setViewType(FileChooserDefs.FileChooserDetailDisplay viewType) {
		filePnl.setFileListType(viewType);
	}

}
