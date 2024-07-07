package com.example.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Adapter.HoaDonAdapter;
import com.example.myapplication.Dao.HoaDonDao;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HoaDonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HoaDonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HoaDonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HoaDonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HoaDonFragment newInstance(String param1, String param2) {
        HoaDonFragment fragment = new HoaDonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView rcvHoaDon;
    ArrayList<HoaDon> lstHoaDon;

    HoaDonAdapter hoaDonAdapter;

    HoaDonDao hoaDonDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        rcvHoaDon = v.findViewById(R.id.rcv_HoaDon);

        lstHoaDon = new ArrayList<>();
        hoaDonDao = new HoaDonDao(getContext());

        lstHoaDon =(ArrayList<HoaDon>) hoaDonDao.getAll();

        hoaDonAdapter = new HoaDonAdapter(lstHoaDon, getContext(), new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {

            }
        });
        rcvHoaDon.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvHoaDon.setAdapter(hoaDonAdapter);
        return v;
    }


}