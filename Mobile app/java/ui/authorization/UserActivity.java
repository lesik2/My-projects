package ui.authorization;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.example.test.User;
import com.google.android.material.snackbar.Snackbar;

public class UserActivity extends AppCompatActivity {
    private String selectedTopic="";
    //подтверждение выхода
    private  int counter=1;
    private int id;
    private String getUserName;
    private DatabaseHelper databaseHelper;
    private TextView profileUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final LinearLayout executeTest=findViewById(R.id.executeTest);
        final LinearLayout results=findViewById(R.id.results);
        final LinearLayout settings=findViewById(R.id.settings);
        final Button  performBtn=findViewById(R.id.performBtm);
        final ImageView button_back=findViewById(R.id.button_back);


        profileUser=findViewById(R.id.ProfileUser);
        databaseHelper=new DatabaseHelper(this);


            getUserName=getIntent().getStringExtra("name");
            id=getIntent().getIntExtra("id",0);
            getUserName=getResources().getString(R.string.User)+getUserName;
            profileUser.setText(getUserName);

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTopic="results";
                results.setBackgroundResource(R.drawable.round_back_white_stroke10);
                executeTest.setBackgroundResource(R.drawable.round_back_white10);
                settings.setBackgroundResource(R.drawable.round_back_white10);
                performBtn.setText("Начать");
            }
        });
        executeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTopic="create";
                executeTest.setBackgroundResource(R.drawable.round_back_white_stroke10);
                results.setBackgroundResource(R.drawable.round_back_white10);
                settings.setBackgroundResource(R.drawable.round_back_white10);
                performBtn.setText("Начать");


            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTopic="settings";
                settings.setBackgroundResource(R.drawable.round_back_white_stroke10);
                results.setBackgroundResource(R.drawable.round_back_white10);
                executeTest.setBackgroundResource(R.drawable.round_back_white10);
                performBtn.setText("Начать");


            }
        });

        performBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTopic.isEmpty())
                {
                    Toast.makeText(UserActivity.this,"Выберите действие!!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = null;
                    if(selectedTopic.equals("create"))
                    {
                        intent=new Intent(UserActivity.this, Choose_testActivity.class);
                        intent.putExtra("userId",id);
                        startActivity(intent);
                    }
                    if(selectedTopic.equals("results"))
                    {
                        intent=new Intent(UserActivity.this, ShowResultsActivity.class);
                        intent.putExtra("userId",id);
                        startActivity(intent);
                    }
                    if(selectedTopic.equals("settings"))
                    {
                        changeInformationWindow();
                    }

                }

            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(counter==2) {

                    startActivity(new Intent(UserActivity.this, MainActivity.class));
                    finish();
                }
                else
                {
                    Toast toast =Toast.makeText(UserActivity.this,"Вы уверены,что хотите выйти",Toast.LENGTH_SHORT);
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
            startActivity(new Intent(UserActivity.this, MainActivity.class));
            finish();
        }
        else
        {
            Toast toast =Toast.makeText(UserActivity.this,"Вы уверены,что хотите выйти",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0,160);
            toast.show();
            counter++;
        }
    }
    private void changeInformationWindow() {
        //создание диалогового окна
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Редактирование личной инфориации(пароль должен иметь не меньше 5 символов)");
        LayoutInflater inflater = LayoutInflater.from(this);
        View registerWindow = inflater.inflate(R.layout.register_window, null);
        dialog.setView(registerWindow);


        final EditText login = (EditText) registerWindow.findViewById(R.id.loginEditText);
        final EditText password = (EditText) registerWindow.findViewById(R.id.passwordEditText);
        final EditText name = (EditText) registerWindow.findViewById(R.id.nameEditText);
        final EditText surname = (EditText) registerWindow.findViewById(R.id.surnameEditText);
        User user=databaseHelper.getUser(id);
        //установление текущих данных
        login.setText(user.getLogin());
        password.setText(user.getPassword());
        name.setText(user.getName());
        surname.setText(user.getSurname());


        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Сохранить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //переопределение setPositiveButton
        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean wantToCloseDialog = false;
                int flag=0;
                if (TextUtils.isEmpty((login.getText().toString()))) {
                    Snackbar.make(registerWindow, "Введите логин", Snackbar.LENGTH_SHORT).show();
                    flag=1;
                }

                if (TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(registerWindow, "Введите имя", Snackbar.LENGTH_SHORT).show();
                    flag=1;

                }
                if (TextUtils.isEmpty(surname.getText().toString())) {
                    Snackbar.make(registerWindow, "Введите фамилию", Snackbar.LENGTH_SHORT).show();
                    flag=1;

                }
                if (password.getText().toString().length() < 5) {
                    Snackbar.make(registerWindow, "Пароль должен содержать не меньше 5 символов", Snackbar.LENGTH_SHORT).show();
                    flag=1;
                }
                if(!isLoginUnique(login.getText().toString())&&flag==0) {
                    Snackbar.make(registerWindow, "Логин должен быть уникальным", Snackbar.LENGTH_SHORT).show();
                    flag=1;
                }
                if(flag==0)
                {
                    wantToCloseDialog=true;
                }
                //если данные правильные,то закрываем окно и перезаписываем их в бд
                if(wantToCloseDialog) {
                    User newUser = new User(0, login.getText().toString(), password.getText().toString(), name.getText().toString(), surname.getText().toString());
                    databaseHelper.updateUser(newUser,id);
                    profileUser.setText(getResources().getString(R.string.User) + newUser.getName() + " " + newUser.getSurname());
                    alertDialog.dismiss();
                }

            }
        });
    }
    //проверка на уникальность логина
    boolean isLoginUnique(String inputLogin)
    {
        for(User empty:databaseHelper.getUsers())
        {
            if(empty.getId()==id)
            {
                continue;
            }
            if(empty.getLogin().equals(inputLogin))
            {
                return false;
            }
        }
        return true;
    }
    }

