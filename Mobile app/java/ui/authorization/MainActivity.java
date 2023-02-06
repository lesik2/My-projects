package ui.authorization;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.example.test.User;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mainRelative;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper=new DatabaseHelper(this);
        mainRelative=findViewById(R.id.mainRelative);
        //кнопка для регистрации
        final Button  registerBtn=findViewById(R.id.RegisterBtn);
        //кнопка для входа
        final Button entryBtn=findViewById(R.id.entryBtn);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showRegisterWindow();
            }
        });
        entryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showEntryWindow();
            }
        });
    }

//диалоговое окно для входа в аккаунт
    private void showEntryWindow() {

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Заполните все данные для входа");
        LayoutInflater inflater=LayoutInflater.from(this);
        View sign_in_window=inflater.inflate(R.layout.entry_window,null);
        dialog.setView(sign_in_window);

        final EditText passwordEntry=(EditText) sign_in_window.findViewById(R.id.passwordEditTextEntry);
        final EditText loginEntry=(EditText) sign_in_window.findViewById(R.id.loginEditTextEntry);


        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        //переопределение метода setPositiveButton
        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //выключаем закрытие диалога
                Boolean wantToCloseDialog = false;
                int flag=0;

                if (TextUtils.isEmpty(loginEntry.getText().toString())) {
                    Snackbar.make(sign_in_window,"Введите логин ",Snackbar.LENGTH_SHORT).show();
                    flag=1;

                }
                if (passwordEntry.getText().toString().length() < 5) {
                    Snackbar.make(sign_in_window, "Пароль должен содержать не меньше 5 символов",Snackbar.LENGTH_SHORT).show();
                    flag=1;
                }

                if(flag==0)
                {
                    //проверка на правильность логина и пароля
                    int userId=isUserRight(loginEntry.getText().toString(),passwordEntry.getText().toString());
                    //если пароль и логин верные
                    if(userId>0)
                    {
                        User user=databaseHelper.getUser(userId);
                        Intent intent;
                        //Если роль=0, открываетя меню пользователя
                        if(user.getRole()==0)
                        {
                            intent = new Intent(MainActivity.this, UserActivity.class);
                            //передача в новое activity id и ФИО пользователя
                            intent.putExtra("id",user.getId());
                            intent.putExtra("name",user.getName()+" "+user.getSurname());
                        }
                        else
                        {
                            //запуск меню администратора
                            intent = new Intent(MainActivity.this, AdminActivity.class);
                            intent.putExtra("name",user.getName()+" "+user.getSurname());
                        }
                        startActivity(intent);
                        //закрытие mainActivity
                        finish();
                    }
                }

                    Snackbar.make(sign_in_window,"Ошибка авторизации. ",Snackbar.LENGTH_SHORT).show();

            }
        });


    }
    //диалоговое окно для регистрации новой учетной записи
    private void showRegisterWindow() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Заполните все данные для регистрации,пароль должен иметь не меньше 5 символов");
        LayoutInflater inflater = LayoutInflater.from(this);
        View registerWindow = inflater.inflate(R.layout.register_window, null);
        dialog.setView(registerWindow);


        final EditText login = (EditText) registerWindow.findViewById(R.id.loginEditText);
        final EditText password = (EditText) registerWindow.findViewById(R.id.passwordEditText);
        final EditText name = (EditText) registerWindow.findViewById(R.id.nameEditText);
        final EditText surname = (EditText) registerWindow.findViewById(R.id.surnameEditText);


        dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

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
                //проверка на уникальность логина
                if(!isLoginUnique(login.getText().toString())&& flag==0) {
                    Snackbar.make(registerWindow, "Логин должен быть уникальным", Snackbar.LENGTH_SHORT).show();
                    flag=1;

                }

                if(flag==0)
                {
                    //если все данные введены верно,диалоговое окно закроется
                    wantToCloseDialog=true;
                }

                if(wantToCloseDialog) {
                    //создание новой записи
                    User newUser=new User(0,login.getText().toString(),password.getText().toString(),name.getText().toString(),surname.getText().toString());
                    databaseHelper.addUser(newUser);
                    Snackbar.make(mainRelative,"Пользователь добавлен",Snackbar.LENGTH_LONG).show();
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
            if(empty.getLogin().equals(inputLogin))
            {
                return false;
            }
        }
        return true;
    }
    //проверка на правильность логина и пароля
    int isUserRight(String inputLogin,String inputPassword)
    {
        for(User empty:databaseHelper.getUsers())
        {
            if(empty.getLogin().equals(inputLogin)&&empty.getPassword().equals(inputPassword))
            {
                return empty.getId();
            }
        }
        return -1;
    }
}