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
		int mausX = Gdx.input.getX(0);
		int mausY = Gdx.input.getY(0);
		myGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		if (mausX >= myGame.screenWidth / 2 - 50 && mausX <= myGame.screenWidth / 2 + 50
				&& myGame.screenHeight - mausY >= myGame.screenHeight / 2
				&& myGame.screenHeight - mausY <= myGame.screenHeight / 2 + 30) {
			myGame.shapeRenderer.setColor(Color.GREEN);
		} else {
			myGame.shapeRenderer.setColor(Color.LIME);
		}
		myGame.shapeRenderer.rect(myGame.screenWidth / 2 - 50, myGame.screenHeight / 2, 100, 30);

		if (mausX >= myGame.screenWidth / 2 - 50 && mausX <= myGame.screenWidth / 2 + 50
				&& myGame.screenHeight - mausY >= myGame.screenHeight / 2 + 40
				&& myGame.screenHeight - mausY <= myGame.screenHeight / 2 + 70) {
			myGame.shapeRenderer.setColor(Color.GREEN);
		} else {
			myGame.shapeRenderer.setColor(Color.LIME);
		}
		myGame.shapeRenderer.rect(myGame.screenWidth / 2 - 50, myGame.screenHeight / 2 + 40, 100, 30);
		if (mausX >= myGame.screenWidth / 2 - 50 && mausX <= myGame.screenWidth / 2 + 50
				&& myGame.screenHeight - mausY >= myGame.screenHeight / 2 - 40
				&& myGame.screenHeight - mausY <= myGame.screenHeight / 2 - 10) {
			myGame.shapeRenderer.setColor(Color.GREEN);
		} else {
			myGame.shapeRenderer.setColor(Color.LIME);
		}
		myGame.shapeRenderer.rect(myGame.screenWidth / 2 - 50, myGame.screenHeight / 2 - 40, 100, 30);
		myGame.shapeRenderer.end();

		myGame.batch.begin();
		myGame.font.draw(myGame.batch, "EASY", myGame.screenWidth / 2 - 20, myGame.screenHeight / 2 + 60);
		myGame.font.draw(myGame.batch, "MEDIUM", myGame.screenWidth / 2 - 32, myGame.screenHeight / 2 + 20);
		myGame.font.draw(myGame.batch, "HARD", myGame.screenWidth / 2 - 20, myGame.screenHeight / 2 - 20);
		myGame.batch.end();
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
				if (x >= myGame.screenWidth / 2 - 50 && x <= myGame.screenWidth / 2 + 50
						&& myGame.screenHeight - y >= myGame.screenHeight / 2
						&& myGame.screenHeight - y <= myGame.screenHeight / 2 + 30) {
					myGame.difficulty = Difficulty.MEDIUM;
					myGame.setScreen(new MainScreen(myGame));
				} else if (x >= myGame.screenWidth / 2 - 50 && x <= myGame.screenWidth / 2 + 50
						&& myGame.screenHeight - y >= myGame.screenHeight / 2 + 40
						&& myGame.screenHeight - y <= myGame.screenHeight / 2 + 70) {
					myGame.difficulty = Difficulty.EASY;
					myGame.setScreen(new MainScreen(myGame));
				} else if (x >= myGame.screenWidth / 2 - 50 && x <= myGame.screenWidth / 2 + 50
						&& myGame.screenHeight - y >= myGame.screenHeight / 2 - 40
						&& myGame.screenHeight - y <= myGame.screenHeight / 2 - 10) {
					myGame.difficulty = Difficulty.HARD;
					myGame.setScreen(new MainScreen(myGame));
				} else {
					return false;
				}
				myGame.startTime = TimeUtils.millis();
				myGame.speed = myGame.difficulty.getStartSpeed();
				myGame.game.feldInit();
				return true;
			}
		});
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

}
