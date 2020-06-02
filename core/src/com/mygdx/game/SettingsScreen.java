package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SettingsScreen extends ScreenAdapter {
	MySnakeGame myGame;

	boolean upAndern = false;
	boolean downAndern = false;
	boolean leftAndern = false;
	boolean rightAndern = false;
	boolean pauseAndern = false;

	public SettingsScreen(MySnakeGame game) {
		myGame = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		myGame.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		if (myGame.saves.isSound()) {
			myGame.shapeRenderer.setColor(Color.GREEN);
		} else {
			myGame.shapeRenderer.setColor(Color.RED);
		}
		myGame.shapeRenderer.rect(myGame.screenWidth - 150, myGame.screenHeight - 50, 100, 30);
		drawButton(myGame.screenWidth - 150, myGame.screenHeight - 90);
		drawButton(myGame.screenWidth - 150, myGame.screenHeight - 130);
		drawButton(myGame.screenWidth - 150, myGame.screenHeight - 170);
		drawButton(myGame.screenWidth - 150, myGame.screenHeight - 210);
		drawButton(myGame.screenWidth - 150, myGame.screenHeight - 250);
		drawButton(myGame.screenWidth - 150, myGame.screenHeight - 290);
		myGame.shapeRenderer.end();

		myGame.batch.begin();
		myGame.font.draw(myGame.batch, "Sounds", myGame.screenWidth - 225, myGame.screenHeight - 28);
		myGame.font.draw(myGame.batch, "Field size", myGame.screenWidth - 225, myGame.screenHeight - 68);
		if (myGame.saves.getFieldSize() == 1) {
			myGame.font.draw(myGame.batch, "12*16", myGame.screenWidth - 120, myGame.screenHeight - 68);
		} else {
			myGame.font.draw(myGame.batch, "24*32", myGame.screenWidth - 120, myGame.screenHeight - 68);
		}
		myGame.font.draw(myGame.batch, Input.Keys.toString(myGame.saves.getUp()), myGame.screenWidth - 120, myGame.screenHeight - 108);
		myGame.font.draw(myGame.batch, "Up", myGame.screenWidth - 225, myGame.screenHeight - 108);

		myGame.font.draw(myGame.batch, Input.Keys.toString(myGame.saves.getDown()), myGame.screenWidth - 120,myGame.screenHeight - 148);
		myGame.font.draw(myGame.batch, "Down", myGame.screenWidth - 225, myGame.screenHeight - 148);

		myGame.font.draw(myGame.batch, Input.Keys.toString(myGame.saves.getLeft()), myGame.screenWidth - 120,myGame.screenHeight - 188);
		myGame.font.draw(myGame.batch, "Left", myGame.screenWidth - 225, myGame.screenHeight - 188);

		myGame.font.draw(myGame.batch, Input.Keys.toString(myGame.saves.getRight()), myGame.screenWidth - 120,myGame.screenHeight - 228);
		myGame.font.draw(myGame.batch, "Right", myGame.screenWidth - 225, myGame.screenHeight - 228);

		myGame.font.draw(myGame.batch, Input.Keys.toString(myGame.saves.getPause()), myGame.screenWidth - 120,myGame.screenHeight - 268);
		myGame.font.draw(myGame.batch, "Pause", myGame.screenWidth - 225, myGame.screenHeight - 268);

		myGame.batch.end();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
//				myGame.setScreen(new TitleScreen(myGame));
				if (activateButton(x, y, myGame.screenWidth - 150, myGame.screenHeight - 50)) {
					myGame.saves.setSound(!myGame.saves.isSound());
				} else if (activateButton(x, y, myGame.screenWidth - 150, myGame.screenHeight - 90)) {
					if (myGame.saves.getFieldSize() == 1) {
						myGame.saves.setFieldSize(2);
					} else {
						myGame.saves.setFieldSize(1);
					}
				}
				if (activateButton(x, y, myGame.screenWidth - 150, myGame.screenHeight - 130)) {
					upAndern = true;
					pauseAndern = false;
					downAndern = false;
					leftAndern = false;
					rightAndern = false;
				} else if (activateButton(x, y, myGame.screenWidth - 150, myGame.screenHeight - 170)) {
					downAndern = true;
					pauseAndern = false;
					upAndern = false;
					leftAndern = false;
					rightAndern = false;
				} else if (activateButton(x, y, myGame.screenWidth - 150, myGame.screenHeight - 210)) {
					leftAndern = true;
					pauseAndern = false;
					upAndern = false;
					downAndern = false;
					rightAndern = false;
				} else if (activateButton(x, y, myGame.screenWidth - 150, myGame.screenHeight - 250)) {
					rightAndern = true;
					pauseAndern = false;
					upAndern = false;
					downAndern = false;
					leftAndern = false;
				} else if (activateButton(x, y, myGame.screenWidth - 150, myGame.screenHeight - 290)) {
					pauseAndern = true;
					upAndern = false;
					downAndern = false;
					leftAndern = false;
					rightAndern = false;
				} else {
					pauseAndern = false;
					upAndern = false;
					downAndern = false;
					leftAndern = false;
					rightAndern = false;
				}

				return true;
			}

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					myGame.setScreen(new TitleScreen(myGame));
				} else if (upAndern) {
					myGame.saves.setUp(keycode);
				} else if (downAndern) {
					myGame.saves.setDown(keycode);
				} else if (leftAndern) {
					myGame.saves.setLeft(keycode);
				} else if (rightAndern) {
					myGame.saves.setRight(keycode);
				} else if (pauseAndern) {
					myGame.saves.setPause(keycode);
				}
				return true;
			}

		});
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	private void drawButton(int x, int y) {
		int mausX = Gdx.input.getX(0);
		int mausY = Gdx.input.getY(0);

		if (mausX >= x && mausX <= x + 100 && myGame.screenHeight - mausY >= y
				&& myGame.screenHeight - mausY <= y + 30) {
			myGame.shapeRenderer.setColor(Color.GRAY);
		} else {
			myGame.shapeRenderer.setColor(Color.DARK_GRAY);
		}
		myGame.shapeRenderer.rect(x, y, 100, 30);
	}

	private boolean activateButton(int x, int y, int buttonX, int buttonY) {
		return x >= buttonX && x <= buttonX + 100 && myGame.screenHeight - y >= buttonY
				&& myGame.screenHeight - y <= buttonY + 30;
	}

}
