package com.example.mafia.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mafia.R;
import com.example.mafia.databases.RepositoryDB;
import com.example.mafia.models.StatisticModel;
import com.example.mafia.utils.Event;
import com.example.mafia.viewmodels.MainViewModel;



public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        mainViewModel.getNavFragmentLD().observe(this, new Observer<Event<Integer>>() {
            @Override
            public void onChanged(Event<Integer> integerEvent) {
                if (!integerEvent.isHasBeenHandled()) {
                    navController.navigate(integerEvent.getContentIfNotHandled());
                }
            }
        });


            TestMethodForStatistic();

    }


    void TestMethodForStatistic(){
        StatisticModel model = new StatisticModel();
        model.setGames(1);
        model.setRole("Mafia");
        model.setTime(3234L);
        RepositoryDB.getInstanse(this).insertStatistic(model);
    }


}
