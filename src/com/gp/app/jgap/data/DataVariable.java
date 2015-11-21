package com.gp.app.jgap.data;

public class DataVariable {

	private String attribute;
	private double value;

	public DataVariable(String attribute, double value) {
		this.attribute = attribute;
		this.value = value;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.format("%s[%s]", attribute, value);
	}

}
