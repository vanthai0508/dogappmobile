package com.example.dog_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.dog_app.Model.DogBreed;
import com.example.dog_app.Model.RvAdapter;
import com.example.dog_app.View.DogApiService;
import com.example.dog_app.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DogApiService apiService;
    private RecyclerView recyclerView;
    private RvAdapter adapter;
    private ArrayList<DogBreed> DogList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setDogList();
        setRecyclerView();
        getDogData();
    }
    private void setDogList()
    {
        DogList=new ArrayList<DogBreed>();
        adapter=new RvAdapter(DogList,getApplicationContext());
    }
   private void setRecyclerView()
   {
       recyclerView=binding.rvDog;
       recyclerView.setLayoutManager(new GridLayoutManager(this,2));
       recyclerView.setAdapter(adapter);
   }
   private void getDogData()
   {
       apiService = new DogApiService();
       apiService.getDogs()
               .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
                   @Override
                   public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
                       Log.d("DEBUG1","Success");
                       for(DogBreed dog: dogBreeds)
                       {
                           DogList.add(dog);
                       }
                       adapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onError(@NonNull Throwable e) {
                       Log.d("DEBUG1","Fail"+e.getMessage());

                   }
               });
   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}