package info.isom.irrreebe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class friendFragment extends Fragment {
    View view;
    ArrayList<IdeasModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_friend, container, false);
        initRecycler();
        return view;
    }

    public void initRecycler() {
        list = new ArrayList<IdeasModel>();
        list.add(new IdeasModel(R.drawable.defoult, "123", "abc"));
        list.add(new IdeasModel(R.drawable.defoult, "123", "abc"));
        list.add(new IdeasModel(R.drawable.defoult, "123", "abc"));
        list.add(new IdeasModel(R.drawable.defoult, "123", "abc"));
        list.add(new IdeasModel(R.drawable.defoult, "123", "abc"));

        RecyclerView recyclerView = view.findViewById(R.id.ideas_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new Ideas(R.layout.ideas_row, list, getActivity()));
    }

}
