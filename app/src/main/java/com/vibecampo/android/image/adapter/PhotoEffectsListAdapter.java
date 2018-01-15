package com.vibecampo.android.image.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vibecampo.android.R;

import ke.co.toshngure.basecode.images.photofilters.FilterBitmapTransformation;

/**
 * Created by Anthony Ngure on 24/02/2017.
 * Email : anthonyngure25@gmail.com.
 * Company : VibeCampo Social Network..
 */

public class PhotoEffectsListAdapter extends
        RecyclerView.Adapter<PhotoEffectsListAdapter.PhotoEffectViewHolder> {

    private Context mContext;
    private Uri mUri;
    private FilterBitmapTransformation.FilterCategory mFilterCategory = FilterBitmapTransformation.FilterCategory.ROTATION;
    private OnItemClickListener onItemClickListener;

    public PhotoEffectsListAdapter(Context context) {
        this.mContext = context;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    public void setPhotoFilterCategory(FilterBitmapTransformation.FilterCategory category){
        this.mFilterCategory = category;
    }

    @Override
    public PhotoEffectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoEffectViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_photo_effect, parent, false));
    }

    @Override
    public void onBindViewHolder(PhotoEffectViewHolder holder, int position) {
        holder.bind(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onFilterItemClick(final int position, final FilterBitmapTransformation.FilterCategory category);
    }

    @Override
    public int getItemCount() {
        return FilterBitmapTransformation.getAvailableFilters(mFilterCategory);
    }

    public class PhotoEffectViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public PhotoEffectViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> onItemClickListener.onFilterItemClick(getAdapterPosition(), mFilterCategory));
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

        }

        @SuppressLint("SetTextI18n")
        private void bind(int position) {
            Glide.with(mContext)
                    .load(mUri)
                    .dontAnimate()
                    .crossFade()
                    .transform(new FilterBitmapTransformation(mContext, position, mFilterCategory))
                    .into(imageView);
            switch (mFilterCategory){
                case CORNERS:
                case ROTATION:
                    textView.setText("-"+(45*position)+"-");
                    break;
                default:
                    textView.setText("-"+position+"-");
            }
        }
    }

}
