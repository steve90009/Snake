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
		myGame.shapeRenderer.end();

		myGame.batch.begin();
		myGame.font.draw(myGame.batch, "Sounds", myGame.screenWidth - 225, myGame.screenHeight - 28);
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
				}
				return true;
			}

			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					myGame.setScreen(new TitleScreen(myGame));
				}
				return true;
			}
		});
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

//	private void drawButton(int x, int y) {
//		int mausX = Gdx.input.getX(0);
//		int mausY = Gdx.input.getY(0);
//
//		if (mausX >= x && mausX <= x + 100 && myGame.screenHeight - mausY >= y
//				&& myGame.screenHeight - mausY <= y + 30) {
//			myGame.shapeRenderer.setColor(Color.GRAY);
//		} else {
//			myGame.shapeRenderer.setColor(Color.DARK_GRAY);
//		}
//		myGame.shapeRenderer.rect(x, y, 100, 30);
//	}
	private boolean activateButton(int x, int y, int buttonX, int buttonY) {
		return x >= buttonX && x <= buttonX + 100 && myGame.screenHeight - y >= buttonY
				&& myGame.screenHeight - y <= buttonY + 30;
	}

}
