package com.example.navigation_example_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.navigation_example_app.databinding.ActivityMain2Binding
import ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.contentToolbar.toolbar.title = "Ventana 2"
        binding.contentToolbar.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.contentToolbar.toolbar.setOnMenuItemClickListener { item  ->
            when(item.itemId){
                R.id.newGroupMenu -> {
                    true
                }
                R.id.settingsMenu -> {
                    true
                } else -> false
            }
        }
    }

    private fun setupTabs() {
        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(this)

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Chats"
                1 -> tab.text = "Novedades"
            }
        }.attach() //importante para hacer visible las pestañas
    }

}