package com.uno;


public class Card {
    private CardColor color;
    private CardValue value;

    public Card(CardColor color, CardValue value) {
        this.color = color;
        this.value = value;
    }

    public CardColor getColor() {
        return color;
    }

    public CardValue getValue() {
        return value;
    }

    public void setColor(CardColor color) {
		this.color = color;
	}

	public void setValue(CardValue value) {
		this.value = value;
	}

	public boolean canPlayOn(Card otherCard) {
        return color == otherCard.getColor() || value == otherCard.getValue();
    }
}
