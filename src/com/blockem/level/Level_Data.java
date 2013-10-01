package com.blockem.level;

public class Level_Data {
	public String[] level_one = new String[] { "Doubt", "Truth", "Honest ",
			"Position " };
	public char[] char_array = new char[] { 'D', 'O', 'U', 'B', 'T', 'R', 'H',
			'N', 'E', 'S', 'P', 'I' };

	public String[] level_one_prononce = new String[] { "dout", "tru",
			"on est", " po-zshn" };
	public String[] level_one_meaning = new String[] {
			"To be undecided or skeptical.", "Reality; actuality.",
			" Marked by or displaying integrity; upright",
			". A place or location." };

	public String[] level_two = new String[] { "Doubt", "Truth", "Honest ",
			"Position " };
	public String[] level_two_prononce = new String[] { "dout", "tru",
			"on est", " po-zshn" };
	public String[] level_two_meaning = new String[] {
			"To be undecided or skeptical.", "Reality; actuality.",
			" Marked by or displaying integrity; upright",
			". A place or location." };

	public String[] level_three = new String[] { "Doubt", "Truth", "Honest ",
			"Position " };

	public String[] level_three_prononce = new String[] { "dout", "tru",
			"on est", " po-zshn" };
	public String[] level_three_meaning = new String[] {
			"To be undecided or skeptical.", "Reality; actuality.",
			" Marked by or displaying integrity; upright",
			". A place or location." };

	public String[] level_four = new String[] { "Doubt", "Truth", "Honest ",
			"Position " };

	public String[] level_four_prononce = new String[] { "dout", "tru",
			"on est", " po-zshn" };
	public String[] level_four_meaning = new String[] {
			"To be undecided or skeptical.", "Reality; actuality.",
			" Marked by or displaying integrity; upright",
			". A place or location." };

	public String[] level_five = new String[] { "Doubt", "Truth", "Honest ",
			"Position " };

	public String[] level_five_prononce = new String[] { "dout", "tru",
			"on est", " po-zshn" };
	public String[] level_five_meaning = new String[] {
			"To be undecided or skeptical.", "Reality; actuality.",
			" Marked by or displaying integrity; upright",
			". A place or location." };

	public String get_Word(int level, int position) {

		if (level == 0)
			return level_one[position];
		else if (level == 1)
			return level_two[position];
		else if (level == 4)
			return level_five[position];
		else if (level == 2)
			return level_three[position];
		else if (level == 3)
			return level_four[position];
		else
			return null;

	}

	public String get_meaning(int level, int position) {
		if (level == 0)
			return level_one_meaning[position];
		else if (level == 1)
			return level_two_meaning[position];
		else if (level == 2)
			return level_three_meaning[position];
		else if (level == 3)
			return level_four_meaning[position];
		else if (level == 4)
			return level_five_meaning[position];
		else
			return null;

	}

	public String get_pronunce(int level, int position) {

		if (level == 0)
			return level_one_prononce[position];
		else if (level == 1)
			return level_two_prononce[position];
		else if (level == 2)
			return level_three_prononce[position];
		else if (level == 3)
			return level_four_prononce[position];
		else if (level == 4)
			return level_five_prononce[position];
		else
			return null;

	}

}
