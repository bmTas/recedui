package com.github.bmTas.recedui.xEnvironment;

public class Env {
	public static final boolean IS_MAC, IS_NIX, IS_WINDOWS;
	public static final String USER_HOME       = System.getProperty("user.home");

	public static enum OsType {
		WINDOWS,
		NIX,
		MAC,
		OTHER
	};

	public static final OsType OS_TYPE;


	static {
		boolean isNix = false, isMac=false, isWin = false;
		try {
			String s = System.getProperty("os.name").toLowerCase();
			if (s != null) {
				isNix = (s.indexOf("nix") >= 0 || s.indexOf("nux") >= 0);
				isMac = s.indexOf("mac") >= 0;
				isWin = s.indexOf("win") >= 0;
			}
		} catch (Exception e) {
		}

		IS_MAC = isMac;
		IS_NIX = isNix;
		IS_WINDOWS = isWin;

		if (IS_WINDOWS) {
			OS_TYPE = OsType.WINDOWS;
		} else if (IS_MAC) {
			OS_TYPE = OsType.MAC;
		} else if (IS_NIX) {
			OS_TYPE = OsType.NIX;
		} else {
			OS_TYPE = OsType.OTHER;
		} 
	}
	//
	public static IEnvironmentValues getEnvironmentDefaults() {
    	if (IS_NIX ) {
    		return new EnvironmentLinux();
    	} 
    	if ( IS_MAC) {
    		return new EnvironmentMac();
    	}
    	return new EnvironmentDefault();
    }
	
}
