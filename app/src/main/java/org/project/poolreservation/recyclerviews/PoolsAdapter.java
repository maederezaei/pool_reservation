package org.project.poolreservation.recyclerviews;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.project.PoolResetvation.*;
import org.project.PoolResetvation.*;
import org.project.PoolResetvation.*;
import org.project.poolreservation.R;
import org.project.poolreservation.UserMainPage;
import org.project.poolreservation.models.Pool;

import java.util.List;

public class PoolsAdapter extends RecyclerView.Adapter<PoolsAdapter.ViewHolder> {

    private List<Pool> items;
    private Context context;

    public PoolsAdapter(List<Pool> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pool_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.poolName.setText(items.get(position).getPoolName());
        holder.poolAddress.setText(items.get(position).poolAddress);
        if (items.get(position).poolRate.equals("null"))
        {
            holder.poolRate.setRating(0f);
            System.out.println(items.get(position).poolRate);

        }
        else
            holder.poolRate.setRating(Float.valueOf(items.get(position).poolRate));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((UserMainPage)context).showPoolInfo(items.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView poolName;
        TextView poolAddress;
        final RatingBar poolRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            poolName=itemView.findViewById(R.id.poolName);
            poolAddress= itemView.findViewById(R.id.address);
            poolRate= itemView.findViewById(R.id.ratingBar);

        }
    }
}
