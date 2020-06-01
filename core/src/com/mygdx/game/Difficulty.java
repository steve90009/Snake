package com.mygdx.game;

public enum Difficulty {
	EASY, MEDIUM, HARD;

	public int getStartSpeed() {
		if (this == EASY) {
			return 400;
		} else if (this == MEDIUM) {
			return 300;
		} else if (this == HARD) {
			return 250;
		}
		return 400;
	}
}
