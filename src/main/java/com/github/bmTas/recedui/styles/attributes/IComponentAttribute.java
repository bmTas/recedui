package com.github.bmTas.recedui.styles.attributes;

import java.awt.Component;


public interface IComponentAttribute {

	public void apply(Component component);
	
	class EmptyAttribute implements IComponentAttribute {
		public void apply(Component component) { 	}
	}
}