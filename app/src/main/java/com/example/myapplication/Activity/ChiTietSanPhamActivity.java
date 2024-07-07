    package com.example.myapplication.Activity;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.myapplication.Dao.HangDao;
    import com.example.myapplication.Dao.SanPhamDao;
    import com.example.myapplication.Model.GioHang;
    import com.example.myapplication.Model.Hang;
    import com.example.myapplication.Model.SanPham;
    import com.example.myapplication.R;
    import com.example.myapplication.Untils.untils;
    import com.squareup.picasso.Picasso;

    import java.util.ArrayList;

    public class ChiTietSanPhamActivity extends AppCompatActivity {

        int maSP;

        TextView tvTenSP, tvHang, tvMoTa, tvGia;
        ImageView images, imagesGH;

        ArrayList<SanPham> lstSP;
        Button btnAddGioHang;

        SanPham sp;
        HangDao hangDao;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chi_tiet_san_pham);
                tvTenSP = findViewById(R.id.tvTenCTSP_kh);
                tvHang = findViewById(R.id.tvHangCTSP_kh);
                tvGia = findViewById(R.id.tvGiaCTSP_kh);
                tvMoTa = findViewById(R.id.tvMoTaCTSP_kh);
                images  = findViewById(R.id.imgCTSP);
                btnAddGioHang = findViewById(R.id.btnAddGioHang);

            //toobar back
            Toolbar toolbar = findViewById(R.id.toolbar_ctsp);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Intent in = getIntent();
            maSP =in.getIntExtra("id", 0);
            SanPhamDao spDao = new SanPhamDao(this);
            sp = spDao.selectID(maSP);

            hangDao = new HangDao(this);
            lstSP =(ArrayList<SanPham>) spDao.getAll();

           Hang hang = hangDao.getID(String.valueOf(sp.getMaHang()));
            // Các xử lý tiếp theo với sp
           tvTenSP.setText("Tên sản phẩm:" + sp.getTenSP().toString());
           tvHang.setText("Tên hãng:"+ hang.getTenHang().toString());
           tvGia.setText("Giá:" + String.valueOf(sp.getGiaTien()));
           tvMoTa.setText("Mô tả:"+sp.getMoTa().toString());

           Picasso.get().load(sp.getImages()).into(images);
            initControl();
        }


   private void initControl(){
       btnAddGioHang.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(untils.mangGioHang.size()<0){
                   Toast.makeText(ChiTietSanPhamActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
               } else {
                   themGioHang();
                   Toast.makeText(ChiTietSanPhamActivity.this, "Thêm giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class));
               }
           }
       });
   }

        private void themGioHang() {
            imagesGH = findViewById(R.id.images_gioHang);

              if(untils.mangGioHang.size() > 0){
                  boolean flag = false;
                  int soLuong = 1;
                  for (int i = 0; i< untils.mangGioHang.size(); i++){
                      if(untils.mangGioHang.get(i).getMaSP() == sp.getMaSP()){
                          flag = true;
                      }
                  }

                  if(flag == false){
                      Hang hang = hangDao.getID(String.valueOf(sp.getMaHang()));
                      int gia = sp.getGiaTien() * soLuong;

                      GioHang gioHang = new GioHang();
                      gioHang.setGia(gia);
                      gioHang.setSoluong(soLuong);
                      gioHang.setMaSP(sp.getMaSP());
                      gioHang.setTenSP(sp.getTenSP());
                      gioHang.setImagesGH(sp.getImages());
                      gioHang.setMaHang(hang.getMaHang());

                      untils.mangGioHang.add(gioHang);
                  }

              } else {
                  Hang hang = hangDao.getID(String.valueOf(sp.getMaHang()));
                  int soLuong = 1;
                  int gia = sp.getGiaTien() * soLuong;

                  GioHang gioHang = new GioHang();
                  gioHang.setGia(gia);
                  gioHang.setSoluong(soLuong);
                  gioHang.setMaSP(sp.getMaSP());
                  gioHang.setTenSP(sp.getTenSP());
                  gioHang.setImagesGH(sp.getImages());
                  gioHang.setMaHang(hang.getMaHang());

                  untils.mangGioHang.add(gioHang);
              }
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == android.R.id.home){
                finish();
            }
            return super.onOptionsItemSelected(item);
        }
    }