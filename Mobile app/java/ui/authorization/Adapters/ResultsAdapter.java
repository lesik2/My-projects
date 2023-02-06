package ui.authorization.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.Results;


import java.util.List;

public class ResultsAdapter extends  RecyclerView.Adapter<ResultsAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Results> results;

    public ResultsAdapter(Context context, List<Results> results) {
        this.inflater = LayoutInflater.from(context);
        this.results = results;
    }
    //возвращает объект ViewHolder, который будет хранить данные по одному объекту
    @NonNull
    @Override
    public ResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.first_list_view, parent, false);
        return new ViewHolder(view);
    }
//выполняет привязку объекта ViewHolder к объекту Result по определенной позиции.
    @Override
    public void onBindViewHolder( ResultsAdapter.ViewHolder holder, int position) {
            Results result=results.get(position);
            String[]compare=result.getResult().split("/");
            int res=Integer.parseInt(compare[0]);
            int questions=Integer.parseInt(compare[1]);
            //если правильных ответов меньше половины,то результат отображается красным
            if(res<=questions/2)
            {
               holder.test_result.setTextColor(Color.parseColor("#fc0505"));
            }
            else
            {
                holder.test_result.setTextColor(Color.parseColor("#07a313"));
            }
        //отображение результатов для определенного пользователя
            if(result.getUserName()==null)
            {
                holder.test_info.setText("Название теста: "+result.getTestName().toString());


            }
        //отображение всех результатов
            else
            {
                holder.test_info.setText("Имя пользователя: "+result.getUserName().toString() +", название теста: "+result.getTestName());
            }

            holder.test_result.setText(result.getResult());

    }
//количество объектов в списке
    @Override
    public int getItemCount() {
        return results.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
       final TextView test_info;
       final TextView test_result;
        ViewHolder(View view){
            super(view);
            test_info=view.findViewById(R.id.test_info);
            test_result=view.findViewById(R.id.test_result);


        }

    }
}
