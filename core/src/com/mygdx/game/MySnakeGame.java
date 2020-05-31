package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

public class MySnakeGame extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;
	SnakeGame game;
	int blockWidth;
	int blockHeight;
	int screenWidth;
	int screenHeight;
	int speed = 400;
	Difficulty difficulty;
	Screen currentScreen = Screen.TITLE;
	boolean directionChange = false;
	private boolean paused = false;
	long startTime = 0L;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		game = new SnakeGame();
		shapeRenderer = new ShapeRenderer();
		game.feldInit();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		blockWidth = screenWidth / game.getFeld()[0].length;
		blockHeight = screenHeight / game.getFeld().length;
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					if (currentScreen == Screen.TITLE) {
						dispose();
						System.exit(0);
					} else {
						currentScreen = Screen.TITLE;
					}
				}
				if (currentScreen == Screen.MAIN_GAME) {
					if (keycode == Input.Keys.SPACE) {
						paused = !paused;
					}
					if (!directionChange && !paused) {
						if (keycode == Input.Keys.DOWN) {

							game.getSnake().changeDirection(Direction.DOWN);
						} else if (keycode == Input.Keys.UP) {
							game.getSnake().changeDirection(Direction.UP);
						} else if (keycode == Input.Keys.LEFT) {
							game.getSnake().changeDirection(Direction.LEFT);
						} else if (keycode == Input.Keys.RIGHT) {
							game.getSnake().changeDirection(Direction.RIGHT);
						}
						directionChange = true;
					}

					return true;
				} else if (currentScreen == Screen.GAME_OVER) {
					currentScreen = Screen.TITLE;
					return true;
				} else {
					return false;
				}
			}

			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				if (currentScreen == Screen.GAME_OVER) {
					game.feldInit();
					currentScreen = Screen.TITLE;
					return true;
				}
				if (currentScreen != Screen.TITLE) {
					return false;
				}
				if (x >= screenWidth / 2 - 50 && x <= screenWidth / 2 + 50 && screenHeight - y >= screenHeight / 2
						&& screenHeight - y <= screenHeight / 2 + 30) {
					currentScreen = Screen.MAIN_GAME;
					difficulty = Difficulty.MEDIUM;
				} else if (x >= screenWidth / 2 - 50 && x <= screenWidth / 2 + 50
						&& screenHeight - y >= screenHeight / 2 + 40 && screenHeight - y <= screenHeight / 2 + 70) {
					currentScreen = Screen.MAIN_GAME;
					difficulty = Difficulty.EASY;
				} else if (x >= screenWidth / 2 - 50 && x <= screenWidth / 2 + 50
						&& screenHeight - y >= screenHeight / 2 - 40 && screenHeight - y <= screenHeight / 2 - 10) {
					currentScreen = Screen.MAIN_GAME;
					difficulty = Difficulty.HARD;
				} else {
					return false;
				}
				startTime = TimeUtils.millis();
				speed = difficulty.getStartSpeed();
				game.feldInit();
				return true;
			}
		});

	}

	@Override
	public void render() {
		if (currentScreen == Screen.MAIN_GAME) {
			speed = difficulty.getStartSpeed() - ((game.getSnake().getLength() - 2) * 15);
			if (speed < 100) {
				speed = 100;
			}
			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			if (!paused && TimeUtils.timeSinceMillis(startTime) > speed) {
				currentScreen = game.snakeMove();
				startTime = TimeUtils.millis();
				directionChange = false;
			}

			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			for (int i = 0; i < game.getFeld().length; i++) {
				Block[] zeile = game.getFeld()[i];
				for (int j = 0; j < zeile.length; j++) {
					if (zeile[j].isApfel()) {
						shapeRenderer.setColor(1, 0, 0, 1);

						shapeRenderer.rect(j * blockWidth + 2, i * blockHeight + 2, blockWidth - 3, blockHeight - 3);

					} else if (zeile[j].isBesetzt()) {
						shapeRenderer.setColor(0, 1, 0, 1);
						shapeRenderer.rect(j * blockWidth + 2, i * blockHeight + 2, blockWidth - 3, blockHeight - 3);

					} else {
						shapeRenderer.setColor(0, 0, 0, 0);
					}
					shapeRenderer.rect(j * blockWidth + 2, i * blockHeight + 2, blockWidth - 3, blockHeight - 3);
				}
			}

			shapeRenderer.end();
			batch.begin();
			font.draw(batch, "Score: " + (game.getSnake().getLength() - 2), 10, screenHeight - 10);
			batch.end();

		} else if (currentScreen == Screen.GAME_OVER) {
			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			font.draw(batch, "Game Over", screenWidth / 2 - 25, screenHeight / 2);
			font.draw(batch, "Score: " + (game.getSnake().getLength() - 2), 10, screenHeight - 10);
			batch.end();
		} else if (currentScreen == Screen.TITLE) {

			Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			int mausX = Gdx.input.getX(0);
			int mausY = Gdx.input.getY(0);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

			if (mausX >= screenWidth / 2 - 50 && mausX <= screenWidth / 2 + 50
					&& screenHeight - mausY >= screenHeight / 2 && screenHeight - mausY <= screenHeight / 2 + 30) {
				shapeRenderer.setColor(Color.GREEN);
			} else {
				shapeRenderer.setColor(Color.LIME);
			}
			shapeRenderer.rect(screenWidth / 2 - 50, screenHeight / 2, 100, 30);

			if (mausX >= screenWidth / 2 - 50 && mausX <= screenWidth / 2 + 50
					&& screenHeight - mausY >= screenHeight / 2 + 40 && screenHeight - mausY <= screenHeight / 2 + 70) {
				shapeRenderer.setColor(Color.GREEN);
			} else {
				shapeRenderer.setColor(Color.LIME);
			}
			shapeRenderer.rect(screenWidth / 2 - 50, screenHeight / 2 + 40, 100, 30);
			if (mausX >= screenWidth / 2 - 50 && mausX <= screenWidth / 2 + 50
					&& screenHeight - mausY >= screenHeight / 2 - 40 && screenHeight - mausY <= screenHeight / 2 - 10) {
				shapeRenderer.setColor(Color.GREEN);
			} else {
				shapeRenderer.setColor(Color.LIME);
			}
			shapeRenderer.rect(screenWidth / 2 - 50, screenHeight / 2 - 40, 100, 30);
			shapeRenderer.end();

			batch.begin();
			font.draw(batch, "EASY", screenWidth / 2 - 20, screenHeight / 2 + 60);
			font.draw(batch, "MEDIUM", screenWidth / 2 - 32, screenHeight / 2 + 20);
			font.draw(batch, "HARD", screenWidth / 2 - 20, screenHeight / 2 - 20);
			batch.end();
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
