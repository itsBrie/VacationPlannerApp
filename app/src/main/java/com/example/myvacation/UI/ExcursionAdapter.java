package com.example.myvacation.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myvacation.Entities.Excursion;
import com.example.myvacation.R;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionItemView2;


        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView3);
            excursionItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Excursion current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("excursionID", current.getExcursionID());
                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("excursionName", current.getExcursionName());
                    intent.putExtra("excursionDate", current.getExcursionDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false); //this inflates the excursion list item
        return new ExcursionViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) { //What we display on the recyclerview
        if (mExcursions != null) {
            Excursion current = mExcursions.get(position);
            String excursionName = current.getExcursionName();
            String excursionDate = current.getExcursionDate();
            holder.excursionItemView.setText(excursionName);
            holder.excursionItemView2.setText(excursionDate);
        } else {
            holder.excursionItemView.setText("No excursion name");
            holder.excursionItemView2.setText("No excursion date");
        }
    }

    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (mExcursions != null) {
            return mExcursions.size();
        } else return 0;


    }
}
