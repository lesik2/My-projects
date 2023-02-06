package ui.authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.R;


public class AdminActivity extends AppCompatActivity {
    private String selectedTopic="";
    //подтверждение выхода
    private  int counter=1;
    private String getAdminName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //действия
        final LinearLayout createTest=findViewById(R.id.createTest);
        final LinearLayout usersResults=findViewById(R.id.UsersResults);
        final LinearLayout deleteTest=findViewById(R.id.delete_test);
        //поля для отображения ФИО админа
        final TextView profileAdmin=findViewById(R.id.ProfileAdmin);
        //кнопка выхода
        final ImageView buttonBack=findViewById(R.id.button_back);
        //кнопка для начала действия
        final Button  performBtn=findViewById(R.id.performBtm);

//получение ФИО администратора
       getAdminName=getIntent().getStringExtra("name");

        getAdminName=getResources().getString(R.string.Admin)+getAdminName;
        profileAdmin.setText(getAdminName);


        createTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTopic="create";
                createTest.setBackgroundResource(R.drawable.round_back_white_stroke10);
                usersResults.setBackgroundResource(R.drawable.round_back_white10);
                deleteTest.setBackgroundResource(R.drawable.round_back_white10);
                performBtn.setText("Начать");


            }
        });
        usersResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTopic="results";
                usersResults.setBackgroundResource(R.drawable.round_back_white_stroke10);
                createTest.setBackgroundResource(R.drawable.round_back_white10);
                deleteTest.setBackgroundResource(R.drawable.round_back_white10);
                performBtn.setText("Начать");
            }
        });
        deleteTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTopic="delete";
                deleteTest.setBackgroundResource(R.drawable.round_back_white_stroke10);
                usersResults.setBackgroundResource(R.drawable.round_back_white10);
                createTest.setBackgroundResource(R.drawable.round_back_white10);
                performBtn.setText("Начать");
            }
        });


        performBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTopic.isEmpty())
                {
                    Toast.makeText(AdminActivity.this,"Выберите действие!!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(selectedTopic.equals("create"))
                    {
                        Intent intent=new Intent(AdminActivity.this, CreateTestActivity.class);
                        startActivity(intent);
                    }
                    else if(selectedTopic.equals("delete"))
                    {
                        Intent intent=new Intent(AdminActivity.this, DeleteTestActivity.class);
                        startActivity(intent);
                    }
                    else if(selectedTopic.equals("results"))
                    {
                        Intent intent=new Intent(AdminActivity.this, ShowResultsActivity.class);
                        startActivity(intent);
                    }


                }

            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(counter==2) {

                    startActivity(new Intent(AdminActivity.this, MainActivity.class));
                    finish();
                }
                else
                {
                    Toast toast =Toast.makeText(AdminActivity.this,"Вы уверены,что хотите выйти",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0,160);
                    toast.show();
                    counter++;
                }
            }
        });

    }
    @Override
    public void onBackPressed() {

        if (counter == 2) {
            startActivity(new Intent(AdminActivity.this, MainActivity.class));
            finish();
        } else {
            Toast toast = Toast.makeText(AdminActivity.this, "Вы уверены,что хотите выйти", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 160);
            toast.show();
            counter++;
        }
    }
    }

