package tn.esprit.sensors.sites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import tn.esprit.sensors.R;
import tn.esprit.sensors.main.MainActivity;

public class CampsiteFragment extends Fragment {

    private static final String ARG_ADDITIONAL_DATA = "argAdditionalData";

    private static final String ARG_IMAGE_RESOURCE = "argImageResource";
    private static final String ARG_IMAGE_NAME = "argimageName";

    public CampsiteFragment() {
        // Required empty public constructor
    }

    public static CampsiteFragment newInstance(int imageResource, String imageName, String additionalData) {
        CampsiteFragment fragment = new CampsiteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RESOURCE, imageResource);
        args.putString(ARG_IMAGE_NAME, imageName);
        args.putString(ARG_ADDITIONAL_DATA, additionalData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campsite, container, false);

        ImageView imageView = view.findViewById(R.id.campsiteImageView);
        TextView descriptionTextView = view.findViewById(R.id.campsiteDescriptionTextView);
        ImageButton btnClose = view.findViewById(R.id.btnClose);

        // Retrieve arguments
        Bundle args = getArguments();
        if (args != null) {
            int imageResource = args.getInt(ARG_IMAGE_RESOURCE);
            String description = args.getString(ARG_IMAGE_NAME);

            // Set the image and description
            imageView.setImageResource(imageResource);
            descriptionTextView.setText(description);
        }

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the fragment and revert visibility
                closeFragment();
            }
        });

        return view;
    }


    // Method to close the fragment and revert visibility
    private void closeFragment() {
        if (getParentFragmentManager() != null) {
            getParentFragmentManager().popBackStack();

            // Revert visibility in MainActivity
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).setVisible(false);
            }
        }
    }
}
