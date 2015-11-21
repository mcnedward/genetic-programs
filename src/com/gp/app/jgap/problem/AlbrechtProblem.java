package com.gp.app.jgap.problem;

import org.jgap.InvalidConfigurationException;

public class AlbrechtProblem extends BaseProblem {

	public AlbrechtProblem() throws InvalidConfigurationException {
		super("resources/albrecht.arff");
	}

	@Override
	protected String[] getAnnotationFilters() {
		return new String[] { "Input" };
	}

}
