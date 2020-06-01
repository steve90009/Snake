package com.mygdx.game;

import com.badlogic.gdx.Input;

public class Saves {
	private boolean sound = true;
	private int fieldSize = 2;
	private int highScore = 0;
	private int up = Input.Keys.UP;
	private int down = Input.Keys.DOWN;
	private int left = Input.Keys.LEFT;
	private int right = Input.Keys.RIGHT;
	public boolean isSound() {
		return sound;
	}
	public void setSound(boolean sound) {
		this.sound = sound;
	}
	public int getFieldSize() {
		return fieldSize;
	}
	public void setFieldSize(int fieldSize) {
		this.fieldSize = fieldSize;
	}
	public int getHighScore() {
		return highScore;
	}
	public void setHighScore(int highScore) {
		this.highScore = Math.max(highScore, this.highScore);
	}
	public int getUp() {
		return up;
	}
	public void setUp(int up) {
		this.up = up;
	}
	public int getDown() {
		return down;
	}
	public void setDown(int down) {
		this.down = down;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getRight() {
		return right;
	}
	public void setRight(int right) {
		this.right = right;
	}

	public void load() {
	}
	public void save() {
	}
}
