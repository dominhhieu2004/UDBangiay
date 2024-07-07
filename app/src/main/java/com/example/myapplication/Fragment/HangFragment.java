package com.example.myapplication.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.HangAdapter;
import com.example.myapplication.Dao.HangDao;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.Model.Hang;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HangFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HangFragment newInstance(String param1, String param2) {
        HangFragment fragment = new HangFragment();
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

    RecyclerView rcvHang;
    FloatingActionButton floatAdd;
    HangDao hangDao;
    ArrayList<Hang> lstHang;
    HangAdapter adapter;

    Dialog dialog;
    EditText edmaHang, edtenHang;
    Button btnHuy, btnSave;

    Hang hang;
    int getPosition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hang, container, false);

        rcvHang = v.findViewById(R.id.rcv_hang);
        hangDao= new HangDao(getContext());
        lstHang = new ArrayList<>();
        floatAdd = v.findViewById(R.id.btn_add_hang);


        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDiaLog(getContext(),0);
            }
        });

        loadData();
        return v;
    }

    public void loadData(){
        lstHang = (ArrayList<Hang>) hangDao.getAll();
        adapter = new HangAdapter(getContext(), lstHang, new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {

                getPosition = position;
                if (type == 0) {
                    openDiaLog(getContext(),1);
                } else {
                    xoa_hang(String.valueOf(position));
                }
            }
        });

        rcvHang.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvHang.setAdapter(adapter);
    }

    public void openDiaLog(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_hang);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView title =dialog.findViewById(R.id.tv_title_hang);
        edmaHang = dialog.findViewById(R.id.edt_maHang);
        edtenHang = dialog.findViewById(R.id.edt_tenHang);

        btnHuy = dialog.findViewById(R.id.btn_cancel_diaHang);
        btnSave = dialog.findViewById(R.id.btn_save_diaHang);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

          if(type != 0){
             hang = lstHang.get(getPosition);
              title.setText("Cập nhật thông tin");
              edmaHang.setText(String.valueOf(hang.getMaHang()));
              edtenHang.setText(hang.getTenHang());
          } else {
              title.setText("Thêm sản phẩm");
              edmaHang.setText("Mã sản phẩm");
          }

      btnSave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
                      hang = new Hang();
                      hang.setTenHang(edtenHang.getText().toString());
                      if(validate() > 0){
                          if(type == 0){
                              if (hangDao.insert(hang) > 0){
                                  Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                              } else {
                                  Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                              }
                          }else {
                              hang.setMaHang(Integer.parseInt(edmaHang.getText().toString()));
                              if(hangDao.update(hang) > 0){
                                  Toast.makeText(context, "Chỉnh sửa thông tin thành công !", Toast.LENGTH_SHORT).show();
                              }else {
                                  Toast.makeText(context, "Chỉnh sửa thông tin thất bại !", Toast.LENGTH_SHORT).show();
                              }
                      }
                          dialog.dismiss();
                          loadData();
                      }
          }
      });

          dialog.show();
    }

    public int validate() {
        int check = 1;
        if (edtenHang.getText().length() == 0 ) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public void xoa_hang(final String id) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa Sản Phẩm");
        builder.setMessage("Bạn có chắc muốn xóa ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                hangDao.delete(id);
                loadData();
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}