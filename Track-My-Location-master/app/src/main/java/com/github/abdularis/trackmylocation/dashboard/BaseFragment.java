package com.github.abdularis.trackmylocation.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.abdularis.trackmylocation.R;
import com.github.abdularis.trackmylocation.model.views.AppScheduler;
import com.github.abdularis.trackmylocation.model.views.IScheduler;
import com.github.abdularis.trackmylocation.model.views.Presenter;

import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected IScheduler scheduler;
    protected MainActivity mainActivity;
    protected Presenter presenter;
    protected Unbinder unbinder;
    protected boolean isViewVisible;
    public static boolean shouldShowSaveAlert;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduler = new AppScheduler();
        initializePresenter();
        if(presenter != null) presenter.onCreate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mainActivity = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(presenter != null) presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter != null) presenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideProgressBar();
        if(presenter != null) presenter.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }

    protected abstract void setUpUI();

    protected abstract void initializePresenter();

    public void showProgressBar() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            ((MainActivity)getActivity()).showProgressBar(getString(R.string.fetch_data));
        }
    }

    public void showProgressBar(String title) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            ((MainActivity)getActivity()).showProgressBar(title);
        }
    }

    public void hideProgressBar() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            ((MainActivity)getActivity()).hideProgressBar();
        }
    }
}
