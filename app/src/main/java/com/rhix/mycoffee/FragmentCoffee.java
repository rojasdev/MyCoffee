package com.rhix.mycoffee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCoffee extends Fragment {

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee, container, false);

        listView = view.findViewById(R.id.listViewCoffee);

        // Call API service
        ApiService api = ApiClient.getClient().create(ApiService.class);
        Call<CoffeeResponse> call = api.getCoffees();

        call.enqueue(new Callback<CoffeeResponse>() {
            @Override
            public void onResponse(Call<CoffeeResponse> call, Response<CoffeeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CoffeeBean> coffeeList = response.body().getItems();

                    if (coffeeList != null && !coffeeList.isEmpty()) {
                        CoffeeAdapter adapter = new CoffeeAdapter(requireContext(), coffeeList);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener((parent, view1, position, id) -> {
                            CoffeeBean selectedCoffee = coffeeList.get(position);

                            // Inflate custom dialog layout
                            View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_coffee, null);

                            ImageView imageCoffee = dialogView.findViewById(R.id.imageCoffee);
                            //TextView textName = dialogView.findViewById(R.id.textCoffeeName);
                            TextView textOrigin = dialogView.findViewById(R.id.textCoffeeOrigin);
                            TextView textRoast = dialogView.findViewById(R.id.textCoffeeRoast);
                            TextView textDetails = dialogView.findViewById(R.id.textCoffeeDescription);

                            // Set coffee details
                            /* String details = "Origin: " + selectedCoffee.getOrigin() + "\n" +
                                    "Roast: " + selectedCoffee.getRoast() + "\n\n" +
                                    "Description:\n" + selectedCoffee.getDescription();
                            textDetails.setText(details);*/

                            //String coffeeName = selectedCoffee.getName();
                            //textName.setText(coffeeName);

                            String coffeeOrigin = "Origin: " + selectedCoffee.getOrigin();
                            textOrigin.setText(coffeeOrigin);

                            String coffeeRoast = "Roast: " + selectedCoffee.getRoast();
                            textRoast.setText(coffeeRoast);

                            String coffeeDescription = selectedCoffee.getDescription();
                            textDetails.setText(coffeeDescription);

                            // Load image using Picasso
                            Picasso.get()
                                    .load(selectedCoffee.getImageUrl()) // make sure CoffeeBean has getImageUrl()
                                    .placeholder(R.drawable.placeholder) // optional placeholder
                                    .error(R.drawable.error)             // optional error image
                                    .into(imageCoffee);

                            // Build and show dialog
                            //AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            //builder.setTitle(selectedCoffee.getName());
                            //builder.setView(dialogView);
                            //builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());
                            //builder.show();

                            // Build dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setTitle(selectedCoffee.getName());
                            builder.setView(dialogView);
                            builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());


                            AlertDialog dialog = builder.create();
                            dialog.show();


                            Button btnInquire = dialogView.findViewById(R.id.btnInquire);
                            btnInquire.setOnClickListener(v -> {
                                dialog.dismiss();

                                // Open FragmentInquire
                                Fragment inquireFragment = new FragmentInquire();

                                // Pass coffee info if needed
                                Bundle args = new Bundle();
                                args.putString("coffee_name", selectedCoffee.getName());
                                inquireFragment.setArguments(args);

                                requireActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.fragment_container, inquireFragment)
                                        .addToBackStack(null)
                                        .commit();
                            });
                        });

                    } else {
                        Toast.makeText(requireContext(), "No coffee beans found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "No data available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CoffeeResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "API Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API Response", "Error: " + t.getMessage(), t);
            }
        });

        return view;
    }
}
