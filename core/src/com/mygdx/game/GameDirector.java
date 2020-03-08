package com.mygdx.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TimerTask;

import java.util.List;
import java.util.Random;
import java.util.Timer;

public class GameDirector
{
	List<String> playerWordList;
	List<String> enemyWordList;

	String[] currentWordList;
	Entity target;

	boolean isPlayerTurn;
	boolean wordIsComplete;

	Timer roundTimer;
	TimerTask roundTask = new TimerTask(){

		@Override
		public void run() {
			endTimer();
		}
	};
	public String[] requestEnemyWord(int wordDifficulty, int numberOfWords, int duration, Entity target)
	{
		// Initialize vars
		int[] wordDims = new int[] {Math.max(2 ,wordDifficulty-2), Math.max(2, wordDifficulty + 2)};
		String[] enemyWordsArray = new String[numberOfWords];
		for (int i = 0; i < enemyWordsArray.length; i++) 
			enemyWordsArray[i] = "";

		// Choose random word.
		for(int i = 0; i < enemyWordsArray.length; i++)
		{
			boolean wordListSet = false;
			while(!wordListSet)
			{
				int randomIndex = new Random().nextInt(enemyWordList.size() - 1);
				String randoWord = enemyWordList.get(randomIndex);

				// Check if word respects difficulty
				if (wordDims[0] <= randoWord.length() && wordDims[1] >= randoWord.length())
					wordListSet = true;

				// Check for duplicates
				for (String nextString: enemyWordsArray) 
					if(nextString.equals(randoWord))
						wordListSet = false;
			}
		}

		// Set timer
		startRoundTimer(duration);
		currentWordList = enemyWordsArray;
		return enemyWordsArray;
	}

	public String[] requestPlayerWordBatch()
	{
		String[] playerWordsArray = new String[] {"","",""};
		
		for(int i = 0; i < playerWordsArray.length; i++)
		{
			boolean wordIsSet = false;
			String randoWord = "";
			while(!wordIsSet)
			{
				int randomIndex = new Random().nextInt(playerWordList.size() - 1);
				randoWord = playerWordList.get(randomIndex);
				wordIsSet = true;

				// Check for duplicates
				for (String nextString: playerWordsArray)
					if (nextString.equals(randoWord))
						wordIsSet = false;
			}
			playerWordsArray[i] = randoWord;
		}

		startRoundTimer(3);
		currentWordList = playerWordsArray;
		return playerWordsArray;
	}

	public void startRoundTimer(int duration)
	{
		roundTimer = new Timer(true);
		wordIsComplete = false;
		roundTimer.schedule(roundTask, duration * 1000);
	}

	public void endTimer()
	{
		if(!isPlayerTurn && !wordIsComplete)
		{
			int damage = 0;
			for (String nextString: currentWordList) damage += nextString.length();
			target.hurt(damage);
		}
		if(isPlayerTurn && wordIsComplete)
		{
			int damage = 0;
			for (String nextString: currentWordList) damage += nextString.length();
			target.hurt(damage);
		}


	}

	public void sendWordsComplete()
	{
		wordIsComplete = true;
	}

	public double getTimeUntilEndOfTimer()
	{
		return Math.max(0, roundTask.scheduledExecutionTime() - System.currentTimeMillis());
	}

	public GameDirector()
	{
		try
		{
			playerWordList = Files.readAllLines(Paths.get(Role.FIGHTER.getFilePath()));
			enemyWordList = Files.readAllLines(Paths.get(Role.BOSS.getFilePath()));
		}
		catch(IOException e)
		{
			System.out.println("Cannot get file! System abort!");
		}
	}
}
