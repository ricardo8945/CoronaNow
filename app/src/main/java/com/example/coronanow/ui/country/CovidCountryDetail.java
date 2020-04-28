package com.example.coronanow.ui.country;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coronanow.R;

public class CovidCountryDetail extends AppCompatActivity {
    TextView tvDetailCountryName, tvDetailTotalCases, tvDetailTodayCases, tvDetailTotalDeaths, tvDetailTodayDeaths,
            tvDetailTotalRecovered, tvDetailTotalActive,tvDetailTotalCritical;
    ImageView imageCountryFlagDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_country_detail);
        //Llamar a la vista
        tvDetailCountryName= findViewById(R.id.tvDetailCountryName);
        tvDetailTotalCases= findViewById(R.id.tvDetailTotalCases);
        tvDetailTodayCases= findViewById(R.id.tvDetailTodayCases);
        tvDetailTotalDeaths= findViewById(R.id.tvDetailTotalDeaths);
        tvDetailTodayDeaths= findViewById(R.id.tvDetailTodayDeaths);
        tvDetailTotalRecovered= findViewById(R.id.tvDetailTotalRecovered);
        tvDetailTotalActive= findViewById(R.id.tvDetailTotalActive);
        tvDetailTotalCritical= findViewById(R.id.tvDetailTotalCritical);
        imageCountryFlagDetail= (ImageView) findViewById(R.id.imgCountryFlagDetail);
        // Llama al fragment CovidCountry
        CovidCountry covidCountry= getIntent().getParcelableExtra("EXTRA_COVID");

        tvDetailCountryName.setText(covidCountry.getmCovidCountry());
        tvDetailTotalCases.setText(Integer.toString(covidCountry.getmCases()));
        tvDetailTodayCases.setText(covidCountry.getmTodayCases());
        tvDetailTotalDeaths.setText(covidCountry.getmDeaths());
        tvDetailTodayDeaths.setText(covidCountry.getmTodayDeaths());
        tvDetailTotalRecovered.setText(covidCountry.getmRecovered());
        tvDetailTotalActive.setText(covidCountry.getmActive());
        tvDetailTotalCritical.setText(covidCountry.getmCritical());
        Glide.with(this).load(covidCountry.getmFlags()).into(imageCountryFlagDetail);
    }
}
