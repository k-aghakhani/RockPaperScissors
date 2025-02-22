package com.aghakhani.rockpaperscissors;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView, scoreTextView, timerTextView;
    private ImageView computerChoiceImageView, userChoiceImageView;
    private Button rockButton, paperButton, scissorsButton, themeButton;
    private LottieAnimationView lottieAnimationView;
    private CountDownTimer timer;

    private String[] choices = {"Rock", "Paper", "Scissors"};
    private int[] choiceImages = {R.drawable.ic_rock, R.drawable.ic_paper, R.drawable.ic_scissors};
    private int userScore = 0, computerScore = 0;

    private MediaPlayer clickSound, winSound, loseSound, tieSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize MediaPlayers
        clickSound = MediaPlayer.create(this, R.raw.click_sound);
        winSound = MediaPlayer.create(this, R.raw.win_sound);
        loseSound = MediaPlayer.create(this, R.raw.lose_sound);
        tieSound = MediaPlayer.create(this, R.raw.tie_sound);

        // Initialize UI components
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        computerChoiceImageView = findViewById(R.id.computerChoiceImageView);
        userChoiceImageView = findViewById(R.id.userChoiceImageView);
        rockButton = findViewById(R.id.rockButton);
        paperButton = findViewById(R.id.paperButton);
        scissorsButton = findViewById(R.id.scissorsButton);
        themeButton = findViewById(R.id.themeButton);
        lottieAnimationView = findViewById(R.id.LottieAnimation);

        scoreTextView.setText("You: 0 | Computer: 0");
        startTimer();

        // Button click listeners
        rockButton.setOnClickListener(v -> {
            clickSound.start();
            animateButton(rockButton);
            playGame("Rock");
        });

        paperButton.setOnClickListener(v -> {
            clickSound.start();
            animateButton(paperButton);
            playGame("Paper");
        });

        scissorsButton.setOnClickListener(v -> {
            clickSound.start();
            animateButton(scissorsButton);
            playGame("Scissors");
        });

        themeButton.setOnClickListener(v -> {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }

    private void playGame(String userChoice) {
        timer.cancel(); // Stop the timer

        // Show user's choice
        int userIndex = java.util.Arrays.asList(choices).indexOf(userChoice);
        userChoiceImageView.setImageResource(choiceImages[userIndex]);
        userChoiceImageView.setVisibility(View.VISIBLE);

        // Computer's choice
        Random random = new Random();
        int computerIndex = random.nextInt(3);
        String computerChoice = choices[computerIndex];
        computerChoiceImageView.setImageResource(choiceImages[computerIndex]);
        computerChoiceImageView.setVisibility(View.VISIBLE);

        // Hide initial animation and show result animation
        lottieAnimationView.setVisibility(View.VISIBLE);

        // Determine result
        String result;
        if (userChoice.equals(computerChoice)) {
            result = "It's a tie!";
            tieSound.start();
            lottieAnimationView.setAnimation("tie.json");
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            result = "You win!";
            userScore++;
            winSound.start();
            lottieAnimationView.setAnimation("win.json");
        } else {
            result = "Computer wins!";
            computerScore++;
            loseSound.start();
            lottieAnimationView.setAnimation("lose.json");
        }

        lottieAnimationView.playAnimation();

        // Update UI
        resultTextView.setText("You: " + userChoice + "\nComputer: " + computerChoice + "\n" + result);
        scoreTextView.setText("You: " + userScore + " | Computer: " + computerScore);

        startTimer(); // Restart timer for next round
    }

    private void animateButton(Button button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1.0f, 1.2f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1.0f, 1.2f, 1.0f);
        scaleX.setDuration(200);
        scaleY.setDuration(200);
        scaleX.start();
        scaleY.start();
    }

    private void startTimer() {
        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Time's up!");
                playGame(choices[new Random().nextInt(3)]);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
        clickSound.release();
        winSound.release();
        loseSound.release();
        tieSound.release();
    }
}