package com.github.bmTas.recedui.def.treeCombo;

import java.util.List;

public interface ITreeComboModel<Code> {
	List<? extends ITreeComboItem<Code>> getChildren();
}
