package com.github.bmTas.recedui.application.displays;

import java.util.ArrayList;
import java.util.List;

import com.github.bmTas.recedui.def.application.displays.IDocumentDetails;
import com.github.bmTas.recedui.def.application.displays.IGuiApplicationPanel;
import com.github.bmTas.recedui.def.application.displays.IInternalFrameCreationDetails;

public class InternalFrameDetailsBuilder {
	
	
	private FrameDetails frameDetails = new FrameDetails();
	

	public InternalFrameDetailsBuilder setDocument(String documentName, Object document) {
		frameDetails.document = new DocumentDetails(documentName, document);
		return this;
	}
	
	public InternalFrameDetailsBuilder setDocument(IDocumentDetails documentDetails) {
		frameDetails.document = documentDetails;
		return this;
	}

	public InternalFrameDetailsBuilder setTitle(String title) {
		frameDetails.title = title;
		return this;
	}

	public InternalFrameDetailsBuilder addPanel(IGuiApplicationPanel panel) {
		frameDetails.panels.add(panel);
		return this;
	}
	
	public IInternalFrameCreationDetails buildParameter() {
		 return frameDetails.deapClone();
	}

	
	private static class FrameDetails implements IInternalFrameCreationDetails {
		private IDocumentDetails document;

		private String title = "No Title Supplied";
		private List<IGuiApplicationPanel> panels = new ArrayList<>();
		
		private FrameDetails deapClone() {
			FrameDetails f = new FrameDetails();
			
			f.document = document == null || document == DocumentDetails.NULL_DOCUMENT
					? DocumentDetails.NULL_DOCUMENT 
					: new DocumentDetails(document);
			f.title = title;
			f.panels.addAll(panels);
			
			return f;
		}
		
		@Override
		public IDocumentDetails getDocumentDetails() {
			return document;
		}

		@Override
		public String getTitle() {
			return title;
		}

		@Override
		public List<IGuiApplicationPanel> getApplicationPanels() {
			return panels;
		}
	}
}
