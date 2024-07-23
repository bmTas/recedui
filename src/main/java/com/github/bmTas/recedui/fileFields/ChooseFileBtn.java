package com.github.bmTas.recedui.fileFields;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import com.github.bmTas.recedui.buttons.UiPanelButton;
import com.github.bmTas.recedui.def.file.FileChooserDefs.FileChooserDetailDisplay;
import com.github.bmTas.recedui.def.file.FileChooserDefs.FileChooserType;
import com.github.bmTas.recedui.fileChooser.UiFileChooser;
import com.github.bmTas.recedui.fileChooser.UiFileChooserDialog;
import com.github.bmTas.recedui.fileFields.params.FileFieldParamBldr;
import com.github.bmTas.recedui.fileFields.params.IChooseFileBtnParam;
import com.github.bmTas.recedui.fileFields.params.IChooseFileBtnParamBldr;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

/**
 * 
 * @author Bruce Martin
 *
 */
public class ChooseFileBtn {
	public static IChooseFileBtnParamBldr newParamBldr() {
		return new FileFieldParamBldr();
	}
	private static boolean useReFileChooser = true;
	public final UiPanelButton chooseButton;
	
	private UiFileChooserDialog webFileChooser;
	private JFileChooser fileChooser;
	private UiFileChooser newChooserCreateDirLists;
	private final ISetSelectedFiles setFiles;
	private boolean open = true;
	private final boolean multiSelectionEnabled; 
	private boolean isDirectory;

	public ChooseFileBtn(
			final JComponent owner, ISetSelectedFiles setFile, 
//			final boolean multiSelectionEnabled,  
//			final boolean isDirectory,	
			//int round,
			IChooseFileBtnParam chooseFileBtnParam) {
//			IRecentFileList recentFileList,        	IRecentFileList recentDirList, 
//    		IUpdateableFileList favouriteFileList,	IUpdateableFileList favouriteDirList) {
		
		this.setFiles = setFile;
		this.isDirectory = chooseFileBtnParam.isDirectory();
		this.multiSelectionEnabled = chooseFileBtnParam.isMultiSelectionEnabled();
 

		//chooseButton = new UiPanelButton ( chooseFileBtnParam.getStyle(), ".." );
		chooseButton = new UiPanelButton ( chooseFileBtnParam.getStyle(), new JButton(IconManager.ICONS.folderOpen.icon()) );
		JButton btn = chooseButton.getMainComponent();
		btn.setToolTipText("<html>Display <b>File Chooser</b></html>");

        
        if (useReFileChooser) {
            newChooserCreateDirLists = new UiFileChooser(chooseFileBtnParam.getFileChooserOptions(), chooseFileBtnParam);
            		
	        btn.addActionListener ( new ActionListener () {
	            @Override
	            public void actionPerformed ( final ActionEvent e ) {
	                // Files selection
	            	File selectedFile = setFiles.getSelectedFile();
	
	            	if (webFileChooser == null) {
	            		webFileChooser = new UiFileChooserDialog(
	            				chooseButton.getMainComponent(), newChooserCreateDirLists);
	                    //webFileChooser.setMultiSelectionEnabled ( multiSelectionEnabled );        
	                    webFileChooser.setViewType(FileChooserDetailDisplay.details);
	                    if (isDirectory) {
	                    	webFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	                    }
	                    Dimension d = webFileChooser.getPreferredSize();
	                    Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
	                    webFileChooser.setSize(
	                    		new Dimension(
	                    				d.width, 
	                    				Math.max(d.height, screenDimensions.height * 3 / 4)));
	            	}
					webFileChooser.setSelectedFile(selectedFile);
	                if (webFileChooser.showDialog ( owner ) == JFileChooser.APPROVE_OPTION) {
	                	setFiles.setSelectedFile (  webFileChooser.getSelectedFile (), true );
	                }
	                
	                // Requesting focus back to this component after file chooser close
	                chooseButton.getMainComponent().requestFocusInWindow ();
	            }
	        } );
        } else {
        	btn.addActionListener ( new ActionListener () {
                @Override
                public void actionPerformed ( final ActionEvent e ) {
                    // Files selection
                	File selectedFile = setFiles.getSelectedFile();
                   	
                   	if (fileChooser == null) {
                   		fileChooser = new JFileChooser();
                   		fileChooser.setMultiSelectionEnabled(multiSelectionEnabled);
                        if (isDirectory) {
                        	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        }
                  	}

                   	if (( selectedFile.isDirectory()) && ! isDirectory) {
                   		fileChooser.setCurrentDirectory(selectedFile);
                   	} else {
                   		fileChooser.setSelectedFile(selectedFile);
                   	}
                   	
                   	int result = open 
                   			? fileChooser.showOpenDialog(owner)
                   			: fileChooser.showSaveDialog(owner);
                    if (result == JFileChooser.APPROVE_OPTION) {
                    	setFiles.setSelectedFile (  fileChooser.getSelectedFile(), true );
                    }
                    
                    // Requesting focus back to this component after file chooser close
                    chooseButton.getMainComponent().requestFocusInWindow ();
               }
        	});
        }
        
        btn.setMinimumSize(new Dimension(SwingUtils.values().stdIconHeight(), btn.getMinimumSize().height));
  //      chooseButton.getComponent().setPreferredSize(new Dimension(16, 16));
	}
	

	public void setChooserType(FileChooserType chooserType) {
		
		open = chooserType == FileChooserType.open;
		if (newChooserCreateDirLists != null) {
			newChooserCreateDirLists.setChooserType(chooserType);
		} 
		
	}

	
	public void setDirectory(boolean directory) {
		this.isDirectory = directory;
		int id = isDirectory ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_AND_DIRECTORIES;
		if (fileChooser != null) {
			fileChooser.setFileSelectionMode(id);
       	}
		if (webFileChooser != null) {
			webFileChooser.setFileSelectionMode(id);
      	}
	}

	public static void setUseUiFileChooser(boolean useREFileChooser) {
		ChooseFileBtn.useReFileChooser = useREFileChooser;
	}
}
