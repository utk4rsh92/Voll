package com.voll.voll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VegAdapter extends RecyclerView.Adapter<VegAdapter.MyViewHolder> {
private List<VegData> vegData;
Context context;
public  VegAdapter(List<VegData> vegData, Context applicationContext){
    this.vegData = vegData;
}
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {

    holder.tvName.setText(vegData.get(position).getName());
    holder.tvPrice.setText(vegData.get(position).getPrice());
    String im = vegData.get(position).getUrl();
        Picasso.get().load(im).into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"You clicked"+getItemCount(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return vegData.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tvName, tvPrice;
    ImageView imageView;
    public RelativeLayout relativeLayout;
        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView)itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView)itemView.findViewById(R.id.tv_price);
            imageView = (ImageView)itemView.findViewById(R.id.imageVeg);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative);

        }
    }
}
