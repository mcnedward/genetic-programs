package com.gp.app.jgap.problem;

import org.jgap.InvalidConfigurationException;

public class ChinaProblem extends BaseProblem {

	public ChinaProblem() throws InvalidConfigurationException {
		super("resources/china.arff");
	}

	@Override
	protected String[] getAnnotationFilters() {
		return null;
	}

}
