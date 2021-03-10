package com.techease.groupiiapplication.ui.fragment.bottomSheetFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.groupiiapplication.R;
import com.techease.groupiiapplication.adapter.Connect;
import com.techease.groupiiapplication.adapter.ConnectionBooleanChangedListener;
import com.techease.groupiiapplication.adapter.GalleryPhotoAdapter;
import com.techease.groupiiapplication.dataModel.getGalleryPhoto.GalleryPhotoDataModel;
import com.techease.groupiiapplication.dataModel.getGalleryPhoto.GetGalleryPhotoResponse;
import com.techease.groupiiapplication.network.BaseNetworking;
import com.techease.groupiiapplication.utils.AlertUtils;
import com.techease.groupiiapplication.utils.AppRepository;
import com.techease.groupiiapplication.utils.GetRecylerViewCountColum;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.ivGalleryPhotoGridView)
    ImageView ivGalleryPhotoGridView;
    @BindView(R.id.rvGalleryPhoto)
    RecyclerView rvGalleryPhoto;
    GalleryPhotoAdapter galleryPhotoAdapter;
    LinearLayoutManager linearLayoutManager;
    Dialog dialog;
    ArrayList<GalleryPhotoDataModel> galleryPhotoDataModels = new ArrayList<>();
    boolean aBooleanIsGridView = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        ButterKnife.bind(this, view);
        ApiCallGetAllGalleryPhoto();
        dialog = AlertUtils.createProgressDialog(getActivity());


        Connect.addMyBooleanListener(new ConnectionBooleanChangedListener() {
            @Override
            public void OnMyBooleanChanged() {
                ApiCallGetAllGalleryPhoto();

            }
        });
        initRecylerView(false);


        return view;
    }

    private void initRecylerView(boolean isGridView) {

        int mNoOfColumns = GetRecylerViewCountColum.calculateNoOfColumns(getActivity(), 120);

        linearLayoutManager = new LinearLayoutManager(getActivity());


        if (isGridView) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), mNoOfColumns);
            rvGalleryPhoto.setLayoutManager(gridLayoutManager);
            galleryPhotoAdapter = new GalleryPhotoAdapter(getActivity(), galleryPhotoDataModels, R.layout.custom_gallery_photo_grid_view_layout);

        } else {
            rvGalleryPhoto.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            galleryPhotoAdapter = new GalleryPhotoAdapter(getActivity(), galleryPhotoDataModels, R.layout.custom_gallery_photo_layout);

        }

        rvGalleryPhoto.setAdapter(galleryPhotoAdapter);

    }

    private void ApiCallGetAllGalleryPhoto() {
        galleryPhotoDataModels.clear();
        Call<GetGalleryPhotoResponse> getGalleryPhotoResponseCall = BaseNetworking.ApiInterface().getAllGalleryPhoto("trips/gallery/" + AppRepository.mTripId(getActivity()));
        getGalleryPhotoResponseCall.enqueue(new Callback<GetGalleryPhotoResponse>() {
            @Override
            public void onResponse(Call<GetGalleryPhotoResponse> call, Response<GetGalleryPhotoResponse> response) {

                Log.d("zma image response", String.valueOf(response));
                if (response.isSuccessful()) {
                    galleryPhotoDataModels.addAll(response.body().getData());
                    Collections.reverse(galleryPhotoDataModels);
                    galleryPhotoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetGalleryPhotoResponse> call, Throwable t) {

            }
        });
    }


    @OnClick({R.id.ivGalleryPhotoGridView})

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivGalleryPhotoGridView:

                if (aBooleanIsGridView) {
                    ivGalleryPhotoGridView.setBackgroundResource(R.drawable.ic_baseline_view_module_24);
                    initRecylerView(true);
                    aBooleanIsGridView = false;
                } else {
                    ivGalleryPhotoGridView.setBackgroundResource(R.drawable.ic_baseline_view_agenda_24);
                    initRecylerView(false);
                    aBooleanIsGridView = true;
                }

                break;
        }
    }
}
