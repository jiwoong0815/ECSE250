package assignment2;

import java.util.Random;

public class Deck {
	public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
	public static Random gen = new Random();

	public int numOfCards; // contains the total number of cards in the deck
	public Card head; // contains a pointer to the card on the top of the deck

	/* 
	 * TODO: Initializes a Deck object using the inputs provided
	 */
	public Deck(int numOfCardsPerSuit, int numOfSuits) {
		/**** ADD CODE HERE ****/
		if ( numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13) {
			throw new IllegalArgumentException("first input is not a number between 1 and 13");
		}
		if (numOfSuits < 1 || numOfSuits > suitsInOrder.length){
			throw new IllegalArgumentException("second input is not a number between 1 and number of suits");
		}
		this.numOfCards = 0;
		this.head = null;

		for(int j = 0; j < numOfSuits; j++){
			for(int i = 1 ; i <= numOfCardsPerSuit; i++){
				Card addingCard = new PlayingCard(suitsInOrder[j],i);
				this.addCard(addingCard);
				}
		}
		Card rJoker = new Joker("red");
		this.addCard(rJoker);
		Card bJoker = new Joker("black");
		this.addCard(bJoker);
	}

	/* 
	 * TODO: Implements a copy constructor for Deck using Card.getCopy().
	 * This method runs in O(n), where n is the number of cards in d.
	 */
	public Deck(Deck d) {
		/**** ADD CODE HERE ****/

		this.numOfCards = 0;
		this.head = null;
		Card pointer = d.head;

		for (int i = 0; i < d.numOfCards; i++){
			addCard(pointer.getCopy());
			pointer=pointer.next;
		}


	}

	/*
	 * For testing purposes we need a default constructor.
	 */
	public Deck() {}

	/* 
	 * TODO: Adds the specified card at the bottom of the deck. This 
	 * method runs in $O(1)$. 
	 */
	public void addCard(Card c) {
		/**** ADD CODE HERE ****/

		if (head == null){
			head = c;
			head.prev = head;
			head.next = head;
		}
		else{
			c.prev = head.prev;
			head.prev.next = c;
			c.next = head;
			head.prev = c;
		}
		this.numOfCards += 1;
	}

	/*
	 * TODO: Shuffles the deck using the algorithm described in the pdf. 
	 * This method runs in O(n) and uses O(n) space, where n is the total 
	 * number of cards in the deck.
	 */
	public void shuffle() {
		/**** ADD CODE HERE ****/

		Card[] copy = new Card[this.numOfCards];
		Card pointer = head;
		for (int i = 0 ; i < this.numOfCards; i++){
			copy[i] = pointer;
			pointer = pointer.next;
		}

		for (int i = numOfCards-1; i >= 1; i--){
			int j = gen.nextInt(i+1);
			Card temp = copy[i];
			copy[i] = copy[j];
			copy[j] = temp;
		}

		this.head = null;
		this.numOfCards = 0;

		for(int i =0; i < copy.length; i++){
			addCard(copy[i]);
		}

	}

	/*
	 * TODO: Returns a reference to the joker with the specified color in 
	 * the deck. This method runs in O(n), where n is the total number of 
	 * cards in the deck. 
	 */
	public Joker locateJoker(String color) {
		/**** ADD CODE HERE ****/
		if(numOfCards != 0){
			Card pointer = head;
			for(int i = 0; i < numOfCards; i++){
				if (pointer instanceof Joker){
					if(((Joker)pointer).getColor().equalsIgnoreCase(color)){
						return (Joker)pointer;
					}
				}pointer = pointer.next;
			}
		}
		return null;
	}

	/*
	 * TODO: Moved the specified Card, p positions down the deck. You can 
	 * assume that the input Card does belong to the deck (hence the deck is
	 * not empty). This method runs in O(p).
	 */
	public void moveCard(Card c, int p) {
		/**** ADD CODE HERE ****/

		p = p % (numOfCards-1); //like circular array

		Card insert_loc = c;
		for (int i = 0; i < p; i++){
			insert_loc = insert_loc.next;
		}

		c.next.prev = c.prev;
		c.prev.next = c.next;

		c.next = insert_loc.next;
		c.prev = insert_loc;

		insert_loc.next.prev = c;
		insert_loc.next = c;



	}

	/*
	 * TODO: Performs a triple cut on the deck using the two input cards. You 
	 * can assume that the input cards belong to the deck and the first one is 
	 * nearest to the top of the deck. This method runs in O(1)
	 */
	public void tripleCut(Card firstCard, Card secondCard) {
		/**** ADD CODE HERE ****/

		if(firstCard == this.head){
			this.head = secondCard.next;

		} else if (secondCard == head.prev) {
			this.head = firstCard;
		}
		else {
			Card tmpH = head;
			Card tmpT = head.prev;
			this.head = secondCard.next;
			this.head.prev = firstCard.prev;
			head.prev.next = head;
			tmpT.next = firstCard;
			tmpH.prev = secondCard;
			firstCard.prev = tmpT;
			secondCard.next = tmpH;
		}
	}

	/*
	 * TODO: Performs a count cut on the deck. Note that if the value of the 
	 * bottom card is equal to a multiple of the number of cards in the deck, 
	 * then the method should not do anything. This method runs in O(n).
	 */
	public void countCut() {
		/**** ADD CODE HERE ****/

		int cutValue = this.head.prev.getValue() % numOfCards;
		Card pointer = head;

		if(cutValue == 0 || cutValue == numOfCards-1) return;

		for(int i = 1; i < cutValue ; i++){
			pointer = pointer.next;
		}

		Card secondLast = head.prev.prev;
		Card last = head.prev;
		Card newHead = pointer.next;

		secondLast.next = head;
		head.prev = secondLast;
		head = newHead;
		head.prev = last;
		pointer.next = last;
		last.prev = pointer;
		last.next = head;

	}

	/*
	 * TODO: Returns the card that can be found by looking at the value of the 
	 * card on the top of the deck, and counting down that many cards. If the 
	 * card found is a Joker, then the method returns null, otherwise it returns
	 * the Card found. This method runs in O(n).
	 */
	public Card lookUpCard() {
		/**** ADD CODE HERE ****/

		int countNum = this.head.getValue() % this.numOfCards;

		Card pointer = head;

		for (int i =0 ; i < countNum; i++) pointer = pointer.next;

		if(pointer instanceof Joker) return null;

		else{
			return pointer;
		}
	}

	/*
	 * TODO: Uses the Solitaire algorithm to generate one value for the keystream 
	 * using this deck. This method runs in O(n).
	 */
	public int generateNextKeystreamValue() {
		/**** ADD CODE HERE ****/
		Joker RJ = locateJoker("red");
		moveCard(RJ,1);
		Joker BJ = locateJoker("black");
		moveCard(BJ,2);

//		Joker joker1 = null;
//
//		Card pointer = head;
//		for (int i = 0; i < numOfCards; i++){
//			if (pointer instanceof Joker){
//				joker1 = (Joker) pointer;
//			}
//		}
//
//		if (joker1 == locateJoker("red")) {
//			tripleCut(joker1,locateJoker("black"));
//		}
//		else {
//			tripleCut(joker1,locateJoker("red"));
//		}

		Card pointer = head;
		while (!(pointer instanceof Joker)){
			pointer = pointer.next;
		}
		Joker firstJ = (Joker) pointer;
		pointer = pointer.next;
		while (!(pointer instanceof Joker)){
			pointer = pointer.next;
		}
		Joker secondJ = (Joker) pointer;

		tripleCut(firstJ,secondJ);


		countCut();

		Card resultCard = lookUpCard();

		if(resultCard == null){
			return generateNextKeystreamValue();
		}
		else{
			return resultCard.getValue();
		}
	}


	public abstract class Card { 
		public Card next;
		public Card prev;

		public abstract Card getCopy();
		public abstract int getValue();

	}

	public class PlayingCard extends Card {
		public String suit;
		public int rank;

		public PlayingCard(String s, int r) {
			this.suit = s.toLowerCase();
			this.rank = r;
		}

		public String toString() {
			String info = "";
			if (this.rank == 1) {
				//info += "Ace";
				info += "A";
			} else if (this.rank > 10) {
				String[] cards = {"Jack", "Queen", "King"};
				//info += cards[this.rank - 11];
				info += cards[this.rank - 11].charAt(0);
			} else {
				info += this.rank;
			}
			//info += " of " + this.suit;
			info = (info + this.suit.charAt(0)).toUpperCase();
			return info;
		}

		public PlayingCard getCopy() {
			return new PlayingCard(this.suit, this.rank);   
		}

		public int getValue() {
			int i;
			for (i = 0; i < suitsInOrder.length; i++) {
				if (this.suit.equals(suitsInOrder[i]))
					break;
			}

			return this.rank + 13*i;
		}

	}

	public class Joker extends Card{
		public String redOrBlack;

		public Joker(String c) {
			if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
				throw new IllegalArgumentException("Jokers can only be red or black"); 

			this.redOrBlack = c.toLowerCase();
		}

		public String toString() {
			//return this.redOrBlack + " Joker";
			return (this.redOrBlack.charAt(0) + "J").toUpperCase();
		}

		public Joker getCopy() {
			return new Joker(this.redOrBlack);
		}

		public int getValue() {
			return numOfCards - 1;
		}

		public String getColor() {
			return this.redOrBlack;
		}
	}

}
