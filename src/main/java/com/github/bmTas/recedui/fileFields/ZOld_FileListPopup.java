package com.github.bmTas.recedui.fileFields;

import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import com.github.bmTas.recedui.def.common.ISetSelectedFile;
import com.github.bmTas.recedui.icons.IconManager;
import com.github.bmTas.recedui.menus.BmPopupMenu;
import com.github.bmTas.recedui.styles.IStyle;
import com.github.bmTas.recedui.xfiles.FileUtils;

@SuppressWarnings("serial")
public class ZOld_FileListPopup extends BmPopupMenu implements Comparator<Path>{
	
	final ISetSelectedFile callback;
	public ZOld_FileListPopup(JComponent parent, IStyle style,
			ISetSelectedFile callback, Path path, 
			boolean onlyDirectory, int width, int popupHeight) {
		super(parent, style, null, -IconManager.ICONS.iconForFile(path).getIconWidth() - 4, width, popupHeight);
		
		this.callback = callback;
		List<Path> files = FileUtils.readDirectory(path, onlyDirectory, 90);
		Collections.sort(files, this);
		
		for (int i = files.size()-1; i > 45; i--) {
			files.remove(i);
		}
		for (Path file : files) {
			add(new FileAction(file));
		}
	}
	
	@Override
	public int compare(Path o1, Path o2) {
		boolean o1Directory = Files.isDirectory(o1);
		if (o1Directory == Files.isDirectory(o2) ) {
			return o1.toString().compareToIgnoreCase(o2.toString());
		} else if (o1Directory){
			return -1;
		}
		return 1;
	}

	private class FileAction extends AbstractAction {
		final Path path;
		FileAction(Path path) {
			super(path.getFileName().toString(), IconManager.ICONS.iconForFile(path));
			
			this.path = path;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			callback.setSelectedFile(path.toFile(), true);
		}
		
	}
}
