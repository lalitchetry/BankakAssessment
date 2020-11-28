package com.lalit.bankakassessment;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.lalit.bankakassessment.databinding.ActivityMainBinding;
import com.lalit.bankakassessment.utils.BaseActivity;
import com.lalit.bankakassessment.viewmodel.TypeViewModel;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    private Dialog dialog;
    private TypeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView() {
        dialog = commonUtils.createCustomLoader(this, false);

        viewModel = new ViewModelProvider(this).get(TypeViewModel.class);
        viewModel.init();
        viewModel.reloadData(1);

        viewModel.getStatus().observe(this, status -> {
            if (status != null) {
                switch (status) {
                    case 0:
                        showInProgress();
                        break;
                    case 1:
                    case -1:
                        hideInProgress();
                        break;
                }
            }
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                if (!commonUtils.isNetworkAvailable()) {
                    commonUtils.snackbar(binding.rllayout, getString(R.string.internet_connection_error));
                } else {
                    hideInProgress();
                    commonUtils.snackbar(binding.rllayout, error);
                }
            }
        });

        viewModel.getDataList().observe(this, dataList->{
            if (dataList!=null){
                Toast.makeText(mContext, "Succesfull", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideInProgress() {
        commonUtils.dismissCustomDialog(dialog);
    }

    private void showInProgress() {
        commonUtils.showCustomDialog(dialog, this);
    }
}