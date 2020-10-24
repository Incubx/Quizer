package com.example.quizer;

import androidx.fragment.app.Fragment;

import com.example.quizer.userCabinet.RegistrationFragment;



public class MainActivity extends SingleFragmentActivity {


    @Override
    public Fragment createFragment() {
        return new RegistrationFragment();
    }
}