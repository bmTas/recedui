package com.github.bmTas.recedui.def.application.displays;

import java.util.List;

public interface IInternalFrameCreationDetails {
	IDocumentDetails getDocumentDetails();
	String getTitle();
	List<IGuiApplicationPanel> getApplicationPanels();
}
