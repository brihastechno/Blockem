package com.blockem.tetris;

import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Level_Adapter_Class extends BaseAdapter {
	private Activity_Vocabulary_Improve_Class activity;
	private ArrayList<Level_class> data;
	private static LayoutInflater inflater = null;

	public Level_Adapter_Class(Activity_Vocabulary_Improve_Class a,
			ArrayList<Level_class> friends) {
		activity = a;
		data = friends;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
			vi = inflater.inflate(R.layout.custom_level_list_layout, null);
			holder = new ViewHolder();
			holder.level_text = (TextView) vi.findViewById(R.id.textView_level);
			holder.level_image = (ImageView) vi
					.findViewById(R.id.imageView_play);
			holder.level_message = (TextView) vi
					.findViewById(R.id.textView_message);

			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		Level_class level = data.get(position);
		holder.level_text.setId(position);
		holder.level_image.setId(position);
		holder.level_text.setId(position);

		holder.level_text.setText(level.level_text);
		Log.d("Level_icon ", "" + level.level_icon);
		holder.level_image.setImageResource(level.level_icon);
		holder.level_message.setText(level.level_message);
		return vi;
	}

	public static class ViewHolder {
		public TextView level_text;
		public TextView level_message;

		public ImageView level_image;

	}
}