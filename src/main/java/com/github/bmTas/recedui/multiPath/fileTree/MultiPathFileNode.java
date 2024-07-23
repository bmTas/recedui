package com.github.bmTas.recedui.multiPath.fileTree;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.github.bmTas.recedui.xEnvironment.Env;
import com.github.bmTas.recedui.xfiles.FileUtils;

public class MultiPathFileNode implements TreeNode, IGetFileType {
	private final List<MultiPathFileNode> EMPTY = Arrays.asList(new MultiPathFileNode[0]);
	private static final ArrayList<Path> EMPTY_PATH_LIST = new ArrayList<>();
	
	
	private final MultiPathFileNode parent;
	private Path[] paths;
	private final BasicFileAttributes[] pathAttributes;
	private final boolean[] loadFileAttributes;
	private List<MultiPathFileNode> children;
	private boolean loaded = false/*, nodeOpened = false*/;
	private final String name;
	private final int level;
	private final String treePathName;
	private boolean filesInTheDirectory = false;
	//private final boolean ignoreCase;
	private final RootDetails rootDetails;
	
	
	public MultiPathFileNode( Path[] paths) {
		this(paths, Env.IS_WINDOWS, null);
	}
	
	public MultiPathFileNode( Path[] paths, boolean ignoreCase, DirectoryStream.Filter<Path> fileFilter) {
		this.parent = null;
		
		int arraySize = paths == null ? 0 : paths.length;
		this.paths = paths;
		this.pathAttributes = new BasicFileAttributes[arraySize];
		this.loadFileAttributes = new boolean[arraySize];
		Arrays.fill(loadFileAttributes, true);
		

		Path fileNamePath = paths[0] == null ? null : paths[0].getFileName();
		this.name = fileNamePath == null ? paths[0].toString() : fileNamePath.toString();
		
		this.level = 0;
		this.treePathName = name;
		this.rootDetails = new RootDetails(this, ignoreCase, fileFilter);
	}
	
	
	protected MultiPathFileNode(MultiPathFileNode parent,  Path[] paths) {

		super();
		this.parent = parent;
		this.paths = paths;
		
		int arraySize = paths == null ? 0 : paths.length;
		this.pathAttributes = new BasicFileAttributes[arraySize];
		this.loadFileAttributes = new boolean[arraySize];
		Arrays.fill(loadFileAttributes, true);
		
		this.rootDetails = parent.rootDetails;
		
		Path firstPath = null;
		
		for (int index = 0; index < paths.length; index++) {
			Path p = paths[index];
			if (p != null) {
				firstPath = p;
				BasicFileAttributes attr = getPathAttributes(index);
				filesInTheDirectory = attr.isRegularFile();
				if (filesInTheDirectory) {
					MultiPathFileNode pn = parent;
					while (pn != null && ! pn.filesInTheDirectory) {
						pn.filesInTheDirectory = true;
						pn = pn.parent;
					}
				}
				break;
			}
		}
		
		Path fileNamePath = firstPath.getFileName();
		this.name = fileNamePath == null ? firstPath.toString() : fileNamePath.toString();

		this.level = parent == null ? 0 : parent.level + 1;

		MultiPathFileNode[] parentNodes =  getNodePath();
		StringBuilder pathName = new StringBuilder(parentNodes[1].name);
		for (int idx = 2; idx < parentNodes.length; idx++) {
			pathName.append('/').append(parentNodes[idx].name);
		}
		this.treePathName = pathName.toString();
	}
	
	
	public boolean isDirectory() {
		return Files.isDirectory(firstPath());
	}
	
	
	/**
	 * @return the filesInTheDirectory
	 */
	public boolean areThereFilesInTheDirectory() {
		return filesInTheDirectory;
	}

	/**
	 * @return the rootDetails
	 */
	protected MultiPathFileNode getRoot() {
		return rootDetails.root;
	}

	/**
	 * @return the treePathName
	 */
	protected String getTreePathName() {
		return treePathName;
	}

	@Override
	public FileType getFileType() {
		Path p = firstPath();
		
		if (p == null) {
			return FileType.DRIVE;
		}
		if (Files.isDirectory(p)) {
			return FileType.DIRECTORY;
		}
		return FileType.FILE;
	}

	public MultiPathFileNode addChild(Path[] paths) {
		return new MultiPathFileNode(this, paths);
	}
	
	public boolean isEmptyPaths() {
		return firstPath() == null;
	}
	
	private Path firstPath() {
		for (Path p : paths) {
			if (p != null) {
				return p;
			}
		}
		
		return null;
	}
	
	public 	BasicFileAttributes getPathAttributes(int index) {
		if (index < pathAttributes.length) {
			if (paths[index] != null && loadFileAttributes[index]) {
				try {
					pathAttributes[index] = Files.getFileAttributeView( paths[index], BasicFileAttributeView.class).readAttributes();
				} catch (IOException e) {
				}
				loadFileAttributes[index] = false;
			}
			
			return pathAttributes[index];
		}
		return null;
	}

	protected void resetPathAttribute(int index) {
		if (index < pathAttributes.length) {
			pathAttributes[index] = null;
			loadFileAttributes[index] = true;
		}
	}
	
	public List<Path> getPaths() {
		return Arrays.asList(paths);
	}
	
	/**
	 * Get the path for a specific index
	 * @param index index of the requested path
	 * @return requested path
	 */
	public Path getPathForIndex(int index) {
		return paths == null || paths.length <= index || index < 0
				? null
				: paths[index];
	}
	
	protected void setPathForIndex(int index, Path path) {
		if (paths == null) {
			paths = new Path[index+1];
		} else if (paths.length > index ) {
			
		} else {
			Path[] t = new Path[index+1];
			System.arraycopy(paths, 0, t, 0, paths.length);
			paths = t;
		}
		paths[index] = path;
	}

	
	@Override
	public int getChildCount() {
		loadChildren();
		
		return children==null ? 0 :  children.size();
	}
	
	
	@Override
	public MultiPathFileNode getParent() {
		return parent;
	}

	@Override
	public boolean getAllowsChildren() {
		return ! isLeaf();
	}

	@Override
	public boolean isLeaf() {
		return getChildCount() == 0;
	}

	@Override
	public Enumeration<? extends TreeNode> children() {
		return Collections.enumeration(children);
	}

	@Override
	public MultiPathFileNode getChildAt(int i) {
		loadChildren();
		
		//nodeOpened = true;
		return children.get(i);
	}
	
	protected void removeChild(MultiPathFileNode node) {
		children.remove(node);
	}

	@Override
	public int getIndex(TreeNode n) {
		if (children == null) {
			load();
		}
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) == n) {
				return i;
			}
		}
		
		return -1;
	}

	
	public void loadChildTree() {
		loadChildren();
		if (children != null) {
		for (MultiPathFileNode fn : children) {
			fn.loadChildTree();
		}
		}
	}

	public void loadChildren() {
		if (children == null && ! loaded) {
			synchronized (this) {
				load();				
			}
		}
	}
	
	public MultiPathFileNode getChildNode(Path path) {
		ArrayList<Path> pathList = new ArrayList<Path>();
		Path current = path;
		boolean searching = true;
		
		while (current != null && searching) {
			pathList.add(current);
			current = current.getParent();
			
			for (Path p : paths) {
				if (p != null && p.equals(current)) {
					searching = true;
					break;
				}
			}
		}
		
		MultiPathFileNode n = this;
		
		int idx = pathList.size() - 1;
		while (n != null && idx >= 0 ) {
			n.load();
			String searchName = pathList.get(idx--).getFileName().toString();
			for (int i = 0; i < children.size(); i++) {
				if (rootDetails.compareString.compare(children.get(i).name, searchName) == 0) {
					if (idx == 0) {
						return children.get(i);
					}
					n = children.get(i);
				}
			}
		}
		return null;
	}
	

	
//	
//	public FileNode getChildForFile(Path searchPath, INotifyFileTreeChanged mdl) {
//		if (children == null) {
//			synchronized (this) {
//				FileNode n = loadGet(searchPath, INITIAL_READ, mdl);
//				if (n != null) {
//					return n;
//				}
//			}
//		}
//		
//		for (int i = 0; i < 7 && ! loaded; i++) {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//			}
//		}
//		//Path searchName = searchPath.getFileName();
//		
//		if (children != null) {
//			String searchStr = searchPath.toString();
////			System.out.println(" ---> " + searchPath);
//			int low = 0, mid = -1, high = children.length-1;
////			System.out.println(" ---> " + searchPath + " " + high);
//			while (low < high) {
//				mid = (low + high) / 2;
//				int compareTo = children[mid].path.toString().compareToIgnoreCase(searchStr);
////				System.out.print("\t" + mid + " " + compareTo + " " + children[mid].path);
//				if (compareTo <0 ) {
//					low = mid + 1;
//				} else if (compareTo > 0 ) {
//					high = mid - 1;
//				} else {
//					return children[mid];
//				}
//			}
////			System.out.println(" ~~~ " + low + " " + high+ " " + mid);
//			if (children[low].path.toString().equalsIgnoreCase(searchStr))  {
//				return children[low];
//			}
//		}
//		return null;
//	}
	
//	public Path getFilePath() {
//		return path;
//	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public TreePath treePath() {
		return new TreePath(getNodePath());
	}


	private MultiPathFileNode[] getNodePath() {
		MultiPathFileNode[] nodePath = new MultiPathFileNode[level+1];
		int i = level;
		MultiPathFileNode n = this;
		do {
			nodePath[i--] = n;
			n = n.parent;
		} while (n != null);
		return nodePath;
	}

	private final void load() {
		if (! loaded) {
			try {
				int max = 10;
				FileManager manager = new FileManager(this);
				for (int i = 0; i < paths.length; i++) {
					ArrayList<Path> files = loadPathList(paths[i]);
					manager.add(files);
					max = Math.max(max, files.size());
				}

				ArrayList<MultiPathFileNode> nodes = new ArrayList<MultiPathFileNode>(max * 12 / 10);

				while (manager.hasMore()) {
					nodes.add(manager.next());
				}

				if (nodes.size() == 0) {
					children = EMPTY;
				} else {
					children = new ArrayList<>(nodes);
					children.sort(rootDetails.compareNodesDirectory);
				}
				loaded = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private ArrayList<Path> loadPathList(Path parentPath) throws IOException {
		if (parentPath == null || ! Files.isDirectory(parentPath)) {
			return EMPTY_PATH_LIST;
		}
		
		ArrayList<Path> files = new ArrayList<>(70);
		try (DirectoryStream<Path>  dirStream = Files.newDirectoryStream(parentPath, rootDetails.fileFilter))  { 
			//dirStream = Files.newDirectoryStream(parentPath, rootDetails.fileFilter);
			Iterator<Path> iterator = dirStream.iterator();
			
			while (iterator.hasNext()) {
				files.add(iterator.next());
			}
	
			files.sort(rootDetails.comparePathName);
			
			return files;
		} 
		
	}

	public final void setChildren(MultiPathFileNode[] children) {
		this.children = Arrays.asList(children);
		this.loaded = true;
	}

//	private static void closeDirStream(DirectoryStream<Path> dirStream) {
//		if (dirStream != null) {
//			try {
//				dirStream.close();
//			} catch (IOException e1) { 	}
//		}
//	}
//	
//	private static class LoadChildren implements Runnable {
//		final FileNode node;
//		final ArrayList<FileNode> nodes;
//
//		final INotifyFileTreeChanged mdl;
//		final DirectoryStream<Path> dirStream;
//		final Iterator<Path> iterator;
//		
//		
//		private LoadChildren(FileNode node, ArrayList<FileNode> nodes, INotifyFileTreeChanged mdl,
//				DirectoryStream<Path> dirStream, Iterator<Path> iterator) {
//			super();
//			this.node = node;
//			this.nodes = nodes;
//			this.mdl = mdl;
//			this.dirStream = dirStream;
//			this.iterator = iterator;
//		}
//
//
//		@Override
//		public void run() {
//			while (iterator.hasNext()) {
//				nodes.add(new FileNode(node, iterator.next()));
//			}
//			
//			FileNode[] array = nodes.toArray(new FileNode[nodes.size()]);
//			Arrays.sort(array, COMPARE);
//			
//			
//			synchronized (node) {
//				node.children = array;
//				node.loaded = true;
//				if (node.nodeOpened) {
//					SwingUtilities.invokeLater(new Runnable() {
//						@Override public void run() {
//							mdl.fireStrucureChanged(node);
//						}
//					});
//				}
//			}
//			closeDirStream(dirStream);
//		}
//		
//		
//	}
	
	private static final class RootDetails {
		
		final MultiPathFileNode root;
		final boolean ignoreCase;
		final DirectoryStream.Filter<Path> fileFilter;
		
//		final Comparator<MultiPathFileNode> compareNodes = new Comparator<MultiPathFileNode>() {
//			@Override public int compare(MultiPathFileNode o1, MultiPathFileNode o2) {
//				if (ignoreCase) {
//					return o1.treePathName.compareToIgnoreCase(o2.treePathName);
//				}
//				return o1.treePathName.compareTo(o2.treePathName);
//			}
//		};
		
		final Comparator<MultiPathFileNode> compareNodesDirectory = new Comparator<MultiPathFileNode>() {
			@Override public int compare(MultiPathFileNode o1, MultiPathFileNode o2) {
				boolean o1IsdDirectory = o1.isDirectory();
				boolean o2IsdDirectory = o2.isDirectory();
				if (o1IsdDirectory != o2IsdDirectory) {
					return o2IsdDirectory ? 1 : -1;
				}
				if (ignoreCase) {
					return o1.treePathName.compareToIgnoreCase(o2.treePathName);
				}
				return o1.treePathName.compareTo(o2.treePathName);
			}
		};
		
		final Comparator<String> compareString = new Comparator<String>() {
			@Override public int compare(String o1, String o2) {
				if (ignoreCase) {
					return o1.compareToIgnoreCase(o2);
				}
				return o1.compareTo(o2);
			}
		};

		final Comparator<Path> comparePathName = new Comparator<Path>() {
			@Override public int compare(Path o1, Path o2) {
				if (ignoreCase) {
					return o1.getFileName().toString().compareToIgnoreCase(o2.getFileName().toString()); 
				}
				return o1.getFileName().toString().compareTo(o2.getFileName().toString());
			}
		};

		RootDetails(MultiPathFileNode root,boolean ignoreCase, DirectoryStream.Filter<Path> fileFilter) {
			super();
			this.root = root;
			this.ignoreCase = ignoreCase;
			this.fileFilter = fileFilter == null ? FileUtils.VISIBLE_FILES_DIRECTORY_FILTER : fileFilter;
		}
	}
	
	private static class FileManager {
		final ArrayList<FileDetail> fileDetails = new ArrayList<>();
		final MultiPathFileNode parent;
		final RootDetails rootDetails;
		
		
		public FileManager(MultiPathFileNode parent) {
			super();
			this.parent = parent;
			this.rootDetails = parent.rootDetails;
		}

		void add(List<Path> paths) {
			fileDetails.add(new FileDetail(paths));
		}
		
		boolean hasMore() {
			return ! endOfPaths();
		}
		
		boolean endOfPaths() {
			for (FileDetail f : fileDetails) {
				if (f.currentPath != null) {
					return false;
				}
			}
			return true;
		}
		
		MultiPathFileNode next() {
			if (endOfPaths()) { return null; }
			Path[] paths = new Path[fileDetails.size()];
			
			String nextName = null;
			
			for (FileDetail f : fileDetails) {
				if (f.currentPath != null) {
					if (nextName == null || rootDetails.compareString.compare(nextName, f.currentName) > 0) {
						nextName =  f.currentName;
					}
				}
			}
			
			for (int i = 0; i < paths.length; i++) {
				FileDetail f = fileDetails.get(i);
				if (rootDetails.compareString.compare(nextName, f.currentName) == 0) {
					paths[i] = f.currentPath;
					f.next();
				}
			}
			
			return parent.addChild(paths);
		}
	}
	
	private static class FileDetail {
		Iterator<Path> iterator;
		Path currentPath;
		String currentName;
		
		FileDetail(List<Path> paths) {
			iterator = paths.iterator();
			next();
		}
		
		void next() {
			if (iterator.hasNext()) {
				currentPath = iterator.next();
				currentName = currentPath.getFileName().toString();
			} else {
				currentPath = null;
				currentName = "";
			}
			
		}
	}
	
	
}
