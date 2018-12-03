package de.munich.iteratec.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomAdapter extends ArrayAdapter<Question> {
    public CustomAdapter(@NonNull Context context, ArrayList<Question> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

            Question currentQuestion = getItem(position);

            TextView question = (TextView) convertView.findViewById(R.id.questionList);
            question.setText(currentQuestion.getQuestion());

            TextView answer1 = (TextView) convertView.findViewById(R.id.answer1List);
            TextView answer2 = (TextView) convertView.findViewById(R.id.answer2List);
            TextView answer3 = (TextView) convertView.findViewById(R.id.answer3List);
            TextView answer4 = (TextView) convertView.findViewById(R.id.answer4List);
            TextView answer5 = (TextView) convertView.findViewById(R.id.answer5List);
            TextView count1 = (TextView) convertView.findViewById(R.id.count1List);
            TextView count2 = (TextView) convertView.findViewById(R.id.count2List);
            TextView count3 = (TextView) convertView.findViewById(R.id.count3List);
            TextView count4 = (TextView) convertView.findViewById(R.id.count4List);
            TextView count5 = (TextView) convertView.findViewById(R.id.count5List);
            TextView questionType = (TextView) convertView.findViewById(R.id.questionType);

            String questionTypeText = currentQuestion.getQuestionType();

            switch (questionTypeText) {
                case "Antworten als Text":

                    answer1.setText(currentQuestion.getAnswer1());
                    answer2.setText(currentQuestion.getAnswer2());
                    answer3.setText(currentQuestion.getAnswer3());
                    answer4.setText(currentQuestion.getAnswer4());
                    count1.setText(String.valueOf(currentQuestion.getCount1()));
                    count2.setText(String.valueOf(currentQuestion.getCount2()));
                    count3.setText(String.valueOf(currentQuestion.getCount3()));
                    count4.setText(String.valueOf(currentQuestion.getCount4()));
                    questionType.setText(currentQuestion.getQuestionType());


                    break;

                case "Antworten als Icons":

                    answer1.setText(currentQuestion.getAnswer1());
                    answer2.setText(currentQuestion.getAnswer2());
                    answer3.setText(currentQuestion.getAnswer3());
                    answer4.setText(currentQuestion.getAnswer4());
                    count1.setText(String.valueOf(currentQuestion.getCount1()));
                    count2.setText(String.valueOf(currentQuestion.getCount2()));
                    count3.setText(String.valueOf(currentQuestion.getCount3()));
                    count4.setText(String.valueOf(currentQuestion.getCount4()));
                    questionType.setText(currentQuestion.getQuestionType());

                    break;

                case "Bewertung":

                    answer1.setText(currentQuestion.getRating1());
                    answer2.setText(currentQuestion.getRating2());
                    answer3.setText(currentQuestion.getRating3());
                    answer4.setText(currentQuestion.getRating4());
                    answer5.setText(currentQuestion.getRating5());
                    count1.setText(String.valueOf(currentQuestion.getCount1()));
                    count2.setText(String.valueOf(currentQuestion.getCount2()));
                    count3.setText(String.valueOf(currentQuestion.getCount3()));
                    count4.setText(String.valueOf(currentQuestion.getCount4()));
                    count5.setText(String.valueOf(currentQuestion.getCount5()));
                    questionType.setText(currentQuestion.getQuestionType());

                    break;

            }

            return convertView;
    }
}
