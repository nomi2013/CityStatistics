package com.nk.citystatistics.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.nk.citystatistics.R;
import com.nk.citystatistics.application.CityStatisticsApplication;
import com.nk.citystatistics.db.model.CityInfo;
import com.nk.citystatistics.event.MessageEvent;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Noman Khan on 28/05/18.
 */
public class CityInputDialogFragment extends DialogFragment implements View.OnClickListener {

    @Inject
    EventBus eventBus;
    private EditText editName;
    private EditText edtPopulation;
    private EditText edtState;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            ((CityStatisticsApplication) getActivity().getApplication()).getAppComponents().inject(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        eventBus.register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_Light_Dialog_Alert);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_city_input_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editName = view.findViewById(R.id.edtName);
        edtPopulation = view.findViewById(R.id.edtPopulation);
        edtState = view.findViewById(R.id.edtState);

        (view.findViewById(R.id.btnCancel)).setOnClickListener(this);
        (view.findViewById(R.id.btnSave)).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnCancel) {
            dismissDialog();
        } else if (id == R.id.btnSave) {
            saveCity();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        eventBus.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent.saveStatus saveStatus) {
        if (saveStatus != null && saveStatus.isSave()) {
            dismissDialog();
        }
    }

    private void dismissDialog() {
        getDialog().dismiss();
    }

    private void saveCity() {
        CityInfo info = new CityInfo();
        info.setCityName(editName.getText().toString());

        String population = edtPopulation.getText().toString();
        if (!TextUtils.isEmpty(population)) {
            info.setCityPopulation(Integer.parseInt(population));
        } else {
            info.setCityPopulation(0);

        }

        info.setState(edtState.getText().toString());
        info.setCreationDate(String.valueOf(System.currentTimeMillis()));

        eventBus.post(info);
    }

}
