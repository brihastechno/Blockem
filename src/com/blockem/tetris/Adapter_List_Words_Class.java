package com.blockem.tetris;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter_List_Words_Class extends BaseAdapter {
	Context activity;
	ArrayList<String> data;
	ArrayList<String> color;
	LayoutInflater inflater;
	Typeface typeface;

	public Adapter_List_Words_Class(Context a, ArrayList<String> words,
			ArrayList<String> arraylist_color) {
		activity = a;
		data = words;
		color = arraylist_color;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		Log.d("sss", "" + data.size());
		typeface = Typeface.createFromAsset(activity.getAssets(),
				"robotomedium.ttf");

	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.custom_layout_list, null);
			holder = new ViewHolder();
			holder.word_text = (TextView) vi.findViewById(R.id.textView_word);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		holder.word_text.setId(position);
		holder.word_text.setTypeface(typeface);

		holder.word_text.setText(data.get(position));
		holder.word_text.setTextColor(Color.parseColor(color.get(position)));

		return vi;
	}

	public static class ViewHolder {
		public TextView word_text;

	}
}
