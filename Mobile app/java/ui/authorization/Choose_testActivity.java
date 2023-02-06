package ui.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.example.test.Test;

import java.util.List;

public class Choose_testActivity extends AppCompatActivity {
   private  ArrayAdapter<Test> arrayAdapter;
    private DatabaseHelper databaseHelper;
    private ListView chooseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test);

        //получение id пользователя
        final int id_user=getIntent().getIntExtra("userId",0);
        chooseList=findViewById(R.id.variation);

        databaseHelper=new DatabaseHelper(this);
        //установление слушателя
        chooseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Test test =arrayAdapter.getItem(position);
                if(test!=null) {
                        Intent intent = new Intent(getApplicationContext(), ExecuteTestActivity.class);
                        intent.putExtra("id", test.getId());
                        intent.putExtra("userId",id_user);
                        startActivity(intent);
                        finish();
                    }

                }

        });


    }

    @Override
    public void onResume() {
        super.onResume();


        List<Test> test=databaseHelper.getAllTestsNamesId();
        if(test.size()!=0)
        {
            //создание адаптера
            arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item2, test);
            chooseList.setAdapter(arrayAdapter);
        }
        else
        {
            Toast.makeText(Choose_testActivity.this,"Сейчас для вас нет тестов",Toast.LENGTH_LONG).show();
        }


    }
}