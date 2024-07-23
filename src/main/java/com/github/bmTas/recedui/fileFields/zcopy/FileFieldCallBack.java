package com.github.bmTas.recedui.fileFields.zcopy;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.nio.file.Path;

import com.github.bmTas.recedui.def.common.IListPopupActions;

public class FileFieldCallBack implements ISetSelectedFiles, IListPopupActions<Path>, IFileCompleteActions, FocusListener {
	
	private boolean isDirectory;

	private final ISetSelectedFiles fileField;
	final FileChangedListnerList changeListners = new FileChangedListnerList();
	private File oldFile;

	
	public FileFieldCallBack(boolean isDirectory, ISetSelectedFiles fileField, File oldFile) {
		super();
		this.isDirectory = isDirectory;
		this.fileField = fileField;
		this.oldFile = oldFile;
	}
	
	@Override
	public void fireFileChanged(Path f) {
		setSelectedFile(f.toFile(), true);
	}

	@Override
	public void popupHidden() {
		
	}

//	@Override public void processSelection(IListItem listItem) {
//		setSelectedFile(new File(listItem.getItemText()), true);
//	}

	@Override public void setSelectedFile(File file, boolean notify) {
		fileField.setSelectedFile(file, notify);
		//notifyUsersOfNewFile(file);
	}
	
	@Override public File getSelectedFile() {
		return fileField.getSelectedFile();
	}
	

	@Override public void fireFileChanged(File f) {
		notifyUsersOfNewFile(f);
	}

	@Override public void fieldExit() {
		
	}

	/**
	 * @param isDirectory the isDirectory to set
	 */
	public final void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		notifyUsersOfNewFile(fileField.getSelectedFile());
	}

	
	private final void notifyUsersOfNewFile(File f) {
		if (f != null && ! f.equals(oldFile)) {
			changeListners.notifyUsersOfNewFile(isDirectory, oldFile, f);
		}
		oldFile = f;
	}

}
