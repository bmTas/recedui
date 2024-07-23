package com.github.bmTas.recedui.listManagers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.github.bmTas.recedui.def.file.IFileListItem;
import com.github.bmTas.recedui.def.listManagers.IListItem;

public class FileListItem extends BasicListItem implements IFileListItem {

	private final Path filePath;
	public static FileListItem  fileListItem(String fileName) {
		File file = new File(fileName);
		return new FileListItem(file.getName(), getReducedName(file), fileName);
	}
//	public static FileListItem  fileListItem(Path path) {
//		File file = path.toFile();
//		return new FileListItem(file.getName(), getReducedName(file), path.toString());
//	}

	public static FileListItem  convert(IListItem item) {
		return item instanceof FileListItem ? (FileListItem) item : fileListItem(item.getItemText());
	}
	
	public static List<IFileListItem> convertShortNameList(List<Path> list) {
		ArrayList<IFileListItem> newList = new ArrayList<>();
		for (Path p : list) {
			File file = p.toFile();
			String n = file.getName();
			newList.add(new FileListItem(n, n, p.toString()));
		}
		
		return newList;
	}


	public FileListItem(String shortText, String displayText, String fileName) {
		super(shortText, displayText, fileName);
		filePath = Paths.get(fileName);
	}
	
	
	@Override
	public String getFileName() {
		return super.itemText;
	}

	@Override
	public File getFile() {
		return new File(super.itemText);
	}

	@Override
	public Path getPath() {
		return filePath;
	}

}
