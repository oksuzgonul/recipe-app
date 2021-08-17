package com.example.bakingApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingApp.R;
import com.example.bakingApp.objects.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder>{

    private List<Step> stepList;
    private final StepAdapterOnclickHandler onclickHandler;

    public interface StepAdapterOnclickHandler {
        void onClick(Step stepOnSlot);
    }

    public StepAdapter(StepAdapterOnclickHandler clickHandler) { onclickHandler = clickHandler; }

    public class StepAdapterViewHolder  extends RecyclerView.ViewHolder {
        public final TextView stepTitleView;

        public StepAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            stepTitleView = itemView.findViewById(R.id.step_title);
        }
    }

    @NonNull
    @Override
    public StepAdapter.StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int LayoutIdForListItem = R.layout.step_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(LayoutIdForListItem, parent, false);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapterViewHolder holder, final int position) {
        Step stepOnSlot = stepList.get(position);
        TextView textView = holder.stepTitleView;
        textView.setText(stepOnSlot.getShortDesc());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Step step = stepList.get(position);
                onclickHandler.onClick(step);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == stepList) return 0;
        return stepList.size();
    }

    public void setStepData(List<Step> stepDataUpdate) {
        stepList = stepDataUpdate;
        notifyDataSetChanged();
    }

}
