package com.github.bmTas.recedui.fileFields.zcopy;

import java.io.File;
/**
 * interface to File / directory field, it allows the setting / retrieveing
 * of the file
 * 
 * @author Bruce Martin
 *
 */
public interface ISetSelectedFiles {
	
    public void setSelectedFile ( final File files, boolean notify );

	public File getSelectedFile();
}
