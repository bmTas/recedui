package com.github.bmTas.recedui.fileChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.github.bmTas.recedui.buttons.UiButton;
import com.github.bmTas.recedui.def.component.IUiComponent;
import com.github.bmTas.recedui.def.component.IUiPanel;
import com.github.bmTas.recedui.def.file.FileChooserDefs;
import com.github.bmTas.recedui.def.file.IFileChangedListner;
import com.github.bmTas.recedui.def.file.IFileDirectoryChangedListner;
import com.github.bmTas.recedui.def.file.FileChooserDefs.ControlButtonDisplay;
import com.github.bmTas.recedui.def.listManagers.IBasicList;
import com.github.bmTas.recedui.def.listManagers.IListItem;
import com.github.bmTas.recedui.def.listManagers.IRecentAndFavoriteFiles;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.fileFields.UiBreadCrumbFileField;
import com.github.bmTas.recedui.fileFields.UiFileField;
import com.github.bmTas.recedui.fileFields.params.FileFieldParamBldr;
import com.github.bmTas.recedui.fileFields.params.IFileChooserOptions;
import com.github.bmTas.recedui.fileFields.params.IFileChooserOptionsBldr;
import com.github.bmTas.recedui.fileFields.params.IFileChooserParam;
import com.github.bmTas.recedui.fileFields.params.IFileChooserParamBldr;
import com.github.bmTas.recedui.fileFields.popup.IProcessMenuSelection;
import com.github.bmTas.recedui.fileTree.UiFileTree;
import com.github.bmTas.recedui.form.CellPosition;
import com.github.bmTas.recedui.form.FormBuilder;
import com.github.bmTas.recedui.form.FormHelper;
import com.github.bmTas.recedui.form.TwoColumnFormBuilder;
import com.github.bmTas.recedui.icons.IIconHolder;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.listManagers.BasicItemList;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.styles.StyleManager;
import com.github.bmTas.recedui.text.ITextChangedListner;
import com.github.bmTas.recedui.text.UiTextRecentFavourites;
import com.github.bmTas.recedui.xEnvironment.Env;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;
import com.github.bmTas.recedui.xfiles.AllFilesFilter;
import com.github.bmTas.recedui.xfiles.FileUtils;
import com.github.bmTas.recedui.xfiles.GroupPathFilter;
import com.github.bmTas.recedui.xfiles.IFileChooserFilter;
import com.github.bmTas.recedui.xfiles.RegularExpressionPathFilter;

public class UiFileChooser implements IUiPanel {
	
	public static IFileChooserParamBldr newParamBldr() {
		return new FileFieldParamBldr();
	}
	public static IFileChooserOptionsBldr newOptionBldr() {
		return new FileFieldParamBldr();
	}
	
	private static final int SOURCE_UNKNOWN = 1;
	private static final int SOURCE_TREE = 2;
	private static final int SOURCE_DIRECTORY_FIELD = 3;
	private static final int SOURCE_TOOLBAR = 4;

	private final JPanel mainPnl = new JPanel(new BorderLayout());
	private final UiFileTree fileTree;
	
	private JLabel fieldsLabel;
	
	private UiBreadCrumbFileField directoryFld;
	private UiFileField fileField;
	
	private UiTextRecentFavourites regExpTxt;
	private JComboBox<IFileChooserFilter> filterCombo;
	
	private RecentMenuDtl recentFileMenu, recentDirMenu;
	
	private final FolderFiles folderFiles;
	private final FileDetailsPane fileDtlsPane;
	private final PlacesPnl placesPnl;
	
	private final JButton acceptBtn = new JButton(), cancelBtn = new JButton("Cancel");
	private final JCheckBoxMenuItem showHidden = new JCheckBoxMenuItem("Show Hidden", false);

	
	private File directory, currentFile;
	private ArrayList<String> usedDirs = new ArrayList<String>();
	private int directoryIdx = 0;
	private FileChooserDefs.FileChooserType chooserType;
	private FileChooserDefs.FileChooserDetailDisplay fileListType;
	
//	private ArrayList<FileDetails> fileDetails = new ArrayList<FileDetails>();
	private boolean isDirectory, fileFilterAtTheTop=false;
//	private boolean directoryAllowed=false;
	private DirectoryStream.Filter<Path> fileFilter;

    private final IUpdateableList favouriteFileList, favouriteDirList;
    private final IRecentList recentFileList, recentDirList;

    private final AbstractAction addFavFileAction;
    private IProcessMenuSelection selectFile = new IProcessMenuSelection() {
 		@Override public void processSelection( IListItem listItem ) {
 			File file = new File(listItem.getItemText());
 			setDirectory(file);
 			setSelectedFile(file);
 		}
    };
    
	private final ActionListener filterChangedAction = new ActionListener() {
		@Override public void actionPerformed(ActionEvent e) {
			loadFileTbl();
		}
	};

	private ArrayList<IFileDirectoryChangedListner> listners = new ArrayList<IFileDirectoryChangedListner>(3);

	
	public UiFileChooser(IFileChooserParam opts) {
		this(opts, opts);
	}
	
	@SuppressWarnings("serial")
	public UiFileChooser(IFileChooserOptions opts, IRecentAndFavoriteFiles recentAndFav) {
		this.isDirectory = opts.isDirectory();
		this.showHidden.setSelected(opts.isShowHiddenFiles());
		this.chooserType = opts.getChooserType();
		this.folderFiles = new FolderFiles(new IFileDirectoryChangedListner()  {
			@Override public void directoryChanged(File file) {
				setDirectory(file, SOURCE_UNKNOWN);
			}

			@Override public void fileChanged(File file) {
				fileField.setSelectedFile(file, false);
				fireFileChanged(file);
			}			
		});
		this.fileDtlsPane = new FileDetailsPane(opts.getFileListType(), folderFiles);
		
		opts.getFileListType();
		
		currentFile = getFile(opts.getInitialPath());
		directory = currentFile == null || currentFile.isDirectory() 
				? currentFile 
				: currentFile.getParentFile();
		directory = checkDirectory(directory);
		
		fileTree = new UiFileTree();
		favouriteFileList = recentAndFav.getFavourites();
		IUpdateableList favouriteDirectory = recentAndFav.getFavouriteDirectory();
		favouriteDirList = favouriteDirectory == null && favouriteFileList != null
				? toDirList(favouriteFileList)
				: favouriteDirectory;
				
		recentFileList = recentAndFav.getRecent();
		IRecentList recentDirectory = recentAndFav.getRecentDirectory();
		
		recentDirList = recentDirectory == null && recentFileList != null 
				? toDirList(recentFileList)
				: recentDirectory;
		placesPnl = new PlacesPnl(recentDirList, favouriteDirList);

		
        this.addFavFileAction = favouriteFileList == null 
        		? null
        		: new AbstractAction("Add current file to Favourites") {
			    		@Override public void actionPerformed(ActionEvent e) {
			    			File f = getSelectedFile();
			    			if (f != null && f.exists()) {
			    				favouriteFileList.addFileItem(f.getPath());
			    			}
//			    			List<File> allSelectedFiles = getAllSelectedFiles();
//			    			if (allSelectedFiles != null && allSelectedFiles.size() > 0) {
//			    				favouriteFileList.addFileItem(allSelectedFiles.get(0).getPath());
//			    			}
			    		}
			      };
			      
		createFilter(opts);

		
		mainPnl.add(topPanel(opts) , BorderLayout.NORTH);
		
		mainPnl.add(centerPane(opts.getSidePaneType()), BorderLayout.CENTER);
		
		mainPnl.add(filePanel(opts), BorderLayout.SOUTH);
		
		if (directory != null) {
			usedDirs.add(directory.getAbsolutePath());
			this.fileTree.setSelectedPath(directory);
		}

		fileField.setSelectedFile(currentFile, false);
		
		SwingUtils swingVals = SwingUtils.values();
		Dimension screenSize = swingVals.getFullScreenSize();
		mainPnl.setPreferredSize(new Dimension(
				Math.min(screenSize.width, swingVals.CHAR_FIELD_WIDTH * 120), 
				Math.max(mainPnl.getPreferredSize().height, screenSize.height * 2 / 3)));

		addListners(opts);
	}
	private File checkDirectory(File dir) {
		while (dir != null && ((! dir.exists()) || isDirInError(dir))) {
//			System.out.println(">" + dir + "<");
			dir = dir.getParentFile();
		}
		return getFile(dir);
	}
	
	private boolean isDirInError(File dir) {
		try {
			dir.toPath();
			return false;
		} catch (Exception e) {
			return true;
		}
	}
	
	private File getFile(File file) {
		if (file == null) {
			File[] roots = File.listRoots();
			if (roots != null && roots.length > 0) {
				file = roots[0];
			} else {
				roots = Env.getEnvironmentDefaults().getStandardMountPoints();
				if (roots != null && roots.length > 0) {
					file = roots[0];
				} else if (Env.IS_WINDOWS) {
					file = new File("C:\\");
				} else {
					file =  new File("/");
				}
			}
		}
		return file;
	}
	
	private BasicItemList toDirList(IBasicList list) {
		List<IListItem> itemList = list.getItemList();
		TreeSet<String> dirSet = new TreeSet<String>();
		
		for (IListItem itm : itemList) {
			File file = itm == null ? null : new File(itm.getItemText());
			String parent = file == null ? null : file.getParent();
			if (parent != null) {
				dirSet.add(parent);
			}
		}
		
		return new BasicItemList("Directory", false, dirSet.toArray(new String[dirSet.size()]));
	}

	private void addListners(IFileChooserOptions opts) {
		directoryFld.addFileChangedListner(new DirChanged(SOURCE_DIRECTORY_FIELD)); 
		
		fileTree.addDirectoryChangedListner(new DirChanged(SOURCE_TREE));
		
		placesPnl.addDirectoryChangedListner(new DirChanged(SOURCE_UNKNOWN));
		
		fileField.addFileChangedListner(new IFileChangedListner() {
			@Override public void fileChanged(File file) {
				if (isDirectory || ! file.isDirectory()) {
					setDirectory(file.getParentFile(), SOURCE_UNKNOWN);
					fileField.setSelectedFile(file, false);
				}
				//fileField.setSelectedFile(file, false);
				fireFileChanged(file);
			}
		});
		
		folderFiles.addListner();
		
		if (opts.getShowControlButtons() != ControlButtonDisplay.FILTERS) {
			ActionListener listner = new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					if (e.getSource() == acceptBtn) {
						fireActionListner(JFileChooser.APPROVE_SELECTION, e);
					} else {
						fireActionListner(JFileChooser.CANCEL_SELECTION, e);
					}
				}
			};
			acceptBtn.addActionListener(listner);
			cancelBtn.addActionListener(listner);
		}
	}

	@Override
	public JComponent getGuiContainer() {
		return mainPnl;
	}

	@Override
	public JPanel getMainComponent() {
		return mainPnl;
	}

	@Override
	public IStyle getStyleId() {
		return null;
	}


	@Override
	public JLabel getFieldsLabel() {
		return fieldsLabel;
	}

	@Override
	public void setFieldsLabel(JLabel label) {
		this.fieldsLabel = label;
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		mainPnl.setBounds(x, y, width, height);
	}
	@Override
	public void setBounds(Rectangle r) {
		mainPnl.setBounds(r);
	}
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		mainPnl.setPreferredSize(preferredSize);
	}
	@Override
	public Dimension getPreferredSize() {
		return mainPnl.getPreferredSize();
	}
	@Override
	public int getX() {
		return mainPnl.getX();
	}
	@Override
	public int getY() {
		return mainPnl.getY();
	}
	
	public final FileChooserDefs.FileChooserType getChooserType() {
		return chooserType;
	}

	public final void setChooserType(FileChooserDefs.FileChooserType chooserType) {
		this.chooserType = chooserType;
		acceptBtn.setText(chooserType.acceptButtonName);
	}

	public final FileChooserDefs.FileChooserDetailDisplay getFileListType() {
		return fileListType;
	}

	public final void setFileListType(FileChooserDefs.FileChooserDetailDisplay fileListType) {
		this.fileListType = fileListType;
	}

	public void setDirectory(File directory) {
		setDirectory(directory, SOURCE_UNKNOWN);
	}
	
	public void setDirectory(File directory, int source) {
		setDirectory(directory, source, true);
	}
	
	public void setDirectory(File dir, int source, boolean notifyOfChanges) {
		
		dir = checkDirectory(dir);
		if (dir.isDirectory() 
		&& (this.directory == null || source == SOURCE_TOOLBAR || (! this.directory.equals(dir)))) {
			this.directory = dir;
			
			if (source != SOURCE_DIRECTORY_FIELD) {
				this.directoryFld.setSelectedFile(dir, false);
			}
			if (source != SOURCE_TREE) {
				this.fileTree.setSelectedPath(dir);
			}
			if (source != SOURCE_TOOLBAR) {
				String d = dir.getAbsolutePath();
				usedDirs.remove(d);
				usedDirs.add(d);
				directoryIdx = usedDirs.size() - 1;
			}
			
			fileField.setParent(dir);
			this.loadFileTbl();
			
			if (notifyOfChanges) {
				fireDirectoryChanged(dir);
			}
		}
	}


	@SuppressWarnings("serial")
	private void createFilter(IFileChooserOptions opts) {
		String tt = "Regular Expression Filter";
		regExpTxt = new UiTextRecentFavourites(UiTextRecentFavourites.paramBldr()
									.setInitialValue(".*")
									.setRecent(new BasicItemList("Recent Filters", ".*"))
									.setFavourites(opts.getRegExpFilterFavourites())
								.asParamAndBuilder()	);
		regExpTxt.addTextChangeListner(new ITextChangedListner() {
			@Override public void textChanged(IUiComponent source, String old, String value) {
				loadFileTbl();
			}
		});
		regExpTxt.getMainComponent().setColumns(20);
		List<IFileChooserFilter> filters = opts.getFilters();
		 regExpTxt.getMainComponent().setToolTipText(tt);
		 //regExpTxt.getFieldsLabel().setToolTipText(tt);
		 
		 boolean vis = true;
		 if (filters.size() == 0) {
			 filters = new ArrayList<IFileChooserFilter>();
			 filters.add(AllFilesFilter.ALL_FILES);
			 vis = false;
		 }
//		 || (filters.size() == 1 && AllFilesFilter.ALL_FILES.equals(filters.get(0)))) {

		 filterCombo = new JComboBox<IFileChooserFilter>(filters.toArray(new IFileChooserFilter[filters.size()]));
		 filterCombo.setVisible(vis);

		 filterCombo.setOpaque(false);
		 

		 filterCombo.setRenderer(new DefaultListCellRenderer() {
			@Override public Component getListCellRendererComponent(JList<?> list, Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				return super.getListCellRendererComponent(
						list, 
						value instanceof IFileChooserFilter ? ((IFileChooserFilter) value).description() : value, 
						index, isSelected, cellHasFocus);
			}				 
		 });
		 SwingUtils swingValues = SwingUtils.values();
		 if (swingValues.IS_NIMBUS_LAF) {
			 Dimension preferredSize = filterCombo.getPreferredSize();

			 filterCombo.setPreferredSize(
					 new Dimension(preferredSize.width + swingValues.CHAR_FIELD_WIDTH * 2, preferredSize.height));
		 }
		 			 
		 filterCombo.addActionListener(filterChangedAction);
//		 }
	}

	@SuppressWarnings("serial")
	private JPanel topPanel(IFileChooserOptions opts) {
		
		JMenuBar bar = new JMenuBar();
		bar.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 1));
		
		//IRecentList recentFileList = recentAndFav.getRecentFiles();
		//IRecentList recentDirList = recentAndFav.getRecentDirectory();

		if (recentFileList   != null || recentDirList    != null
		|| favouriteFileList != null || favouriteDirList != null) {

			AbstractAction addDirToFavouritesAction = new AbstractAction("Add current Path to Favourites") {
				@Override public void actionPerformed(ActionEvent e) {
					favouriteDirList.addFileItem(directoryFld.getSelectedFile().toString());		
				}
			};

			JMenu fileMenu    = new JMenu("File");

			if (recentFileList != null) {
				recentFileMenu = new RecentMenuDtl(
						"Recent Files", recentFileList, 
						new IProcessMenuSelection() {
							@Override public void processSelection( IListItem listItem ) {
								recentFileList.moveToTop(listItem);
								File file = new File(listItem.getItemText());
								setDirectory(file);
								//setSelectedFile(file);
								//editSelectedFileName();
							}
						});
				fileMenu.add(recentFileMenu.recentMenu);
			}
			if (recentDirList != null) {
				recentDirMenu = new RecentMenuDtl(
						"Recent Directory", recentDirList, 
						new IProcessMenuSelection() {
							@Override public void processSelection( IListItem listItem ) {
								recentDirList.moveToTop(listItem);
								setDirectory(new File(listItem.getItemText()));
							}
						});
				fileMenu.add(recentDirMenu.recentMenu);
			}
			if (favouriteFileList != null) {
				fileMenu.add(new RecentMenuDtl(
						"Favourite Files", 
						favouriteFileList, 
						addFavFileAction,
						selectFile).recentMenu);
			}
			if (favouriteDirList != null) {
				fileMenu.add(new RecentMenuDtl(
						"Favourite Directories", favouriteDirList, 
						addDirToFavouritesAction,		
						new IProcessMenuSelection() {
							@Override public void processSelection( IListItem listItem ) {
								setDirectory(new File(listItem.getItemText()));
							}
						}).recentMenu);
			}

			bar.add ( fileMenu );
		}
		
		
		IconManager iconMgr = IconManager.ICONS;
		AbstractAction backAction = new AbstractAction("", iconMgr.goPrevious.toolbarIcon()) { 
			@Override public void actionPerformed(ActionEvent e) {
				if (directoryIdx > 0) {
					directoryIdx -= 1;
					setDirectory(new File(usedDirs.get(directoryIdx)), SOURCE_TOOLBAR);
				}
			}
		};
//		JButton backBtn = toolBarBtn(backAction, "Last Directory");
		AbstractAction forwardAction = new AbstractAction("", iconMgr.goNext.toolbarIcon()) {
			@Override public void actionPerformed(ActionEvent e) {
				if (directoryIdx < usedDirs.size()-1) {
					directoryIdx += 1;
					setDirectory(new File(usedDirs.get(directoryIdx)), SOURCE_TOOLBAR);
				}
			}
		};
		AbstractAction upAction = new AbstractAction("", iconMgr.goUp.toolbarIcon()) {
			@Override public void actionPerformed(ActionEvent e) {
				File parent = directory.getParentFile();
				if (parent != null) {
					setDirectory(parent, SOURCE_UNKNOWN);
				}
			}
		};
		AbstractAction homeAction = new AbstractAction("", iconMgr.home.toolbarIcon()) {
			@Override public void actionPerformed(ActionEvent e) {
				setDirectory(new File(FileUtils.homePathStr()), SOURCE_TOOLBAR);
			}
		};
		AbstractAction refreshAction = new AbstractAction("", iconMgr.viewRefresh.toolbarIcon()) {
			@Override public void actionPerformed(ActionEvent e) {
				setDirectory(directory, SOURCE_TOOLBAR);
			}
		};
		AbstractAction newFolderAction = new AbstractAction("", iconMgr.folderNew.toolbarIcon()) {
			@Override public void actionPerformed(ActionEvent e) {
				createFolder();
			}
		};
	
		JMenu viewMenu = new JMenu("View");
		viewMenu.add(showHidden);
		viewMenu.addSeparator();
		bar.add(viewMenu);
		//bar.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5)));
		//bar.add(new JPanel());
		bar.add(new JLabel("          "));
		bar.add(toolBarBtn(     backAction, "Last Directory"));
		bar.add(toolBarBtn(  forwardAction, "Next Directory in the list"));
		bar.add(toolBarBtn(       upAction, "Parent Directory"));
		bar.add(toolBarBtn(     homeAction, "User Home directory"));
		bar.add(toolBarBtn(  refreshAction, "Refresh"));
		bar.add(toolBarBtn(newFolderAction, "Create New Folder"));
		//bar.add(new JPanel());
	
		
		fileDtlsPane.addListOptions(viewMenu);
		
		
		showHidden.addActionListener(filterChangedAction);
		
		directoryFld = new UiBreadCrumbFileField(FileFieldParamBldr.newFileFieldParamBuilder()
								.initialPath(directory)
								.favouriteFiles(favouriteDirList)
								.recentFiles(recentDirList)
								.directory(true)
								.completeFile(true)
						.asBuilderAndParameter()
				); 

		

		 TwoColumnFormBuilder form = FormBuilder.twoColumnForm("path")
						.setRowSizes(0, FormHelper.PREFERRED, FormHelper.GAP, SwingUtils.values().NORMAL_FIELD_HEIGHT)
						.setFieldCoumnCount(2)
					.form();
		 form 
				.lineSwing(bar, CellPosition.FULL_WIDTH, 1, 0, form.getLastFieldColumn()+1)
				.line("Directory", directoryFld);
	 
		 if (fileFilterAtTheTop) {
			 form.line("Filter", regExpTxt, FormBuilder.LEFT_1_COL);
			 if (filterCombo != null) {
				 form.cellSwing(filterCombo, FormBuilder.RIGHT, 1, form.getLastFieldColumn(), form.getLastFieldColumn());
			 }
		 }
		 return form.build();
	}
	
	private void createFolder() {
		String s = JOptionPane.showInputDialog(mainPnl, "New Directory name", "");
		//JOptionPane.showOptionDialog(parentComponent, message, title, optionType, messageType, icon, options, initialValue)
		
		if (s != null && s.length() > 0) {
			try {
				Files.createDirectories(new File(directory, s).toPath());
				setDirectory(directory, SOURCE_TOOLBAR);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(mainPnl, "Create Directory " + s + " Failed: " + e);
				e.printStackTrace();
			}
		}
	}
	
	private void adjustHeight(JComponent c, int h) {
		Dimension d = c.getPreferredSize();
		if (h > d.height) {
			c.setPreferredSize(new Dimension(d.width, h));
		}
	}
	
	
	public JButton toolBarBtn(Action action, String toolTip ) {
		UiButton btn = new UiButton(
				StyleManager.styles().toolbarBtnsFileChooser(), action);
//		btn.setBorder(btnBorder);
//		btn.setOpaque(false);
		btn.getMainComponent().setToolTipText(toolTip);
	
		
		return btn.getMainComponent();
	}
	
//	public JPanel toolBarBtn(Action action, String toolTip ) {
//		StdComponents.UiPnlButton btn = new StdComponents.UiPnlButton(
//				StyleManager.styles().toolbarBtnsFileChooser(), action);
////		btn.setBorder(btnBorder);
////		btn.setOpaque(false);
//		btn.getComponent().setToolTipText(toolTip);
//		
//		return btn.getContainer();
//	}

	
//	public List<File> getAllSelectedFiles() {
//		return null;
//	}
	
	public File getCurrentDirectory() {
		return directory;
	}
	

	
	public File[] getSelectedFiles() {
		File f = getSelectedFile();
		return f == null ? null : new File[] {f};
	}
	public void setSelectedFiles(File[] files) {
		if (files != null && files.length > 0) {
			setSelectedFile(files[0]);
		}
	}
	
	public void setSelectedFiles(Collection<File> files) {
		if (files!= null && files.size() > 0) {
			setSelectedFile(files.iterator().next());
		}
	}
	public File getSelectedFile() {
		File f = fileField.getSelectedFile();
		
		if (f != null && (! folderFiles.directoryAllowed) && f.isDirectory()) {
			return null;
		}
		
		return f;
	}

	public void setSelectedFile(File file) {
		
		if (file == null) {
		} else if (file.isDirectory()) {
			setDirectory(file, SOURCE_UNKNOWN);
		} else if (file.getParentFile() != null) {
			setDirectory(file.getParentFile(), SOURCE_UNKNOWN);
			fileField.setSelectedFile(file, false);
		}
	}

	private JPanel centerPane(FileChooserDefs.FileChooserSide sidePaneType) {
		JSplitPane splitP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, folderSelection(sidePaneType), fileList());
		JPanel centerPane = new JPanel(new BorderLayout(4, 4));
		
		centerPane.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1)), BorderLayout.NORTH);
		centerPane.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2)), BorderLayout.EAST);
		centerPane.add(new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2)), BorderLayout.WEST);
		centerPane.add(splitP, BorderLayout.CENTER);
		
		splitP.setDividerLocation(SwingUtils.values().CHAR_FIELD_WIDTH * 35);
		splitP.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		return centerPane;
	}
	
	private JTabbedPane folderSelection(FileChooserDefs.FileChooserSide sidePaneType) {
		JTabbedPane tab = new JTabbedPane();
		
		tab.addTab(
				"Tree ", IconManager.ICONS.folderExplorer.icon(), new JScrollPane(fileTree.getMainComponent()), 
				"<b>Folder Tree</b> where you can select the folder to be displayed");
		tab.addTab("Places", placesPnl.getGuiContainer());
		tab.setName("FcFpTab");
		if (sidePaneType == FileChooserDefs.FileChooserSide.PLACES) {
			tab.setSelectedIndex(1);
		}
		
		return tab;
	}
	
	private JComponent fileList() {	
		loadFileTbl();
		
		int diff = fileDtlsPane.fileScrollPane.getPreferredSize().width 
				 - folderFiles.fileTbl.getMainComponent().getPreferredSize().width;
		if (diff > 0) {
			TableColumnModel columnModel = folderFiles.fileTbl.getMainComponent().getColumnModel();
			
			TableColumn filenameColumn = columnModel.getColumn(FolderFiles.FileTableMdl.FILE_NAME_IDX);
			filenameColumn.setPreferredWidth(diff * 3 / 4 + filenameColumn.getPreferredWidth());
			
			TableColumn sizeColumn = columnModel.getColumn(FolderFiles.FileTableMdl.SIZE_IDX);
			sizeColumn.setPreferredWidth(diff / 5 + sizeColumn.getPreferredWidth());
		}

		return fileDtlsPane.fileScrollPane;
	}
	
	@SuppressWarnings("incomplete-switch")
	private JPanel filePanel(IFileChooserOptions opts) {
		directory = currentFile.isDirectory() ? currentFile : currentFile.getParentFile();
		
		fileField = new UiFileField(FileFieldParamBldr.newFileFieldParamBuilder()
							.initialPath(currentFile)
							.favouriteFiles(favouriteFileList)
							.recentFiles(recentFileList)
							.completeFile(false)
							.showDirectory(false)
							.directory(isDirectory)
						.asBuilderAndParameter()
				); 
//		System.out.println(" ~~~> " + directoryFld.getPreferredSize().height + " "
//				+ + directoryFld.getSize().height + " " + ScreenConstants.NORMAL_HEIGHT);
		 int height = directoryFld.getGuiContainer().getPreferredSize().height;
		 adjustHeight(fileField.getGuiContainer(), height);
		 adjustHeight(regExpTxt.getGuiContainer(), height);

		TwoColumnFormBuilder form = FormBuilder.twoColumnForm("file")
					.setFieldCoumnCount(2)
				.form();

		if (fileFilterAtTheTop) {
			return form
						.line("File", fileField)
					.build();
		} else {
			JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
			
			if (filterCombo != null) {
				p.add(filterCombo);
			}
			switch (opts.getShowControlButtons()) {
			case FILTERS_APPROVE:
				p.add(acceptBtn);
				break;
			case ALL_CONTROLS:
				p.add(acceptBtn);
				p.add(cancelBtn);
			}
			
			acceptBtn.setText(opts.getChooserType().acceptButtonName);
			return form
						.line( "File", fileField)
						.line("Filter", regExpTxt, FormBuilder.LEFT_1_COL)
						.cellSwing(p, FormBuilder.RIGHT, 1, form.getLabelColumn(), form.getLastFieldColumn())
					.build();
		}
	
	}

	
	private void loadFileTbl() {
		if (directory != null && directory.isDirectory()) {
			DirectoryStream<Path> dirStream = null;
			try {
				//DirectoryStream.Filter<Path> filter = null;
				GroupPathFilter filter = GroupPathFilter.and();
				if (! showHidden.isSelected()) {
					filter.add(FileUtils.VISIBLE_FILES_DIRECTORY_FILTER);
				}
				
				DirectoryStream.Filter<Path> f;
				if (filterCombo != null && (f = (DirectoryStream.Filter<Path>)filterCombo.getSelectedItem()) != null) {
					filter.add(f);
				}
				String regX = regExpTxt.getText();
				if (regExpTxt != null && regX.length() > 0 && ! ".*".equals(regX)) {
					try {
						filter.add(new RegularExpressionPathFilter(regX));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (fileFilter != null) {
					filter.add(fileFilter);
				}
				
				if (filter.numberOfFilters() > 0) {
					if (isDirectory) {
						filter.add(FileUtils.DIRECTORY_FILTER);
					} else {
						Filter<Path> directoryFilter = showHidden.isSelected()
								? FileUtils.DIRECTORY_FILTER
								: FileUtils.VISIBLE_DIRECTORIES_FILTER;
						filter = GroupPathFilter.or()
										.add(filter.toFilter())
										.add(directoryFilter);
					}
				}
				dirStream = Files.newDirectoryStream(directory.toPath(), filter);
				Iterator<Path> pathIterator = dirStream.iterator();
				
				folderFiles.loadFileDetails(pathIterator);
			} catch (Exception e) {
				e.printStackTrace();
//			} catch (InvalidPathException ex) {
//				ex.printStackTrace();
			} finally {
				if (dirStream!= null) {
					try {
						dirStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

    private List<ActionListener> actionListners = new ArrayList<ActionListener>();
    public final void addActionListner(ActionListener l) {
    	actionListners.add(l);
    }
    public final void removeActionListner(ActionListener l) {
    	actionListners.remove(l);
    }
    
    private void fireActionListner(String eventType, ActionEvent sourceEvent) {
    	if (actionListners.size() > 0) {
      //      fireApproveAction ( new ActionEvent ( fileList, e.getID (), "Files selected", e.getWhen (), e.getModifiers () ) );
    		ActionEvent e = new ActionEvent(
    				sourceEvent.getSource(), sourceEvent.getID(),
    				eventType, 
    				sourceEvent.getWhen(), sourceEvent.getModifiers());
    		for (ActionListener l : actionListners) {
    			l.actionPerformed(e);
    		}
    	}
    }
    
    public final void setFileFilter(DirectoryStream.Filter<Path> fileFilter) {
		this.fileFilter = fileFilter;
	}

	public void setFileSelectionModeImp(int mode) {
     	
		setFileFilter(null);
		folderFiles.directoryAllowed = true;
		boolean oldDir = isDirectory;
    	switch (mode) {
    	case JFileChooser.FILES_ONLY:
    		setFileFilter(FileUtils.ALL_FILES);
    		folderFiles.directoryAllowed = false;
    		isDirectory = false;
    		break;
    	case JFileChooser.DIRECTORIES_ONLY:
//    		setFileFilter(FileUtils.DIRECTORY_FILTER);
    		isDirectory = true;
     		break;
    	case JFileChooser.FILES_AND_DIRECTORIES:
//    		setFileFilter(FileUtils.ALL_FILES);
    		isDirectory = false;
    		break;
    	}
    	
    	if (isDirectory != oldDir) {
    		fileField.setDirectory(isDirectory);
    	}
    }

	public void setAcceptButtonTxt(IIconHolder icon, String txt) {
		if (icon != null) {
			acceptBtn.setIcon(icon.icon());
		}
		if (txt != null) {
			acceptBtn.setText(txt);
		}
	}

	public void setAcceptButtonToolTipTxt(String txt) {
		acceptBtn.setText(txt);
	}
	
	public void clickAcceptBtn() {
		acceptBtn.doClick(1);
	}
	
	public void addFileChooserListner(IFileDirectoryChangedListner l) {
		listners.add(l);
	}
	
	public void addFilter(IFileChooserFilter filter) {
		this.filterCombo.addItem(filter);
		filterCombo.setVisible(true);
	}
	
	private void fireDirectoryChanged(File file) {
		for (IFileDirectoryChangedListner l : listners) {
			l.directoryChanged(file);
		}
	}
	
	private void fireFileChanged(File file) {
		for (IFileDirectoryChangedListner l : listners) {
			l.fileChanged(file);
		}
	}
	

//	private static String[] FILE_TABLE_NAMES = {
//		"File Name", "Extension", "Changed", "Size"	
//	};
//
//	@SuppressWarnings("serial")
//	public class FileTableMdl extends AbstractTableModel {
//
//		private static final int FILE_NAME_IDX = 0;
//		private static final int EXTENSION_IDX = 1;
//		private static final int CHANGED_IDX = 2;
//		private static final int SIZE_IDX = 3;
//
//		@Override
//		public int getRowCount() {
//			return fileDetails.size();
//		}
//
//		@Override
//		public int getColumnCount() {
//			return FILE_TABLE_NAMES.length;
//		}
//
//		
//		@Override
//		public String getColumnName(int column) {
//			return FILE_TABLE_NAMES[column];
//		}
//
//		@Override
//		public Object getValueAt(int rowIndex, int columnIndex) {
//			FileDetails row = fileDetails.get(rowIndex);
//			row.loadDetails();
//			switch (columnIndex) {
//			case FILE_NAME_IDX: return row.fullname;
//			case EXTENSION_IDX: return row.extension;
//			case CHANGED_IDX: return row.changedStr;
//			case SIZE_IDX:
//				if (row.size > 0) {
//					return row.sizeStr;
//				}
//			}
//			return "";
//		}
//	};
//	
//	@SuppressWarnings("serial")
//	public class FileListMdl extends AbstractListModel {
//
//		@Override
//		public int getSize() {
//			return fileDetails.size();
//		}
//
//		@Override
//		public Object getElementAt(int index) {
//			return fileDetails.get(index);
//		}
//	}
	
//	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yy HH:mm");
//	public static class FileDetails implements Comparable<FileDetails> {
//		final boolean isDirectory;
//		final String fullname;
//		String extension;
//		boolean getAttributes = true;
//		long changed;
//		String changedStr;
//		Long size=(long) 0;
//		String sizeStr = "";
//		
//		FileDetails(Path path) {
//			this.fullname = path.toString();
//			this.isDirectory = Files.isDirectory(path);
//			
//			int p = fullname.lastIndexOf('.');
//			
//			String ext = "";
//			if (p >= 0) {
//				ext = fullname.substring(p+1);
//			}
//			extension = ext;
//		}
//		
//		private void loadDetails() {
//			if (getAttributes) {
//				File f = new File(fullname);
//				changed = f.lastModified();
//				changedStr = dateFormat.format(new Date(changed));
//				if (isDirectory) {
//					extension = "";
//				} else {
//					size = f.length();
//					if (size > 10 * 1024 * 1024) {
//						long l = (size + (long)500000) >> 24;
//						sizeStr = l + " MB";
//					} else if (size > 10 * 1024) {
//						long l = (size + (long)500) >> 12;
//						sizeStr = l + " KB";
//					} else {
//						sizeStr = size + "   ";
//					}
//				}
//				getAttributes = false;
//			}
//		}
//
//		@Override
//		public int compareTo(FileDetails o) {
//			if (isDirectory == o.isDirectory) {
//				return fullname.compareTo(o.fullname);
//			} 
//			return isDirectory ? -1 : 1;
//		}
//		
//		
//	}
	
//	private static class FileNameRender implements TableCellRenderer {
//		JLabel label = new JLabel();
//		FileNameRender() {
//			label.setOpaque(true);
//			label.setBackground(Color.WHITE);
//		}
//		@Override
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
//				int row, int column) {
//			String v = "";
//			Icon icon = null;
//			if (value != null) {
//				Path path = Paths.get(value.toString());
//				icon = IconManager.ICONS.iconForFile(path);
//				v = path.getFileName().toString();
//			}
//			
//			label.setIcon(icon);
//			label.setText(v);
//			
//			return label;
//		}
//		
//	}
	
//	private final class HeaderSort extends MouseAdapter {
//
//		private int lastCol = -1;
//		boolean[] ascending = {true, true, true, true};
//
//		/**
//		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
//		 */
//		public void mousePressed(MouseEvent e) {
//			if (e.getClickCount() == 2 && ! e.isConsumed()) {
//				JTableHeader header = (JTableHeader) e.getSource();
//				int col = header.columnAtPoint(e.getPoint());
//
//				Comparator<FileDetails> comparator = new Comparator<FileDetails>() {
//					@Override public int compare(FileDetails o1, FileDetails o2) {
//						int ret = o1.compareTo(o2);
//						return ascending[col] ? ret : - ret;
//					}  
//				};
//				switch (col) {
//				case FileTableMdl.FILE_NAME_IDX:
//					break;
//				case FileTableMdl.EXTENSION_IDX:
//					comparator = new Comparator<FileDetails>() {
//						@Override public int compare(FileDetails o1, FileDetails o2) {
//							int ret = o1.extension.compareTo(o2.extension);
//							return ascending[col] ? ret : - ret;
//						}  
//					};
//					break;
//				case FileTableMdl.SIZE_IDX:
//					comparator = new Comparator<FileDetails>() {
//						@Override public int compare(FileDetails o1, FileDetails o2) {
//							int ret = Long.compare(o1.size, o2.size);
//
//							return ascending[col] ? ret : - ret;
//						}  
//					};
//					break;
//				case FileTableMdl.CHANGED_IDX:
//					comparator = new Comparator<FileDetails>() {
//						@Override public int compare(FileDetails o1, FileDetails o2) {
//							int ret = (new Date(o1.changed)).compareTo(new Date(o2.changed));
//
//							return ascending[col] ? ret : - ret;
//						}  
//					};
//					break;
//				}
//				Collections.sort(fileDetails, comparator);
//				ascending[col] = ! ascending[col];
//			}
//		}
//	}
	   

	@Override
	public void setVisible(boolean b) {
		mainPnl.setVisible(b);
		
		if (fieldsLabel != null) {
			fieldsLabel.setVisible(b);
		}
	}
	@Override
	public void setToolTipText(String text) {
		mainPnl.setToolTipText(text);
	}


	private class DirChanged implements IFileDirectoryChangedListner {
		final int id;
		public DirChanged(int id) {
			super();
			this.id = id;
		}

		@Override
		public void directoryChanged(File file) {
			setDirectory(file, id);
		}

		@Override
		public void fileChanged(File file) {
			setDirectory(file, id);
		}
		
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater ( new Runnable ()  {
            public void run () {
             	BasicItemList rf = new BasicItemList("File",
            			"/home/bruce/work/c1.xml", "/home/bruce/work/c2.xml",
            			"/home/bruce/work/c1/cp1.xml", "/home/bruce/work/c2/cp1.xml");
              	BasicItemList rd = new BasicItemList("Directory",
            			"/home/bruce/work/", "/home/bruce/work/c1",
            			"/home/bruce/work/c2", "/home/bruce/.RecordEditor/HSQLDB/SampleFiles",
            			"/home/bruce/.RecordEditor/HSQLDB/SampleFiles/Xml");
              	UiFileChooser fcPnl = new UiFileChooser(
              			FileFieldParamBldr.newFileChooserParamBuilder()
              					//.initialPath(initialPath)
              					.recentFiles(rf)
              					.recentDirectory(rd)
              					.favouriteFiles(rf)
              					.favouriteDirectory(rd)
              					.addFileFilterOption(AllFilesFilter.ALL_FILES)
              					.addFileExtensionOption("Csv", "csv", "tsv")
              					.addFileExtensionOption("Txt/Csv", "txt", "csv", "tsv")
              					.addFileExtensionOption("Txt", "Txt")
             					.addFileExtensionOption("Bin", "bin")
              			.asBuilderAndParameter()
              			);
              			//FileChooserType.open_TextField, ControlButtonDisplay.ALL_CONTROLS, rf, rd, rf, rd);
	
//				WebFileChooserPanel2 fcPnl1 = new WebFileChooserPanel2(FileChooserType.open, true);
				
				
				JFrame frame = new JFrame();
//				JFrame frame2 = new JFrame();
				
				frame.getContentPane().add(fcPnl.getMainComponent());
				frame.pack();
				
				fcPnl.setDirectory(new File("/home/bruce/.RecordEditor/HSQLDB/SampleFiles"));//"/home/bruce/work/temp/jEdit/"));
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setVisible(true);
            }
		});
	}

}
