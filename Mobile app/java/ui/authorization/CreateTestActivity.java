package ui.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.DatabaseHelper;
import com.example.test.QuestionList;
import com.example.test.R;
import com.example.test.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateTestActivity extends AppCompatActivity {
    private List<QuestionList> questionsList;
    private Test test;
    private  int counter=1;
    private int currentQuestionPosition=0;
    //количество вопросов в тесте
    private int test_size;
    private AppCompatButton nextBtm;
    private EditText question,option1,option2,option3,option4,correctAnswer,topicName,timer;
    private TextView questions;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);


        //кнопка выхода
        final ImageView backBtm=findViewById(R.id.buttonBack);
        //количество вопросов в тесте
        final EditText numberOfQuestions=findViewById(R.id.NumberOfQuestions);

        questionsList=new ArrayList<>();
        databaseHelper=new DatabaseHelper(this);
        //название теста
         topicName=findViewById(R.id.topicName);
         //время выделенное на выполнение теста
         timer=findViewById(R.id.timer);
         //текущий вопрос
         questions=findViewById(R.id.questions);

         question=findViewById(R.id.question);
         option1=findViewById(R.id.option1);
         option2=findViewById(R.id.option2);
         option3=findViewById(R.id.option3);
         option4=findViewById(R.id.option4);
         correctAnswer=findViewById(R.id.CorrectAnswer);
         nextBtm=findViewById(R.id.nextBtm);


//установление слушателя
        numberOfQuestions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!numberOfQuestions.getText().toString().equals(""))
                {
                    test_size= Integer.parseInt(numberOfQuestions.getText().toString());
                    questions.setText((currentQuestionPosition+1)+"/"+ test_size);
                    //если количество вопросов в тесте 1,изменяет текст кнопки на сохранить
                    if((currentQuestionPosition+1)==test_size)
                    {
                        nextBtm.setText("Сохранить");
                    }

                }
                else
                {
                    questions.setText((currentQuestionPosition+1)+"/"+ "?");
                }

            }
        });

        nextBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // проверка на заполненность всех полей
                if(question.getText().toString().equals("") || option1.getText().toString().equals("") || option2.getText().toString().equals("") || option3.getText().toString().equals("") ||  option4.getText().toString().equals("") ||  correctAnswer.getText().toString().equals("")) {

                    Toast.makeText(CreateTestActivity.this,"Не все поля заполнены",Toast.LENGTH_SHORT).show();
                }
                //проверка на уникальность вариантов ответов
                else if(isQniqueAnswers(option1.getText().toString(),option2.getText().toString(),option3.getText().toString(),option4.getText().toString()))
                {
                    Toast.makeText(CreateTestActivity.this,"Ответы должны быть уникальными",Toast.LENGTH_SHORT).show();
                }
                //проверка ввода правильного ответа
                else if(isCorrectAnswerRight(option1.getText().toString(),option2.getText().toString(),option3.getText().toString(),option4.getText().toString(),correctAnswer.getText().toString()))
                {
                    Toast.makeText(CreateTestActivity.this,"Правильный ответ должен являться одним из предложенных вариантов",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    QuestionList questionList=new QuestionList(question.getText().toString(),option1.getText().toString(),option2.getText().toString(),option3.getText().toString(),option4.getText().toString(),correctAnswer.getText().toString(),"");
                    questionsList.add(questionList);
                    if(test_size!=0)
                    {
                        changeNextQuestion();
                    }
                    else
                    {
                        Toast.makeText(CreateTestActivity.this,"Введите количество вопросов в тесте!!!",Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });



        backBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(counter==2) {

                    Intent intent=new Intent(CreateTestActivity.this,AdminActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast toast =Toast.makeText(CreateTestActivity.this,"Вы уверены,что хотите выйти",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0,160);
                    toast.show();
                    counter++;
                }
            }
        });


    }
    @Override
    public void onBackPressed() {

        if(counter==2) {
            Intent intent=new Intent(CreateTestActivity.this,AdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast toast =Toast.makeText(CreateTestActivity.this,"Вы уверены,что хотите выйти",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0,160);
            toast.show();
            counter++;

        }

    }
    private void changeNextQuestion(){


        currentQuestionPosition++;
        //если вопрос последний
        if((currentQuestionPosition+1)==test_size)
        {

            nextBtm.setText("Сохранить");

        }
        //возобновление формы для заполнения
        if(currentQuestionPosition<test_size)
        {
            questions.setText((currentQuestionPosition+1)+"/"+test_size);
            question.setText("");
            question.setHint("Вопрос");
            option1.setText("");
            option1.setHint("Ответ 1");
            option2.setText("");
            option2.setHint("Ответ 2");
            option3.setText("");
            option3.setHint("Ответ 3");
            option4.setText("");
            option4.setHint("Ответ 4");
            correctAnswer.setText("");
            correctAnswer.setHint("Правильный ответ");

        }
        else
        {
            //проверка на наличие названия теста и таймера
            if(topicName.getText().toString().equals("")||timer.getText().toString().equals(""))
            {

                Toast.makeText(CreateTestActivity.this,"Не все поля заполнены",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //получение количества тестов в бд
                int id_current_test=databaseHelper.getNotes();

                test=new Test(topicName.getText().toString(),timer.getText().toString(),test_size,questionsList,id_current_test+1);
                //запись теста в бд
                databaseHelper.createTest(test);
                Toast.makeText(CreateTestActivity.this,"Тест создан!!!",Toast.LENGTH_LONG).show();
                //переход к меню администратора
                Intent intent=new Intent(CreateTestActivity.this,AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();

            }

        }

    }
    //проверка на уникальность вариантов ответа
    boolean isQniqueAnswers(String opt1,String opt2,String opt3,String opt4)
    {
        //создание множества(уникальные элементы)
        Set<String> set=new HashSet<>();
        set.add(opt1);
        set.add(opt2);
        set.add(opt3);
        set.add(opt4);
        if(set.size()==4)
        {
            return false;
        }
        return true;

    }

    boolean isCorrectAnswerRight(String opt1,String opt2,String opt3,String opt4,String CorrectAnswer)
    {
        if(CorrectAnswer.equals(opt1)||CorrectAnswer.equals(opt2)||CorrectAnswer.equals(opt3)||CorrectAnswer.equals(opt4))return false;
        return true;

    }

}