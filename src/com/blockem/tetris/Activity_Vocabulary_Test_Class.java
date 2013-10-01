package com.blockem.tetris;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.blockem.level.Level_Data;

public class Activity_Vocabulary_Test_Class extends FragmentActivity {
	String[] word_questions;
	int[] word_answer;
	public static int level_ch;
	Level_Data level_data;
	Button button_take_test;
	TextView textView_quest, textView_level, textView_page_no;
	int active_quest;
	int right = 0, wrong = 0;
	Dialog dialog;
	ViewPager mviewPager;
	public String[] level_one;
	public static int LEVEL_INTENT = 1;
	int level_select;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocabulary_test);
		level_data = new Level_Data();
		textView_level = (TextView) findViewById(R.id.textView_level);
		textView_page_no = (TextView) findViewById(R.id.textView_page_no);
		mviewPager = (ViewPager) findViewById(R.id.pager);
		button_take_test = (Button) findViewById(R.id.button_take_test);
		level_select = getIntent().getIntExtra("active_level", 1);
		mviewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						// TODO Auto-generated method stub
						textView_page_no.setText("" + (arg0 + 1));
					}
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub
					}
					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub
					}
				});
		level_ch = getIntent().getIntExtra("position", 0);
		textView_level.setText("Level " + (level_ch + 1));
		level_data = new Level_Data();
		MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager(),
				level_data.level_one);
		mviewPager.setAdapter(mAdapter);
		button_take_test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Activity_Vocabulary_Test_Class.this,
						Activity_GameScreen_Class.class);
				if (level_ch == 0) {
					intent.putExtra("level_load", level_data.level_one);
					intent.putExtra("char_array", level_data.char_array);
				} else if (level_ch == 1) {
					intent.putExtra("level_load", level_data.level_two);
					intent.putExtra("char_array", level_data.char_array);

				} else if (level_ch == 2) {
					intent.putExtra("level_load", level_data.level_three);
					intent.putExtra("char_array", level_data.char_array);
				} else if (level_ch == 3) {
					intent.putExtra("level_load", level_data.level_four);
					intent.putExtra("char_array", level_data.char_array);
				} else if (level_ch == 4) {
					intent.putExtra("level_load", level_data.level_five);
					intent.putExtra("char_array", level_data.char_array);
				}

				intent.putExtra("active_level", level_select);
				startActivityForResult(intent, LEVEL_INTENT);
			}
		});

	}

	/**
	 * 
	 * @author lenovo
	 * 
	 */
	public static class MyAdapter extends FragmentStatePagerAdapter {
		String[] db;

		public MyAdapter(FragmentManager fm, String[] db) {
			super(fm);
			this.db = db;
		}

		/**
		 * Get the number of fragments to be displayed in the ViewPager.
		 */

		@Override
		public int getCount() {
			return db.length;
		}

		/**
		 * Return a new GridFragment that is used to display n items at the
		 * position given.
		 * 
		 * @param position
		 *            int - the position of the fragement; 0..numFragments-1
		 */

		@Override
		public Fragment getItem(int position) {
			// Create a new Fragment and supply the fragment number, image
			// position, and image count as arguments.
			Level_Data level = new Level_Data();
			// Return a new GridFragment object.
			Fragment_Vocabulary_Word f = new Fragment_Vocabulary_Word(
					level.get_Word(level_ch, position), level.get_pronunce(
							level_ch, position), level.get_meaning(level_ch,
							position));
			f.setArguments(null);
			return f;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == LEVEL_INTENT && resultCode == RESULT_OK) {
			Log.e("Value OF data", "" + data);
			if (data != null) {
				Log.e("Value OF data", "data is not nuullll");
				String score = data.getStringExtra("score");
				Intent i = new Intent();
				i.putExtra("score", "" + score);
				setResult(RESULT_OK, i);
				finish();
			} else {
				Log.e("Value OF data", "data is nuullll");
				setResult(RESULT_OK);
				finish();
			}
		}
		if (requestCode == LEVEL_INTENT && resultCode == RESULT_FIRST_USER) {
			String score = data.getStringExtra("score");
			Intent i = new Intent();
			i.putExtra("score", "" + score);
			setResult(RESULT_CANCELED, i);

			finish();

		}
	}
}
