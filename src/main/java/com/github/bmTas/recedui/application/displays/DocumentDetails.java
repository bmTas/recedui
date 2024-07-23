package com.github.bmTas.recedui.application.displays;

import com.github.bmTas.recedui.def.application.displays.IDocumentDetails;

public class DocumentDetails implements IDocumentDetails {
	public static final IDocumentDetails NULL_DOCUMENT = new DocumentDetails("", null);

	private final String documentName;
	private final Object document;
	
	public DocumentDetails(String documentName, Object document) {
		super();
		this.documentName = documentName;
		this.document = document;
	}
	
	public DocumentDetails(IDocumentDetails documentDetails) {
		super();
		this.documentName = documentDetails.getDocumentName();
		this.document = documentDetails.getDocument();
	}

	@Override
	public String getDocumentName() {
		return documentName;
	}

	@Override
	public Object getDocument() {
		return document;
	}

}
