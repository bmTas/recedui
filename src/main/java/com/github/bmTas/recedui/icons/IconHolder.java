package com.github.bmTas.recedui.icons;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.github.bmTas.recedui.xEnvironment.SwingUtils;


public class IconHolder implements IIconHolder {
	private Icon icon, largeIcon, toolbarIcon;
	final String name;
	
	public IconHolder( String name) {
		super();
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.icons.IIconHolder#icon()
	 */
	@Override
	public final Icon icon() {
		
		if (icon == null) {
			int height = SwingUtils.values().stdIconHeight();
			icon = height == SwingUtils.values().toolbarIconHeight()
					? toolbarIcon()
					: loadIcon(height);
		}
		
		return icon;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.recordEditor.ui.icons.IIconHolder#icon()
	 */
	@Override
	public final Icon largeIcon() {
		
		if (largeIcon == null) {
			int height = SwingUtils.values().largeIconHeight();
			largeIcon = height == SwingUtils.values().toolbarIconHeight()
					? toolbarIcon()
					: loadIcon(height);
		}
		
		return largeIcon;
	}

	@Override
	public Icon toolbarIcon() {
		
		if (toolbarIcon == null) {
			toolbarIcon = loadIcon(SwingUtils.values().toolbarIconHeight());
		}
		
		return toolbarIcon;
	}

	protected Icon loadIcon(int height) {
		Icon icon = null;
		String relativeName = "i" + height + "/" + name;
		//System.out.println("   >> " + relativeName);
		try {
			icon = new ImageIcon ( IconHolder.class.getResource ( relativeName));
		} catch (Exception e) {
			System.out.println("   ===>>> " + relativeName);
		}
		return icon;
	}
	
	

}
