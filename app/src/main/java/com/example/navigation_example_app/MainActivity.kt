package com.example.navigation_example_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.navigation_example_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar = binding.contentToolbar.toolbar
        setSupportActionBar(toolbar)
        setupDrawer(toolbar)
        toolbar.title = "Titulo desde logica"
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.newGroupMenu -> {
                    //Tarea
                    true
                }

                R.id.settingsMenu -> {
                    //Tarea
                    true
                } else -> false
            }
        }
    }

    private fun setupDrawer(toolbar: Toolbar) {
        val drawerLayout = binding.drawerLayout
        val navView = binding.navView

        //Esta linea aplica el color original a los iconos
        navView.itemIconTintList = null

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer
        )
        // Aplicar color al drawable
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.white)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //Configuracón del listener del Navigation Drawer
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item1 -> {
                    Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_iten2 -> {
                    Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show()
                    true
                } else -> false
            }
        }
    }
}