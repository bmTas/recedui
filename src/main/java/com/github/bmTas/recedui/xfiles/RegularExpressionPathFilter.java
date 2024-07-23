package com.github.bmTas.recedui.xfiles;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * Regular Expression Based Path filter
 */
public class RegularExpressionPathFilter implements IFileChooserFilter {

	private final String description;
	public final String patternStr;
	private final Pattern pattern;
	private final boolean isIncludeFilter;
	
	public RegularExpressionPathFilter(String patternStr) {
		this("", patternStr, true);
	}
	
	public RegularExpressionPathFilter(String description, String patternStr) {
		this(description, patternStr, true);
	}
	
	/**
	 * Regular Expression filter
	 * @param description Filter description
	 * @param patternStr Regular Expression pattern
	 * @param isIncludeFilter wether files match the patter should be included or excluded
	 */
	public RegularExpressionPathFilter(String description, String patternStr, boolean isIncludeFilter) {
		this.description = description;
		this.patternStr = patternStr;
		try {
			this.pattern = Pattern.compile(patternStr);
		} catch (Exception e) {
			throw new RuntimeException("Error Compiling regular Expression: " + patternStr + " > " + e);
		}
		this.isIncludeFilter = isIncludeFilter;
	}

	@Override
	public boolean accept(Path entry) throws IOException {
		return entry != null && (pattern.matcher(entry.getFileName().toString()).matches() == isIncludeFilter);
	}

	@Override
	public String description() {
		return description;
	}

}
