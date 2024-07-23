package com.github.bmTas.recedui.fileFields;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JComponent;

import com.github.bmTas.recedui.common.DropDownFavouriteBtns;
import com.github.bmTas.recedui.common.MultiComponentBuilder;
import com.github.bmTas.recedui.def.common.IGetSetText;
import com.github.bmTas.recedui.def.file.IFileChangedListner;
import com.github.bmTas.recedui.def.file.IFileListItem;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.fileFields.params.FileFieldParamBldr;
import com.github.bmTas.recedui.fileFields.params.IFileChooserOptions;
import com.github.bmTas.recedui.fileFields.params.IFileFieldParam;
import com.github.bmTas.recedui.fileFields.params.IFileFieldParamAndBuilder;
import com.github.bmTas.recedui.listManagers.AddToFavouriteAction;
import com.github.bmTas.recedui.standard.component.BmPanel;
import com.github.bmTas.recedui.styles.StyleManager;

/**
 * This class implements a BreadCrumb style File Field
 * with:<ul>
 * <li>Recent button
 * <li>Favourite Button
 * <li>File Selection Button
 * </ul>
 * 
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class UiFileField extends BmPanel implements ISetSelectedFiles, IGetSetText {
	
	public static IFileFieldParamAndBuilder newParamBuilder() {return new FileFieldParamBldr(); };

//	private final IFileFieldParam param;
	private final UiBasicFileTextField fileField;
	private final DropDownFavouriteBtns<IFileListItem> recentFavBtns;
	private       ChooseFileBtn chooseFileBtn;
	private final IRecentList recentList;
	private final IUpdateableList favouritesList;
	private final FileFieldCallBack callback;
	
//	private FileChangedListnerList changeListners = new FileChangedListnerList();
//	private File oldFile;
	
	
	public UiFileField(IFileFieldParam param) {
		super(param.getStyle() == null ? StyleManager.styles().panelField(): param.getStyle(), null);
		
		this.recentList = param.getRecent();
		

		boolean isDirectory = param.isDirectory();
		File oldFile = param.getInitialPath();

		callback = new FileFieldCallBack(isDirectory, this, oldFile);

		this.fileField = new UiBasicFileTextField(
				StyleManager.styles().basicFileField(),
				oldFile, 
				this.getMainComponent(), isDirectory, param.isCompleteFile(), 
				param.isShowDirectory(), callback);
		
		//= new BmBasicFileTextField(param.getInitialPath(), param.isDirectory(), param.isAutoComplete());
		MultiComponentBuilder componentBldr = new MultiComponentBuilder(this.getGuiContainer(), fileField.getGuiContainer());
//		this.recentFavBtns = new DropDownFavouriteBtns(
//				componentBldr, this, param.getRecentFiles(), param.getFavouriteFiles(), 
//				callback, 
//				new AddToFavouriteAction(param.getFavouriteFiles(), fileField));
		
		favouritesList = param.getFavourites();
		
		this.recentFavBtns = FileListAction.createDropDown(
				componentBldr, this, recentList, favouritesList, 
				callback, 
				new AddToFavouriteAction(favouritesList, fileField));

		IFileChooserOptions fileChooserOptions = param.getFileChooserOptions();
		if (fileChooserOptions != null) {
			chooseFileBtn = new ChooseFileBtn(
					this.getGuiContainer(), callback, ChooseFileBtn.newParamBldr()
							.style(StyleManager.styles().comboFileChooser())
							.directory(isDirectory)
							.multiSelectionEnabled(false)
							.fileChooserOptions(fileChooserOptions)
							.setRecentFavourite(param)
						.asBuilderAndParameter());
			componentBldr.add(chooseFileBtn.chooseButton.getGuiContainer());
			
		}
		componentBldr.build();
		
		fileField.getMainComponent().setName("FileField");
		fileField.getMainComponent().addFocusListener(callback);
		//this.setBackground(new Color(168, 188, 223));
		
//		this.addFocusListener(new FocusAdapter() {
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				System.out.println(" --- lost focus ");
//			}
//			
//		});
	}



	public void setSelectedFile(File file, boolean notify) {
		fileField.setSelectedFile(file);
		if (notify) {
			callback.fireFileChanged(file);
		}
	}


	/**
	 * @return
	 * @see com.github.bmTas.recedui.text.UiTextField#getText()
	 */
	@Override
	public String getText() {
		return fileField.getText();
	}



	/**
	 * @param t
	 * @see com.github.bmTas.recedui.text.UiTextField#setText(java.lang.String)
	 */
	@Override
	public void setText(String t) {
		fileField.setText(t);
		callback.fireFileChanged(new File(t));

	}



	public File getSelectedFile() {
		return fileField.getSelectedFile();
	}
	
	public final IRecentList getRecentList() {
		return recentList;
	}


	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		
		adjHeight(recentFavBtns.recentBtn, preferredSize.height);
		adjHeight(recentFavBtns.favoriteBtn, preferredSize.height);
		if (chooseFileBtn != null) {
			adjHeight(chooseFileBtn.chooseButton.getMainComponent(), preferredSize.height);
		}
}

	private void adjHeight(JComponent c, int height) {
		if (c != null && height > 0) {
			c.setPreferredSize(new Dimension(height, height));
		}
	}
//	
//	private final void notifyUsersOfNewFile(File f) {
//		if (f != null && ! f.equals(oldFile)) {
//			changeListners.notifyUsersOfNewFile(isDirectory, oldFile, f);
//		}
//		oldFile = f;
//	}

	public void addFileChangedListner(IFileChangedListner listner) {
		callback.changeListners.listners.add(listner);
	}

	public void removeFileChangedListner(IFileChangedListner listner) {
		callback.changeListners.listners.remove(listner);
	}

	public void setParent(File parent) {
		if (fileField.parent == null || ! fileField.parent.equals(parent)) {
			fileField.parent = parent;
			fileField.setDirectory(fileField.parent);
		}
	}


	@Override
	public void setName(String name) {
		super.setName(name + "Pnl");
		fileField.getMainComponent().setName(name + "_Txt");
		recentFavBtns.setButtonNames(name);
		
		if (chooseFileBtn != null) {
			chooseFileBtn.chooseButton.getMainComponent().setName(name + "..");
		}
	}
	
	public void setDirectory(boolean isDirectory) {
		this.callback.setDirectory(isDirectory);
		this.fileField.setDirectory(isDirectory);
		if (this.chooseFileBtn != null) {
			this.chooseFileBtn.setDirectory(isDirectory);
		}
	}

//	public static void main(String[] a) {
//		String[] ss = {"Red", "Yellow"};
//		
////    	for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
////            if ("Nimbus".equals(info.getName())) {
////                try {
////					UIManager.setLookAndFeel(info.getClassName());
////				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
////						| UnsupportedLookAndFeelException e1) {
////					e1.printStackTrace();
////				}
////                break;
////            }
////        }
//
//       	BasicItemList rf = new BasicItemList("File",
//    			"/home/bruce/work/c1.xml", "/home/bruce/work/c2.xml",
//    			"/home/bruce/work/c1/cpy1.xml", "/home/bruce/work/c2/cpy1.xml");
//      	BasicItemList rd = new BasicItemList("Directory",
//    			"/home/bruce/work/", "/home/bruce/work/c1",
//    			"/home/bruce/work/c2", "/home/bruce/.RecordEditor/HSQLDB/SampleFiles",
//    			"/home/bruce/.RecordEditor/HSQLDB/SampleFiles/Xml");
//    	BasicItemList ud = new BasicItemList("Directory",
//    			"/home/bruce/work/c2", "/home/bruce/.RecordEditor/HSQLDB/SampleFiles",
//    			"/home/bruce/work/", "/home/bruce/work/c1",
//    			"/home/bruce/.RecordEditor/HSQLDB/SampleFiles/Xml");
//
//    	BmBreadCrumbFileField fileField = new BmBreadCrumbFileField(
//				FileFieldParamBldr.newFileFieldParamBuilder()
//						.initialPath(new File("/home/bruce/work/temp/jEdit"))
//						.recentFiles(rf)
//						//.recentDirectory(rd)
//						.favouriteFiles(rf)
//						//.chooseFileBtnType(net.sf.recordEditor.ui.def.Common.FileChooserType.openStd)
//						.autoComplete(true)
//					.asBuilderAndParameter());
//
//    	JFrame f = new JFrame();
//    	JPanel p = new JPanel(new BorderLayout());
//    	
//    	p.add(new JPanel(), BorderLayout.NORTH);
//    	p.add(fileField, BorderLayout.CENTER);
//    	//p.add(new JComboBox<String>(ss), BorderLayout.EAST);
//    	p.add(new JTextField(12), BorderLayout.SOUTH);
//    	//p.add(new JTextField(25));
//
//    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	f.getContentPane().add(p);
//		f.pack();
//		f.setVisible(true);
//		
//		System.out.println(fileField.getPreferredSize().height + " " + fileField.getSize().height);
//	}

}
