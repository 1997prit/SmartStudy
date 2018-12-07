package com.example.hp.smartstudy;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.hp.smartstudy.R.id.rb1;
import static com.example.hp.smartstudy.R.id.rb3;

/**
 * Created by HP on 17-02-2017.
 */

public class QuizquestionAdapter extends BaseAdapter {
    Activity a;
    ArrayList<HashMap<String, String>> arrayList;
    LayoutInflater layoutInflater;

    QuizquestionAdapter(Activity a, ArrayList<HashMap<String, String>> arrayList) {
        this.a = a;
        this.arrayList = arrayList;
        layoutInflater = a.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        private TextView tvquizquestion;
        private TextView rb1, rb2, rb3, rb4;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.raw_quizquestion, null);
            holder = new ViewHolder();
            holder.tvquizquestion = (TextView) convertView.findViewById(R.id.tvquizquestion);
            holder.rb1 = (TextView) convertView.findViewById(rb1);
            holder.rb2 = (TextView) convertView.findViewById(R.id.rb2);
            holder.rb3 = (TextView) convertView.findViewById(rb3);
            holder.rb4 = (TextView) convertView.findViewById(R.id.rb4);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final HashMap<String, String> hashMap = arrayList.get(position);
        holder.tvquizquestion.setText(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_DESCRIPTION));
        holder.rb1.setText(hashMap.get(QuizquestionActivity.TAG_OPTION_A));
        holder.rb2.setText(hashMap.get(QuizquestionActivity.TAG_OPTION_B));
        holder.rb3.setText(hashMap.get(QuizquestionActivity.TAG_OPTION_C));
        holder.rb4.setText(hashMap.get(QuizquestionActivity.TAG_OPTION_D));

        if (a.getSharedPreferences("Quiz", 0).getString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), "").equals("")) {
            holder.rb1.setBackgroundColor(Color.TRANSPARENT);
            holder.rb2.setBackgroundColor(Color.TRANSPARENT);
            holder.rb3.setBackgroundColor(Color.TRANSPARENT);
            holder.rb4.setBackgroundColor(Color.TRANSPARENT);
        } else {
            if (a.getSharedPreferences("Quiz", 0).getString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), "").equals(holder.rb1.getText().toString())) {
                if (holder.rb1.getText().toString().equals(hashMap.get(QuizquestionActivity.TAG_CORRECT_ANSWER))) {
                    holder.rb1.setBackgroundColor(Color.GREEN);
                    holder.rb2.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb3.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb4.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    holder.rb1.setBackgroundColor(Color.RED);
                    holder.rb2.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb3.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb4.setBackgroundColor(Color.TRANSPARENT);
                }
            } else if (a.getSharedPreferences("Quiz", 0).getString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), "").equals(holder.rb2.getText().toString())) {
                if (holder.rb2.getText().toString().equals(hashMap.get(QuizquestionActivity.TAG_CORRECT_ANSWER))) {
                    holder.rb1.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb2.setBackgroundColor(Color.GREEN);
                    holder.rb3.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb4.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    holder.rb1.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb2.setBackgroundColor(Color.RED);
                    holder.rb3.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb4.setBackgroundColor(Color.TRANSPARENT);
                }
            } else if (a.getSharedPreferences("Quiz", 0).getString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), "").equals(holder.rb3.getText().toString())) {
                if (holder.rb3.getText().toString().equals(hashMap.get(QuizquestionActivity.TAG_CORRECT_ANSWER))) {
                    holder.rb1.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb2.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb3.setBackgroundColor(Color.GREEN);
                    holder.rb4.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    holder.rb1.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb2.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb3.setBackgroundColor(Color.RED);
                    holder.rb4.setBackgroundColor(Color.TRANSPARENT);
                }
            } else if (a.getSharedPreferences("Quiz", 0).getString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), "").equals(holder.rb4.getText().toString())) {
                if (holder.rb4.getText().toString().equals(hashMap.get(QuizquestionActivity.TAG_CORRECT_ANSWER))) {
                    holder.rb1.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb2.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb3.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb4.setBackgroundColor(Color.GREEN);
                } else {
                    holder.rb1.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb2.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb3.setBackgroundColor(Color.TRANSPARENT);
                    holder.rb4.setBackgroundColor(Color.RED);
                }
            }
        }

        final ViewHolder holder1 = holder;
        holder.rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = a.getSharedPreferences("Quiz", 0);
                if (sharedPreferences.getString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), "").equals("")) {
                    if (holder1.rb1.getText().toString().equals(hashMap.get(QuizquestionActivity.TAG_CORRECT_ANSWER))) {
                        holder1.rb1.setBackgroundColor(Color.GREEN);
                    } else {
                        holder1.rb1.setBackgroundColor(Color.RED);
                    }
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), holder1.rb1.getText().toString());
                    e.commit();
                }
            }
        });
        holder.rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = a.getSharedPreferences("Quiz", 0);
                if (sharedPreferences.getString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), "").equals("")) {
                    if (holder1.rb2.getText().toString().equals(hashMap.get(QuizquestionActivity.TAG_CORRECT_ANSWER))) {
                        holder1.rb2.setBackgroundColor(Color.GREEN);
                    } else {
                        holder1.rb2.setBackgroundColor(Color.RED);
                    }

                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), holder1.rb2.getText().toString());
                    e.commit();
                }
            }
        });
        holder.rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = a.getSharedPreferences("Quiz", 0);
                if (sharedPreferences.getString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), "").equals("")) {
                    if (holder1.rb3.getText().toString().equals(hashMap.get(QuizquestionActivity.TAG_CORRECT_ANSWER))) {
                        holder1.rb3.setBackgroundColor(Color.GREEN);
                    } else {
                        holder1.rb3.setBackgroundColor(Color.RED);
                    }

                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), holder1.rb3.getText().toString());
                    e.commit();
                }
            }
        });
        holder.rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = a.getSharedPreferences("Quiz", 0);
                if (sharedPreferences.getString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), "").equals("")) {
                    if (holder1.rb4.getText().toString().equals(hashMap.get(QuizquestionActivity.TAG_CORRECT_ANSWER))) {
                        holder1.rb4.setBackgroundColor(Color.GREEN);
                    } else {
                        holder1.rb4.setBackgroundColor(Color.RED);
                    }
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putString(hashMap.get(QuizquestionActivity.TAG_QUIZZ_QUESTION_ID), holder1.rb4.getText().toString());
                    e.commit();
                }
            }
        });
        return convertView;
    }
}
