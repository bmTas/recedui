package com.github.bmTas.recedui.common.event;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.time.LocalTime;

public class CreateEvent {
	public static int eventId = 1;
	
	public static MouseEvent CreateMouseEvent(Component source,  int clickCount) {
		return CreateMouseEvent(source, clickCount, clickCount, clickCount, clickCount, clickCount);
	}

	public static MouseEvent CreateMouseEvent(Component source,  int modifiers, int x, int y, int clickCount, int button) {
		return new MouseEvent(source, eventId++, LocalTime.now().toSecondOfDay(), modifiers, x, y, clickCount, false, button);
	}
}
