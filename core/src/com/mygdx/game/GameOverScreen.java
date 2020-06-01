package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class GameOverScreen extends ScreenAdapter {
	MySnakeGame myGame;

	public GameOverScreen(MySnakeGame game) {
		myGame = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		myGame.batch.begin();
		myGame.font.draw(myGame.batch, "Game Over", myGame.screenWidth / 2 - 25, myGame.screenHeight / 2);
		myGame.font.draw(myGame.batch, "Score: " + (myGame.game.getSnake().getLength() - 2), 10,
				myGame.screenHeight - 10);
		myGame.batch.end();
	}

	@Override
	public void show() {
		myGame.saves.setHighScore(myGame.game.getSnake().getLength() - 2);
		if (myGame.saves.isSound()) {
			myGame.goSound.play();
		}
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				myGame.setScreen(new TitleScreen(myGame));
				return true;
			}

			@Override
			public boolean keyDown(int keycode) {
				myGame.setScreen(new TitleScreen(myGame));
				return true;
			}
		});
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

}
