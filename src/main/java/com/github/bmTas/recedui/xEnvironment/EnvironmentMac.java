package com.github.bmTas.recedui.xEnvironment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EnvironmentMac extends EnvironmentDefault {

	private static final String VOLUME_START_STRING = " on /Volumes";

	@Override
	public File[] getOtherMountPoints() {
	    List<File> roots = new ArrayList<File> ();
		try {
			Process mountProcess = Runtime.getRuntime().exec ( "mount" );
			mountProcess.waitFor();
			getFiles(roots, mountProcess.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return roots.toArray(new File[roots.size()]);
	}

	public static void getFiles(List<File> roots, InputStream in ) throws IOException {
		BufferedReader mountOutput = new BufferedReader ( new InputStreamReader(in ) );
		try {
		    String line;
		    while ( (line = mountOutput.readLine ()) != null ) {
	
		        // the line will be formatted as "... on <filesystem> (...)"; get the substring we need
		        int indexStart = line.indexOf ( VOLUME_START_STRING );
		        int indexEnd = line.indexOf ( " ", indexStart + 4 );
		        int volumeStart = indexStart + VOLUME_START_STRING.length() + 1;
		        
		       // System.out.println(line + "\t" + indexStart);
		        
		        if (indexStart >= 0 && volumeStart < indexEnd) {
					String s = line.substring ( indexStart + 4, indexEnd );

					roots.add ( new File(s) );
		        }
		    }
		} finally {
			mountOutput.close();
		}
	}
	
//	public static void main(String[] args) throws IOException {
//	    List<File> roots = new ArrayList<File> ();
//		try {
//			Process mountProcess = Runtime.getRuntime().exec ( "mount" );
//			mountProcess.waitFor();
//			getFiles(roots, mountProcess.getInputStream());
//			
//			for (File f : roots) {
//				System.out.println(f.getPath());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//	}
}
