package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

public class TitleScreen extends ScreenAdapter {
	MySnakeGame myGame;

	public TitleScreen(MySnakeGame game) {
		myGame = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		myGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		drawButton(myGame.screenWidth / 2 - 50, myGame.screenHeight / 2 + 40);
		drawButton(myGame.screenWidth / 2 - 50, myGame.screenHeight / 2);
		drawButton(myGame.screenWidth / 2 - 50, myGame.screenHeight / 2 - 40);
		drawButton(myGame.screenWidth / 2 - 50, myGame.screenHeight / 2 - 100);
		myGame.shapeRenderer.end();

		myGame.batch.begin();
		myGame.font.draw(myGame.batch, "EASY", myGame.screenWidth / 2 - 20, myGame.screenHeight / 2 + 60);
		myGame.font.draw(myGame.batch, "MEDIUM", myGame.screenWidth / 2 - 30, myGame.screenHeight / 2 + 20);
		myGame.font.draw(myGame.batch, "HARD", myGame.screenWidth / 2 - 20, myGame.screenHeight / 2 - 20);
		myGame.font.draw(myGame.batch, "Settings", myGame.screenWidth / 2 - 25, myGame.screenHeight / 2 - 80);
		myGame.font.draw(myGame.batch, "by Stefan", (myGame.screenWidth - myGame.screenWidth) + 5,
				(myGame.screenHeight - myGame.screenHeight) + 15);
		myGame.batch.end();
	}

	private void drawButton(int x, int y) {
		int mausX = Gdx.input.getX(0);
		int mausY = Gdx.input.getY(0);

		if (mausX >= x && mausX <= x + 100 && myGame.screenHeight - mausY >= y
				&& myGame.screenHeight - mausY <= y + 30) {
			myGame.shapeRenderer.setColor(Color.GREEN);
		} else {
			myGame.shapeRenderer.setColor(Color.LIME);
		}
		myGame.shapeRenderer.rect(x, y, 100, 30);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					dispose();
					System.exit(0);
				}
				return true;
			}

			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				if (activateButton(x, y, myGame.screenWidth / 2 - 50, myGame.screenHeight / 2 + 40)) {
					myGame.difficulty = Difficulty.EASY;
					myGame.setScreen(new MainScreen(myGame));
				} else if (activateButton(x, y, myGame.screenWidth / 2 - 50, myGame.screenHeight / 2)) {
					myGame.difficulty = Difficulty.MEDIUM;
					myGame.setScreen(new MainScreen(myGame));
				} else if (activateButton(x, y, myGame.screenWidth / 2 - 50, myGame.screenHeight / 2 - 40)) {
					myGame.difficulty = Difficulty.HARD;
					myGame.setScreen(new MainScreen(myGame));
				} else if (activateButton(x, y, myGame.screenWidth / 2 - 50, myGame.screenHeight / 2 - 100)) {
					myGame.setScreen(new SettingsScreen(myGame));
					return true;
				} else {
					return false;
				}
				myGame.startTime = TimeUtils.millis();
				myGame.speed = myGame.difficulty.getStartSpeed();
				myGame.game.feldInit(myGame.saves.getFieldSize());
				return true;
			}
		});
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	private boolean activateButton(int x, int y, int buttonX, int buttonY) {
		return x >= buttonX && x <= buttonX + 100 && myGame.screenHeight - y >= buttonY
				&& myGame.screenHeight - y <= buttonY + 30;
	}

}
