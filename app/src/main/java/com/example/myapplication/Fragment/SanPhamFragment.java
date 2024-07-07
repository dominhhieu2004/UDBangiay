package com.example.myapplication.Fragment;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.HangSpinnerAdapter;
import com.example.myapplication.Adapter.SanPhamAdapter;
import com.example.myapplication.Dao.HangDao;
import com.example.myapplication.Dao.SanPhamDao;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.Model.Hang;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SanPhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SanPhamFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SanPhamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SanPhamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SanPhamFragment newInstance(String param1, String param2) {
        SanPhamFragment fragment = new SanPhamFragment();
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

    RecyclerView rcv_sp;
    SanPhamDao sanPhamDao;
    HangDao hangDao;
    ArrayList<Hang> lstHang;
    ArrayList<SanPham> lstSanPham;

    Dialog dialog;

    SanPham sanPham;
    Hang hang;

    SanPhamAdapter sanPhamAdapter;

    EditText edMaSP, edTenSP, edGiaTien, edSoLuong, edImages, edMoTa;

    Spinner spinnerHang;

    HangSpinnerAdapter hangSpinnerAdapter;

    int maHang = 0;
    int getPosition;

    FloatingActionButton fltSP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View v =  inflater.inflate(R.layout.fragment_san_pham, container, false);

      rcv_sp = v.findViewById(R.id.rcv_sp);
      fltSP = v.findViewById(R.id.btn_add_sp);
      sanPhamDao = new SanPhamDao(getContext());
      hangDao = new HangDao(getContext());
      lstHang = new ArrayList<>();
      lstSanPham = new ArrayList<>();

      sanPham = new SanPham();
      hang = new Hang();

      lstHang =(ArrayList<Hang>) hangDao.getAll();

      fltSP.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
               openDiaLog(getContext(), 0);
          }
      });
        loadData();
      return v;
    }

    public void loadData(){
        lstSanPham = (ArrayList<SanPham>) sanPhamDao.getAll();
        sanPhamAdapter = new SanPhamAdapter(getContext(), lstSanPham, new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {
                 getPosition = position;
                 if(type == 0){
                     openDiaLog(getContext(), 1);
                 } else {
                     xoa_sp(String.valueOf(position));
                 }
            }
        });

        rcv_sp.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcv_sp.setAdapter(sanPhamAdapter);
    }

    public void openDiaLog(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sp);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tv_title = dialog.findViewById(R.id.tv_title_sp);
        edMaSP = dialog.findViewById(R.id.edt_maSP);
        edTenSP = dialog.findViewById(R.id.edt_tenSP_ad);
        edGiaTien = dialog.findViewById(R.id.edt_giaTien_ad);
        edSoLuong = dialog.findViewById(R.id.edt_soLuong_ad);
        edMoTa = dialog.findViewById(R.id.edt_moTaSP_ad);
        edImages = dialog.findViewById(R.id.edt_imagesSP_ad);
        spinnerHang = dialog.findViewById(R.id.spinner_Hang);

        Button btnSave = dialog.findViewById(R.id.btn_save_diaSP);
        Button btnOut = dialog.findViewById(R.id.btn_cancel_diaSP);

        hangSpinnerAdapter = new HangSpinnerAdapter(getContext(), R.layout.item_view_spinner, lstHang);
        spinnerHang.setAdapter(hangSpinnerAdapter);
        spinnerHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               maHang = lstHang.get(i).getMaHang();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(type != 0){
            sanPham = lstSanPham.get(getPosition);
            tv_title.setText("Cập nhật thông tin");
            edMaSP.setText(String.valueOf(sanPham.getMaSP()));
            edTenSP.setText(sanPham.getTenSP());
            edGiaTien.setText(String.valueOf(sanPham.getGiaTien()));
            edSoLuong.setText(String.valueOf(sanPham.getSoLuong()));
            edMoTa.setText(sanPham.getMoTa());
            edImages.setText(sanPham.getImages());

            for (int i = 0; i< lstHang.size(); i++){
                if(sanPham.getMaHang() == lstHang.get(i).getMaHang()){
                    maHang = i;
                }
            }
            spinnerHang.setSelection(maHang);
        } else {
            tv_title.setText("Thêm Sản Phảm");
            edMaSP.setText("Mã Sản Phẩm");
        }

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sanPham = new SanPham();
               sanPham.setTenSP(edTenSP.getText().toString());
               sanPham.setMaHang(maHang);
//               sanPham.setGiaTien(Integer.parseInt(edGiaTien.getText().toString()));
//               sanPham.setSoLuong(Integer.parseInt(edSoLuong.getText().toString()));

                try {
                    sanPham.setGiaTien(Integer.parseInt(edGiaTien.getText().toString()));
                    sanPham.setSoLuong(Integer.parseInt(edSoLuong.getText().toString()));
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Giá tiền và số lượng phải là số nguyên", Toast.LENGTH_SHORT).show();
                    return;
                }

                sanPham.setMoTa(edMoTa.getText().toString());
               sanPham.setImages(edImages.getText().toString());

               if(validate() > 0){
                   if(type == 0){
                       if (sanPhamDao.insert(sanPham) > 0){
                           Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                       }
                   } else {
                       sanPham.setMaSP(Integer.parseInt(edMaSP.getText().toString()));
                       if(sanPhamDao.update(sanPham) > 0){
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

    public int validate(){
        int check = 1;
        if(edTenSP.getText().length() == 0 || edGiaTien.getText().length() == 0 || edSoLuong.getText().length() == 0 || edMoTa.getText().length() == 0 || edImages.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    public void xoa_sp(final String id) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa Sản Phẩm");
        builder.setMessage("Bạn có chắc muốn xóa ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                sanPhamDao.delete(id);
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