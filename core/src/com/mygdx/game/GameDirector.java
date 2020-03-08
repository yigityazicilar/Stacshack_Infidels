package com.mygdx.game;

import sun.rmi.rmic.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TimerTask;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameDirector
{

	private Role playerClass;
	List<String> playerWordList;
	List<String> enemyWordList;

	String[] currentWordList;
	Entity target;
	int damage;

	boolean isPlayerTurn;
	boolean wordIsComplete;

//	Timer roundTimer;

	ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	ScheduledFuture<?> future;

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
			String randoWord = "";
			while(!wordListSet)
			{
				int randomIndex = new Random().nextInt(enemyWordList.size() - 1);
				randoWord = enemyWordList.get(randomIndex);

				// Check if word respects difficulty
				if (wordDims[0] <= randoWord.length() && wordDims[1] >= randoWord.length())
					wordListSet = true;

				// Check for duplicates
				for (String nextString: enemyWordsArray) 
					if(nextString.equals(randoWord))
						wordListSet = false;
			}
			enemyWordsArray[i] = randoWord;
		}

		// Set timer
		future = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				MainGame.generateWords = true;
				MainGame.playerTurn = true;
				endTimer();
			}
		}, duration, 999, TimeUnit.SECONDS);
		currentWordList = enemyWordsArray;
		this.target = target;
		return enemyWordsArray;
	}

	public String[] requestPlayerWordBatch()
	{
		String[] playerWordsArray = new String[] {"","",""};
		
		for(int i = 0; i < playerWordsArray.length; i++) {
			boolean wordIsSet = false;
			String randoWord = "";
			while (!wordIsSet) {
				int randomIndex = new Random().nextInt(playerWordList.size() - 1);
				randoWord = playerWordList.get(randomIndex);
				wordIsSet = true;

				// Check for duplicates
				System.out.println(randoWord);

				for (String nextString : playerWordsArray) {
					System.out.println(nextString);
					if (nextString.equals(randoWord))
						wordIsSet = false;
				}
				playerWordsArray[i] = randoWord;
			}
		}

		future = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				MainGame.generateWords = true;
				MainGame.playerTurn = false;
				endTimer();
			}
		}, 3, 999, TimeUnit.SECONDS);
		currentWordList = playerWordsArray;
		this.target = target;
		return playerWordsArray;
	}



	public void endTimer()
	{
		if(!isPlayerTurn && !wordIsComplete)
		{
			target.hurt(damage);
			damage = 0;
		}
		if(isPlayerTurn && wordIsComplete)
		{
			target.hurt(damage);
			damage = 0;
		}

		future.cancel(true);

	}

	public void sendWordsComplete(Boolean wordIsComplete)
	{
		this.wordIsComplete = wordIsComplete;
		endTimer();
	}

	public GameDirector(Role role)
	{
		playerClass = role;
		try
		{
			playerWordList = Files.readAllLines(Paths.get(playerClass.getFilePath()));
			enemyWordList = Files.readAllLines(Paths.get(Role.BOSS.getFilePath()));
		}
		catch(IOException e)
		{
			System.out.println("Cannot get file! System abort!");
		}
	}

	public void updateDamage(String[] playerWords, String[] enemyWords){

		for(String word : playerWords){
			for(String attack : enemyWords){
				if(!word.equals(attack))
					damage += word.length();
				damage += word.length();
			}
		}
	}

}
