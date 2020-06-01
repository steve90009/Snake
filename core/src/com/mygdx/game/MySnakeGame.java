package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MySnakeGame extends Game {
	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;
	SnakeGame game;
	Saves saves;
	int blockWidth;
	int blockHeight;
	int screenWidth;
	int screenHeight;
	int speed = 400;
	Difficulty difficulty;
	boolean directionChange = false;
	boolean paused = false;
	long startTime = 0L;
	Sound goSound;
	Sound sound;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		game = new SnakeGame();
		saves = new Saves();
		saves.load();
		sound = Gdx.audio.newSound(Gdx.files.internal("apple_sound.mp3"));
		goSound = Gdx.audio.newSound(Gdx.files.internal("game_over.mp3"));
		shapeRenderer = new ShapeRenderer();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
//		blockWidth = screenWidth / game.getBreite();//Feld()[0].length;
//		blockHeight = screenHeight / game.getHoehe();//Feld().length;
		setScreen(new TitleScreen(this));

	}

	@Override
	public void render() {
		super.render();
		if (!(getScreen() instanceof SettingsScreen)) {

			batch.begin();
			font.draw(batch, "High Score: " + saves.getHighScore(), screenWidth - 140, screenHeight - 10);
			batch.end();
		}
	}

	@Override
	public void dispose() {
		saves.save();
		sound.dispose();
		goSound.dispose();
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}
