package com.example.quizer.userCabinet;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.quizer.SingleFragmentActivity;

public class UserCabinetActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment() {
        return new UserCabinetFragment();
    }

   public static Intent newIntent(Context context){
       return new Intent(context,UserCabinetActivity.class);
   }
}
