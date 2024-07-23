package com.github.bmTas.recedui.multiPath.fileTree;

import java.nio.file.Path;

public interface ICreateFileNode {

	MultiPathFileNode newNode(MultiPathFileNode parent, Path[] paths);
}
