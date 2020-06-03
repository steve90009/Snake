package com.mygdx.game;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input;

public class Saves {
	private boolean sound = true;
	private int fieldSize = 2;
	private int highScore = 0;
	private int up = Input.Keys.UP;
	private int down = Input.Keys.DOWN;
	private int left = Input.Keys.LEFT;
	private int right = Input.Keys.RIGHT;
	private int pause = Input.Keys.SPACE;

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
		List<String> saves = new ArrayList<String>();
		try {

			Path path = Paths.get("save.txt");
			if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
				saves = Files.readAllLines(path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (saves.size() == 8) {
			sound = Boolean.valueOf(saves.get(0).split("=")[1]);
			fieldSize = tryRead(1, saves, fieldSize, 0);
			up = tryRead(2, saves, up, 1);
			down = tryRead(3, saves, down, 1);
			left = tryRead(4, saves, left, 1);
			right = tryRead(5, saves, right, 1);
			pause = tryRead(6, saves, pause, 1);
			highScore = tryRead(7, saves, highScore, 2);

		}
	}

	public void save() {
		try {
			File file = new File("save.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter pw = new PrintWriter(file);
			pw.println("Sound=" + isSound());
			pw.println("Size=" + getFieldSize());
			pw.println("Up=" + getUp());
			pw.println("Down=" + getDown());
			pw.println("Left=" + getLeft());
			pw.println("Right=" + getRight());
			pw.println("Pause=" + getPause());
			pw.println("HighScore=" + getHighScore());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getPause() {
		return pause;
	}

	public void setPause(int pause) {
		this.pause = pause;
	}

	private int tryRead(int index, List<String> saves, int defaultValue, int type) {
		try {
			int value = Integer.valueOf(saves.get(index).split("=")[1]);
			if (type == 1 && Input.Keys.toString(value) != null) {
				return value;
			} else if (type == 0 && (value == 1 || value == 2)) {
				return value;
			} else if (type == 2) {
				return value;
			}
		} catch (NumberFormatException e) {
		}
		return defaultValue;
	}
}
