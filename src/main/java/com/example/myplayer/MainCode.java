package com.example.myplayer;// Import statements for necessary JavaFX and media classes
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// Main class representing the Blackjack game
public class MainCode extends Application {
    // Instance variables for game components
    private Deck deck;
    private Player dealer;
    private Player player;
    private Text statusLabel; private VBox dealerHandView; private VBox playerHandView; private MediaPlayer mediaPlayer;

    // Override method to start the application
    @Override
    public void start(Stage primaryStage) {
        // Show introduction screen before starting the game
        Introduction introduction = new Introduction();
        introduction.showIntroduction(primaryStage, () -> {
            initializeGame(primaryStage); // Initialize the game after introduction
        }, MainCode.class);
    }

    // Method to initialize the game components
    private void initializeGame(Stage primaryStage) {
        // Initialize deck, players, and status label
        deck = new Deck();
        dealer = new Player("Dealer");
        player = new Player("Player");
        statusLabel = new Text();

        // Create welcome message text
        Text welcomeText = new Text("WELCOME TO THE BLACKJACK GAME");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 70));
        VBox.setMargin(welcomeText, new Insets(0, 0, 70, 0)); // Add margin to bottom

        // Create buttons for player actions
        Button hitButton = new Button("Hit");
        hitButton.setOnAction(e -> {
            playerHit(); // Player selects hit action
        });

        Button standButton = new Button("Stand");
        standButton.setOnAction(e -> {
            playerStand(); // Player selects stand action
        });

        Button dealButton = new Button("Deal");
        dealButton.setOnAction(e -> {
            deal(); // Start a new round
        });

        // Slider for adjusting volume
        Slider volumeSlider = new Slider();
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(0.5); // Default volume
        volumeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });

        // Layout for buttons and settings
        HBox buttonBox = new HBox(10, hitButton, standButton, dealButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox settingsBox = new HBox(volumeSlider);
        settingsBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(settingsBox);

        // Create views for dealer and player hands
        dealerHandView = createHandView(dealer);
        playerHandView = createHandView(player);

        // Button to quit the game
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(event -> {
            credits creditsPage = new credits();
            creditsPage.start(new Stage()); // Show credits page
            primaryStage.close(); // Close the main stage
        });
        // Layout for the game components
        VBox root = new VBox(10, welcomeText, dealerHandView, playerHandView, buttonBox, statusLabel, quitButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Set background image
        Image backgroundImage = new Image("background.jpeg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        root.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        // Load and play background music
        String audioFile = "/Pitx_-_Ode_To_Joy.mp3";
        URL resourceUrl = getClass().getResource(audioFile);
        if (resourceUrl != null) {
            Media media = new Media(resourceUrl.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set cycle count to INDEFINITE
            mediaPlayer.play();
        } else {
            System.err.println("Error: Audio file 'Pitx_-_Ode_To_Joy.mp3' not found or could not be loaded.");
        }
        // Create and set the scene
        Scene scene = new Scene(root);
        primaryStage.setFullScreen(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blackjack");
        primaryStage.show();

        deal(); // Start the game by dealing cards
    }
    // Method to create a view for a player's hand
    private VBox createHandView(Player player) {
        VBox handView = new VBox(5);
        handView.setAlignment(Pos.CENTER);
        Text nameLabel = new Text(player.getName() + "'s Hand:");
        handView.getChildren().add(nameLabel);

        HBox cardView = new HBox(5);
        cardView.setAlignment(Pos.CENTER);
        for (Card card : player.getHand()) {
            ImageView imageView = new ImageView(card.getImage());
            imageView.setFitWidth(50); // Adjust image size as needed
            imageView.setFitHeight(70); // Adjust image size as needed
            Text cardValue = new Text(String.valueOf(card.getRank().getValue()));
            HBox cardInfo = new HBox(5); // Container for image and value
            cardInfo.getChildren().addAll(imageView, cardValue);
            cardView.getChildren().add(cardInfo);
        }
        handView.getChildren().add(cardView);
        return handView;
    }

    // Method to deal cards to players
    private void deal() {
        deck.shuffle();
        dealer.clearHand();
        player.clearHand();
        dealer.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        updateView(); // Update the UI after dealing
    }

    // Method to update the UI with current game state
    private void updateView() {
        statusLabel.setText("Dealer's Hand: " + (dealer.getHandValue() == 0 ? "" : dealer.getHandValue()));
        statusLabel.setText("Player's Hand: " + (player.getHandValue() == 0 ? "" : player.getHandValue()));

        dealerHandView.getChildren().clear();
        dealerHandView.getChildren().add(createHandView(dealer));

        playerHandView.getChildren().clear();
        playerHandView.getChildren().add(createHandView(player));
    }

    // Method for player hitting
    private void playerHit() {
        player.addCard(deck.drawCard());
        updateView();
        if (player.getHandValue() > 21) {
            statusLabel.setFill(Color.RED);
            statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            statusLabel.setText("Player busts! Dealer wins.");
            playWompAudio(); // Play sound for bust
        }
    }

    // Method for player standing
    private void playerStand() {
        while (dealer.getHandValue() < 17) {
            dealer.addCard(deck.drawCard());
        }
        updateView();
        if (dealer.getHandValue() > 21 || dealer.getHandValue() < player.getHandValue()) {
            statusLabel.setFill(Color.YELLOW); // Set text color to yellow
            statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set font size
            statusLabel.setText("Player wins!!");
            playJackpotAudio(); // Play sound for win
        } else if (dealer.getHandValue() > player.getHandValue()) {
            statusLabel.setFill(Color.RED); // Set text color to red
            statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30)); // Set font size
            statusLabel.setText("Dealer wins!!");
            playWompAudio(); // Play sound for loss
        } else {
            statusLabel.setText("It's a tie!");
        }
    }

    // Method to play sound for player bust
    private void playWompAudio() {
        String womp = "/womp.mp3";
        URL resourceUrl2 = getClass().getResource(womp);
        if (resourceUrl2 != null) {
            Media media2 = new Media(resourceUrl2.toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(media2);
            mediaPlayer2.play();
        } else {
            System.err.println("Error: Audio file 'womp.mp3' not found or could not be loaded.");
        }
    }

    // Method to play sound for player win
    private void playJackpotAudio() {
        String jackpot = "/jackpot.mp3";
        URL resourceUrl3 = getClass().getResource(jackpot);
        if (resourceUrl3 != null) {
            Media media3 = new Media(resourceUrl3.toString());
            MediaPlayer mediaPlayer3 = new MediaPlayer(media3);
            mediaPlayer3.play();
        } else {
            System.err.println("Error: Audio file 'jackpot.mp3' not found or could not be loaded.");
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}

// Enum for card suits
enum Suit {
    CLUBS, DIAMONDS, HEARTS, SPADES
}
// Enum for card ranks
enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
    SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10),
    QUEEN(10), KING(10), ACE(1);

    private final int value;
    Rank(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}

// Class representing a playing card
class Card {
    private final Rank rank;
    private final Suit suit;
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
    public Rank getRank() {
        return rank;
    }
    public Suit getSuit() {
        return suit;
    }

    // Method to get the image of the card
    public Image getImage() {
        String imageName;
        if (rank == Rank.TEN) {
            imageName = "10_of_" + suit.toString().toLowerCase() + ".png";
        } else {
            imageName = rank.getValue() + "_of_" + suit.toString().toLowerCase() + ".png";
        }
        return new Image(getClass().getResourceAsStream("/PNG-cards-1.3/" + imageName));
    }
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}

// Class representing a deck of cards
class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    // Method to shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Method to draw a card from the deck
    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No cards left in the deck.");
        }
        return cards.remove(0);
    }
}

// Class representing a player in the game
class Player {
    private String name;
    private List<Card> hand;
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();}
    // Method to add a card to the player's hand
    public void addCard(Card card) {
        hand.add(card);
    }
    // Method to clear the player's hand
    public void clearHand() {
        hand.clear();
    }
    // Method to calculate the value of the player's hand
    public int getHandValue() {
        int value = 0;
        boolean hasAce = false;
        for (Card card : hand) {
            value += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                hasAce = true;}}
        if (hasAce && value <= 11) {
            value += 10; // Count ace as 11 if it doesn't bust
        }
        return value;
    }
    public List<Card> getHand() {
        return hand;
    }
    public String getName() {
        return name;
    } }
