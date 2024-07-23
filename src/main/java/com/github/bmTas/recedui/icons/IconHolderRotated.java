package com.github.bmTas.recedui.icons;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconHolderRotated extends IconHolder {

	public IconHolderRotated(String name) {
		super(name);
	}

	@Override
	protected Icon loadIcon(int height) {
		Icon icon = null;
		String relativeName = "i" + height + "/" + name;
		try {
			BufferedImage image = ImageIO.read(IconHolderRotated.class.getResource ( relativeName));
			BufferedImage bi = new BufferedImage(image.getHeight(), image.getWidth(), BufferedImage.TYPE_INT_ARGB_PRE);
//			AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(270));
//			transform.
			
			for (int h = image.getHeight()-1; h >= 0; h--) {
				for (int w = image.getWidth()-1; w >= 0; w--) {
					bi.setRGB(w, h, image.getRGB(h,  w));
				}
			}
			icon = new ImageIcon ( bi );
		} catch (Exception e) {
			System.out.println("   ===>>> " + relativeName);
		}
		return icon;
	}

}
