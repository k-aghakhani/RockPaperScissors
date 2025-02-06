package com.aghakhani.rockpaperscissors;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView; // TextView to display the result
    private ImageView computerChoiceImageView; // ImageView to show the computer's choice
    private Button rockButton, paperButton, scissorsButton; // Buttons for user's choices

    public com.airbnb.lottie.LottieAnimationView lottieAnimationView;

    // Array of possible choices
    private String[] choices = {"Rock", "Paper", "Scissors"};
    // Array of drawable resources for each choice
    private int[] choiceImages = {
            R.drawable.ic_rock, // Image for Rock
            R.drawable.ic_paper, // Image for Paper
            R.drawable.ic_scissors // Image for Scissors
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        resultTextView = findViewById(R.id.resultTextView);
        computerChoiceImageView = findViewById(R.id.computerChoiceImageView);
        rockButton = findViewById(R.id.rockButton);
        paperButton = findViewById(R.id.paperButton);
        scissorsButton = findViewById(R.id.scissorsButton);
        lottieAnimationView = findViewById(R.id.LottieAnimation);
        // Set click listener for the Rock button
        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("Rock"); // Call the game logic with "Rock" as the user's choice
            }
        });

        // Set click listener for the Paper button
        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("Paper"); // Call the game logic with "Paper" as the user's choice
            }
        });

        // Set click listener for the Scissors button
        scissorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("Scissors"); // Call the game logic with "Scissors" as the user's choice
            }
        });
    }

    // Main game logic function
    private void playGame(String userChoice) {
        computerChoiceImageView.setVisibility(View.VISIBLE);
        lottieAnimationView.setVisibility(View.INVISIBLE);
        // Generate a random choice for the computer
        Random random = new Random();
        int computerIndex = random.nextInt(3); // Random number between 0 and 2
        String computerChoice = choices[computerIndex];

        // Display the computer's choice image
        computerChoiceImageView.setImageResource(choiceImages[computerIndex]);

        // Determine the result of the game
        String result;
        if (userChoice.equals(computerChoice)) {
            result = "It's a tie!";
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            result = "You win!";
        } else {
            result = "Computer wins!";
        }

        // Display the result in the TextView
        resultTextView.setText("You: " + userChoice + "\nComputer: " + computerChoice + "\n" + result);
    }
}