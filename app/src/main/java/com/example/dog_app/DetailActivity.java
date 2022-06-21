package com.example.dog_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dog_app.Model.DogBreed;
import com.example.dog_app.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    TextView tv_bredfor,tv_breedgroup,tv_height,tv_lifespandetail,tv_namedetail,tv_origindetail,tv_temperament,tv_weight;
    ImageView thumbnail;
    ActivityDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        View view =binding.getRoot();
        setContentView(view);
        setComponents();
        getData();
    }
    private void setComponents()
    {
        tv_bredfor=binding.tvBredfor;
        tv_breedgroup=binding.tvBreedgroup;
        tv_height=binding.tvHeight;
        tv_lifespandetail=binding.tvLifespandetail;
        tv_namedetail=binding.tvNamedetail;
        tv_origindetail=binding.tvOrigindetail;
        tv_temperament=binding.tvTemperament;
        tv_weight=binding.tvWeight;
        thumbnail=binding.thumbnaildetail;
    }
    private void getData()
    {
        Bundle bundle=getIntent().getExtras();
        if(bundle==null) return;
        DogBreed dog = (DogBreed) bundle.get("dog_object");
        DogBreed.Height height = dog.getHeight();
        DogBreed.Weight weight = dog.getWeight();
        tv_bredfor.append(dog.getBred_for());
        tv_breedgroup.append(dog.getBreed_group());
        tv_lifespandetail.append(dog.getLife_span());
        tv_namedetail.append(dog.getName());
        tv_origindetail.append(dog.getOrigin());
        tv_temperament.append(dog.getTemperament());
        tv_height.append(height.getImperial()+"-"+height.getMetric());
        tv_weight.append(weight.getImperial()+"-"+weight.getMetric());
        Glide.with(this)
                .load(dog.getUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(thumbnail);
    }
}