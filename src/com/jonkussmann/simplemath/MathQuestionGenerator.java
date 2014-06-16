package com.jonkussmann.simplemath;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;
import android.widget.Toast;

public class MathQuestionGenerator {
	private static final String TAG = "MathQuestionGenerator";
	private char operation;
	private int firstNumber, secondNumber;
	private String mathQuestion;
	private static final String CORRECTANSWER = "correctAnswer";
	private static final String WRONGANSWER = "wrongAnswer";
	private static final String NOTANUMBER = "notANumber";
	private int theCorrectAnswer;
	
	public MathQuestionGenerator() {
		
	}
	
	// Generates a new math question
	public void Generate() {
		operation = randomOperation();
		firstNumber = randomNumber();
		secondNumber = randomNumber();
		
		setMathQuestion(operation, firstNumber, secondNumber);
		
	}
		
	
	// Generates a random number based on the operator
	private int randomNumber() {
		int number;
		Random random = new Random();
		if (operation == '*') {
		number = random.nextInt(11);
		} else {
			number = random.nextInt(20);
		}		
		return number;
		
	}
	
	// Generates a random math operator based on user selection 
	private char randomOperation() {
		Log.d(TAG, Application.getOperations());
		
		if (Application.getOperations().equals("addSubtract")) {
			Random randomOperation = new Random();
			int number = randomOperation.nextInt(2);
			if (number == 0) {
				operation = '+';
			} else {
				operation = '-';
			}
			
		} else if (Application.getOperations().equals("multiply")) {
			operation = '*';
			
		} else if (Application.getOperations().equals("both")) {
			Random randomOperation = new Random();
			int number = randomOperation.nextInt(3);
			Log.d(TAG, "random operation number " + number);
			switch (number){
			case 0:
				operation = '+';
				break;
			case 1:
				operation = '-';
				break;
			case 2:
				operation = '*';
				break;
			}
			
		} else {
			operation = '*';
		}
		return operation;
	
	}
	
	// Checks answer based off of the math question generated
		String checkAnswer(ArrayList<String> answer) {
			try {
				int answerToCheck = Integer.parseInt(answer.get(0));
				
				switch(operation) {
				case '+':
					theCorrectAnswer = firstNumber + secondNumber;
					setTheCorrectAnswer(theCorrectAnswer);
					if (answerToCheck == theCorrectAnswer) {
						return CORRECTANSWER;
						
					} else {
						return WRONGANSWER;
					}
					
				case '-':
					theCorrectAnswer = Math.abs(firstNumber - secondNumber);
					setTheCorrectAnswer(theCorrectAnswer);
					if (answerToCheck == theCorrectAnswer) {
						return CORRECTANSWER;
						
					} else {
						return WRONGANSWER;
					}
					
				case '*':
					theCorrectAnswer = firstNumber * secondNumber;
					setTheCorrectAnswer(theCorrectAnswer);
					if (answerToCheck == theCorrectAnswer) {
						return CORRECTANSWER;
						
					} else {
						return WRONGANSWER;
					}
					
				}
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return NOTANUMBER;
			}
			return WRONGANSWER;
			
		}



		public String getMathQuestion() {
			return mathQuestion;
		}



		public void setMathQuestion(char operation, int firstNumber, int secondNumber) {
			 if (firstNumber > secondNumber) {
					this.mathQuestion = String.valueOf(firstNumber) + " " + operation + " " + String.valueOf(secondNumber);
					 } else {
						 this.mathQuestion = String.valueOf(secondNumber) + " " + operation + " " +  String.valueOf(firstNumber);
					 }
		}

		public int getTheCorrectAnswer() {
			return theCorrectAnswer;
		}

		public void setTheCorrectAnswer(int theCorrectAnswer) {
			this.theCorrectAnswer = theCorrectAnswer;
		}
		
		
}
