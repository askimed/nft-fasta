package com.askimed.nf.test.genomics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class PathExtension {

	public static Object getFasta(Path self) throws FileNotFoundException, IOException {
		return FastaUtil.readAsMap(self);
	}

	
}
