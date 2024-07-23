package com.github.bmTas.recedui.def.common;

public interface IListPopupActions<What>  {

	public void fireFileChanged(What f);

	/** 
	 * popup has been hidden
	 */
	public void  popupHidden() ;
}
