package com.example.myvacation.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myvacation.Entities.Vacation;
import com.example.myvacation.R;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder>{
    private List<Vacation> mVacations;
    private final Context context;

    private final LayoutInflater mInflater;

    public VacationAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    public class VacationViewHolder extends RecyclerView.ViewHolder {

        private final TextView vacationItemView;
        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView=itemView.findViewById(R.id.textView2); //initializes the vacationItemView and assigns to appropriate id
            itemView.setOnClickListener(new View.OnClickListener() { //when the vacation item on the list is clicked
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition(); //find the position on the list
                    final Vacation current=mVacations.get(position); //get vacation item at the adapter position
                    Intent intent= new Intent(context,VacationDetails.class);
                    //sending over information
                    intent.putExtra("id",current.getVacationID());
                    intent.putExtra("vacationTitle",current.getVacationTitle());
                    intent.putExtra("vacationHotel",current.getVacationHotelName());
                    intent.putExtra("vacationStartDate",current.getVacationStartDate());
                    intent.putExtra("vacationEndDate",current.getVacationEndDate());
                    context.startActivity(intent);
                }
            });

        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.vacation_list_item,parent,false); //this inflates the product list item
        return new VacationViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) { //What we display on the recyclerview
        if (mVacations!=null){
            Vacation current=mVacations.get(position);
            String name=current.getVacationTitle();
            holder.vacationItemView.setText(name);
        }
        else {
            holder.vacationItemView.setText("No vacation name");
        }
    }

    @Override
    public int getItemCount() {
        if (mVacations!=null){
            return mVacations.size();
        }
        else return 0;

    }

    public void setVacations(List<Vacation> vacations){
        mVacations=vacations;
        notifyDataSetChanged();
    }

}