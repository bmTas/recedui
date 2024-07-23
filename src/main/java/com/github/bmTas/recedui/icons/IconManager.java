package com.github.bmTas.recedui.icons;

import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.github.bmTas.recedui.xEnvironment.Env;
import com.github.bmTas.recedui.xEnvironment.SwingUtils;

public class IconManager {
	public static IconManager ICONS = new IconManager();
	
//	public final IconHolder arrowBack = new IconHolder("arrow-back.png");
//	public final IconHolder arrowForward = new IconHolder("arrow-forward.png");
//	public final IconHolder arrowUp = new IconHolder("arrow-up.png");
//	public final IconHolder update = new IconHolder("update.png");
//	public final IconHolder home = new IconHolder("house.png");

	public final IconHolder drive = new IconHolder("drive.png");
//	public final IconHolder blueFolder = new IconHolder("blue-folder.png");
//	public final IconHolder blueFolderHorizontal = new IconHolder("blue-folder-horizontal.png");
//	
//	public final IconHolder blueDocumentText = new IconHolder("blue-document-text.png");

	public final IconHolder folderOpen    = new IconHolder("folder-open.png");
	public final IconHolder folderExplorer= new IconHolder("folders_explorer.png");
	public final IconHolder monitor       = new IconHolder("devices/video-display.png");
//	public final IconHolder monitor       = new IconHolder("monitor.png");
	
//	public final IconHolder open = new IconHolder("open.png");
	public final IIconHolder summary      = new LetterIconHolder('\u03A3', Color.BLUE);

	public final IconHolder resizeColumns = new IconHolder("resizeColumns.png");
	public final IconHolder script        = new IconHolder("script-lightning.png");
	public final IconHolder appView       = new IconHolder("application-view.png");
	public final IconHolder favourite     = new IconHolder("star.png");
			
//	public final IconHolder excelTable    = new IconHolder("table_excel.png");
//	public final IconHolder extensionBin  = new IconHolder("file_extension_bin.png");
//	public final IconHolder extensionDat  = new IconHolder("file_extension_dat.png");
//	public final IconHolder extensionLog  = new IconHolder("file_extension_log.png");
//	public final IconHolder extensionTxt  = new IconHolder("file_extension_txt.png");

	public final IconHolder sort          = new IconHolder("azSort.png");
	public final IconHolder sortSum       = new IconHolder("SortSum.png");

	public final IconHolder extensionBin  = new IconHolder("gpl/bin-3.png");
	public final IconHolder extensionDat  = new IconHolder("gpl/dat-2.png");
	public final IconHolder extensionLog  = new IconHolder("gpl/log-2.png");
	public final IconHolder extensionTxt  = new IconHolder("gpl/oficina-txt.png");

	public final IconHolder extensionHtml = new IconHolder("mimetypes/text-html.png");
	public final IconHolder extensionXml  = new IconHolder("mimetypes/text-xml.png");
	public final IconHolder extensionCsv  = new IconHolder("mimetypes/text-csv.png");
	public final IconHolder extensionPo   = new IconHolder("mimetypes/text-x-po.png");
	public final IconHolder extensionJava = new IconHolder("mimetypes/text-x-java.png");
	public final IconHolder extensionPy   = new IconHolder("mimetypes/text-x-python.png");
	public final IconHolder extensionRuby = new IconHolder("mimetypes/application-x-ruby.png");
	public final IconHolder cobolIcon     = new IconHolder("Cobol.png");
	
	public final IIconHolder csvExport    = new ExportHolder(extensionCsv, false, Color.RED);
	public final IIconHolder htmlExport   = new ExportHolder(extensionHtml, false, Color.RED);
	public final IIconHolder scriptExport = new ExportHolder(script, true, Color.BLUE);
	public final IIconHolder fixedExport  = new ExportHolder(new IconHolder("text-align-justify.png"), true,Color.RED);
	public final IIconHolder xmlExport    = new ExportHolder(extensionXml, true,Color.BLUE);
	public final IIconHolder velocityExport  = new ExportHolder(
							new LetterIconHolder(
									LetterIconHolder.LetterPosition.BOTTOM, 
									0.8, 'v', new Color(0, 148, 255), Color.WHITE), 
							true,Color.RED);

	public final IIconHolder layoutAdd			= new IconHolder("layout-add.png");
	public final IIconHolder layoutEdit			= new IconHolder("layout-edit.png");

	public final IconHolder document = new IconHolder("x-office-document.png");
	
	public final IconHolder folder          = new IconHolder("places/folder.png");
	public final IconHolder folderDocuments = new IconHolder("places/folder-documents.png");
	public final IconHolder folderFavorites = new IconHolder("places/folder-favorites.png");
	public final IconHolder folderHome      = new IconHolder("places/user-home.png");


	public final IIconHolder alignHorizontalRightOut	= new IconHolder("actions/align-horizontal-right-out.png");
//	public final IIconHolder configure			= new IconHolder("actions/configure.png");
//	public final IIconHolder document			= new IconHolder("actions/document.png");
//	public final IIconHolder exit				= new IconHolder("actions/application-exit.png");
	public final IIconHolder documentEdit		= new IconHolder("actions/document-edit.png");
	public final IIconHolder documentExportTable= new IconHolder("actions/document-export-table.png");
	public final IIconHolder documentNew		= new IconHolder("actions/document-new.png");
	public final IIconHolder documentOpenRecent	= new IconHolder("actions/document-open-recent.png");
	public final IIconHolder documentOpen		= new IconHolder("actions/document-open.png");
	public final IIconHolder documentSaveAs		= new IconHolder("actions/document-save-as.png");
	public final IIconHolder documentSave		= new IconHolder("actions/document-save.png");
	public final IIconHolder editCopy			= new IconHolder("actions/edit-copy.png");
	public final IIconHolder editCut			= new IconHolder("actions/edit-cut.png");
	public final IIconHolder editDelete			= new IconHolder("actions/edit-delete.png");
	public final IIconHolder editFind			= new IconHolder("actions/edit-find.png");
	public final IIconHolder editPaste			= new IconHolder("actions/edit-paste.png");
	public final IIconHolder editTableDelCol	= new IconHolder("actions/edit-table-delete-column.png");
	public final IIconHolder editTableInsertRowAbove	= new IconHolder("actions/edit-table-insert-row-above.png");
	public final IIconHolder editTableInsertRowBelow	= new IconHolder("actions/edit-table-insert-row-below.png");
	public final IIconHolder editTableInsertColLeft		= new IconHolder("actions/edit-table-insert-column-left.png");
	public final IIconHolder editTableInsertColRight	= new IconHolder("actions/edit-table-insert-column-right.png");
	public final IIconHolder formatList			= new IconHolder("actions/format-list-unordered.png");

	public final IIconHolder folderNew			= new IconHolder("actions/folder-new.png");
	public final IIconHolder generate			= new IconHolder("actions/run-build.png");
	public final IIconHolder run                = generate;

	public final IIconHolder goDownMax 			= new IconHolderRotated("actions/go-last-view.png");
	public final IIconHolder goDown				= new IconHolder("actions/go-down.png");
	public final IIconHolder goFirstView		= new IconHolder("actions/go-first-view.png");
	public final IIconHolder home				= new IconHolder("actions/go-home.png");
	public final IIconHolder goJump				= new IconHolder("actions/go-jump.png");
	public final IIconHolder goLastView			= new IconHolder("actions/go-last-view.png");
	public final IIconHolder goNext				= new IconHolder("actions/go-next.png");
	public final IIconHolder goPrevious			= new IconHolder("actions/go-previous.png");
	public final IIconHolder goUp	 			= new IconHolder("actions/go-up.png");
	public final IIconHolder goUpMax 			= new IconHolderRotated("actions/go-first-view.png");
	public final IIconHolder viewFilter			= new IconHolder("actions/view-filter.png");
	public final IIconHolder viewRefresh		= new IconHolder("actions/view-refresh.png");
	public final IIconHolder help				= new IconHolder("actions/help-contents.png");
	public final IIconHolder print				= new IconHolder("actions/document-print.png");
//	public final IIconHolder printPreview		= new IconHolder("actions/document-print-preview.png");
	public final IIconHolder wizard				= new IconHolder("actions/tools-wizard.png");
	public final IIconHolder insertRecord		= new IconHolder("actions/insert-table.png");
	public final IIconHolder tree            	= new IconHolder("actions/view-list-tree.png");
	public final IIconHolder viewTable         	= new IconHolder("actions/view-form-table.png");
	public final IIconHolder rotatedTable      	= new IconHolderRotated("actions/view-form-table.png");
	
	public final IIconHolder preferences		= new IconHolder("categories/preferences-system.png");
	
	public final Icon smallBlankIcon, largeBlankIcon;
	
	private IconManager() {
		int smallIconHeight = SwingUtils.values().stdIconHeight();
		int largeIconHeight = SwingUtils.values().largeIconHeight();
		smallBlankIcon = new BlankIcon(smallIconHeight, smallIconHeight );
		largeBlankIcon = new BlankIcon(largeIconHeight, largeIconHeight );
	}
	public final Icon folderIcon() {
		return folderIconHolder().icon();
	}
		
	public final Icon iconForFile(Path path) {
		if (Files.isDirectory(path)) {
			return folderIconHolder().icon();
		} 
		String fname = path.toString().toLowerCase();
		if (fname.endsWith(".bak") || fname.endsWith("~")) {
			return smallBlankIcon;
		}

		return getHolder(fname, SwingUtils.values().stdIconHeight()).icon();
	}
	
	public final Icon largeIconForFile(Path path) {
		if (Files.isDirectory(path)) {
			return folderIconHolder().largeIcon();
		} 
		String fname = path.toString().toLowerCase();
		if (fname.endsWith(".bak") || fname.endsWith("~")) {
			return largeBlankIcon;
		}
		
		return getHolder(fname, SwingUtils.values().largeIconHeight()).largeIcon();
	}
	
	private IIconHolder getHolder(String fname, int size) {
		String ext;
		if (fname.endsWith(".po")) {
			return extensionPo;
		} else if (fname.endsWith(".html")) {
			return extensionHtml;
		} else if (fname.endsWith(".java")) {
			return extensionJava;
		} else if (fname.endsWith(".py")) {
			return extensionPy;
		} else if (fname.endsWith(".ruby")) {
			return extensionRuby;
		} else if (fname.length() < 4 || fname.charAt(fname.length() - 4) != '.') {
			
		} else if ("csv".equals(ext = fname.substring(fname.length() - 3))) {
			return extensionCsv;
		} else if ("xml".equals(ext)) {
			return extensionXml;
		} else if ("cbl".equals(ext)) {
			return cobolIcon;
		} else if (size < 32) {
			
		} else if ("log".equals(ext)) {
			return extensionLog;
		} else if ("dat".equals(ext)) {
			return extensionDat;
		} else if ("bin".equals(ext)) {
			return extensionBin;
		} else if ("txt".equals(ext)) {
			return extensionBin;
		}
		return document;
	
	}

	
	public final IIconHolder folderIconHolder() {
		IIconHolder holder;
		if ( (Env.IS_WINDOWS)) {
			holder = folderOpen;
		} else {
			holder = folder;
		}
		return holder;
	}
	
	public ExportIcon zzExportIcon(String name, boolean arrowAtTop, Color color) {
		return new ExportIcon(new ImageIcon ( IconHolder.class.getResource ( name)), arrowAtTop, color);
	}

}
