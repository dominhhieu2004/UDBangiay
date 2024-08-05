package com.example.myapplication.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.myapplication.Activity.Login_adminActivity;
import com.example.myapplication.Fragment.DoanhSo_Fragment;
import com.example.myapplication.Fragment.HangFragment;
import com.example.myapplication.Fragment.HoaDonFragment;
import com.example.myapplication.Fragment.HomeAdminFragment;
import com.example.myapplication.Fragment.SanPhamFragment;
import com.example.myapplication.Fragment.Top10_Fragment;
import com.example.myapplication.Fragment.thongtinUserFragment;
import com.example.myapplication.R;
import com.google.android.material.navigation.NavigationView;

public class HomeAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;

    DrawerLayout drawerLayout;

    NavigationView navigationView;

    ActionBarDrawerToggle drawerToggle;

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_SANPHAM = 1;
    private static final int FRAGMENT_DONHANG = 2;

    private static final int FRAGMENT_HANG = 3;

    private static final int FRAGMENT_DoanhThu = 4;

    private static final int FRAGMENT_TOP = 5;

    private static final int FRAGMENT_DOIMK = 6;
    private int currentFragment = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout_home);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open,
                                                 R.string.navigation_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.navigationView_home);
        navigationView.setNavigationItemSelectedListener(this);

        //màn hình vừa đăng nhập là 1 fragment trong navigation
        replaceFragmnet(new SanPhamFragment());
        navigationView.getMenu().findItem(R.id.sanPham).setChecked(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.donhang){
            if(currentFragment != FRAGMENT_DONHANG){
                toolbar.setTitle("Đơn Hàng");
                replaceFragmnet(new HoaDonFragment());
                currentFragment = FRAGMENT_DONHANG;
            }
        } else if (id == R.id.sanPham) {
            if (currentFragment != FRAGMENT_SANPHAM){
                toolbar.setTitle("Sản Phẩm");
                replaceFragmnet(new SanPhamFragment());
                currentFragment = FRAGMENT_SANPHAM;
            }
        } else if (id == R.id.hang) {
            if (currentFragment != FRAGMENT_HANG){
                toolbar.setTitle("Hãng");
                replaceFragmnet(new HangFragment());
                currentFragment = FRAGMENT_HANG;
            }
        } else if (id == R.id.doanhthu) {

            if(currentFragment != FRAGMENT_DoanhThu){
                toolbar.setTitle("Doanh Thu");
                replaceFragmnet(new DoanhSo_Fragment());
                currentFragment = FRAGMENT_DoanhThu;
            }
        } else if (id == R.id.top10) {
            if(currentFragment != FRAGMENT_TOP){
                toolbar.setTitle("Top 10");
                replaceFragmnet(new Top10_Fragment());
                currentFragment = FRAGMENT_TOP;
            }
        } else if (id == R.id.changePassword) {
            if(currentFragment != FRAGMENT_DOIMK){
                toolbar.setTitle("Đổi Mật Khẩu");
                replaceFragmnet(new thongtinUserFragment());
                currentFragment = FRAGMENT_DOIMK;
            }
        }

        else if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeAdmin.this);
            builder.setMessage("Bạn có chắc chắn muốn đăng xuất tài khoản này không ?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(HomeAdmin.this, Login_adminActivity.class));
                    finishAffinity();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            }

        // chọn vaof item thì sẽ đóng menu
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    //nếu menu đang mở thì ấn vào nút back của máy thì sẽ khong bị thoát app
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void  replaceFragmnet(Fragment fragment){
        FragmentTransaction trans =  getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.frameLayout_home, fragment);
        trans.commit();
    }
}