package com.github.bmTas.recedui.fileTree;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import com.github.bmTas.recedui.xfiles.AllFilesFilter;
import com.github.bmTas.recedui.xfiles.FileUtils;
import com.github.bmTas.recedui.xfiles.IFileChooserFilter;

public class FileNode {
	private static int INITIAL_READ = 500;
	private static final FileNode[] EMPTY = new FileNode[0];
	
	private static final Comparator<FileNode> COMPARE = new Comparator<FileNode>() {
		@Override public int compare(FileNode o1, FileNode o2) {
			return o1.path.getFileName().toString().compareToIgnoreCase(o2.path.getFileName().toString());
		}
	};
	
	
	final FileNode parent;
	private final Path path;
	private FileNode[] children;
	private boolean loaded = false, nodeOpened = false;
	private final String name;
	private final int level;
//	private final DirectoryStream.Filter<Path> filter;
	
//	public FileNode(Path path) {
//		this(path, null);
//	}

	public FileNode(Path path/*, DirectoryStream.Filter<Path> filter*/) {
		this.level = 0;
		this.parent = null;
		this.path = path;
		this.name = getNodeName(path);
//		this.filter = filter == null ? AllFilesFilter.ALL_FILES : filter;
	}

	public FileNode(FileNode parent, Path path) {

		super();
		this.parent = parent;
		this.path = path;
		
		this.name = getNodeName(path);
		this.level = parent.level + 1;
//		this.filter = parent.filter;
	}

	private String getNodeName(Path path) {
		if (path == null) {
			loaded = true;
			return "";
		}
		Path fileNamePath = path.getFileName();
		return fileNamePath == null ? path.toString() : fileNamePath.toString();
	}
	
	
	
	public int count(INotifyFileTreeChanged mdl) {
		checkLoad(mdl);
		
		return children==null ? 0 :  children.length;
	}

	private void checkLoad(INotifyFileTreeChanged mdl) {
		if (children == null) {
			synchronized (this) {
				loadGet(null, INITIAL_READ, INITIAL_READ, mdl);				
			}
		}
	}
	
	public FileNode get(int i, INotifyFileTreeChanged mdl) {
		checkLoad(mdl);
		
		nodeOpened = true;
		return children[i];
	}

	public int getIndex(FileNode n) {
		if (children == null) {
			loadGet(null, Integer.MAX_VALUE, Integer.MAX_VALUE, null);
		}
		for (int i = 0; i < children.length; i++) {
			if (children[i] == n) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	public FileNode getChildForFile(Path searchPath, INotifyFileTreeChanged mdl) {
		if (children == null) {
			synchronized (this) {
				FileNode n = loadGet(searchPath, INITIAL_READ, Integer.MAX_VALUE, mdl);
				if (n != null) {
					return n;
				}
			}
		}
		
		for (int i = 0; i < 7 && ! loaded; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		//Path searchName = searchPath.getFileName();
		
		if (children != null) {
			String searchStr = searchPath.toString();
//			System.out.println(" ---> " + searchPath);
			int low = 0, mid = -1, high = children.length-1;
//			System.out.println(" ---> " + searchPath + " " + high);
			while (low < high) {
				mid = (low + high) / 2;
				int compareTo = children[mid].path.toString().compareToIgnoreCase(searchStr);
//				System.out.print("\t" + mid + " " + compareTo + " " + children[mid].path);
				if (compareTo <0 ) {
					low = mid + 1;
				} else if (compareTo > 0 ) {
					high = mid - 1;
				} else {
					return children[mid];
				}
			}
//			System.out.println(" ~~~ " + low + " " + high+ " " + mid);
			if (children[low].path.toString().equalsIgnoreCase(searchStr))  {
				return children[low];
			}
		}
		return null;
	}
	
	public Path getFilePath() {
		return path;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public TreePath treePath() {
		FileNode[] nodePath = new FileNode[level+1];
		int i = level;
		FileNode n = this;
		do {
			nodePath[i--] = n;
			n = n.parent;
		} while (n != null);
		return new TreePath(nodePath);
	}

	private final FileNode loadGet(Path searchPath, int limit, int loadLimit, INotifyFileTreeChanged mdl) {
		FileNode n, ret = null;
		if (! loaded) {
			ArrayList<FileNode> nodes = new ArrayList<FileNode>(70);
			DirectoryStream<Path> dirStream = null;
			try {
				dirStream = Files.newDirectoryStream(path, FileUtils.DIRECTORY_FILTER);
				Iterator<Path> iterator = dirStream.iterator();
				Path nextPath;
				int i = 0;
				
				while (iterator.hasNext() 
					&& (i < limit || ret == null)
					&& i++ <= loadLimit) {
					nextPath = iterator.next();
					n = new FileNode(this, nextPath);
					if (nextPath.equals(searchPath)) {
						ret = n;
					}
					nodes.add(n);
				}
				
				if (searchPath != null && ret ==null) {
					ret = new FileNode(this, searchPath);
					nodes.add(ret);
				}
				
				if (nodes.size() == 0) {
					children = EMPTY;
				} else {
					children = nodes.toArray(new FileNode[nodes.size()]);
					Arrays.sort(children, COMPARE);
				}
				if (iterator.hasNext()) {
					new Thread(new LoadChildren(this, nodes, mdl, dirStream, iterator)).start();;
				} else {
					loaded = true;
					closeDirStream(dirStream);
				}
			} catch (AccessDeniedException e) {
				closeDirStream(dirStream);
			} catch (IOException e) {
				e.printStackTrace();
				
				closeDirStream(dirStream);
			}
		}
		
		return ret;
	}

	public final void setChildren(FileNode[] children) {
		this.children = children;
		this.loaded = true;
	}

	private static void closeDirStream(DirectoryStream<Path> dirStream) {
		if (dirStream != null) {
			try {
				dirStream.close();
			} catch (IOException e1) { 	}
		}
	}
	
	private static class LoadChildren implements Runnable {
		final FileNode node;
		final ArrayList<FileNode> nodes;

		final INotifyFileTreeChanged mdl;
		final DirectoryStream<Path> dirStream;
		final Iterator<Path> iterator;
		
		
		private LoadChildren(FileNode node, ArrayList<FileNode> nodes, INotifyFileTreeChanged mdl,
				DirectoryStream<Path> dirStream, Iterator<Path> iterator) {
			super();
			this.node = node;
			this.nodes = nodes;
			this.mdl = mdl;
			this.dirStream = dirStream;
			this.iterator = iterator;
		}


		@Override
		public void run() {
			while (iterator.hasNext()) {
				nodes.add(new FileNode(node, iterator.next()));
			}
			
			FileNode[] array = nodes.toArray(new FileNode[nodes.size()]);
			Arrays.sort(array, COMPARE);
			
			
			synchronized (node) {
				node.children = array;
				node.loaded = true;
				if (node.nodeOpened) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override public void run() {
							mdl.fireStrucureChanged(node);
						}
					});
				}
			}
			closeDirStream(dirStream);
		}
		
		
	}
}
