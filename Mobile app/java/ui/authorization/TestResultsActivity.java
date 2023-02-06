package ui.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.CorrectionInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;

import org.w3c.dom.Text;

public class TestResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);

        final AppCompatButton finishTest=findViewById(R.id.completeTest);
        final TextView correctAnswers=findViewById(R.id.test_result);
        final TextView AmountOfQuestions=findViewById(R.id.test_amount_questions);
        final int getCorrectAnswers=getIntent().getIntExtra("correct",0);
        final int getAmountOfQuestions=getIntent().getIntExtra("size",0);
        final int runOutOfTime=getIntent().getIntExtra("run_out_of_time",0);
        //Время теста вышло
        if(runOutOfTime==1)
        {
            Toast toast =Toast.makeText(TestResultsActivity.this,"Время вышло",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0,160);
            toast.show();
        }
        if(getCorrectAnswers<=getAmountOfQuestions/2)
        {
            correctAnswers.setTextColor(getResources().getColor(R.color.red));

        }
        else
        {
            correctAnswers.setTextColor(getResources().getColor(R.color.lime));

        }
        correctAnswers.setText(""+getCorrectAnswers);
        AmountOfQuestions.setText("/"+getAmountOfQuestions);


        finishTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TestResultsActivity.this,UserActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(TestResultsActivity.this,UserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}