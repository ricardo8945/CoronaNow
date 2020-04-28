package com.example.coronanow.ui.country;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.coronanow.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryFragment extends Fragment {

    RecyclerView rvCovidCountry;
    ProgressBar progressBar;
    CovidCountryAdapter covidCountryAdapter;
    private static final String TAG= CountryFragment.class.getSimpleName();

    List<CovidCountry> covidCountries;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_country, container, false);

        //set has option menu ya que tenemos Menú (Filtro de paises)
        setHasOptionsMenu(true);

        //Llamar view
        rvCovidCountry = root.findViewById(R.id.rvCovidCountry);
        progressBar = root.findViewById(R.id.progress_circular_country);
        rvCovidCountry.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Llamar el divisor
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(rvCovidCountry.getContext(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.line_divider));
        rvCovidCountry.addItemDecoration(dividerItemDecoration);
        //Llamar al covid country adapter
        covidCountries=new ArrayList<>();
        //Llamar el método volley
        getDataFromServer();
        return root;
    }

    private void showRecyclerView(){
          covidCountryAdapter= new CovidCountryAdapter(covidCountries, getActivity());
         rvCovidCountry.setAdapter(covidCountryAdapter);
         ItemClickSupport.addTo(rvCovidCountry).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
             @Override
             public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                 showSelectedCovidCountry(covidCountries.get(position));
             }
         });
    }

    private void showSelectedCovidCountry(CovidCountry covidCountry){
        Intent covidCovidCountryDetail= new Intent(getActivity(),CovidCountryDetail.class);
        covidCovidCountryDetail.putExtra("EXTRA_COVID",covidCountry);
        startActivity(covidCovidCountryDetail);
    }

    private void getDataFromServer() {
        String url="https://corona.lmao.ninja/v2/countries";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse" + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            //Json para flags para extraer Jsonobject dentro del JsonObject
                            JSONObject countryInfo=data.getJSONObject("countryInfo");
                            covidCountries.add(new CovidCountry(data.getString("country"), data.getString("cases"),
                                    data.getString("todayCases"),data.getString("deaths"),
                                    data.getString("todayDeaths"),data.getString("recovered"),
                                    data.getString("active"),data.getString("critical"),
                                    countryInfo.getString("flag")));
                        }
                        //ActionBar Title
                        getActivity().setTitle(jsonArray.length()+" países afectados");
                        showRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG,"onResponse"+error);
                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
        }
        /*
        private String getSpanish(String countryenglish){
            String countrySpanish="";
            Locale outLocale = Locale.forLanguageTag("es_ES");
            Locale inLocale = Locale.forLanguageTag("en_GB");
            //new Locale("es", "ES")
            for (Locale l : Locale.getAvailableLocales()) {
                if (l.getDisplayCountry(inLocale).equals(countryenglish)) {
                    countrySpanish=l.getDisplayCountry(outLocale);
                    break;
                }
            }
            return countrySpanish;
        }*/

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(getActivity());
        searchView.setQueryHint("Buscando..");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(covidCountryAdapter!=null){
                    covidCountryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
