package com.blockem.tetris;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class Fragment_Vocabulary_Word extends Fragment {
	TextView textView_word, textView_pronounced, textView_meaning;
	String word;
	String word_pronounced;
	String word_meaning;

	public Fragment_Vocabulary_Word(String word, String pronounced,
			String meaning) {
		this.word = word;
		this.word_pronounced = pronounced;
		this.word_meaning = meaning;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_vocabulary_test,
				container, false);
		textView_word = (TextView) view.findViewById(R.id.textView_word);
		textView_pronounced = (TextView) view
				.findViewById(R.id.textView_pronounced);
		textView_meaning = (TextView) view.findViewById(R.id.textView_meaning);
		textView_word.setText("" + word);
		textView_meaning.setText("" + word_meaning);
		textView_pronounced.setText("" + word_pronounced);

		return view;
	}

}
