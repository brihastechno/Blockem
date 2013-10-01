package com.blockem.tetris;

import com.blockem.tetris.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/*************************************************/
/* The game board ********************************/
/*************************************************/
public class BoardView extends View {

	// Drawables for the board boxes, the playable zone
	public Drawable[][] block = new Drawable[Activity_GameScreen_Class.max_number_row][10];
	public Rect[][] block_rect = new Rect[Activity_GameScreen_Class.max_number_row][10];
	String[][] block_char = new String[Activity_GameScreen_Class.max_number_row][10];

	// Drawables for the wall (yes, it's done with tiles)
	public Drawable[] wall = new Drawable[(Activity_GameScreen_Class.max_number_row * 4) + 40];
	// Drawable for the background, and boolean for drawing it or not
	Drawable mbg;
	boolean bg = false;
	// Context and canvas to be used along the class
	Context context;
	Canvas c;
	Paint p;
	Typeface typeface;

	/*************************************************/
	/* Class constructor **************************** */
	/*************************************************/
	/* Defines the context and the canvas *********** */
	/*************************************************/
	public BoardView(Context cont, AttributeSet attrs) {
		super(cont, attrs);
		context = cont;
		p = new Paint();
		typeface = Typeface.createFromAsset(context.getAssets(),
				"cooper_black.ttf");
	}

	/*************************************************/
	/* Initializes drawables for playable boxes ***** */
	/*************************************************/
	/* Must be initialized one by one from the Game * */
	/* activity, passing all the parameters ********* */
	/*************************************************/
	public void initialize(int i, int j, int left, int top, int side) {
		block[i][j] = context.getResources().getDrawable(R.drawable.alpha);
		block[i][j].setBounds(left, top, left + side, top + side);
		block_rect[i][j] = new Rect(left, top, left + side, top + side);
		block_char[i][j] = "";
	}

	/*************************************************/
	/* Draws the board wall ************************* */
	/*************************************************/
	/* Needs the top-left point of the board frame ** */
	/* and the width of the wall ******************** */
	/*************************************************/
	public void createWall(int left, int top, int side) {
		int i = 0, x, y;
		x = left - side / 2;
		y = top;
		// The left wall
		while (i < (Activity_GameScreen_Class.max_number_row * 2)) {
			wall[i] = context.getResources().getDrawable(R.drawable.brick);
			if (side % 2 != 0) {
				if (i % 2 == 0) {
					wall[i].setBounds(x, y, x + side / 2, y + (side / 2) + 1);
					y = y + (side / 2) + 1;
				} else {
					wall[i].setBounds(x, y, x + side / 2, y + (side / 2));
					y = y + side / 2;
				}

			} else {
				wall[i].setBounds(x, y, x + side / 2, y + (side / 2));
				y = y + side / 2;
			}
			i = i + 1;
		}

		x = left + side * 10;
		y = top;

		// The right wall
		while (i <= (Activity_GameScreen_Class.max_number_row * 4)) {
			wall[i] = context.getResources().getDrawable(R.drawable.brick);
			if (side % 2 != 0) {
				if (i % 2 == 0) {
					wall[i].setBounds(x, y, x + side / 2, y + (side / 2) + 1);
					y = y + (side / 2) + 1;
				} else {
					wall[i].setBounds(x, y, x + side / 2, y + (side / 2));
					y = y + side / 2;
				}

			} else {
				wall[i].setBounds(x, y, x + side / 2, y + (side / 2));
				y = y + side / 2;
			}
			i = i + 1;
		}

		x = left - side / 2;
		y = top + Activity_GameScreen_Class.max_number_row * side;

		// The floor
		while (i < ((Activity_GameScreen_Class.max_number_row * 4) + 22)) {
			wall[i] = context.getResources().getDrawable(R.drawable.brick);
			if (side % 2 != 0) {
				if (i % 2 == 0) {
					wall[i].setBounds(x, y, x + (side / 2) + 1, y + side / 2);
					x = x + (side / 2) + 1;
				} else {

					wall[i].setBounds(x, y, x + side / 2, y + side / 2);
					x = x + side / 2;
				}
			} else {
				wall[i].setBounds(x, y, x + side / 2, y + side / 2);
				x = x + side / 2;
			}

			i = i + 1;
		}
	}

	/*************************************************/
	/* Draws the board background ******************* */
	/*************************************************/
	/* Needs the top-left point of the board frame ** */
	/* and the width of the wall ******************** */
	/*************************************************/
	public void createBg(int left, int top, int side) {
		// Set board background (if any)
		bg = true;
		int bgn = 1 + (int) (Math.random() * 14);
		switch (bgn) {
		case 1:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 2:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 3:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 4:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 5:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 6:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 7:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 8:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 9:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 10:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 11:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 12:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 13:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 14:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 15:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 16:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 17:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 18:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		case 19:
			mbg = getResources().getDrawable(R.drawable.black_background);
			break;
		}
		mbg.setBounds((int) (left), (int) (top), (int) (left + side * 10),
				(int) (top + Activity_GameScreen_Class.max_number_row * side));
	}

	/*************************************************/
	/* Draws the board ****************************** */
	/*************************************************/
	/* Draws the walls, the bg and all the boxes **** */
	/*************************************************/
	@Override
	protected void onDraw(Canvas canvas) {

		// Log.d("CALL IN oNDRAW", "CALL IN ONDRAW");
		c = canvas;

		p.setColor(Color.BLACK);
		p.setTextSize(Activity_GameScreen_Class.text_size);
		p.setTypeface(typeface);

		super.onDraw(canvas);
		if (bg)
			mbg.draw(canvas);
		for (int i = 0; i < ((Activity_GameScreen_Class.max_number_row * 4) + 22); i++) {
			// Log.d("The Value Of i ", "i --->"+i);
			wall[i].draw(c);
		}
		for (int i = 0; i < Activity_GameScreen_Class.max_number_row; i++)
			for (int j = 0; j < 10; j++) {
				block[i][j].draw(canvas);
				// Log.e("block erroorr", block_char[i][j]);
				c.drawText("" + block_char[i][j],
						block_rect[i][j].centerX() - 3,
						block_rect[i][j].centerY() + 3, p);
			}
		// Actually draw
		invalidate();
	}

	/*************************************************/
	/* Canvas getter ******************************** */
	/*************************************************/
	public Canvas getCanvas() {
		return c;
	}

	/*************************************************/
	/* Colors a box ********************************* */
	/*************************************************/
	/* Changes the drawable for the indicated box to */
	/* to 'c'. Can also be COLOR_NONE to undraw ***** */
	/*************************************************/
	public void setColor(int i, int j, byte c) {
		Rect rect;
		rect = block[i][j].getBounds();
		switch (c) {
		case Values.COLOR_NONE:
			block[i][j] = context.getResources().getDrawable(R.drawable.alpha);
			;
			break;
		case Values.COLOR_RED:
			block[i][j] = context.getResources().getDrawable(
					R.drawable.block_red);
			break;
		case Values.COLOR_GREEN:
			block[i][j] = context.getResources().getDrawable(
					R.drawable.block_green);
			break;
		case Values.COLOR_BLUE:
			block[i][j] = context.getResources().getDrawable(
					R.drawable.block_blue);
			break;
		case Values.COLOR_YELLOW:
			block[i][j] = context.getResources().getDrawable(
					R.drawable.block_yellow);
			break;
		case Values.COLOR_PINK:
			block[i][j] = context.getResources().getDrawable(
					R.drawable.block_pink);
			break;
		case Values.COLOR_PURPLE:
			block[i][j] = context.getResources().getDrawable(
					R.drawable.block_purple);
			break;
		case Values.COLOR_WHITE:
			block[i][j] = context.getResources().getDrawable(
					R.drawable.block_white);
			break;
		}
		block[i][j].setBounds(rect);
	}

	public void setText(int i, int j, char text) {
		block_char[i][j] = "" + text;

	}

	public String[][] getBlock_char() {
		return block_char;
	}

}
