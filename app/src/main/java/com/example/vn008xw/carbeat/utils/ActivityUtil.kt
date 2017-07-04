package com.example.vn008xw.carbeat.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.vn008xw.carbeat.R

class ActivityUtil {

    companion object {
        @JvmStatic
        fun replaceFragmentInActivity(manager: FragmentManager, fragment: Fragment?, frameId: Int) {
            if (fragment != null){
                manager.apply{
                    beginTransaction()
                            .replace(frameId, fragment)
                            .commit()
                }
            }
        }

        @JvmStatic
        fun replaceFragmentSlideIn(manager: FragmentManager, fragment: Fragment?, frameId: Int) {
            if (fragment != null){
                manager.apply{
                    beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_from_right, android.R.anim.slide_out_right)
                            .replace(frameId, fragment)
                            .commit()
                }
            }
        }
    }
}