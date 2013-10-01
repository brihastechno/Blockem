package com.blockem.tetris;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 
 * @author lenovo Activity_Splash_Loading_Screen Default First Loading Screen Of
 *         App ... wait for 5Sec and than move to
 *         {@link Activity_HomeScreen_Class}
 * 
 */
public class Activity_Splash_Loading_Screen extends Activity {
	// Title Textview...
	TextView textView_B, textView_L, textView_O, textView_C, textView_K,
			textView_M;
	// typeface set for text...
	Typeface typeface;
	// ObjectAnimator for some animation
	ObjectAnimator objectAnimator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// Set the layout to activity ..activity_splash_loading_layout.xml file
		setContentView(R.layout.activity_splash_loading_layout);

		// Initialize the layout TextView components
		textView_B = (TextView) findViewById(R.id.textView_B);
		textView_O = (TextView) findViewById(R.id.textView_L);
		textView_L = (TextView) findViewById(R.id.textView_O);
		textView_C = (TextView) findViewById(R.id.textView_C);
		textView_K = (TextView) findViewById(R.id.textView_K);
		textView_M = (TextView) findViewById(R.id.textView_M);

		// Generate the TypeFace from assets folder and apply to text Blocks..
		typeface = Typeface.createFromAsset(getAssets(), "cooper_black.ttf");
		textView_B.setTypeface(typeface);
		textView_O.setTypeface(typeface);
		textView_L.setTypeface(typeface);
		textView_C.setTypeface(typeface);
		textView_K.setTypeface(typeface);
		textView_M.setTypeface(typeface);
		// showing Animation To TextView...
		show_animation_to_TextView(textView_B);

	}

	/**
	 * 
	 * @param TextView
	 */
	private void show_animation_to_TextView(final TextView textview) {
		// TODO Auto-generated method stub
		objectAnimator = ObjectAnimator.ofFloat(textview, "alpha", 1f, 1f);
		objectAnimator.setDuration(4000);

		objectAnimator.addListener(new AnimatorListener() {
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub

			}

			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
			}

			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub

				// Start Activity_SocialHome_Activity class
				Intent intent_home_screen = new Intent(
						Activity_Splash_Loading_Screen.this,
						Activity_HomeScreen_Class.class);

				startActivity(intent_home_screen);

				// finish splash activity not going back to this screen after
				finish();
			}

			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
			}
		});

		objectAnimator.start();

	}

}
