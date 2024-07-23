package com.github.bmTas.recedui.fileFields.params;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.github.bmTas.recedui.def.file.FileChooserDefs;
import com.github.bmTas.recedui.def.file.FileChooserDefs.FileChooserSide;
import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFiles;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.xEnvironment.Env;
import com.github.bmTas.recedui.xfiles.FileExtensionFilter;
import com.github.bmTas.recedui.xfiles.IFileChooserFilter;


//import net.sf.RecordEditor.utils.params.IEnvironmentValues;
//import net.sf.RecordEditor.utils.params.Parameters; 

public class FileFieldParamBldr implements IFileFieldParamAndBuilder, 
IFileChooserParamAndBuilder, IChooseFileBtnParamAndBuilder {

	public static IFileFieldParamBldr newFileFieldParamBuilder() {
		return new FileFieldParamBldr();
	}
	public static IFileChooserParamBldr newFileChooserParamBuilder() {
		return new FileFieldParamBldr();
	}
	public static IFileChooserOptionsBldr newFileChooserOptionBuilder() {
		return new FileFieldParamBldr();
	}

	public static IFileChooserParamAndBuilder newFileChooserParamAndBuilder() {
		return new FileFieldParamBldr();
	}
	
	public static IChooseFileBtnParamBldr newChooseFileBtnParam() {
		return new FileFieldParamBldr();
	}
	
	private IRecentList recentFiles;
	private IUpdateableList favouriteFiles;
	
	private IRecentList recentDirectory;
	private IUpdateableList favouriteDirectory;
	
	private FileChooserDefs.FileChooserType chooserType = FileChooserDefs.FileChooserType.open;
	private FileChooserDefs.ControlButtonDisplay showControlButtons = FileChooserDefs.ControlButtonDisplay.ALL_CONTROLS;
	private FileChooserDefs.FileChooserDetailDisplay fileListType = FileChooserDefs.FileChooserDetailDisplay.details;
	private FileChooserDefs.FileChooserSide sidePaneType = Env.IS_NIX 
						? FileChooserDefs.FileChooserSide.PLACES
						: FileChooserDefs.FileChooserSide.FOLDER_TREE;


	private File initialPath;
	private boolean directory=false, multiSelectionEnabled=false;
	
	private boolean fileCompletion = true;
	private boolean allowNewFiles = true;
	private boolean showDirectory = true;
	private boolean showHidenFiles = false;
	
	private IFileChooserOptions fileChooserOptions;
	private IStyle style;
	
	private ArrayList<IFileChooserFilter> filters = new ArrayList<IFileChooserFilter>();
	private IUpdateableList regExpFavourites;
	
	
	
	public final List<IFileChooserFilter> getFilters() {
		return filters;
	}
	
	
	@Override public FileFieldParamBldr addFileFilterOption(IFileChooserFilter filter) {
		filters.add(filter);
		return this;
	}

	/**
	 * @param recentFiles the recentFiles to set
	 */
	@Override public FileFieldParamBldr addFileExtensionOption(String description, String... extensions) {
		filters.add(new FileExtensionFilter(description, extensions));
		return this;
	}
	

	
	/**
	 * @return the recentList
	 */
	 @Override public IRecentList getRecent() {
		return recentFiles;
	}

	/**
	 * @param recentFiles the recentFiles to set
	 */
	@Override public FileFieldParamBldr recentFiles(IRecentList recentFiles) {
		this.recentFiles = recentFiles;
		return this;
	}

	/**
	 * @return the favouriteList
	 */
	@Override public IUpdateableList getFavourites() {
		return favouriteFiles;
	}
	/**
	 * @param favouriteFiles the favouriteFiles to set
	 */
	@Override public FileFieldParamBldr favouriteFiles(IUpdateableList favouriteFiles) {
		this.favouriteFiles = favouriteFiles;
		return this;
	}
	/**
	 * @return the recentDirectory
	 */
	@Override public IRecentList getRecentDirectory() {
		return recentDirectory;
	}
	/**
	 * @param recentDirectory the recentDirectory to set
	 */
	@Override public FileFieldParamBldr recentDirectory(IRecentList recentDirectory) {
		this.recentDirectory = recentDirectory;
		return this;
	}
	/**
	 * @return the favouriteDirectory
	 */
	@Override public IUpdateableList getFavouriteDirectory() {
		return favouriteDirectory;
	}
	/**
	 * @param favouriteDirectory the favouriteDirectory to set
	 */
	@Override public FileFieldParamBldr favouriteDirectory(IUpdateableList favouriteDirectory) {
		this.favouriteDirectory = favouriteDirectory;
		return this;
	}
	/**
	 * @return the chooserType
	 */
	@Override public FileChooserDefs.FileChooserType getChooserType() {
		return chooserType;
	}
	/**
	 * @param chooserType the chooserType to set
	 */
	@Override public FileFieldParamBldr chooserType(FileChooserDefs.FileChooserType chooserType) {
		this.chooserType = chooserType;
		return this;
	}
	/**
	 * @return the showControlButtons
	 */
	@Override public FileChooserDefs.ControlButtonDisplay getShowControlButtons() {
		return showControlButtons;
	}
	/**
	 * @param showControlButtons the showControlButtons to set
	 */
	@Override public FileFieldParamBldr showControlButtons(FileChooserDefs.ControlButtonDisplay showControlButtons) {
		this.showControlButtons = showControlButtons;
		return this;
	}
	
	@Override
	public final FileChooserDefs.FileChooserDetailDisplay getFileListType() {
		return fileListType;
	}
	@Override
	public final FileFieldParamBldr fileListType(FileChooserDefs.FileChooserDetailDisplay fileListType) {
		this.fileListType = fileListType;
		return this;
	}
	
	
	@Override
	public IFileChooserOptionsBldr sidePaneType(FileChooserSide sidePane) {
		this.sidePaneType = sidePane;
		return this;
	}
	@Override
	public FileChooserSide getSidePaneType() {
		return sidePaneType;
	}
	/**
	 * @return the initialPath
	 */
	@Override public File getInitialPath() {
		return initialPath == null ?  getStandardMount() : initialPath;
	}
	
	private File getStandardMount() {
		File[] standardMountPoints = File.listRoots();;
		if (standardMountPoints != null && standardMountPoints.length > 0) {
			return standardMountPoints[0];
		}
		return new File(System.getProperty("user.home"));
	}
	/**
	 * @param initialPath the initialPath to set
	 */
	@Override public FileFieldParamBldr initialPath(File initialPath) {
		this.initialPath = initialPath;
		return this;
	}
//	/**
//	 * @return the addChooseFileBtn
//	 */
//	@Override public Common.FileChooserType getChooseFileBtnType() {
//		return chooseFileBtnType;
//	}
//
//	/**
//	 * @param addChooseFileBtn the addChooseFileBtn to set
//	 */
//	@Override public FileFieldParamBldr chooseFileBtnType(Common.FileChooserType addChooseFileBtn) {
//		this.chooseFileBtnType = addChooseFileBtn;
//		return this;
//	}
	/**
	 * @return the directory
	 */
	@Override public boolean isDirectory() {
		return directory;
	}
	/**
	 * @param directory the directory to set
	 */
	@Override public FileFieldParamBldr directory(boolean directory) {
		this.directory = directory;
		return this;
	}
	/**
	 * @return the autoComplete
	 */
	@Override public boolean isCompleteFile() {
		return fileCompletion;
	}
	/**
	 * @param autoComplete the autoComplete to set
	 */
	@Override public FileFieldParamBldr completeFile(boolean fileCompletion) {
		this.fileCompletion = fileCompletion;
		return this;
	}


	/**
	 * @return the allowNewFiles
	 */
	@Override public boolean isAllowNewFiles() {
		return allowNewFiles;
	}
	/**
	 * @param allowNewFiles the allowNewFiles to set
	 */
	@Override public FileFieldParamBldr allowNewFiles(boolean allowNewFiles) {
		this.allowNewFiles = allowNewFiles;
		return this;
	}
	
	@Override
	public boolean isShowDirectory() {
		return this.showDirectory;
	}
	/**
	 * @param allowNewFiles the allowNewFiles to set
	 */
	@Override public FileFieldParamBldr showDirectory(boolean showDir) {
		this.showDirectory = showDir;
		return this;
	}
	
	
	
	@Override
	public FileFieldParamBldr showHiddenFiles(boolean showHidden) {
		this.showHidenFiles= showHidden;
		return this;
	}
	@Override
	public boolean isShowHiddenFiles() {
		// TODO Auto-generated method stub
		return showHidenFiles;
	}
	/**
	 * @return the multiSelectionEnabled
	 */
	public boolean isMultiSelectionEnabled() {
		return multiSelectionEnabled;
	}
	
	public FileFieldParamBldr multiSelectionEnabled(boolean multiSelectionEnabled) {
		this.multiSelectionEnabled = multiSelectionEnabled;
		return this;
	}
	
	
	
	@Override
	public FileFieldParamBldr fileChooserOptions(IFileChooserOptions options) {
		fileChooserOptions = options;
		return this;
	}
	@Override
	public IFileChooserOptions getFileChooserOptions() {
		return fileChooserOptions;
	}
	
	
	
	@Override
	public IUpdateableList getRegExpFilterFavourites() {
		return regExpFavourites;
	}
	@Override
	public IFileChooserParamBldr regExpFilterFavourites(IUpdateableList filterFavourites) {
		regExpFavourites = filterFavourites;
		return this;
	}
	@Override
	public IStyle getStyle() {
		return style;
	}
	@Override
	public FileFieldParamBldr style(IStyle styleId) {
		this.style = styleId;
		return this;
	}
	/* (non-Javadoc)
	 * @see com.alee.extended.bm.params.IFileFieldParamBldr#setRecentFav(com.alee.extended.bm.params.ICommonFileFieldParams)
	 */
	@Override
	public FileFieldParamBldr setRecentFavourite(IRecentAndFavoriteFiles recentFav) {
		
		this.initialPath = recentFav.getInitialPath();
		this.recentDirectory = recentFav.getRecentDirectory();
		this.recentFiles = recentFav.getRecent();
		this.favouriteDirectory = recentFav.getFavouriteDirectory();
		this.favouriteFiles = recentFav.getFavourites();
		
		return this;
	}
	/* (non-Javadoc)
	 * @see com.alee.extended.bm.params.IFileFieldParamBldr#asBldrAndInterface()
	 */
	@Override
	public FileFieldParamBldr asBuilderAndParameter() {
		return this;
	}
	/* (non-Javadoc)
	 * @see com.alee.extended.bm.params.IFileFieldParamBldr#buildParam()
	 */
	@Override
	public FileFieldParamBldr buildParam() {
		try {
			return (FileFieldParamBldr) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
