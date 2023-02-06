package ui.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.example.test.Test;

import java.util.List;

import ui.authorization.Adapters.TestAdapter;

public class DeleteTestActivity extends AppCompatActivity {

    private int deletePosition;
    private TestAdapter adapter;
    private Test deleteTest;
    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_test);

       final RecyclerView chooseList = findViewById(R.id.listTest);
       final AppCompatButton performBtn = findViewById(R.id.deleteTest);
       final DatabaseHelper databaseHelper = new DatabaseHelper(this);

        List<Test> tests = databaseHelper.getAllTestsNamesId();
        if (tests.size() != 0) {

            TestAdapter.OnTestClickListener testClickListener=new TestAdapter.OnTestClickListener() {
                @Override
                public void onTestClick(Test test, int position) {
                    Toast.makeText(getApplicationContext(), "Был выбран пункт " + test.getName(),
                            Toast.LENGTH_SHORT).show();
                    deleteTest=test;
                    deletePosition=position;


                }
            };
         adapter = new TestAdapter(DeleteTestActivity.this, tests, testClickListener);

            chooseList.setAdapter(adapter);
        } else {
            Toast.makeText(DeleteTestActivity.this, "Сейчас нет тестов", Toast.LENGTH_LONG).show();
        }
        performBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(counter==2) {
                    //удаление выбранного теста
                    databaseHelper.deleteTest(deleteTest.getId());
                    tests.remove(deletePosition);
                    //уведомление адаптера
                    adapter.notifyItemRemoved(deletePosition);
                    if(tests.size()==0)
                    {
                        Toast.makeText(DeleteTestActivity.this, "Сейчас нет тестов", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast toast =Toast.makeText(DeleteTestActivity.this,"Вы уверены,что хотите удалить тест",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0,160);
                    toast.show();
                    counter++;
                }

            }
        });
    }
    }









