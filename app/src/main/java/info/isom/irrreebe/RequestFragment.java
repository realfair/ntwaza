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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {
    public View view;
    public List<IdentityModel> list;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_request, container, false);
        initRecycler();
        return view;
    }
    public void initRecycler() {
        list = new ArrayList<IdentityModel>();
        list.add(new IdentityModel(R.drawable.defoult, "abc", "abc"));
        list.add(new IdentityModel(R.drawable.defoult, "abc", "abc"));
        list.add(new IdentityModel(R.drawable.defoult, "abc", "abc"));
        list.add(new IdentityModel(R.drawable.defoult, "abc", "abc"));
        list.add(new IdentityModel(R.drawable.defoult, "abc", "abc"));

        RecyclerView recyclerView = view.findViewById(R.id.display_national_id);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new IdentityList(R.layout.search_row, list, getActivity()));
    }

}
