package com.vibecampo.android.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.vibecampo.android.R;
import com.vibecampo.android.activity.BaseActivity;
import com.vibecampo.android.fragment.ProgressDialogFragment;
import com.vibecampo.android.image.adapter.PhotoEffectsListAdapter;
import com.vibecampo.android.storage.SimpleStorage;
import com.vibecampo.android.storage.util.FileCreate;
import com.vibecampo.android.utils.StorageUtils;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ke.co.toshngure.basecode.app.DialogAsyncTask;
import ke.co.toshngure.basecode.images.photofilters.FilterBitmapTransformation;
import ke.co.toshngure.basecode.images.simplecrop.CropImageView;
import ke.co.toshngure.basecode.images.simplecrop.callback.CropCallback;
import ke.co.toshngure.basecode.images.simplecrop.callback.LoadCallback;
import ke.co.toshngure.basecode.images.simplecrop.util.CropUtils;
import ke.co.toshngure.basecode.log.BeeLog;

public class ImageEditActivity extends BaseActivity implements PhotoEffectsListAdapter.OnItemClickListener {


    private static final String TAG = ImageEditActivity.class.getSimpleName();
    @BindView(R.id.cropImageView)
    CropImageView mCropImageView;

    @BindView(R.id.rotateOptionsVS)
    ViewStub mRotateOptionsVS;

    @BindView(R.id.ratioOptionsVS)
    ViewStub mRatioOptionsVS;

    @BindView(R.id.shapeOptionsVS)
    ViewStub mShapeOptionsVS;

    @BindView(R.id.filterOptionsVS)
    ViewStub mFilterOptionsVS;

    @BindView(R.id.blurOptionsVS)
    ViewStub mBlurOptionsVS;

    private Bitmap mBitmapBeforeApplyingFilter;

    @BindViews({R.id.rotateOptionsVS, R.id.ratioOptionsVS, R.id.shapeOptionsVS, R.id.filterOptionsVS, R.id.blurOptionsVS})
    List<ViewStub> mViewStubList;

    private Uri mUri;

    private static final ButterKnife.Setter<ViewStub, Integer> STUBS_VISIBILITY = new ButterKnife.Setter<ViewStub, Integer>() {

        @Override
        public void set(@NonNull ViewStub view, Integer value, int index) {
            if (view.getVisibility() != value) {
                view.setVisibility(value);
            }
        }
    };
    private LinearLayoutManager mLayoutManager;
    private PhotoEffectsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getToolbar().setVisibility(View.GONE);

        ButterKnife.bind(this);

        mUri = getIntent().getData();

        mCropImageView.startLoad(mUri, mLoadCallback);

        initStubs();
        //bindViews();
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new PhotoEffectsListAdapter(this);
        mAdapter.setOnItemClickListener(this);

    }

    private void initStubs() {
        mRotateOptionsVS.setLayoutResource(R.layout.layout_image_rotations);
        mRatioOptionsVS.setLayoutResource(R.layout.layout_image_aspect_ratios);
        mShapeOptionsVS.setLayoutResource(R.layout.layout_image_shapes);
        mFilterOptionsVS.setLayoutResource(R.layout.layout_image_filters);
        mBlurOptionsVS.setLayoutResource(R.layout.layout_image_seekbar_option);
    }

    private void resetStubs() {
        ButterKnife.apply(mViewStubList, STUBS_VISIBILITY, View.GONE);
    }

    public static void start(AppCompatActivity appCompatActivity, Uri uri, int requestCode) {
        Intent starter = new Intent(appCompatActivity, ImageEditActivity.class);
        starter.setData(uri);
        appCompatActivity.startActivityForResult(starter, requestCode);
    }

    private final View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1_1:
                    mCropImageView.setCropMode(CropImageView.CropMode.SQUARE);
                    break;
                case R.id.button3_4:
                    mCropImageView.setCropMode(CropImageView.CropMode.RATIO_3_4);
                    break;
                case R.id.button4_3:
                    mCropImageView.setCropMode(CropImageView.CropMode.RATIO_4_3);
                    break;
                case R.id.button9_16:
                    mCropImageView.setCropMode(CropImageView.CropMode.RATIO_9_16);
                    break;
                case R.id.button16_9:
                    mCropImageView.setCropMode(CropImageView.CropMode.RATIO_16_9);
                    break;
                case R.id.button7_5:
                    mCropImageView.setCustomRatio(7, 5);
                    break;
                case R.id.fitBtn:
                    mCropImageView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
                    break;
                case R.id.squareBtn:
                    mCropImageView.setCropMode(CropImageView.CropMode.SQUARE);
                    break;
                case R.id.freeBtn:
                    mCropImageView.setCropMode(CropImageView.CropMode.FREE);
                    break;
                case R.id.circleBtn:
                    mCropImageView.setCropMode(CropImageView.CropMode.CIRCLE);
                    break;
                case R.id.circleSquareBtn:
                    mCropImageView.setCropMode(CropImageView.CropMode.CIRCLE_SQUARE);
                    break;
                case R.id.rotateLeftBtn:
                    mCropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
                    break;
                case R.id.rotateRightBtn:
                    mCropImageView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);
                    break;
                case R.id.applyFilterBtn:
                    mFilterOptionsVS.setVisibility(View.GONE);
                    break;
                case R.id.cancelFilterBtn:
                    mFilterOptionsVS.setVisibility(View.GONE);
                    mCropImageView.setImageBitmap(mBitmapBeforeApplyingFilter);
                    break;
            }
        }
    };

    @OnClick(R.id.pickBtn)
    public void pickImage() {
        CropUtils.pickImage(this);
    }

    @OnClick(R.id.ratioBtn)
    public void showRatios() {
        resetStubs();
        mRatioOptionsVS.setVisibility(View.VISIBLE);
        findViewById(R.id.button1_1).setOnClickListener(btnListener);
        findViewById(R.id.button3_4).setOnClickListener(btnListener);
        findViewById(R.id.button4_3).setOnClickListener(btnListener);
        findViewById(R.id.button9_16).setOnClickListener(btnListener);
        findViewById(R.id.button16_9).setOnClickListener(btnListener);
        findViewById(R.id.button7_5).setOnClickListener(btnListener);
    }

    @OnClick(R.id.shapeBtn)
    public void showShapes() {
        resetStubs();
        mShapeOptionsVS.setVisibility(View.VISIBLE);
        findViewById(R.id.fitBtn).setOnClickListener(btnListener);
        findViewById(R.id.squareBtn).setOnClickListener(btnListener);
        findViewById(R.id.freeBtn).setOnClickListener(btnListener);
        findViewById(R.id.circleBtn).setOnClickListener(btnListener);
        findViewById(R.id.circleSquareBtn).setOnClickListener(btnListener);
    }

    @OnClick({R.id.filterBtn, R.id.rotateBtn, R.id.cornersBtn, R.id.colorBtn})
    public void showFilters(View view) {
        resetStubs();
        mBitmapBeforeApplyingFilter = mCropImageView.getImageBitmap();
        mFilterOptionsVS.setVisibility(View.VISIBLE);
        findViewById(R.id.applyFilterBtn).setOnClickListener(btnListener);
        findViewById(R.id.cancelFilterBtn).setOnClickListener(btnListener);
        RecyclerView filtersRV = (RecyclerView) findViewById(R.id.filtersRV);
        filtersRV.setLayoutManager(mLayoutManager);
        filtersRV.setAdapter(mAdapter);
        mAdapter.setUri(mUri);
        switch (view.getId()) {
            case R.id.filterBtn:
                mAdapter.setPhotoFilterCategory(FilterBitmapTransformation.FilterCategory.ALL);
                break;
            case R.id.rotateBtn:
                mAdapter.setPhotoFilterCategory(FilterBitmapTransformation.FilterCategory.ROTATION);
                break;
            case R.id.cornersBtn:
                mAdapter.setPhotoFilterCategory(FilterBitmapTransformation.FilterCategory.CORNERS);
                break;
            case R.id.colorBtn:
                mAdapter.setPhotoFilterCategory(FilterBitmapTransformation.FilterCategory.COLOR);
                break;
            default:
        }
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.blurBtn)
    public void showBlurOptions() {
        resetStubs();
    }


    @SuppressLint("StaticFieldLeak")
    private void cropImage() {
/*
        showProgressDialogFragment();
        mCropImageView.startCrop(mCropCallback);
*/

        new DialogAsyncTask<Bitmap, Void, Uri>() {

            @Override
            protected Activity getActivity() {
                return ImageEditActivity.this;
            }

            @Override
            protected Uri doInBackground(final Bitmap... params) {
                BeeLog.i(TAG,"doInBackground START");
                final Uri[] compressedImage = new Uri[1];
                ImageCompressor.getDefault(ImageEditActivity.this, new ImageCompressor.CompressCallBack() {
                    @Override
                    public void onFail() {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        params[0].compress(ImageCompressor.COMPRESS_FORMAT, ImageCompressor.QUALITY, out);
                        FileCreate fileCreate = SimpleStorage.getExternalStorage()
                                .createFile(StorageUtils.getPendingUploadDirectory(), System.currentTimeMillis() + ".jpg",
                                        out.toByteArray());
                        if (fileCreate.isCreated) {
                            compressedImage[0] = Uri.fromFile(fileCreate.file);
                        } else {
                            compressedImage[0] = mUri;
                        }
                    }

                    @Override
                    public void onSuccess(Uri uri) {
                        compressedImage[0] = uri;
                    }
                }).compress(params[0]);
                BeeLog.i(TAG,"doInBackground FINISH");
                return compressedImage[0];
            }

            @Override
            protected void onPostExecute(Uri uri) {
                super.onPostExecute(uri);
                Intent intent = new Intent();
                intent.setData(uri);
                setResult(Activity.RESULT_OK, intent);
                ImageEditActivity.this.finish();
            }

        }.execute(mCropImageView.getCroppedBitmap());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == CropUtils.REQUEST_PICK && resultCode == Activity.RESULT_OK) {
            showProgressDialogFragment();
            mUri = result.getData();
            mCropImageView.startLoad(mUri, mLoadCallback);
            if (mFilterOptionsVS.getVisibility() == View.VISIBLE) {
                mAdapter.setUri(mUri);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    // Callbacks ///////////////////////////////////////////////////////////////////////////////////
    private final LoadCallback mLoadCallback = new LoadCallback() {
        @Override
        public void onSuccess() {
            mBitmapBeforeApplyingFilter = mCropImageView.getImageBitmap();
            dismissProgressDialogFragment();
        }

        @Override
        public void onError() {
            dismissProgressDialogFragment();
        }
    };

    private final CropCallback mCropCallback = new CropCallback() {
        @Override
        public void onSuccess(Bitmap cropped) {
        }

        @Override
        public void onError() {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_photo_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done:
                cropImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void showProgressDialogFragment() {
        ProgressDialogFragment f = ProgressDialogFragment.getInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(f, ProgressDialogFragment.class.getSimpleName())
                .commitNow();
        getSupportFragmentManager().executePendingTransactions();
    }

    protected void dismissProgressDialogFragment() {
        ProgressDialogFragment f = (ProgressDialogFragment) getSupportFragmentManager()
                .findFragmentByTag(ProgressDialogFragment.class.getSimpleName());
        if (f != null) {
            getSupportFragmentManager().beginTransaction().remove(f).commitNow();
            getSupportFragmentManager().executePendingTransactions();
        }
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public void onFilterItemClick(final int position, final FilterBitmapTransformation.FilterCategory category) {
        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgressDialogFragment();
            }

            @Override
            protected Bitmap doInBackground(Void... params) {
                return FilterBitmapTransformation.applyFilter(mBitmapBeforeApplyingFilter, position, category);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                mCropImageView.setImageBitmap(bitmap);
                dismissProgressDialogFragment();
            }
        }.execute();
    }
}
