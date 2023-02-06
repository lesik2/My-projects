package ui.authorization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.example.test.Results;

import java.util.List;

import ui.authorization.Adapters.ResultsAdapter;

public class ShowResultsActivity extends AppCompatActivity {
    private ResultsAdapter adapter;
   private List<Results> results;
   private RecyclerView resultsList;
    private TextView my_results;
    private DatabaseHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        resultsList=findViewById(R.id.list_with_results);
        my_results=findViewById(R.id.MyResults);
        databaseHelper=new DatabaseHelper(this);

        userId=getIntent().getIntExtra("userId",0);
        if(userId==0)
        {
            my_results.setText("Результаты пользователей");
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        if(userId==0)
        {
            //получение из бд результатов всех пользователей
            results=databaseHelper.getUsersResults();
        }
        else
        {
            //получение результаты пользователя по id
            results=databaseHelper.getUserResults(userId);
        }

        if(results.size()!=0)
        {
            adapter = new ResultsAdapter(ShowResultsActivity.this, results);
            resultsList.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(ShowResultsActivity.this,"Нет результатов",Toast.LENGTH_LONG).show();
        }

    }
}