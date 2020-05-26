package com.mygdx.game;

public enum Direction {
	UP, LEFT, DOWN, RIGHT;

	public boolean isOpposite(Direction d1) {
		return d1 == UP && this == DOWN || d1 == DOWN && this == UP
				|| d1 == LEFT && this == RIGHT ||d1 == RIGHT && this == LEFT;
	}
}
