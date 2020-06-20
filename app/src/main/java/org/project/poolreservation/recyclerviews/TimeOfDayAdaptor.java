package org.project.poolreservation.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.project.poolreservation.R;
import org.project.poolreservation.models.TimeOfDay;

import java.util.List;

public class TimeOfDayAdaptor extends RecyclerView.Adapter<TimeOfDayAdaptor.ViewHolder> {
    List<TimeOfDay> items;
    private Context context;

    public TimeOfDayAdaptor(List<TimeOfDay> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_of_day_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (items.get(position).isShow()){
            holder.itemView.getLayoutParams().height= RelativeLayout.LayoutParams.WRAP_CONTENT;
        }else {
            holder.itemView.getLayoutParams().height=0;
        }

        holder.time.setText(items.get(position).getTime());

        if (items.get(position).getGender().equals("male")) {
            holder.gender.setText("آقایان");
        }else {
            holder.gender.setText("بانوان");
        }
        holder.capacity.setText(" ظرفیت " + items.get(position).getCapacity() + " نفر");
        holder.off.setText("تخفیف " + items.get(position).getOff() + " %");

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, gender, capacity, off;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            gender = itemView.findViewById(R.id.gender);
            capacity = itemView.findViewById(R.id.capacity);
            off = itemView.findViewById(R.id.off);

        }
    }
}
