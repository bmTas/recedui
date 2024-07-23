package com.github.bmTas.recedui.treeCombo;

import java.util.List;

import com.github.bmTas.recedui.def.treeCombo.ITreeComboItem;
import com.github.bmTas.recedui.def.treeCombo.ITreeComboModel;
import com.github.bmTas.recedui.standard.component.BaseComboParam;

public class TreeComboParam<Code> extends BaseComboParam<TreeComboParam<Code>>
implements ITreeComboParam<Code>, ITreeComboParamBldr<Code> {

	private ITreeComboModel<Code> comboModel;
	private ITreeComboItem<Code> blankItem;
	private boolean addBlankItem = false;
	
	
	@Override
	public ITreeComboModel<Code> getComboModel() {
		return comboModel;
	}


	@Override
	public ITreeComboParamBldr<Code> setComboModel(ITreeComboModel<Code> comboModel) {
		this.comboModel = comboModel;
		return this;
	}
	
	@Override
	public ITreeComboParamBldr<Code> setComboModel(ITreeComboItem<Code>[] children) {
		this.comboModel = new TreeComboModel<>(children);
		return this;
	}

	@Override
	public ITreeComboParamBldr<Code> setComboModel(List<ITreeComboItem<Code>> children) {
		this.comboModel = new TreeComboModel<Code>(children);
		return this;
	}
	
	

	@Override
	public ITreeComboItem<Code> getBlankItem() {
		return blankItem == null ?  new TreeComboCodeItem<>("", null, (Code) null) : blankItem;
	}


	@Override
	public ITreeComboParamBldr<Code> setBlankItem(ITreeComboItem<Code> blankItem) {
		this.blankItem = blankItem;
		return this;
	}


	@Override
	public boolean isAddBlankItem() {
		return addBlankItem;
	}


	@Override
	public ITreeComboParamBldr<Code> setAddBlankItem(boolean addBlankItem) {
		this.addBlankItem = addBlankItem;
		return this;
	}


	@Override
	public TreeComboParam<Code> asParam() {
		return this;
	}



}
