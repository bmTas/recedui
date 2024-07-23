package com.github.bmTas.recedui.xEnvironment;

import java.io.File;

public interface IEnvironmentValues {

	public File getDocumentDirectory();

	public File getDesktopDirectory();

	public File[] getStandardMountPoints();

	public File[] getOtherMountPoints();
	
	public boolean isQuauaAvailable(); 
}
