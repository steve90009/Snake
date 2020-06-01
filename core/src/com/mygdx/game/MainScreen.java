package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

public class MainScreen extends ScreenAdapter {
	MySnakeGame myGame;

	private boolean paused;

	protected boolean directionChange;

	private long startTime;

	public MainScreen(MySnakeGame game) {
		myGame = game;
	}

	@Override
	public void render(float delta) {

		myGame.speed = myGame.difficulty.getStartSpeed() - ((myGame.game.getSnake().getLength() - 2) * 15);
		if (myGame.speed < 100) {
			myGame.speed = 100;
		}
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		MoveResult moveResult = MoveResult.NONE;
		if (!paused && TimeUtils.timeSinceMillis(startTime) > myGame.speed) {
			moveResult = myGame.game.snakeMove();
			startTime = TimeUtils.millis();
			directionChange = false;
		}
		if (moveResult == MoveResult.APPLE && myGame.saves.isSound()) {
			myGame.sound.play();
		}
		myGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (int i = 0; i < myGame.game.getFeld().length; i++) {
			Block[] zeile = myGame.game.getFeld()[i];
			for (int j = 0; j < zeile.length; j++) {
				if (zeile[j].isApfel()) {
					myGame.shapeRenderer.setColor(1, 0, 0, 1);

					myGame.shapeRenderer.rect(j * myGame.blockWidth + 2, i * myGame.blockHeight + 2,
							myGame.blockWidth - 3, myGame.blockHeight - 3);

				} else if (zeile[j].isBesetzt()) {
					myGame.shapeRenderer.setColor(0, 1, 0, 1);
					myGame.shapeRenderer.rect(j * myGame.blockWidth + 2, i * myGame.blockHeight + 2,
							myGame.blockWidth - 3, myGame.blockHeight - 3);

				} else {
					myGame.shapeRenderer.setColor(0, 0, 0, 0);
				}
				myGame.shapeRenderer.rect(j * myGame.blockWidth + 2, i * myGame.blockHeight + 2, myGame.blockWidth - 3,
						myGame.blockHeight - 3);
			}
		}

		myGame.shapeRenderer.end();
		myGame.batch.begin();
		myGame.font.draw(myGame.batch, "Score: " + (myGame.game.getSnake().getLength() - 2), 10,
				myGame.screenHeight - 10);
		myGame.batch.end();
		if (moveResult == MoveResult.GAMEOVER) {
			myGame.setScreen(new GameOverScreen(myGame));
		}
	}

	@Override
	public void show() {
		myGame.game.feldInit();

		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					myGame.setScreen(new TitleScreen(myGame));
				}
				if (keycode == Input.Keys.SPACE) {
					paused = !paused;
				}
				if (!directionChange && !paused) {
					if (keycode == Input.Keys.DOWN) {
						myGame.game.getSnake().changeDirection(Direction.DOWN);
					} else if (keycode == Input.Keys.UP) {
						myGame.game.getSnake().changeDirection(Direction.UP);
					} else if (keycode == Input.Keys.LEFT) {
						myGame.game.getSnake().changeDirection(Direction.LEFT);
					} else if (keycode == Input.Keys.RIGHT) {
						myGame.game.getSnake().changeDirection(Direction.RIGHT);
					}
					directionChange = true;
				}

				return true;
			}
		});

	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}


}
