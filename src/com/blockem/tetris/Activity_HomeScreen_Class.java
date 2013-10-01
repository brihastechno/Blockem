package com.blockem.tetris;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blockem.homescreen.slides.ReflectingImageAdapter;
import com.blockem.homescreen.slides.ResourceImageAdapter;

/**
 * 
 * @author lenovo Activity_HomeScreen_Class displays First Home Screen of APP
 *         And Display the Slider as menus in APp
 * 
 *         classes Linked with .{@link Activity_Word_Of_Day_Class},
 *         {@link Activity_GameScreen_Class},
 *         {@link Activity_Vocabulary_Improve_Class}
 */
public class Activity_HomeScreen_Class extends Activity {
	Typeface typeface;
	TextView textView_B, textView_L, textView_O, textView_C, textView_K,
			textView_M;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// Hide title bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// set Layouts
		setContentView(R.layout.activity_homescreen_layout);
		typeface = Typeface.createFromAsset(getAssets(), "cooper_black.ttf");

		textView_B = (TextView) findViewById(R.id.textView_B);
		textView_O = (TextView) findViewById(R.id.textView_L);
		textView_L = (TextView) findViewById(R.id.textView_O);
		textView_C = (TextView) findViewById(R.id.textView_C);
		textView_K = (TextView) findViewById(R.id.textView_K);
		textView_M = (TextView) findViewById(R.id.textView_M);

		textView_B.setTypeface(typeface);
		textView_O.setTypeface(typeface);
		textView_L.setTypeface(typeface);
		textView_C.setTypeface(typeface);
		textView_K.setTypeface(typeface);
		textView_M.setTypeface(typeface);

		final CoverFlow coverFlow1 = (CoverFlow) findViewById(this
				.getResources().getIdentifier("coverflow", "id",
						"com.blockem.tetris"));
		setupCoverFlow(coverFlow1, false);

	}

	/**
	 * Setup cover flow.
	 * 
	 * @param mCoverFlow
	 *            the m cover flow
	 * @param reflect
	 *            the reflect
	 */
	private void setupCoverFlow(final CoverFlow mCoverFlow,
			final boolean reflect) {
		BaseAdapter coverImageAdapter;
		if (reflect) {
			coverImageAdapter = new ReflectingImageAdapter(
					new ResourceImageAdapter(Activity_HomeScreen_Class.this));
		} else {
			coverImageAdapter = new ResourceImageAdapter(
					Activity_HomeScreen_Class.this);
		}
		mCoverFlow.setAdapter(coverImageAdapter);
		mCoverFlow.setSelection(1, true);
		setupListeners(mCoverFlow);
	}

	/**
	 * Sets the up listeners.
	 * 
	 * @param mCoverFlow
	 *            the new up listeners
	 */
	/**
	 * Sets the up listeners.
	 * 
	 * @param mCoverFlow
	 *            the new up listeners
	 */
	private void setupListeners(final CoverFlow mCoverFlow) {
		mCoverFlow.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, final long id) {
				int key = position;
				switch (key) {
				case 0:
					// Start Activity Word Of Day....
					Intent intent2 = new Intent(Activity_HomeScreen_Class.this,
							Activity_Word_Of_Day_Class.class);
					startActivity(intent2);
					break;
				case 1:
					Intent intent3 = new Intent(Activity_HomeScreen_Class.this,
							Activity_Vocabulary_Improve_Class.class);
					startActivity(intent3);

					break;
				case 2:
					Intent intent = new Intent(Activity_HomeScreen_Class.this,
							Activity_GameScreen_Class.class);
					startActivity(intent);
					break;

				default:
					break;
				}
			}

		});
		mCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(final AdapterView<?> parent,
					final View view, final int position, final long id) {
				// textView.setText("Item selected! : " + id);

			}

			@Override
			public void onNothingSelected(final AdapterView<?> parent) {
				// textView.setText("Nothing clicked!");
			}
		});
	}
}
