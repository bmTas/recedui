package com.github.bmTas.recedui.icons;

import java.awt.Color;

import javax.swing.Icon;

public class ExportHolder implements IIconHolder {

	private ExportIcon icon, largeIcon, toolbarIcon;
	private final IIconHolder holder;
	private final Color color;
	private final boolean arrowAtTop;
	public ExportHolder(IIconHolder holder, Boolean arrowAtTop, Color color) {
		this.holder = holder;
		this.color = color;
		this.arrowAtTop = arrowAtTop;
	}

	@Override
	public Icon icon() {
		if (icon == null) {
			//System.out.print("\t" + holder.);
			icon = new ExportIcon(holder.icon(), arrowAtTop, color);
		}
		return icon;
	}

	@Override
	public Icon largeIcon() {
		if (largeIcon == null) {
			largeIcon = new ExportIcon(holder.largeIcon(), arrowAtTop, color);
		}
		return largeIcon;
	}

	@Override
	public Icon toolbarIcon() {
		if (toolbarIcon == null) {
			toolbarIcon = new ExportIcon(holder.toolbarIcon(), arrowAtTop, color);
		}
		return toolbarIcon;
	}

}
