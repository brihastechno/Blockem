package com.blockem.tetris;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blockem.tetris.Panel.OnPanelListener;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import easing.interpolator.BackInterpolator;
import easing.interpolator.EasingType.Type;

/*************************************************/
/* Main game activity ****************************/
/*************************************************/
public class Activity_GameScreen_Class extends Activity implements
		OnTouchListener, OnPanelListener {

	BoardView gameBoard;
	int d; // The side of a box
	public Box[][] box;
	int x, y;

	Display display;
	CountDownTimer timer;
	Piece currentPiece;
	Piece nextPiece;
	boolean pieceOnGame;

	// Score and combo
	TextView textScore;

	int score = 0;
	int combo = 1;
	Vibrator vibrator;
	// The pause indicator
	boolean game;
	// Preferences
	boolean prefMusic;
	boolean prefFX;
	boolean prefVib;
	boolean prefBg;
	boolean prefScreen;
	char[][] temp_box;
	static int max_number_row = 15;
	static int text_size = 10;
	float oldTouchValueX = 0;
	float oldTouchValueY = 0;
	LinearLayout layout_score;
	// FrameLayout layout_score_back;
	ListView listview_words;
	ArrayList<String> string_words;
	ImageView nextPieceImg;
	String[] s_test;

	char[] text_array = new char[] { 'B', 'O', 'X', 'F', 'Y', 'T', 'J' };
	ArrayList<String> arraylist_color;
	String color_green = "#168716", color_red = "#B9000D";
	boolean in_level = true;
	int active_level = 0;

	/*************************************************/
	/* On activity creation ************************* */
	/*************************************************/
	/* Initializes variables, ui elements, and ****** */
	/* starts the game ****************************** */
	/* See inline comments for more details ********* */
	/*************************************************/
	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Load preferences
		SharedPreferences settings = getSharedPreferences("settings", 0);
		prefMusic = settings.getBoolean("music", false);
		prefFX = settings.getBoolean("fx", true);
		prefVib = settings.getBoolean("vib", true);
		prefBg = settings.getBoolean("backgrounds", true);
		prefScreen = settings.getBoolean("keepon", true);
		// Log.e("Vib", Boolean.toString(prefVib));
		// Assign layouts
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		display = getWindowManager().getDefaultDisplay();
		// Log.d("", "width =" + display.getWidth() + " dissplay heights"
		// + display.getHeight());
		int width = (int) (display.getWidth());
		int height = (int) (display.getHeight() * 0.9);
		if (display.getWidth() >= 800) {
			text_size = 25;
		} else if (display.getWidth() >= 400 && display.getWidth() < 800) {
			text_size = 17;
		} else {
			text_size = 11;
		}
		x = (int) (width * 0.05);
		y = (int) (height * 0.05);
		d = (int) (width * .9 / 10);

		max_number_row = (int) height / d;
		// Log.d("the value of  d", "" + d);
		// Log.d("the value of  width", "" + width);
		// Log.d("the value of  heiights", "" + height);
		setContentView(R.layout.activity_gamescreen_layout);
		// Assign buttons
		// Get measures for the board

		Panel panel = (Panel) findViewById(R.id.leftPanel1);
		panel.setOnPanelListener(this);
		// panel.setInterpolator(new BackInterpolator(Type.OUT, 2));
		listview_words = (ListView) findViewById(R.id.listView_words);
		string_words = new ArrayList<String>();
		arraylist_color = new ArrayList<String>();

		s_test = getIntent().getStringArrayExtra("level_load");
		active_level = getIntent().getIntExtra("active_level", 1);
		text_array = getIntent().getCharArrayExtra("char_array");
		if (s_test == null) {
			in_level = false;
			s_test = new String[] { "BOX", "FOX", "BOY", "TOY", "OX", "JOY" };
			text_array = new char[] { 'B', 'O', 'X', 'F', 'Y', 'T', 'J' };
		}

		for (int i = 0; i < s_test.length; i++) {
			string_words.add(s_test[i]);
			arraylist_color.add(color_red);
		}
		Adapter_List_Words_Class adapter = new Adapter_List_Words_Class(
				getApplicationContext(), string_words, arraylist_color);
		listview_words.setAdapter(adapter);

		gameBoard = (BoardView) findViewById(R.id.GameView);
		// Asign score and combo resources
		textScore = (TextView) findViewById(R.id.TextViewScore);
		layout_score = (LinearLayout) findViewById(R.id.scrore_layout);
		// layout_score_back = (FrameLayout)
		// findViewById(R.id.score_layout_back);

		// Initialize boxes and draw the wall

		box = new Box[max_number_row][10];
		// Log.d("the max_number_row of  x", "" + max_number_row);
		// number
		// Log.d("the value of  x", "" + x);
		// Log.d("the value of  y", "" + y);
		gameBoard.createWall(x, y, d);
		if (prefBg)
			gameBoard.createBg(x, y, d);
		for (int i = 0; i < max_number_row; i++) {
			x = (int) (width * 0.05);
			for (int j = 0; j < 10; j++) {
				box[i][j] = new Box(x + d * j, y + d * i, d);
				// Log.d("values" + i + " j " + j, " value of " + (x + d * j)
				// + "  __" + (y + d * i) + " " + d);
				gameBoard.initialize(i, j, x, y, d);
				x = x + d;
			}
			y = y + d;
		}
		;
		// initialize vibrator
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		// Initialize pieces
		currentPiece = new Piece(max_number_row, text_array);
		nextPiece = new Piece(max_number_row, text_array);
		nextPieceImg = (ImageView) findViewById(R.id.imageViewNext);

		// Start the time bucle
		game = true;
		timer = new CountDownTimer(150000, 1000) {
			public void onTick(long millisUntilFinished) {
				gameAction();
			}

			public void onFinish() {
				gameAction();
				start();
			}
		}.start();
		// Assign buttons action

		// Start the game
		textScore.setText("0");
		currentPiece.start();
		// Set image for next piece
		// Has to be done here, or there is no next piece image at the beggining
		switch (nextPiece.type) {
		case Values.PIECE_0:
			nextPieceImg.setImageResource(R.drawable.piece0);
			break;
		case Values.PIECE_1:
			nextPieceImg.setImageResource(R.drawable.piece1);
			break;
		case Values.PIECE_2:
			nextPieceImg.setImageResource(R.drawable.piece2);
			break;
		case Values.PIECE_3:
			nextPieceImg.setImageResource(R.drawable.piece3);
			break;
		case Values.PIECE_4:
			nextPieceImg.setImageResource(R.drawable.piece4);
			break;
		case Values.PIECE_5:
			nextPieceImg.setImageResource(R.drawable.piece5);
			break;
		case Values.PIECE_6:
			nextPieceImg.setImageResource(R.drawable.piece6);
			break;
		}
		// update_score();

		// Set image for next piece
		// Has to be done here, or there is no next piece image at the beggining
		// layout_score_back.setOnTouchListener(this);
		gameBoard.setOnTouchListener(this);
	}

	/*************************************************/
	/* Main time bucle ****************************** */
	/*************************************************/
	/* Checks the game state (ongoing, paused, ****** */
	/* ended... On each bucle, it tries to move the * */
	/* current piece down. If its imposible, it ***** */
	/*
	 * checks for filled rows, updates the combo flag / /* if necessary, and
	 * check if the game is loose,
	 */
	/* otherwise it initiates a new piece *********** */
	/*************************************************/
	public void gameAction() {
		if (game == true) {
			unDraw();
			// Try to move it down.

			if (currentPiece.moveDown() == false) {
				// If couldnt move the piece down, the boxes occupied by it
				// become ocuupied boxes
				// Log.d(" movedown ", "" + currentPiece.moveDown());
				for (int i = 0; i < max_number_row; i++)
					for (int j = 0; j < 10; j++) {
						if (currentPiece.box[i][j] == true) {
							box[i][j].setColor(currentPiece.getColor());
							box[i][j].setchar(currentPiece.get_box_char(i, j));
							gameBoard.setColor(i, j, currentPiece.getColor());
							gameBoard.setText(i, j,
									currentPiece.get_box_char(i, j));

						}
					}
				look_for_words();
				if (combo >= 1) {
					vibrate(500);
					score = score + Values.SCORE_PER_ROW * combo;
					textScore.setText(Integer.toString(score));
				}
				// / ...check if there is any full row...

				// ... check if the game is loose...
				checkGameLoose();
				// ... and start a new piece
				currentPiece = nextPiece;
				currentPiece.start();
				nextPiece = new Piece(max_number_row, text_array);
				// Set the next piece image
				switch (nextPiece.type) {
				case Values.PIECE_0:
					nextPieceImg.setImageResource(R.drawable.piece0);
					break;
				case Values.PIECE_1:
					nextPieceImg.setImageResource(R.drawable.piece1);
					break;
				case Values.PIECE_2:
					nextPieceImg.setImageResource(R.drawable.piece2);
					break;
				case Values.PIECE_3:
					nextPieceImg.setImageResource(R.drawable.piece3);
					break;
				case Values.PIECE_4:
					nextPieceImg.setImageResource(R.drawable.piece4);
					break;
				case Values.PIECE_5:
					nextPieceImg.setImageResource(R.drawable.piece5);
					break;
				case Values.PIECE_6:
					nextPieceImg.setImageResource(R.drawable.piece6);
					break;
				}
			}
			// Copy the board info to the piece
			currentPiece.readBoard(box);
			reDraw();
		}
	}

	/*************************************************/
	/* Checks if the current game is loose*********** */
	/*************************************************/
	/* Checks if there is something in the first two */
	/* rows of the board. If there is something, **** */
	/* the game is loose. Score is checked for ****** */
	/* highscores and those are updated if necessary */
	/* A dialog is shown to the user where the score */
	/* and a trophy (if highscore) are shown, and *** */
	/* asking to choose between exiting or sharing ** */
	/* the score with an external app. On exit, the * */
	/* activity is finished and you return to the *** */
	/* main menu ************************************ */
	/*************************************************/
	private void checkGameLoose() {
		int hScore1, hScore2, hScore3, aux;
		String hScore1Date, hScore2Date, hScore3Date, auxDate;
		boolean loose = false;
		for (int j = 0; j < 10; j++)
			if (box[1][j].getColor() != Values.COLOR_NONE)
				loose = true;
		if (loose == false)
			return;
		// If I get here, the game is loose. Game state variable is set to false
		game = false;
		// Vibrate if vibration is active in prefenrences
		// TODO: See line above
		vibrate(1000);
		// Add high scores if needed
		SharedPreferences highScores = getSharedPreferences("highScores", 0);
		hScore1 = highScores.getInt("hScore1", 0);
		hScore2 = highScores.getInt("hScore2", 0);
		hScore3 = highScores.getInt("hScore3", 0);
		hScore1Date = highScores.getString("hScore1Date", "0");
		hScore2Date = highScores.getString("hScore2Date", "0");
		hScore3Date = highScores.getString("hScore3Date", "0");
		Calendar currentDate = Calendar.getInstance();
		Date dateNow = currentDate.getTime();
		if (score > hScore3) {
			hScore3 = score;
			hScore3Date = dateNow.toString();
		}
		if (hScore3 > hScore2) {
			aux = hScore2;
			auxDate = hScore2Date;
			hScore2 = hScore3;
			hScore2Date = hScore3Date;
			hScore3 = aux;
			hScore3Date = auxDate;
		}
		if (hScore2 > hScore1) {
			aux = hScore1;
			auxDate = hScore1Date;
			hScore1 = hScore2;
			hScore1Date = hScore2Date;
			hScore2 = aux;
			hScore2Date = auxDate;
		}
		SharedPreferences.Editor editor = highScores.edit();
		editor.putInt("hScore1", hScore1);
		editor.putInt("hScore2", hScore2);
		editor.putInt("hScore3", hScore3);
		editor.putString("hScore1Date", hScore1Date);
		editor.putString("hScore2Date", hScore1Date);
		editor.putString("hScore3Date", hScore1Date);
		editor.commit();
		// Show dialog showing score
		// TODO:Show a trophy icon if high score
		SharedPreferences sharedpref = getSharedPreferences("mygame_blockm",
				MODE_PRIVATE);
		boolean b = sharedpref.getBoolean("level_lock_" + (active_level + 1),
				false);
		Log.d(" valuesssssss", "" + score + " in_level " + in_level + " b " + b);
		if (score > 0 && in_level && b == false) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Level Unlocked");
			String msg = getString(R.string.score) + Integer.toString(score);
			builder.setMessage(msg)
					.setCancelable(false)
					.setNegativeButton("Share Score",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									Intent i = new Intent();
									i.putExtra("score", "" + score);
									setResult(RESULT_OK, i);
									finish();

								}
							})
					// A button to just quit. Finishes the activity, so the user
					// returns to the main menu
					// A button to just share score with an external app
					.setPositiveButton("Move To Next Level",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									setResult(RESULT_OK);
									finish();
								}
							});
			AlertDialog endGameAlert = builder.create();
			endGameAlert.show();

		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.gameover);
			String msg = getString(R.string.score) + Integer.toString(score);
			builder.setMessage(msg)
					.setCancelable(false)
					// A button to just quit. Finishes the activity, so the user
					// returns to the main menu
					.setNegativeButton(R.string.exit,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									finish();
								}
							})
					// A button to just share score with an external app
					.setPositiveButton(R.string.shareScore,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Intent shareIntent = new Intent(
											Intent.ACTION_SEND);
									shareIntent.setType("text/plain");
									shareIntent
											.putExtra(
													Intent.EXTRA_TEXT,
													getString(R.string.shareScore1)
															+ " "
															+ Integer
																	.toString(score)
															+ " "
															+ getString(R.string.shareScore2));
									shareIntent.putExtra(Intent.EXTRA_SUBJECT,
											getString(R.string.app_name));
									startActivity(Intent
											.createChooser(
													shareIntent,
													getString(R.string.shareScoreSelector)));
								}
							});
			AlertDialog endGameAlert = builder.create();
			endGameAlert.show();
		}
	}

	/*************************************************/
	/* Checks for filled rows ********** */
	/*************************************************/
	/* Check if some row is filled. If there is some */
	/* it calls to removeRow(), that will remove the */
	/* row and increase score. ********************** */
	/* This function returns a boolean indicating if */
	/* something has been removed, to keep track of */
	/* the combo multiplier. ************************ */
	/*************************************************/
	public boolean lookForRows() {
		boolean somethingRemoved = false; // To determine if some row has been
											// removed to keep the combo
		boolean full = true;
		for (int i = 1; i < max_number_row; i++) {
			full = true;
			for (int j = 0; j < 10; j++) {
				if (box[i][j].getColor() == Values.COLOR_NONE)
					full = false;
			}
			if (full == true) {
				somethingRemoved = true;
				// Remove the row. The score is increase here

				removeRow(i);
			}
		}
		return somethingRemoved;
	}

	/**
	 * check for words match Horizontally
	 */

	public void look_for_words() {
		// Temp String to test
		// String[] s_test = new String[] { "BOX", "FOX", "BOY", "TOY",
		// "OX","JOY" };
		combo = 0;
		for (int w = 0; w < s_test.length; w++) {
			// loop for find the First Word position
			for (int i = 1; i < max_number_row; i++) {
				for (int j = 0; j < 10; j++) {
					// check for First Letter Match and Check the position that
					// it shouldn't right most
					if (box[i][j].getChar() == s_test[w].charAt(0)
							&& j < 10 - (s_test[w].length()-1)) {
						// perform comparing for full word//
						Log.d("value i " + i + " j " + j,
								"" + box[i][j].getChar());

						// perform search //sent value position row(i) column(j)
						// string word(s_test)
						perform_compare(i, j, s_test[w]);

					}

				}

			}
		}

	}

	/**
	 * 
	 * position row(i) column(j) string word(stest)
	 * 
	 * @param i
	 * @param j
	 * @param s_test
	 */
	public void perform_compare(int i, int j, String s_test) {
		int temp_j = j;
		boolean b = false;
		// loop for get compare the string by character ..if not matched break
		// the loop
		for (int k = 0; k < s_test.length(); k++) {
			if (s_test.charAt(k) == box[i][temp_j].getChar()) {
				temp_j++;
				b = true;
			} else {
				b = false;
				break;
			}

		}

		// if b true i.e. word found in row remove the word sent position row()
		if (b) {
			int index = string_words.indexOf(s_test);
			arraylist_color.set(index, color_green);
			Adapter_List_Words_Class adapter = new Adapter_List_Words_Class(
					getApplicationContext(), string_words, arraylist_color);
			listview_words.setAdapter(adapter);
			Log.d("call to remove " + i + " " + j, "" + s_test.length());
			// position row(i) column(j) string word length
			remove_word(i, j, s_test.length());
			combo++;

		}

	}

	/**
	 * 
	 * // position row(row) column(j_start) string word length
	 * 
	 * @param row
	 * @param j_start
	 * @param length
	 */

	private void remove_word(int row, int j_start, int length) {
		// TODO Auto-generated method stub
		char[][] box_char = new char[max_number_row][10];
		String[][] box_charr = gameBoard.getBlock_char();

		char[][] box_char2 = new char[max_number_row][10];
		for (int i = 0; i < max_number_row; i++) {
			for (int j = 0; j < 10; j++) {
				String s = box_charr[i][j];
				if (s.length() > 0) {
					// Log.d("value", s);
					box_char[i][j] = s.charAt(0);

				} else {
					box_char[i][j] = ' ';
				}
				// char c=s.charAt(0);
			}
		}

		// Log.d("the value of full i ", " " + row);
		for (int i = row; i > 1; i--)
			for (int j = j_start; j < (j_start + length); j++) {
				box[i][j].setColor(box[i - 1][j].getColor());
				box[i][j].setchar(box[i - 1][j].getChar());
				gameBoard.setColor(i, j, (byte) box[i - 1][j].getColor());
				// Log.d("the value of char  ", " "+box_char2[i][j]);
				// Log.d("the value of char  -1", " "+box_char2[i-1][j]);
				box_char2[i][j] = box_char[i - 1][j];
				gameBoard.setText(i, j, box[i][j].getChar());
			}
	}

	/*************************************************/
	/* Removes the row passed as argument *********** */
	/*************************************************/
	/* First increases the score according to the *** */
	/* combo multiplier. Then doubles the combo ***** */
	/* multiplier (never higher than 16). Finally it */
	/* moves all the rows above the removed one one * */
	/* position down. ******************************* */
	/*************************************************/
	public void removeRow(int row) {
		vibrate(500);
		score = score + Values.SCORE_PER_ROW * combo;
		textScore.setText(Integer.toString(score));
		// update_score();
		// Setcombo multiplier
		combo = combo * 2;
		if (combo == 32)
			combo = 16;

		char[][] box_char = new char[max_number_row][10];
		String[][] box_charr = gameBoard.getBlock_char();

		char[][] box_char2 = new char[max_number_row][10];
		for (int i = 0; i < max_number_row; i++) {
			for (int j = 0; j < 10; j++) {
				String s = box_charr[i][j];
				if (s.length() > 0) {
					// Log.d("value", s);
					box_char[i][j] = s.charAt(0);

				} else {
					box_char[i][j] = ' ';
				}
				// char c=s.charAt(0);
			}
		}

		// Log.d("the value of full i ", " " + row);
		for (int i = row; i > 1; i--)
			for (int j = 0; j < 10; j++) {
				box[i][j].setColor(box[i - 1][j].getColor());
				box[i][j].setchar(box[i - 1][j].getChar());
				gameBoard.setColor(i, j, (byte) box[i - 1][j].getColor());
				// Log.d("the value of char  ", " "+box_char2[i][j]);
				// Log.d("the value of char  -1", " "+box_char2[i-1][j]);
				box_char2[i][j] = box_char[i - 1][j];
				gameBoard.setText(i, j, box_char2[i][j]);
			}
	}

	/*************************************************/
	/* Draws the piece being played ***************** */
	/*************************************************/
	/* Draws cubes in the positions occupied by the * */
	/*
	 * current piece. Should be called after undraw() /
	 * /************************************************
	 */
	public void reDraw() {
		// Read where the piece is and colorize
		for (int i = 0; i < max_number_row; i++)
			for (int j = 0; j < 10; j++) {
				if (currentPiece.box[i][j] == true) {
					// Log.e("In draw true box " + i + " " + j, ""
					// + currentPiece.box[i][j]);
					box[i][j].setColor(currentPiece.getColor());
					box[i][j].setchar(currentPiece.get_box_char(i, j));
					gameBoard.setColor(i, j, currentPiece.getColor());
					gameBoard.setText(i, j, currentPiece.get_box_char(i, j));

				}
			}
	}

	/*************************************************/
	/* Clears the piece being played **************** */
	/*************************************************/
	/* Clears cubes in the positions occupied by the */
	/* current piece. Should be called befors draw() */
	/*************************************************/
	public void unDraw() {

		for (int i = 0; i < max_number_row; i++)
			for (int j = 0; j < 10; j++) {

				if (currentPiece.box[i][j] == true) {
					// Log.d("In Undraw true box " + i + " " + j, ""
					// + currentPiece.box[i][j]);
					box[i][j].setColor(0);
					box[i][j].setchar(' ');

					gameBoard.setColor(i, j, (byte) 0);
					gameBoard.setText(i, j, ' ');
				}
			}
	}

	/*************************************************/
	/* On finish activity *************************** */
	/*************************************************/
	/* Saves the game if it's running. Otherwise it * */
	/* deletes last saved game ********************** */
	/*************************************************/
	@Override
	public void onDestroy() { // TODO: Implement. Check back button behavior
		if (game) { // If the game is running. //TODO: Check behavior when game
					// is paused
			// Save state of all boxes
			// Save current piece
			// Save next piece
			// Save score
			// Save combo
			game = false; // Actually pauses the game

		}
		handler_hide_show.removeCallbacksAndMessages(null);

		super.onDestroy();
	}

	/*************************************************/
	/* Load game ************************************ */
	/*************************************************/
	/*
	 * Loads saved game state. Returns false if unable/
	 * /************************************************
	 */
	public boolean loadGame() { // TODO: Implement
		// Load board state
		// Load current piece
		// Load next piece
		// Load score
		// Load combo
		return true;
	}

	public boolean vibrate(int time) {
		if (prefVib == true) {
			vibrator.vibrate(time);
			return true;
		} else
			return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// if (v.getId() == R.id.score_layout_back) {
		// if (layout_score.getVisibility() == 4)
		// update_score();
		// }
		//
		// else {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			oldTouchValueX = event.getX();
			oldTouchValueY = event.getY();
			// Log.e("touch event down ", "" + event.getX());
			break;
		}
		case MotionEvent.ACTION_UP: {
			// Log.e("touch event up ", "" + event.getX());
			float currentX = event.getX();
			float currentY = event.getY();
			if (currentX - oldTouchValueX > 20
					&& (currentY - oldTouchValueY < 20 || oldTouchValueY
							- currentY < 20)) {
				unDraw();
				currentPiece.moveRight();
				vibrate(30);
				reDraw();
			} else if (oldTouchValueX - currentX > 20
					&& (currentY - oldTouchValueY < 20 || oldTouchValueY
							- currentY < 20)) {
				unDraw();
				currentPiece.moveLeft();
				vibrate(30);
				reDraw();
			}

			else if (currentY - oldTouchValueY > 20
					&& (oldTouchValueX - currentX < 20 || currentX
							- oldTouchValueX < 20)) {
				// Log.d("current y ", " current " + currentY + "  old "
				// + oldTouchValueY);
				unDraw();
				currentPiece.moveDown();
				vibrate(30);
				reDraw();
			} else if ((oldTouchValueX - currentX < 20 || currentX
					- oldTouchValueX < 20)
					&& (currentY - oldTouchValueY < 20 || oldTouchValueY
							- currentY < 20)) {
				unDraw();
				currentPiece.rotateRight();
				vibrate(30);
				reDraw();
			} else {

			}
			break;

		}
		case MotionEvent.ACTION_MOVE: {

		}
		}
		// }

		return true;
	}

	public void update_score() {

		hide_menus_seekBars();
	}

	/**
	 * Auto_Hide Layouts..Time set 3000 3 seconds To Hide
	 */
	public void hide_menus_seekBars() {
		show_all_controllers();
		handler_hide_show.postDelayed(hideControllerThread, 5000);
	}

	private Runnable hideControllerThread = new Runnable() {

		public void run() {
			// Hide controllers with animation
			hide_controllers_animation();
		}
	};

	/**
	 * Make The Fading Animation to all The controller Views
	 * 
	 * 
	 */
	private void hide_controllers_animation() {
		// TODO Auto-generated method stub
		ObjectAnimator object_one = ObjectAnimator.ofFloat(layout_score,
				"alpha", 1f, 0f);

		// make the animator set
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(object_one);
		animatorSet.setDuration(2000);
		animatorSet.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
				layout_score.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub

			}
		});

		animatorSet.start();
	}

	/**
	 * Showing all controllers with animation fade in
	 * 
	 * 
	 */
	private void show_all_controllers() {
		// TODO Auto-generated method stub
		ObjectAnimator object_one = ObjectAnimator.ofFloat(layout_score,
				"alpha", 0f, 1f);

		final AnimatorSet animSetXY = new AnimatorSet();
		animSetXY.playTogether(object_one);
		animSetXY.setDuration(200);
		animSetXY.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
				layout_score.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub

			}
		});

		animSetXY.start();
	}

	public void onPanelClosed(Panel panel) {
		String panelName = getResources().getResourceEntryName(panel.getId());
		// Log.d("Test", "Panel [" + panelName + "] closed");
	}

	public void onPanelOpened(Panel panel) {
		String panelName = getResources().getResourceEntryName(panel.getId());
		// Log.d("Test", "Panel [" + panelName + "] opened");
	}

	private Handler handler_hide_show = new Handler();
}
