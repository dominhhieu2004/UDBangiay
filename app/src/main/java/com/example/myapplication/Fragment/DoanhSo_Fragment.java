package com.example.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Dao.ThongKeDao;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoanhSo_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoanhSo_Fragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public DoanhSo_Fragment() {
        // Required empty public constructor
    }


    public static DoanhSo_Fragment newInstance(String param1, String param2) {
        DoanhSo_Fragment fragment = new DoanhSo_Fragment();
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

    View view;
    EditText edt_tuNgay, edt_denNgay;
    TextView txt_DoanhThu;
    ThongKeDao thongKeDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_doanh_so_, container, false);
        edt_tuNgay = view.findViewById(R.id.edt_TuNgay);
        edt_denNgay = view.findViewById(R.id.edt_DenNgay);
        txt_DoanhThu = view.findViewById(R.id.txt_DoanhThu);
        thongKeDao = new ThongKeDao(getContext());

        view.findViewById(R.id.btn_TuNgay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(edt_tuNgay);
            }
        });

        view.findViewById(R.id.btn_DenNgay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(edt_denNgay);
            }
        });

        view.findViewById(R.id.btn_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tuNgay = edt_tuNgay.getText().toString();
                String denNgay = edt_denNgay.getText().toString();

                if (!tuNgay.isEmpty() && !denNgay.isEmpty()) {
                    txt_DoanhThu.setText(thongKeDao.DoanhThu(tuNgay, denNgay) + " VND");
                } else {
                    // Xử lý khi người dùng không nhập đầy đủ ngày
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ ngày", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, yearSelected, monthOfYear, dayOfMonthSelected) -> {
                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(yearSelected, monthOfYear, dayOfMonthSelected);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(selectedDateCalendar.getTime());
                    editText.setText(selectedDate);
                },
                year,
                month,
                dayOfMonth
        );
        datePickerDialog.show();
    }
}