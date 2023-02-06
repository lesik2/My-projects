package ui.authorization.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.Test;

import java.util.List;

public class TestAdapter extends  RecyclerView.Adapter<TestAdapter.ViewHolder>{

    public interface OnTestClickListener{
        void onTestClick(Test test, int position);
    }

    private final LayoutInflater inflater;
    private final List<Test> tests;
    private final OnTestClickListener onClickListener;

    public TestAdapter(Context context, List<Test> tests, OnTestClickListener onClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.tests = tests;
        this.onClickListener = onClickListener;
    }
    //возвращает объект ViewHolder, который будет хранить данные по одному объекту
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
    //выполняет привязку объекта ViewHolder к объекту Result по определенной позиции.
    @Override
    public void onBindViewHolder(TestAdapter.ViewHolder holder, int position) {
            Test test=tests.get(position);
            holder.testName.setText(test.toString());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onTestClick(test,holder.getAdapterPosition());

                    holder.testName.setBackgroundResource(R.drawable.round_back_stroke2_10);
                }
            });
    }
    //количество объектов в списке
    @Override
    public int getItemCount() {
        return tests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView testName;
        ViewHolder(View view){
            super(view);
            testName=view.findViewById(R.id.testName);
        }

    }
}
