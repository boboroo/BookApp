package com.shiny.bookapp.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.shiny.bookapp.R
import com.shiny.bookapp.databinding.ActivityMainBinding
import com.shiny.bookapp.presentation.main.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        if (savedInstanceState == null) {
            val mainFragment = SearchFragment.newInstance()

            mainFragment.lifecycle.addObserver(object: DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    binding.mainFragmentContainer.visibility = View.VISIBLE
                }
            })

            supportFragmentManager.beginTransaction()
                .add(binding.mainFragmentContainer.id, mainFragment)
                .commitAllowingStateLoss()
        }
    }
}