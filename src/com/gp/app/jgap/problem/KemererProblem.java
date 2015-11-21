package com.gp.app.jgap.problem;

import org.jgap.InvalidConfigurationException;

public class KemererProblem extends BaseProblem {

	public KemererProblem() throws InvalidConfigurationException {
		super("resources/kemerer.arff");
	}

	@Override
	protected String[] getAnnotationFilters() {
		return new String[] { "" };
	}

}
