package com.example.myapplication.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.myapplication.Dao.QTVDao;
import com.example.myapplication.Model.QTV;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class thongtinUserFragment extends Fragment {

    TextInputEditText txt_PassOld, txt_PassNew, txt_PassConf;
    QTVDao qtvDao;
    ArrayList<QTV> listQTV;
    QTV qtv;
    String maQTV = "admin"; // Thay thế bằng giá trị thực tế của maQTV

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thongtin_user, container, false);
        txt_PassOld = v.findViewById(R.id.edpassOld);
        txt_PassNew = v.findViewById(R.id.edpassNew);
        txt_PassConf = v.findViewById(R.id.edPassConf);

        qtvDao = new QTVDao(getContext());
        listQTV = new ArrayList<>();


        v.findViewById(R.id.btnDoiMK_admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passO = txt_PassOld.getText().toString();
                String passN = txt_PassNew.getText().toString();
                String passC = txt_PassConf.getText().toString();

                // Lấy đối tượng QTV trước khi validate
                qtv = qtvDao.getID(maQTV);
                if (qtv != null && validate(passO, passN, passC)) {
                    qtv.setMatKhau(passN);
                    if (qtvDao.update(qtv) > 0) {
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        txt_PassOld.setText("");
                        txt_PassNew.setText("");
                        txt_PassConf.setText("");
                    } else {
                        Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else if (qtv == null) {
                    Toast.makeText(getContext(), "Không tìm thấy QTV với ID đã cho", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }

    public boolean validate(String passOld, String passNew, String passConf) {
        if (!passOld.isEmpty() && !passNew.isEmpty() && !passConf.isEmpty()) {
            QTV qq = qtvDao.getID(maQTV);
            if (!passOld.equals(qq.getMatKhau())) {
                Toast.makeText(getContext(), "Mật khẩu cũ chưa chính xác !", Toast.LENGTH_SHORT).show();
                txt_PassOld.setError("Mật khẩu cũ chưa chính xác !");
                return false;
            } else if (!passNew.equals(passConf)) {
                txt_PassConf.setError("Nhập lại mật khẩu chưa chính xác");
                return false;
            }
        } else {
            if (passOld.isEmpty()) {
                txt_PassOld.setError("Vui lòng nhập mật khẩu cũ");
                txt_PassOld.requestFocus();
            }
            if (passNew.isEmpty()) {
                txt_PassNew.setError("Vui lòng nhập mật khẩu mới");
                txt_PassNew.requestFocus();
            }
            if (passConf.isEmpty()) {
                txt_PassConf.setError("Vui lòng nhập lại mật khẩu");
            }
            return false;
        }
        return true;
    }
}
