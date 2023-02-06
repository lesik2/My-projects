    package ui.authorization;
    
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.AppCompatButton;
    
    import android.content.Intent;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.view.Gravity;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;
    
    import com.example.test.DatabaseHelper;
    import com.example.test.R;
    import com.example.test.Test;
    
    import java.util.Timer;
    import java.util.TimerTask;
    
    public class ExecuteTestActivity extends AppCompatActivity {
        private DatabaseHelper databaseHelper;
        //количество вопросов
        private TextView questions;
        //вопрос
        private TextView question;
        private AppCompatButton option1,option2,option3,option4;
        private  AppCompatButton nextBtm;
        private Timer quizTimer;
        //время на выполнение теста в минутах
        private int totalTimeInMinute;

        private int userId;
        private int seconds=0;
        private  int counter=1;
        private int currentQuestionPosition=0;
        private String selectedOptionByUser="";
        private Test chosenTest;
        private String results;
        //индикатор завершения теста
        private int indicator=0;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_execute_test);
    
            databaseHelper=new DatabaseHelper(this);
            //получение id пользователя
            userId=getIntent().getIntExtra("userId",0);
            //получение id теста
            final int testId=getIntent().getIntExtra("id",0);

            final ImageView backBtm=findViewById(R.id.buttonBack);
            final TextView timer=findViewById(R.id.timer);
            final TextView topicName=findViewById(R.id.topicName);
    
    
            questions=findViewById(R.id.questions);
            question=findViewById(R.id.question);
            option1=findViewById(R.id.option1);
            option2=findViewById(R.id.option2);
            option3=findViewById(R.id.option3);
            option4=findViewById(R.id.option4);
            nextBtm=findViewById(R.id.nextBtm);
            //получение теста из бд
            chosenTest=databaseHelper.getTest(testId);
            topicName.setText(chosenTest.getName());
            totalTimeInMinute= Integer.parseInt(chosenTest.getTimer());
            //запуск таймера
            startTimer(timer);
    
            questions.setText((currentQuestionPosition+1)+"/"+chosenTest.getSize());
            changeTextInQuestions(currentQuestionPosition);
    
            backBtm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
    
                    if(counter==2) {
                        quizTimer.purge();
                        quizTimer.cancel();
                        Intent intent=new Intent(ExecuteTestActivity.this,UserActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast toast =Toast.makeText(ExecuteTestActivity.this,"Вы уверены,что хотите выйти",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP, 0,160);
                        toast.show();
                        counter++;
                    }
                }
            });
            option1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedOptionByUser.isEmpty()){
                        selectedOptionByUser=option1.getText().toString();
                        option1.setBackgroundResource(R.drawable.round_back_red10);
                        option1.setTextColor(Color.WHITE);
                        revealAnswer();
                        chosenTest.getQuestionsList().get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
    
                    }
    
                }
            });
    
    
            option2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedOptionByUser.isEmpty()){
                        selectedOptionByUser=option2.getText().toString();
                        option2.setBackgroundResource(R.drawable.round_back_red10);
                        option2.setTextColor(Color.WHITE);
                        revealAnswer();
                        chosenTest.getQuestionsList().get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
    
                    }
    
                }
            });
    
    
    
            option3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedOptionByUser.isEmpty()){
                        selectedOptionByUser=option3.getText().toString();
                        option3.setBackgroundResource(R.drawable.round_back_red10);
                        option3.setTextColor(Color.WHITE);
                        revealAnswer();
                        chosenTest.getQuestionsList().get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
    
                    }
    
                }
            });
            option4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedOptionByUser.isEmpty()){
                        selectedOptionByUser=option4.getText().toString();
                        option4.setBackgroundResource(R.drawable.round_back_red10);
                        option4.setTextColor(Color.WHITE);
                        revealAnswer();
                        chosenTest.getQuestionsList().get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
    
                    }
                }
            });
            nextBtm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedOptionByUser.isEmpty()){
                        Toast.makeText(ExecuteTestActivity.this,"Сделайте выбор",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        changeNextQuestion();
                    }
                }
            });
    
        }
        //запуск таймера
        private void startTimer(TextView timerTextView)
        {
            quizTimer=new Timer();
            quizTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(seconds==0&&totalTimeInMinute==0&& indicator==0)
                    {
                        //удаляет прерванные задания из очереди таймера
                        quizTimer.purge();
                        //прерывает поток таймера
                        quizTimer.cancel();
                        Intent intent  = new Intent(ExecuteTestActivity.this, TestResultsActivity.class);
                        intent.putExtra("correct",getCorrectAnswers());
                        intent.putExtra("size",chosenTest.getSize());
                        intent.putExtra("run_out_of_time",1);
                        results=getCorrectAnswers()+"/"+chosenTest.getSize();
                        databaseHelper.setResult(userId,results,chosenTest.getId());
                        startActivity(intent);
                        finish();
                    }
                    else if(seconds==0){
                        totalTimeInMinute--;
                        seconds=59;
                    }
                    else
                    {
                        seconds--;
                    }
                    //Ui поток
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String finalMinutes= String.valueOf(totalTimeInMinute);
                            String finalSeconds=String.valueOf(seconds);
                            if(finalMinutes.length()==1)
                            {
                                finalMinutes="0"+finalMinutes;
    
                            }
                            if(finalSeconds.length()==1)
                            {
                                finalSeconds="0"+finalSeconds;
                            }
                            timerTextView.setText(finalMinutes+":"+finalSeconds);
                        }
                    });
    
    
                }
            },500,1000);
        }
        private int getCorrectAnswers() {
    
            int correctAnswers=0;
            for(int i=0;i<chosenTest.getSize();i++){
                final String getUserSelectedAnswer=chosenTest.getQuestionsList().get(i).getUserSelectedAnswer();
                final String getAnswer=chosenTest.getQuestionsList().get(i).getAnswer();
                if(getUserSelectedAnswer.equals(getAnswer))
                {
                    correctAnswers++;
                }
            }
    
    
            return correctAnswers;
        }
        @Override
        public void onBackPressed() {

            if (counter == 2) {
                quizTimer.purge();
                quizTimer.cancel();
                Intent intent = new Intent(ExecuteTestActivity.this, UserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else {
                Toast toast = Toast.makeText(ExecuteTestActivity.this, "Вы уверены,что хотите выйти", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 160);
                toast.show();
                counter++;
            }

        }
        //отображение првильного ответа
        private void revealAnswer(){
            final String getAnswer =chosenTest.getQuestionsList().get(currentQuestionPosition).getAnswer();
            if(option1.getText().toString().equals(getAnswer)){
                option1.setBackgroundResource(R.drawable.round_back_green10);
                option1.setTextColor(Color.WHITE);
    
            }
            else if (option2.getText().toString().equals(getAnswer)){
                option2.setBackgroundResource(R.drawable.round_back_green10);
                option2.setTextColor(Color.WHITE);
            }
            else if (option3.getText().toString().equals(getAnswer)){
                option3.setBackgroundResource(R.drawable.round_back_green10);
                option3.setTextColor(Color.WHITE);
            }
            else if (option4.getText().toString().equals(getAnswer)){
                option4.setBackgroundResource(R.drawable.round_back_green10);
                option4.setTextColor(Color.WHITE);
            }
        }
    
        private void changeNextQuestion(){
            currentQuestionPosition++;
            if((currentQuestionPosition+1)==chosenTest.getSize())
            {
                nextBtm.setText("Готово");
            }
            if(currentQuestionPosition<chosenTest.getSize())
            {
                selectedOptionByUser="";
                turnBackBackground();
                questions.setText((currentQuestionPosition+1)+"/"+chosenTest.getSize());
                changeTextInQuestions(currentQuestionPosition);
            }
            else
            {
                indicator=1;
                Intent intent=new Intent(ExecuteTestActivity.this, TestResultsActivity.class);
                intent.putExtra("correct",getCorrectAnswers());
                intent.putExtra("size",chosenTest.getSize());
                results=getCorrectAnswers()+"/"+chosenTest.getSize();
                //запись результата в бд
                databaseHelper.setResult(userId,results,chosenTest.getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        }
        private void changeTextInQuestions(int currentQuestionPosition)
        {
            question.setText(chosenTest.getQuestionsList().get(currentQuestionPosition).getQuestion());
            option1.setText(chosenTest.getQuestionsList().get(currentQuestionPosition).getOption1());
            option2.setText(chosenTest.getQuestionsList().get(currentQuestionPosition).getOption2());
            option3.setText(chosenTest.getQuestionsList().get(currentQuestionPosition).getOption3());
            option4.setText(chosenTest.getQuestionsList().get(currentQuestionPosition).getOption4());
        }
        private void turnBackBackground()
        {
            option1.setBackgroundResource(R.drawable.round_white_back_stroke2_10);
            option1.setTextColor(getResources().getColor(R.color.black));
            option2.setBackgroundResource(R.drawable.round_white_back_stroke2_10);
            option2.setTextColor(getResources().getColor(R.color.black));
            option3.setBackgroundResource(R.drawable.round_white_back_stroke2_10);
            option3.setTextColor(getResources().getColor(R.color.black));
            option4.setBackgroundResource(R.drawable.round_white_back_stroke2_10);
            option4.setTextColor(getResources().getColor(R.color.black));
        }
    }