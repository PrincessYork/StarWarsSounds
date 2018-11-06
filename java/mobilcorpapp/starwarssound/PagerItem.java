package mobilcorpapp.starwarssound;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.List;


public class PagerItem extends Fragment {

    private final static String ARG_POSITION = "mobilcorpapp.starwarssound.PagerItem.position";
    private final static String ARG_CONTENT_COUNT = "mobilcorpapp.starwarssound.PagerItem.contentСount";

    private int position;
    private int contentСount;

    private TableLayout tableLayout;

    public PagerItem() {}

    public static PagerItem newInstance(int position, int content_count) {
        PagerItem fragment = new PagerItem();
        Bundle args = new Bundle();

        args.putInt(ARG_POSITION, position);
        args.putInt(ARG_CONTENT_COUNT, content_count);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
            contentСount = getArguments().getInt(ARG_CONTENT_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager_item, container, false);
        tableLayout = view.findViewById(R.id.pager_item_table_layout);

        final List<Sound> sounds = MainActivity.player.getSounds();
        TableRow tableRow = createNewRow();

        for (int i = position * contentСount; i < (position + 1) * contentСount && i < sounds.size(); i++) {
            final Sound sound = sounds.get(i);
            if (tableRow.getChildCount() >= 2)
                tableRow = createNewRow();

            Button button = (Button) getLayoutInflater().inflate(R.layout.sound_button, null, false);
            button.setText(sound.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.player.play(sound);
                }
            });

            tableRow.addView(button);
        }

        return view;
    }

    private TableRow createNewRow() {
        TableRow tableRow = new TableRow(getActivity());
        tableRow.setGravity(Gravity.CENTER);
        tableRow.setPadding(8, 8, 8, 8);
        tableLayout.addView(tableRow);
        return tableRow;
    }

}
