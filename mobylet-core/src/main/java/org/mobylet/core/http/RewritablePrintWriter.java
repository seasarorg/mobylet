package org.mobylet.core.http;

import java.io.PrintWriter;
import java.io.Writer;

public class RewritablePrintWriter extends PrintWriter {


	public RewritablePrintWriter(Writer out) {
		super(out);
	}

}
