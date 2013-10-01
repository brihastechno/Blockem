package com.blockem.tetris;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author lenovo Activity_Vocabulary_Improve_Class handle the levels and
 *         display according to lock,done and in progress... On click the menus
 *         set to new screen display the test...
 * 
 */
public class Activity_Vocabulary_Improve_Class extends Activity {
	TextView text_level;
	ListView listview;
	Level_Adapter_Class mlevel_adapter;
	ArrayList<Level_class> arraylist_level;
	public static int LEVEL_INTENT = 1;
	int level_select;

	SharedPreferences sharedpref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vocabulary_improve_layout);
		sharedpref = getSharedPreferences("mygame_blockm", MODE_PRIVATE);
		listview = (ListView) findViewById(R.id.listView1);
		arraylist_level = new ArrayList<Level_class>();
		for (int i = 0; i < 5; i++) {
			Level_class level = new Level_class();
			boolean b = sharedpref.getBoolean("level_lock_" + (i + 1), false);
			if (b || i == 0) {
				Log.d("level_icon", "R.drawable.lock_icon_green");
				level.level_icon = R.drawable.lock_icon_green;
			} else {
				Log.d("level_icon", " R.drawable.lock_icon_red");
				level.level_icon = R.drawable.lock_icon_red;
			}
			// level.level_icon = R.drawable.lock_icon;
			level.level_text = "Level " + (i + 1);
			level.level_message = "" + ((i + 1) * 4) + " Words";
			level.play_level = false;
			arraylist_level.add(level);
		}
		mlevel_adapter = new Level_Adapter_Class(
				Activity_Vocabulary_Improve_Class.this, arraylist_level);
		listview.setAdapter(mlevel_adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				boolean b = sharedpref.getBoolean("level_lock_" + (arg2 + 1),
						false);

				if (b || arg2 == 0) {
					level_select = (arg2 + 1);
					Intent intent_test_screen = new Intent(
							Activity_Vocabulary_Improve_Class.this,
							Activity_Vocabulary_Test_Class.class);
					intent_test_screen.putExtra("position", +arg2);
					intent_test_screen.putExtra("active_level", level_select);
					startActivityForResult(intent_test_screen, LEVEL_INTENT);
				} else {
					Toast.makeText(getApplicationContext(),
							"Level Is Locked .. ", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == LEVEL_INTENT && resultCode == RESULT_OK) {
			if (data != null) {
				update_UI();
				String score = data.getStringExtra("score");
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_TEXT,
						"Open new Level with Score  " + score);
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Blockm Game APP");
				startActivity(Intent.createChooser(shareIntent, "Share..."));
			} else {
				update_UI();
			}
		}

	}

	public void update_UI() {
		final SharedPreferences.Editor editor = sharedpref.edit();
		editor.putBoolean("level_lock_" + (level_select + 1), true);
		Log.e("Level in Activity result ", "level_lock_" + (level_select + 1)
				+ " hhhh" + level_select);
		editor.commit();
		arraylist_level.clear();
		for (int i = 0; i < 5; i++) {
			Level_class level = new Level_class();
			boolean b = sharedpref.getBoolean("level_lock_" + (i + 1), false);
			if (b || i == 0) {
				Log.d("level_icon", "R.drawable.lock_icon_green");
				level.level_icon = R.drawable.lock_icon_green;
			} else {
				Log.d("level_icon", " R.drawable.lock_icon_red");
				level.level_icon = R.drawable.lock_icon_red;
			}
			// level.level_icon = R.drawable.lock_icon;
			level.level_text = "Level " + (i + 1);
			level.level_message = "" + ((i + 1) * 4) + " Words";
			level.play_level = false;
			arraylist_level.add(level);
		}
		mlevel_adapter = new Level_Adapter_Class(
				Activity_Vocabulary_Improve_Class.this, arraylist_level);
		listview.setAdapter(mlevel_adapter);

	}

}
