package com.gp.app.jgap.data;

import java.util.List;

public class DataSet {

	private List<DataVariable> variables;
	private double effort;

	public DataSet(List<DataVariable> variables, double effort) {
		this.variables = variables;
		this.effort = effort;
	}

	public double getVariableByAttribute(String attributeName) {
		for (DataVariable variable : variables) {
			if (variable.getAttribute().equals(attributeName))
				return variable.getValue();
		}
		return 0;
	}

	public List<DataVariable> getVariables() {
		return variables;
	}

	public double getEffort() {
		return effort;
	}
	
	@Override
	public String toString() {
		return "DataSet - Effort[" + effort + "] - Variable Count[" + variables.size() + "]";
	}

}
