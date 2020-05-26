package com.mygdx.game;

import java.util.LinkedList;

public class Snake {
	private Direction direction;
	private LinkedList<Block> place = new LinkedList<Block>();

	public Snake(Direction direction) {
		super();
		this.direction = direction;
	}

	public void snakeInit(Block head, Block tail) {
		place.add(head);
		head.setBesetzt(true);
		place.add(tail);
		tail.setBesetzt(true);
	}

	public void changeDirection(Direction newDirection) {
		if (!direction.isOpposite(newDirection)) {
			direction = newDirection;
		}
	}
	public Direction getDirection() {
		return direction;
	}

	public Block getHead() {
		return place.getFirst();
	}
	public void setHead(Block newHead) {
		place.addFirst(newHead);
		newHead.setBesetzt(true);
	}
	public void deleteTail() {
		Block tail = place.removeLast();
		tail.setBesetzt(false);
	}
	public int getLength() {
		return place.size();
	}
}
