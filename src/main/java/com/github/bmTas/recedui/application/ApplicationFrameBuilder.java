package com.github.bmTas.recedui.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import com.github.bmTas.recedui.application.properties.PropertiesDataStore;
import com.github.bmTas.recedui.common.SetVisible;
import com.github.bmTas.recedui.def.application.IApplicationParam;
import com.github.bmTas.recedui.def.application.IApplicationProperties;
import com.github.bmTas.recedui.def.application.displays.ISetVisible;
import com.github.bmTas.recedui.def.component.IBasicFrame;

public class ApplicationFrameBuilder {
	
	private ApplParam applParam = new ApplParam();
	
	ApplicationFrameBuilder(IBasicFrame basicFrame) {
		applParam.frame = basicFrame;
	}
	
	public ApplicationFrameBuilder setCreateMenuBar(boolean createMenuBar) {
		applParam.aMenuBar = createMenuBar;
		return this;
	}
	
	public ApplicationFrameBuilder setCreateToolBar(boolean createToolBar) {
		applParam.aToolBar = createToolBar;
		return this;
	}
	
	public ApplicationFrameBuilder setVisibleClass(ISetVisible visibilityClass) {
		applParam.setVisibleClass = visibilityClass;
		return this;
	}
	
//	
//	public ApplicationFrameBuilder setPropertiesFileNameInHomeDirectory(String fileName, boolean addDotOnNix) 
//	throws FileNotFoundException, IOException {
//		applParam.applicationProperties = PropertiesDataStore.newPropertiesInHomeDirectory(fileName, addDotOnNix);
//		return this;
//	}

	public ApplicationFrameBuilder setPropertiesFileName(String fileName) 
	throws FileNotFoundException, IOException {
		//applParam.applicationProperties = PropertiesDataStore.newPropertiesFullFileName(fileName);
		applParam.propertiesFileName = fileName;
		return this;
	}
	
	public ApplicationFrameBuilder setPropertiesDirectory(String directory) {
		applParam.appPropertiesDirectory = directory;
		return this;
	}
	public ApplicationFrameBuilder setPropertiesDirectoryRelativeToHome(String directory) {
		applParam.appPropertiesDirectory =  Paths.get(System.getProperty("user.home"),directory).toString();
		return this;
	}
	
	public UiApplication build() {
		return new UiApplication(applParam);
	}

	private static class ApplParam implements IApplicationParam {

		IBasicFrame frame;
		boolean aMenuBar=true, aToolBar=true;
		ISetVisible setVisibleClass;
		IApplicationProperties applicationProperties;
		String appPropertiesDirectory, propertiesFileName;
		
		@Override
		public String getAppPropertiesDirectory() {
			return appPropertiesDirectory;
		}

		@Override
		public IBasicFrame getFrame() {
			return frame;
		}

		@Override
		public boolean isCreateMenuBar() {
			return aMenuBar;
		}

		@Override
		public boolean isCreateToolBar() {
			return aToolBar;
		}

		@Override
		public ISetVisible getSetVisibleClass() {
			return setVisibleClass == null ? new SetVisible() : setVisibleClass;
		}

		@Override
		public IApplicationProperties getApplicationProperties() {
			if (applicationProperties == null && appPropertiesDirectory != null && propertiesFileName != null) {
				try {
					applicationProperties = PropertiesDataStore.newPropertiesFullFileName(
							Paths.get(appPropertiesDirectory, propertiesFileName).toString());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return applicationProperties;
		}
		
		
	}
}
