package com.jonkussmann.simplemath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final int REQUEST_CODE = 3333;
	public static String TAG = "MainActivity";
	private TextView mathQuestion;
	private int firstNumber;
	private int secondNumber;
	private SpeechRecognizer speechRecognizer;
	private Button startAgainButton;
	private char operation;
	private static final String TIMEOUT = "timeout";
	private static final String WRONGANSWER = "wrongAnswer";
	private static final String NOTANUMBER = "notANumber";
	private static final String CORRECTANSWER = "correctAnswer";
	private TextView yourAnswer;
	private TextView currentScore;
	private TextView extraMessage;
	private int currentScoreCount = 0;
	private int theCorrectAnswer;
	private MathQuestionGenerator mathQuestionGenerator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mathQuestionGenerator = new MathQuestionGenerator();

		// Initialize views
		currentScore = (TextView) findViewById(R.id.currentScore);
		setCurrentScore(0);
		yourAnswer = (TextView) findViewById(R.id.yourAnswer);
		setYourAnswer("");
		mathQuestion = (TextView) findViewById(R.id.mathQuestion);
		mathQuestion.setText("Press Start to begin");

		extraMessage = (TextView) findViewById(R.id.extraMessage);

		// Sets up speech recognizer
		speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
		speechRecognizer.setRecognitionListener(new RecognitionListener() {

			@Override
			public void onRmsChanged(float rmsdB) {

			}

			// Called when there is an answer to be checked; displays spoken
			// answer and calls a check to see if it is correct
			@Override
			public void onResults(Bundle results) {
				Log.d(TAG, "onResults " + results);
				ArrayList<String> answers = results
						.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
				setYourAnswer(answers);
				if (mathQuestionGenerator.checkAnswer(answers).equals(
						CORRECTANSWER)) {
					Log.d(TAG, "the answer was correct!");
					setCurrentScore(getCurrentScore() + 1);
//					playScoreSound();
					mathQuestionGenerator.Generate();
					mathQuestion.setText(mathQuestionGenerator
							.getMathQuestion());
					startVoiceRecognitionService();
				} else {
					Log.d(TAG, "the answer was not correct");
					gameOver(mathQuestionGenerator.checkAnswer(answers));
					Log.d(TAG, mathQuestionGenerator.checkAnswer(answers));
				}

			}

			@Override
			public void onReadyForSpeech(Bundle params) {
				Log.d(TAG, "onReadyForSpeech called");

			}

			@Override
			public void onPartialResults(Bundle partialResults) {
				Log.d(TAG, "onPartialResults called");

			}

			@Override
			public void onEvent(int eventType, Bundle params) {
				Log.d(TAG, "onEvent called");

			}

			// Called when the user does not give an answer quickly enough
			@Override
			public void onError(int error) {
				Log.d(TAG, "onError called");
				gameOver(TIMEOUT);

			}

			@Override
			public void onEndOfSpeech() {
				Log.d(TAG, "onEndOfSpeech called");

			}

			@Override
			public void onBufferReceived(byte[] buffer) {
				Log.d(TAG, "onBufferReceived called");

			}

			@Override
			public void onBeginningOfSpeech() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onBeginningofSpeech called");

			}
		});

		startAgain();

	}

	// Starts an intent to start listening for an answer
	private void startVoiceRecognitionService() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		speechRecognizer.startListening(intent);

	}

	@Override
	protected void onDestroy() {
		speechRecognizer.destroy();
		super.onDestroy();
	}

	private void gameOver(String condition) {
//		playGameOverSound();
		if (condition.equals(TIMEOUT)) {
			Log.d(TAG, "You are too slow!");
			extraMessage.setVisibility(View.VISIBLE);
			extraMessage.setText("You were too slow!");
			startAgain();
		} else if (condition.equals(WRONGANSWER)) {
			Log.d(TAG, "Wrong answer!");
			extraMessage.setVisibility(View.VISIBLE);
			extraMessage.setText("Wrong! The correct answer: "
					+ mathQuestionGenerator.getTheCorrectAnswer());
			startAgain();
		} else if (condition.equals(NOTANUMBER)) {
			extraMessage.setVisibility(View.VISIBLE);
			extraMessage.setText("That's not a number!");
			startAgain();
		}
		speechRecognizer.cancel();

	}

	private void startAgain() {
		startAgainButton = (Button) findViewById(R.id.startAgainButton);
		startAgainButton.setVisibility(View.VISIBLE);

		startAgainButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startAgainButton.setVisibility(View.INVISIBLE);
				setYourAnswer("");
				setCurrentScore(0);
				mathQuestionGenerator.Generate();
				mathQuestion.setText(mathQuestionGenerator.getMathQuestion());
				startVoiceRecognitionService();
				if (extraMessage.getVisibility() == View.VISIBLE) {
					extraMessage.setVisibility(View.INVISIBLE);
				}

			}
		});

	}

	protected void setCurrentScore(int i) {
		currentScoreCount = i;
		currentScore.setText("Current Score: " + currentScoreCount);

	}

	protected int getCurrentScore() {
		return currentScoreCount;
	}

	private void setYourAnswer(ArrayList<String> answer) {
		Log.d(TAG, "set your Answer called " + answer.get(0));
		yourAnswer.setText("Your Answer: " + answer.get(0));

	}

	private void setYourAnswer(String value) {
		yourAnswer.setText("Your Answer: " + value);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

//	private void playGameOverSound() {
//		if (Application.getSound()) {
//			MediaPlayer mp = MediaPlayer.create(this, R.raw.gameover);
//			mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//				@Override
//				public void onPrepared(MediaPlayer mp) {
//					mp.start();
//
//				}
//			});
//			
//		}
//	}
//
//	private void playScoreSound() {
//		Log.d(TAG, "playScoreSound " + Application.getSound());
//		if (Application.getSound()) {
//			MediaPlayer mp = MediaPlayer.create(this, R.raw.score);
//			mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//				@Override
//				public void onPrepared(MediaPlayer mp) {
//					Log.d(TAG, "onprepared");
//					mp.start();
//
//				}
//			});
//			
//		}
//	}

}
