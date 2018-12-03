package de.munich.iteratec.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private DatabaseReference database;
    private ListView listView;
    private BarChart barChart;
    private BarDataSet questionSet;
    private BarData questionData;

    TextView questionChart;

    private TextView answer1Legend, answer2Legend, answer3Legend, answer4Legend, answer5Legend;
    private LinearLayout legend1, legend2, legend3, legend4, legend5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container,false);

        questionChart = (TextView) view.findViewById(R.id.questionChart);

        answer1Legend = (TextView) view.findViewById(R.id.answer1Legend);
        answer2Legend = (TextView) view.findViewById(R.id.answer2Legend);
        answer3Legend = (TextView) view.findViewById(R.id.answer3Legend);
        answer4Legend = (TextView) view.findViewById(R.id.answer4Legend);
        answer5Legend = (TextView) view.findViewById(R.id.answer5Legend);

        legend1 = (LinearLayout) view.findViewById(R.id.legend1);
        legend2 = (LinearLayout) view.findViewById(R.id.legend2);
        legend3 = (LinearLayout) view.findViewById(R.id.legend3);
        legend4 = (LinearLayout) view.findViewById(R.id.legend4);
        legend5 = (LinearLayout) view.findViewById(R.id.legend5);



        final ArrayList<Question> questions = new ArrayList<>();

        //initialize BarChart and settings
        barChart = (BarChart) view.findViewById(R.id.barchart);
        barChart.setFitBars(true);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.setNoDataText("Momentan keine Frage ausgewählt");
        barChart.setNoDataTextColor(android.R.color.darker_gray);

        Description description = barChart.getDescription();
        description.setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);


        //initialize Axis and settings
        final XAxis xaxis = barChart.getXAxis();
        xaxis.setDrawGridLines(false);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setGranularity(1f);
        xaxis.setDrawLabels(true);
        xaxis.setAxisMinimum(0);

        YAxis yaxisLeft = barChart.getAxisLeft();
        yaxisLeft.setDrawGridLines(false);
        yaxisLeft.setAxisMinimum(0);

        YAxis yaxisRight = barChart.getAxisRight();
        yaxisRight.setEnabled(false);


        //initialize ArrayLists for BarEntries, XAxis values
        final ArrayList<BarEntry> values = new ArrayList<>();
        final ArrayList<String> xValues = new ArrayList<>();
        final ArrayList<LegendEntry> legendEntries = new ArrayList<>();
        final int[] colors = new int[]{Color.rgb(97, 192, 236), Color.rgb(88, 132, 249),
                Color.rgb(146, 88, 249), Color.rgb(239, 91, 223), Color.rgb(239, 91, 156)};


        //initiate ListView, create ArrayList and ArrayAdapter, set ArrayAdapter to ListView, set OnCLickListener
        listView = view.findViewById(R.id.listView);
        final CustomAdapter adapter = new CustomAdapter(getActivity().getApplicationContext(), questions);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                questionChart.setText(((TextView) view.findViewById(R.id.questionList)).getText().toString());

                Log.i("Answer1 Textview", ((TextView) view.findViewById(R.id.answer1List)).getText().toString());
                Log.i("Count1 Textview", ((TextView) view.findViewById(R.id.count1List)).getText().toString());

                //get counts and answers out of invisible TextViews from from ListView
                String count1 = ((TextView) view.findViewById(R.id.count1List)).getText().toString();
                float i1 = Float.parseFloat(count1);

                String count2 = ((TextView) view.findViewById(R.id.count2List)).getText().toString();
                float i2 = Float.parseFloat(count2);

                String count3 = ((TextView) view.findViewById(R.id.count3List)).getText().toString();
                float i3 = Float.parseFloat(count3);

                String count4 = ((TextView) view.findViewById(R.id.count4List)).getText().toString();
                float i4 = Float.parseFloat(count4);

                String answer1 = ((TextView) view.findViewById(R.id.answer1List)).getText().toString();
                String answer2 = ((TextView) view.findViewById(R.id.answer2List)).getText().toString();
                String answer3 = ((TextView) view.findViewById(R.id.answer3List)).getText().toString();
                String answer4 = ((TextView) view.findViewById(R.id.answer4List)).getText().toString();

                //get QuestionType for if condition
                String questionType = ((TextView) view.findViewById(R.id.questionType)).getText().toString();
                Log.i("Questiotype", questionType);

                //setup BarChart with right values
                if (questionType.equals("Antworten als Text") || questionType.equals("Antworten als Icons")) {
                    values.clear();
                    xValues.clear();
                    values.add(new BarEntry(1, i1));
                    values.add(new BarEntry(2, i2));
                    values.add(new BarEntry(3, i3));
                    values.add(new BarEntry(4, i4));

                    xValues.add("");
                    xValues.add(answer1);
                    xValues.add(answer2);
                    xValues.add(answer3);
                    xValues.add(answer4);
                    xaxis.setValueFormatter(new IndexAxisValueFormatter(xValues));

                    answer1Legend.setText(answer1);
                    legend1.setVisibility(View.VISIBLE);
                    answer2Legend.setText(answer2);
                    legend2.setVisibility(View.VISIBLE);
                    answer3Legend.setText(answer3);
                    legend3.setVisibility(View.VISIBLE);
                    answer4Legend.setText(answer4);
                    legend4.setVisibility(View.VISIBLE);
                    legend5.setVisibility(View.INVISIBLE);


                } else if (questionType.equals("Bewertung")) {

                    Log.i("Count 1 Bewertung", ((TextView) view.findViewById(R.id.count1List)).getText().toString());
                    String count5 = ((TextView) view.findViewById(R.id.count5List)).getText().toString();
                    float i5 = Float.parseFloat(count5);
                    String answer5 = ((TextView) view.findViewById(R.id.answer5List)).getText().toString();

                    values.clear();
                    xValues.clear();
                    values.add(new BarEntry(1, i1));
                    values.add(new BarEntry(2, i2));
                    values.add(new BarEntry(3, i3));
                    values.add(new BarEntry(4, i4));
                    values.add(new BarEntry(5, i5));

                    xValues.add("");
                    xValues.add(answer1);
                    xValues.add(answer2);
                    xValues.add(answer3);
                    xValues.add(answer4);
                    xValues.add(answer5);
                    xaxis.setValueFormatter(new IndexAxisValueFormatter(xValues));

                    answer1Legend.setText(answer1);
                    legend1.setVisibility(View.VISIBLE);
                    answer2Legend.setText(answer2);
                    legend2.setVisibility(View.VISIBLE);
                    answer3Legend.setText(answer3);
                    legend3.setVisibility(View.VISIBLE);
                    answer4Legend.setText(answer4);
                    legend4.setVisibility(View.VISIBLE);
                    answer5Legend.setText(answer5);
                    legend5.setVisibility(View.VISIBLE);

                }

                //setup BarDataSet, BarData, set it to BarChart
                barChart.animateY(2000, Easing.EaseOutBounce);
                questionSet = new BarDataSet(values, "Values");
                questionData = new BarData(questionSet);
                questionSet.setColors(ColorTemplate.COLORFUL_COLORS);
                barChart.setData(questionData);


                //update BarChart
                questionSet.notifyDataSetChanged();
                questionSet.setColors(colors);
                questionData.notifyDataChanged();
                barChart.invalidate();
            }
        });


        //initiate Database and set ChildEventListener to retrieve questions
        database = FirebaseDatabase.getInstance().getReference();
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //add questions to ListView
                String questionLayout = dataSnapshot.child("Fragentyp").getValue(String.class);
                Log.i("Integer", String.valueOf(dataSnapshot.child("Antwort 1 Clicks").getValue(Integer.class)));

                if(questionLayout != null && questionLayout.equals("Antworten als Text")){
                    questions.add(new Question(dataSnapshot.getKey().toString(), dataSnapshot.child("Fragentyp").getValue(String.class),
                            dataSnapshot.child("Antwort 1").getValue(String.class), dataSnapshot.child("Antwort 1 Clicks").getValue(Long.class),
                            dataSnapshot.child("Antwort 2").getValue(String.class), dataSnapshot.child("Antwort 2 Clicks").getValue(Long.class),
                            dataSnapshot.child("Antwort 3").getValue(String.class), dataSnapshot.child("Antwort 3 Clicks").getValue(Long.class),
                            dataSnapshot.child("Antwort 4").getValue(String.class), dataSnapshot.child("Antwort 4 Clicks").getValue(Long.class)));
                } else if (questionLayout != null && questionLayout.equals("Antworten als Icons")){
                    questions.add(new Question(dataSnapshot.getKey().toString(), dataSnapshot.child("Fragentyp").getValue(String.class),
                            dataSnapshot.child("Beschreibung 1").getValue(String.class), dataSnapshot.child("Antwort 1 Clicks").getValue(Long.class),
                            dataSnapshot.child("Beschreibung 2").getValue(String.class), dataSnapshot.child("Antwort 2 Clicks").getValue(Long.class),
                            dataSnapshot.child("Beschreibung 3").getValue(String.class), dataSnapshot.child("Antwort 3 Clicks").getValue(Long.class),
                            dataSnapshot.child("Beschreibung 4").getValue(String.class), dataSnapshot.child("Antwort 4 Clicks").getValue(Long.class)));
                } else if (questionLayout != null && questionLayout.equals("Bewertung")){
                    questions.add(new Question(dataSnapshot.getKey().toString(), dataSnapshot.child("Fragentyp").getValue(String.class),
                            dataSnapshot.child("Rating 1").getValue(String.class), dataSnapshot.child("Rating 1 Clicks").getValue(Long.class),
                            dataSnapshot.child("Rating 2").getValue(String.class), dataSnapshot.child("Rating 2 Clicks").getValue(Long.class),
                            dataSnapshot.child("Rating 3").getValue(String.class), dataSnapshot.child("Rating 3 Clicks").getValue(Long.class),
                            dataSnapshot.child("Rating 4").getValue(String.class), dataSnapshot.child("Rating 4 Clicks").getValue(Long.class),
                            dataSnapshot.child("Rating 5").getValue(String.class), dataSnapshot.child("Rating 5 Clicks").getValue(Long.class)));
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //update Click count
                Iterator<Question> iterator = questions.iterator();
                while (iterator.hasNext()) {
                    Question question = iterator.next();
                    if (question.getQuestion().equals(dataSnapshot.getKey().toString()) && (question.getQuestionType().equals("Antworten als Text") || question.getQuestionType().equals("Antworten als Icons"))) {
                        question.setCount1(dataSnapshot.child("Antwort 1 Clicks").getValue(Long.class));
                        question.setCount2(dataSnapshot.child("Antwort 2 Clicks").getValue(Long.class));
                        question.setCount3(dataSnapshot.child("Antwort 3 Clicks").getValue(Long.class));
                        question.setCount4(dataSnapshot.child("Antwort 4 Clicks").getValue(Long.class));
                        //Log.i("Count 1 Text und Icons", question.getCount1().toString());
                        break;
                    } else if (question.getQuestion().equals(dataSnapshot.getKey().toString()) && question.getQuestionType().equals("Bewertung")) {
                        question.setCount1(dataSnapshot.child("Rating 1 Clicks").getValue(Long.class));
                        question.setCount2(dataSnapshot.child("Rating 2 Clicks").getValue(Long.class));
                        question.setCount3(dataSnapshot.child("Rating 3 Clicks").getValue(Long.class));
                        question.setCount4(dataSnapshot.child("Rating 4 Clicks").getValue(Long.class));
                        question.setCount5(dataSnapshot.child("Rating 5 Clicks").getValue(Long.class));
                        //Log.i("Count 1 Bewertung", question.getCount1().toString());
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //remove ListItems when DataSnapshot is removed in Database
                Iterator<Question> iterator = questions.iterator();
                while (iterator.hasNext()) {
                    Question question = iterator.next();
                    if (question.getQuestion().equals(dataSnapshot.getKey().toString())) {
                        iterator.remove();
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
}