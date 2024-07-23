package com.github.bmTas.recedui.fileFields.zcopy;



import java.awt.Dimension;
import java.io.File;
import java.nio.file.Path;

import com.github.bmTas.recedui.common.DropDownFavouriteBtns;
import com.github.bmTas.recedui.common.MultiComponentBuilder;
import com.github.bmTas.recedui.def.file.IFileChangedListner;
import com.github.bmTas.recedui.def.listManagers.IRecentList;
import com.github.bmTas.recedui.def.listManagers.IUpdateableList;
import com.github.bmTas.recedui.fileFields.params.IFileChooserOptions;
import com.github.bmTas.recedui.fileFields.params.IFileFieldParam;
import com.github.bmTas.recedui.listManagers.AddToFavouriteAction;
import com.github.bmTas.recedui.standard.component.BmPanel;
import com.github.bmTas.recedui.styles.StyleManager;

/**
 * This class implements a BreadCrumb style File Field
 * with:<ul>
 * <li>Recent button
 * <li>Favourite Nutton
 * <li>File Selection Button
 * </ul>
 * 
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class UiBreadCrumbFileField extends BmPanel implements ISetSelectedFiles {

//	private final IFileFieldParam param;
	private final UiBasicBreadCrumbFileField fileField;
	private final DropDownFavouriteBtns<Path> recentFavBtns;
	private       ChooseFileBtn chooseFileBtn = null;
	private final IRecentList recentList;
	private final FileFieldCallBack callback;
	private boolean isDirectory;
//
//	private FileChangedListnerList changeListners = new FileChangedListnerList();
//	private File oldFile;
	
	public UiBreadCrumbFileField(IFileFieldParam param) {
		super(param.getStyle() == null ? StyleManager.styles().panelField(): param.getStyle(), null);
		
//		this.param = param;
		this.isDirectory = param.isDirectory();
		this.recentList = param.getRecent();
		
		this.callback = new FileFieldCallBack(isDirectory, this, param.getInitialPath()) ;

		this.fileField = new UiBasicBreadCrumbFileField(param.getInitialPath(), param.isDirectory(), param.isCompleteFile());
		MultiComponentBuilder componentBldr = new MultiComponentBuilder(this.getMainComponent(), fileField.getMainComponent());
		
		IUpdateableList favouriteFiles = param.getFavourites();
		
		this.recentFavBtns = FileListAction.createDropDown(
				componentBldr, this, recentList, favouriteFiles, 
				callback, 
				new AddToFavouriteAction(favouriteFiles, fileField));
		
//		System.out.println(" >>> " + fileField.getPreferredSize().height
//				 + " "+ fileField.getMinimumSize().height);

		IFileChooserOptions fileChooserOptions = param.getFileChooserOptions();
		if (fileChooserOptions != null) {
			chooseFileBtn = new ChooseFileBtn(
					this.getGuiContainer(), callback, ChooseFileBtn.newParamBldr()
							.style(StyleManager.styles().comboFileChooser())
							.directory(param.isDirectory())
							.multiSelectionEnabled(false)
							.setRecentFavourite(param)
							.fileChooserOptions(fileChooserOptions)
						.asBuilderAndParameter());
			componentBldr.add(chooseFileBtn.chooseButton.getMainComponent());
		}
		if (recentFavBtns.recentBtn  != null) {
			Dimension preferredSize = recentFavBtns.recentBtn.getPreferredSize();
			int h = Math.max(preferredSize.height, fileField.getPreferredSize().height);
			recentFavBtns.recentBtn.setPreferredSize(new Dimension((h + 1) * 3 / 4, h));
			if (chooseFileBtn != null) {
				chooseFileBtn.chooseButton.getMainComponent().setPreferredSize(new Dimension(h, h));
			}
		}
		

		componentBldr.build();
		
		
		fileField.getMainComponent().addFocusListener(callback);
//		this.doLayout();
//		System.out.println(" >>> " + fileField.getPreferredSize().height
//				 + " "+ fileField.getMinimumSize().height);

	}


//	@Override
//	public void processSelection(IListItem listItem) {
//		fileField.setSelectedFile(new File(listItem.getItemText()));
//	}

	public void setSelectedFile(File file, boolean notify) {
		fileField.setSelectedFile(file, notify);
	}



	public File getSelectedFile() {
		return fileField.getSelectedFile();
	}


	public final IRecentList getRecentList() {
		return recentList;
	}


//
//
//	private final void notifyUsersOfNewFile(File f) {
//		if (f != null && ! f.equals(oldFile)) {
//			changeListners.notifyUsersOfNewFile(isDirectory, oldFile, f);
//		}
//		oldFile = f;
//	}

	public void addFileChangedListner(IFileChangedListner listner) {
		callback.changeListners.listners.add(listner);
		fileField.addFileChangedListner(listner);
	}

	public void removeFileChangedListner(IFileChangedListner listner) {
		callback.changeListners.listners.remove(listner);
	}


	@Override
	public void setName(String name) {
		super.setName(name + "Pnl");
		fileField.getMainComponent().setName(name);
		recentFavBtns.setButtonNames(name);
		
		if (chooseFileBtn != null) {
			chooseFileBtn.chooseButton.getMainComponent().setName(name + "..");
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
