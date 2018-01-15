package com.vibecampo.android.apptourlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vibecampo.android.R;


/**
 * @author Vlonjat Gashi (vlonjatg)
 */
public class MaterialSlide extends Fragment {

    private static final String ARG_DRAWABLE = "drawable";
    private static final String ARG_TITLE = "title";
    private static final String ARG_CONTENT = "content";

    int drawable;
    String title;
    String content;

    LinearLayout slideRelativeLayout;
    ImageView slideImageView;
    TextView slideTitleTextView;
    TextView slideContentTextView;

    public MaterialSlide() {
    }

    /**
     * Create Material Slide
     *
     * @param imageDrawable Image resource for the slide
     * @param title         String value of the slide title
     * @param content       String value of the slide content
     * @return Returned the isCreated slide
     */
    public static MaterialSlide newInstance(int imageDrawable, String title, String content) {

        MaterialSlide materialSlide = new MaterialSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_DRAWABLE, imageDrawable);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        materialSlide.setArguments(args);

        return materialSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().size() != 0) {
            drawable = getArguments().getInt(ARG_DRAWABLE);
            title = getArguments().getString(ARG_TITLE);
            content = getArguments().getString(ARG_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_material_slide, container, false);

        slideRelativeLayout = (LinearLayout) rootView.findViewById(R.id.slideRelativeLayout);
        slideImageView = (ImageView) rootView.findViewById(R.id.slideImageView);
        slideTitleTextView = (TextView) rootView.findViewById(R.id.slideTitleTextView);
        slideContentTextView = (TextView) rootView.findViewById(R.id.slideContentTextView);

        slideImageView.setImageResource(drawable);
        slideTitleTextView.setText(title);
        slideContentTextView.setText(content);

        return rootView;
    }
}
