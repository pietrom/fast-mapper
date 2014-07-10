package org.amicofragile.fm;

public interface ClassMapper<InT, OutT> {
	public OutT map(InT input);
}
