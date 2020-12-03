package com.lalit.bankakassessment;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.lalit.bankakassessment.databinding.ActivityMainBinding;
import com.lalit.bankakassessment.model.TypeResponseModel;
import com.lalit.bankakassessment.utils.BaseActivity;
import com.lalit.bankakassessment.utils.DynamicView;
import com.lalit.bankakassessment.viewmodel.TypeViewModel;

import java.util.ArrayList;

import static com.lalit.bankakassessment.utils.Constants.TYPE_DROPDOWN;
import static com.lalit.bankakassessment.utils.Constants.TYPE_TEXT;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    private Dialog dialog;
    private TypeViewModel viewModel;
    ArrayList<String> spinnerArray = new ArrayList<String>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        dialog = commonUtils.createCustomLoader(this, false);

        viewModel = new ViewModelProvider(this).get(TypeViewModel.class);
        viewModel.init();

        binding.spType.setItems("Option 1", "Option 2", "Option 3");
        binding.spType.setOnItemSelectedListener((view, position, id, item) -> {
            switch (position) {
                case 0:
                    viewModel.reloadData(1);
                    break;
                case 1:
                    viewModel.reloadData(2);
                    break;
                case 2:
                    viewModel.reloadData(3);
                    break;
            }
        });

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

        viewModel.getDataList().observe(this, dataList -> {
            if (dataList != null) {
                setData(dataList);
            }
        });


        binding.btnSubmit.setOnClickListener(view -> {
            submitValues();
        });

    }


    private void submitValues() {
        int count = binding.llDynamicLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = binding.llDynamicLayout.getChildAt(i);
            if (view instanceof EditText) {
                String validatedString = commonUtils.validate((EditText) view, (String) view.getTag());
                if (!validatedString.isEmpty()) {
                    commonUtils.snackbar(binding.rllayout, validatedString);
                    return;
                } else
                    commonUtils.snackbar(binding.rllayout, "You can now proceed");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setData(TypeResponseModel dataList) {
        DynamicView dynamicView = new DynamicView(this);
        binding.llDynamicLayout.removeAllViews();
        for (int i = 0; i < dataList.getResult().getFields().size(); i++) {
            switch (dataList.getResult().getFields().get(i).getUiType().getType()) {
                case TYPE_TEXT:
                    binding.llDynamicLayout.addView(dynamicView.textView(this, dataList.getResult().getFields().get(i).getPlaceholder()));
                    binding.llDynamicLayout.addView(dynamicView.editText(this, dataList.getResult().getFields().get(i).getHintText(), dataList.getResult().getFields().get(i).getRegex()));
                    break;
                case TYPE_DROPDOWN:
                    for (int j = 0; j < dataList.getResult().getFields().get(i).getUiType().getValues().size(); j++) {
                        spinnerArray.add(dataList.getResult().getFields().get(i).getUiType().getValues().get(j).getName());
                    }
                    binding.llDynamicLayout.addView(dynamicView.textView(this, dataList.getResult().getFields().get(i).getPlaceholder()));
                    binding.llDynamicLayout.addView(dynamicView.materialSpinner(this, spinnerArray));
                    break;
            }
        }
    }

    private void hideInProgress() {
        commonUtils.dismissCustomDialog(dialog);
    }

    private void showInProgress() {
        commonUtils.showCustomDialog(dialog, this);
    }


}