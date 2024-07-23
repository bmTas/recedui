package com.github.bmTas.recedui.xfiles;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;

public class GroupPathFilter implements DirectoryStream.Filter<Path> {
	private final ArrayList<DirectoryStream.Filter<Path>> filters = new ArrayList<DirectoryStream.Filter<Path>>();
	private final boolean lookingFor;
	
	/**
	 * Create <b>or</b> filter
	 */
	public static GroupPathFilter or() {
		return new GroupPathFilter(true);
	}
	/**
	 * Create <b>and</b> filter
	 */
	public static GroupPathFilter and() {
		return new GroupPathFilter(false);
	}
	
	
	private GroupPathFilter(boolean lookingFor) {
		this.lookingFor = lookingFor;
	}

	@Override
	public boolean accept(Path entry) throws IOException {
		try {
			for (DirectoryStream.Filter<Path> f : filters) {
				if ( f.accept(entry) == lookingFor) {
					return lookingFor;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ! lookingFor;
	}

	public GroupPathFilter add(DirectoryStream.Filter<Path> f) {
		if (f != null && f.getClass() != AllFilesFilter.ALL_FILES.getClass()) {
			filters.add(f);
		}
		return this;
	}
	
	public DirectoryStream.Filter<Path> toFilter() {
		switch (filters.size()) {
		case 0: return AllFilesFilter.ALL_FILES;
		case 1:
			return filters.get(0);
		}
		return this;
	}
	
	public int numberOfFilters() {
		return filters.size();
	}
}
