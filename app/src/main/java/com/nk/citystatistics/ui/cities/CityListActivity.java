package com.nk.citystatistics.ui.cities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.nk.citystatistics.R;
import com.nk.citystatistics.application.CityStatisticsApplication;
import com.nk.citystatistics.base.BaseActivity;
import com.nk.citystatistics.db.CityStatisticsDatabase;
import com.nk.citystatistics.db.model.CityInfo;
import com.nk.citystatistics.dialog.CityInputDialogFragment;
import com.nk.citystatistics.event.MessageEvent;
import com.nk.citystatistics.event.MessageEvent.saveStatus;
import com.nk.citystatistics.ui.cities.adapter.CityAdapter;
import com.nk.citystatistics.ui.splash.SplashActivity;
import com.nk.citystatistics.utils.AppConstant;
import com.nk.citystatistics.utils.ToastUtils;
import io.reactivex.Scheduler;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CityListActivity extends BaseActivity implements CityListMvpView,
        View.OnClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private  String TAG_DIALOG = "dialog";

    @Inject
    CityListPresenter presenter;

    @Inject
    EventBus eventBus;

    @Inject
    CityAdapter cityAdapter;

    @Inject
    CityStatisticsDatabase database;

    @Inject
    @Named(AppConstant.MAIN_THREAD)
    Scheduler mainThread;


    @Inject
    @Named(AppConstant.NEW_THREAD)
    Scheduler newThread;

    private RecyclerView recyclerView;

    private List<CityInfo> infoList;
    private boolean isToRemoveFromDb;

    public static Intent newIntent(Context context) {
        return new Intent(context, CityListActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.rcv);

        (findViewById(R.id.btnFab)).setOnClickListener(this);

        eventBus.register(this);
        presenter.attachView(this);
        presenter.getAllValue(database, mainThread, newThread);

        setUpRecyclerView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_sort_name) {
            presenter.sortCityName(mainThread, newThread, infoList);
        } else if (itemId == R.id.action_sort_population) {
            presenter.sortPopulation(mainThread, newThread, infoList);
        } else if (itemId == R.id.action_sort_creation_date) {
            presenter.sortCreationDate(mainThread, newThread, infoList);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnFab) {
            showCityInfoDialog();
        }
    }

    @Override
    public void injectDependency() {
        CityStatisticsApplication.getAppComponents().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_city_list;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CityInfo cityInfo) {
        if (cityInfo != null) {
            presenter.validateForm(applicationContext, cityInfo);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        eventBus.unregister(this);
        presenter.detachView();
    }

    @Override
    public void onSwiped(ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CityAdapter.CityViewHolder) {
            try {
                // get the removed item name to display it in snack bar
                String name = infoList.get(viewHolder.getAdapterPosition()).getCityName();

                // backup of removed item for undo purpose
                final CityInfo deletedItem = infoList.get(viewHolder.getAdapterPosition());
                final int deletedIndex = viewHolder.getAdapterPosition();

                // remove the item from recycler view
                cityAdapter.removeItem(viewHolder.getAdapterPosition(), recyclerView);

                isToRemoveFromDb = true;

                // showing snack bar with Undo option
                Snackbar snackbar = Snackbar
                        .make(recyclerView, name + getString(R.string.removed_from_list), Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.undo,
                        view -> cityAdapter.restoreItem(deletedItem, deletedIndex, recyclerView));
                snackbar.setAction("OK", v -> {
                    cityAdapter.restoreItem(deletedItem, deletedIndex, recyclerView);
                    isToRemoveFromDb = false;
                });

                snackbar.setActionTextColor(Color.YELLOW);

                snackbar.addCallback(new Snackbar.Callback() {

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (isToRemoveFromDb) {
                            presenter.removeCity(database, mainThread, newThread, deletedItem);
                        }
                        isToRemoveFromDb = true;
                    }

                    @Override
                    public void onShown(Snackbar snackbar) {

                    }
                });

                snackbar.show();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void showErrorMessage(String msg) {
        ToastUtils.showToastMessageNormal(applicationContext, msg);
    }

    @Override
    public void validationSuccessfull(CityInfo cityInfo) {
        presenter.saveData(database, mainThread, newThread, cityInfo);
    }

    @Override
    public void dataSaveSuccessfully(CityInfo cityInfo) {
        MessageEvent.saveStatus saveStatus = new saveStatus();
        saveStatus.setSave(true);
        eventBus.post(saveStatus);

        cityAdapter.addItemAtPosition(cityInfo, infoList.size(), recyclerView);
        ToastUtils.showToastMessageNormal(applicationContext, getString(R.string
                .city_save_message));
    }

    @Override
    public void dataSavingError(Throwable e) {
        MessageEvent.saveStatus saveStatus = new saveStatus();

        if (e instanceof SQLiteConstraintException) {
            saveStatus.setSave(false);
            ToastUtils.showToastMessageNormal(applicationContext, getString(R.string
                    .city_name_exist));
        } else {
            saveStatus.setSave(true);
            ToastUtils.showToastMessageNormal(applicationContext, e.getMessage());
        }

        eventBus.post(saveStatus);

    }

    @Override
    public void getAllCitiesData(List<CityInfo> cityInfo) {
        this.infoList = cityInfo;
        cityAdapter.upadateAdapter(cityInfo, recyclerView);
    }

    @Override
    public void getAllCitiesFailed(Throwable t) {
        infoList = new ArrayList<>();
    }

    @Override
    public void sortedCityData(List<CityInfo> infoList) {
        this.infoList = infoList;
        cityAdapter.upadateAdapter(infoList, recyclerView);
    }

    private void setUpRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(
                getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(cityAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private void showCityInfoDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(TAG_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new CityInputDialogFragment();
        dialogFragment.show(ft, TAG_DIALOG);
        dialogFragment.setCancelable(false);
    }
}
