package com.rhix.mycoffee;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInquire extends Fragment {

    private EditText etName, etEmail, etInquiry;
    private Button btnSubmit;
    private String coffeeName; // passed from dialog

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inquire, container, false);

        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etInquiry = view.findViewById(R.id.etInquiry);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        // Get coffee name from arguments
        if (getArguments() != null) {
            coffeeName = getArguments().getString("coffee_name", null);
        }

        btnSubmit.setOnClickListener(v -> handleSubmit());

        return view;
    }

    private void handleSubmit() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String inquiry = etInquiry.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(inquiry)) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        InquiryRequest request = new InquiryRequest(coffeeName, name, email, inquiry);

        // connect to API
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<InquiryResponse> call = apiService.submitInquiry(request);

        btnSubmit.setEnabled(false); // disable while sending

        call.enqueue(new Callback<InquiryResponse>() {
            @Override
            public void onResponse(@NonNull Call<InquiryResponse> call, @NonNull Response<InquiryResponse> response) {
                btnSubmit.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    InquiryResponse res = response.body();
                    Toast.makeText(getContext(), res.getMessage(), Toast.LENGTH_LONG).show();

                    if (res.isSuccess()) {
                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                } else {
                    Toast.makeText(getContext(), "Submission failed!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<InquiryResponse> call, @NonNull Throwable t) {
                btnSubmit.setEnabled(true);
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
