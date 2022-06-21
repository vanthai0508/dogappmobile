package com.example.dog_app.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.dog_app.DetailActivity;
import com.example.dog_app.R;

import java.util.ArrayList;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> implements Filterable {
    ArrayList<DogBreed> DogBreeds;
    ArrayList<DogBreed> list_input;
    Context context;
    private final ViewBinderHelper viewBinderHelper= new ViewBinderHelper();
    public RvAdapter(ArrayList<DogBreed> dogBreeds, Context context) {
        DogBreeds = dogBreeds;
        list_input=dogBreeds;
        this.context = context;
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.dog_card,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int position) {
        DogBreed dog = DogBreeds.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, dog.getName());
        Glide.with(context)
                .load(dog.getUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.img_thumnail);
        holder.name.setText(dog.getName());
        holder.origin.setText(dog.getOrigin());
        holder.lifespan.setText(dog.getLife_span());
        holder.bredfor.setText(dog.getBred_for());
        holder.breedgroup.setText(dog.getBreed_group());
        DogBreed.Height height = dog.getHeight();
        holder.height.setText(height.getImperial()+" - "+height.getImperial());
        DogBreed.Weight weight = dog.getWeight();
        holder.weight.setText(weight.getImperial()+" - "+weight.getMetric());
        holder.temperament.setText(dog.getTemperament());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               OnClicKGotoDetail(dog);
            }
        });
    }
    private void OnClicKGotoDetail(DogBreed dogBreed)
    {
        Intent intent=new Intent(context, DetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("dog_object",dogBreed);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return DogBreeds.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<DogBreed> listFiltered=new ArrayList<DogBreed>();
                if(charString.isEmpty())
                {
                    listFiltered.addAll(list_input);
                }
                else {
                    for(DogBreed c : list_input)
                    {
                        if(c.getName().toLowerCase().contains(charString.toLowerCase()))
                        {
                            listFiltered.add(c);
                        }
                    }
                }
                FilterResults results=new FilterResults();
                results.values=listFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                DogBreeds=(ArrayList<DogBreed>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_thumnail;
        private TextView name,origin,lifespan,bredfor,breedgroup,height,temperament,weight;
        private ConstraintLayout layout;
        private SwipeRevealLayout swipeRevealLayout;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            img_thumnail=view.findViewById(R.id.dog_thumbnail);
            name=view.findViewById(R.id.tv_dogname);
            origin=view.findViewById(R.id.tv_origin);
            lifespan=view.findViewById(R.id.tv_lifespan);
            layout=view.findViewById(R.id.cv_layout);
            bredfor=view.findViewById(R.id.subtv_bredfor);
            breedgroup=view.findViewById(R.id.subtv_breedgroup);
            height=view.findViewById(R.id.subtv_height);
            temperament=view.findViewById(R.id.subtv_temperament);
            weight=view.findViewById(R.id.subtv_weight);
            swipeRevealLayout= view.findViewById(R.id.swipeRevealLayout);
        }
    }
}
