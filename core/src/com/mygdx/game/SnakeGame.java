package com.mygdx.game;

import java.util.Random;

public class SnakeGame {

	int feldHoehe = 24;
	int feldBreite = 32;
	private Block[][] feld = new Block[feldHoehe][feldBreite];
	private Snake snake;

	public Snake getSnake() {
		return snake;
	}

	public void feldInit() {
		snake = new Snake(Direction.LEFT);
		for (int x = 0; x < feldHoehe; x++) {
			for (int y = 0; y < feldBreite; y++) {
				feld[x][y] = new Block(x, y);
			}
		}
		snake.snakeInit(feld[feldHoehe / 2][feldBreite / 2 - 1], feld[feldHoehe / 2][feldBreite / 2]);
		setApfel();
	}

	public void setApfel() {
		Random dice = new Random();
		boolean besetzt = true;
		while (besetzt) {
			int x = dice.nextInt(feldHoehe);
			int y = dice.nextInt(feldBreite);
			if (!feld[x][y].isBesetzt()) {
				besetzt = false;
				feld[x][y].setApfel(true);
			}
		}
	}

	public Block[][] getFeld() {
		return feld;
	}

	public boolean snakeMove() {

		Block head = snake.getHead();
		Block newHead;
		Block tail = snake.getTail();
		int xPos = head.getPosX();
		int yPos = head.getPosY();
		try {
			switch (snake.getDirection()) {
			case UP:
				newHead = feld[xPos + 1][yPos];
				break;
			case DOWN:
				newHead = feld[xPos - 1][yPos];
				break;
			case LEFT:
				newHead = feld[xPos][yPos - 1];
				break;
			case RIGHT:
				newHead = feld[xPos][yPos + 1];
				break;
			default:
				newHead = head;
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			return false;
		}
		if (newHead.isBesetzt() && !newHead.equals(tail)) {
			return false;
		}
		if (!newHead.isApfel()) {
			snake.deleteTail();
			snake.setHead(newHead);
		} else {
			snake.setHead(newHead);
			setApfel();
			newHead.setApfel(false);
		}
		return true;
	}
}