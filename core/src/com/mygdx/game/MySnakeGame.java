package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MySnakeGame extends Game {
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
	boolean directionChange = false;
	boolean paused = false;
	long startTime = 0L;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		game = new SnakeGame();
		shapeRenderer = new ShapeRenderer();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		blockWidth = screenWidth / game.getFeld()[0].length;
		blockHeight = screenHeight / game.getFeld().length;
		setScreen(new TitleScreen(this));

	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
