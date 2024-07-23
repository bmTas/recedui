package com.github.bmTas.recedui.xfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUtils {

	public static final DirectoryStream.Filter<Path> FILES_FILTER = new DirectoryStream.Filter<Path>() {
		@Override public boolean accept(Path entry) throws IOException {
			return Files.exists(entry) &&  Files.isRegularFile(entry);
		}
	};

	public static final DirectoryStream.Filter<Path> DIRECTORY_FILTER = new DirectoryStream.Filter<Path>() {
		@Override public boolean accept(Path entry) throws IOException {
			return Files.exists(entry) && Files.isDirectory(entry);
		}
	};

	public static final DirectoryStream.Filter<Path> VISIBLE_DIRECTORIES_FILTER = new DirectoryStream.Filter<Path>() {
		@Override public boolean accept(Path entry) throws IOException {
			return Files.exists(entry) && Files.isDirectory(entry) && ! Files.isHidden(entry);
		}
	};

	public static final DirectoryStream.Filter<Path> ALL_FILES = AllFilesFilter.ALL_FILES;
	
	public static final DirectoryStream.Filter<Path> VISIBLE_FILES_DIRECTORY_FILTER = new DirectoryStream.Filter<Path>() {
		@Override public boolean accept(Path entry) throws IOException {
			return Files.exists(entry) && ! Files.isHidden(entry);
		}
	};
	
	public static final DirectoryStream.Filter<Path> VISIBLE_FILE_OR_DIRECTORY_FILTER = new DirectoryStream.Filter<Path>() {
		@Override public boolean accept(Path entry) throws IOException {
			return Files.exists(entry) && ((! Files.isHidden(entry)) || Files.isDirectory(entry));
		}
	};
	
	public static final DirectoryStream.Filter<Path> VISIBLE_FILES_FILTER = new DirectoryStream.Filter<Path>() {
		@Override public boolean accept(Path entry) throws IOException {
			return Files.exists(entry) && (! Files.isHidden(entry))  && (! Files.isDirectory(entry));
		}
	};

	
	public static List<Path> readDirectory(Path parent, boolean onlyDirectory, int limit) {
		return readDirectory(parent, getFileFilter(onlyDirectory), limit);
	};

	public static String homePathStr() {
		String homeStr = System.getProperty ( "user.home" );
		if (! homeStr.endsWith(File.separator)) {
			homeStr += File.separatorChar;
		}
		return homeStr;
	}
	
	public static List<Path> readDirectory(Path parent, DirectoryStream.Filter<? super Path> filter, int limit) {
		ArrayList<Path> paths = new ArrayList<Path>(Math.min(30, limit));
		DirectoryStream<Path> dirStream = null;
		try {
			dirStream = Files.newDirectoryStream(parent, filter);
			Iterator<Path> pathIterator = dirStream.iterator();
			
			for (int i = 0; i < limit && pathIterator.hasNext(); i++) {
				paths.add(pathIterator.next());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dirStream!= null) {
				try {
					dirStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return paths;
	}
	
	public static DirectoryStream.Filter<Path> getFileFilter(boolean getDirectories) {
		return getDirectories
				? DIRECTORY_FILTER
				: VISIBLE_FILE_OR_DIRECTORY_FILTER;
	}

	
	public static DirectoryStream.Filter<Path> getFileOrDirectiryFilter(boolean getDirectories) {
		return getDirectories
				? DIRECTORY_FILTER
				: VISIBLE_FILES_FILTER;
	}
}
