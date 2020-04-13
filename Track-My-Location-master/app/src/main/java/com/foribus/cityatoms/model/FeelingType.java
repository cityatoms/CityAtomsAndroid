package com.foribus.cityatoms.model;

public enum FeelingType {

	NORMAL(1),
	HAVING_SYMPTOMS(2),
	HOSPITAL(3),
	C19(4);

	private final int value;

	FeelingType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
