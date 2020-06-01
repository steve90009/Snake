package com.mygdx.game;

public class Block {

	private int posX;
	private int posY;
	private boolean besetzt;
	private boolean apfel;

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public boolean isBesetzt() {
		return besetzt;
	}

	public void setBesetzt(boolean besetzt) {
		this.besetzt = besetzt;
	}

	public boolean isApfel() {
		return apfel;
	}

	public void setApfel(boolean apfel) {
		this.apfel = apfel;
	}

	public Block(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}

}
