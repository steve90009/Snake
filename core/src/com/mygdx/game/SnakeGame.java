package com.mygdx.game;

import java.util.Random;

public class SnakeGame {
	// 12*16
	int feldHoehe = 12;
	int feldBreite = 16;
	int size = 2;
	private Block[][] feld;
	private Snake snake;

	public Snake getSnake() {
		return snake;
	}

	public void feldInit(int size) {
		this.size = size;
		snake = new Snake(Direction.LEFT);
		int hoehe = feldHoehe * size;
		int breite = feldBreite * size;
		feld = new Block[hoehe][breite];
		for (int x = 0; x < hoehe; x++) {
			for (int y = 0; y < breite; y++) {
				feld[x][y] = new Block(x, y);
			}
		}
		snake.snakeInit(feld[hoehe / 2][breite / 2 - 1], feld[hoehe / 2][breite / 2]);
		setApfel();
	}

	public void setApfel() {
		Random dice = new Random();
		boolean besetzt = true;
		while (besetzt) {
			int x = dice.nextInt(feldHoehe * size);
			int y = dice.nextInt(feldBreite * size);
			if (!feld[x][y].isBesetzt()) {
				besetzt = false;
				feld[x][y].setApfel(true);
			}
		}
	}

	public Block[][] getFeld() {
		return feld;
	}

	public MoveResult snakeMove() {

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
			return MoveResult.GAMEOVER;
		}
		if (newHead.isBesetzt() && !newHead.equals(tail)) {
			return MoveResult.GAMEOVER;
		}
		if (!newHead.isApfel()) {
			snake.deleteTail();
			snake.setHead(newHead);
		} else {
			snake.setHead(newHead);
			setApfel();
			newHead.setApfel(false);
			return MoveResult.APPLE;
		}
		return MoveResult.NONE;
	}

	public int getHoehe() {
		return feldHoehe * size;
	}

	public int getBreite() {
		return feldBreite * size;
	}
}