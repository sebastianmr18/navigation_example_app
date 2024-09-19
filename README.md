# 📱 navigation_example_app

> Esta es una aplicación sencilla donde se prueba funcionalidades de Kotlin tales como Toolbar, Navigation Drawer, Tabs con viewpager2 y bottom Navigation

> Se deben agregar las siguientes dependencias en el build.gradel.kts de la aplicación.
```bash
dependencies {
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.google.android.material:material:1.9.0")
```

## Tabla de contenidos
1. [Intrucciones](##Instrucciones)
2. [Toolbar](###Toolbar)
3. [Navigation Drawer](###NavigationDrawer)
4. [Tabs y ViewPager2](###Tabs)
5. [BottomNavigation](###BottomNavigation)

---

## Instrucciones

A continuación, se explica como implementar las distintas funcionalidades.

### Toolbar:
En la carpeta 'layout' se crea un archivo tollbar.xml. 
> IMPORTANTE: 
* Usar CoordinatorLayout
* Anidar la Toolbar así: CoordinatorLayout -> AppBarLayout -> Toolbar
```bash
<!--toolbar.xml-->
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:context=".MainActivity">

        <!-- Se agrega un AppBarLayout para la ToolBar -->
        <com.google.android.material.appbar.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <!-- Se agrega una Toolbar-->
            <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:navigationIcon="@drawable/ic_back"
                app:title="MI toolbal"
                app:menu="@menu/toolbar_menu">

            </androidx.appcompat.widget.Toolbar>


        </com.google.android.material.appbar.AppBarLayout>

    </androidx.coordinatorlayout.widget.CoordinatorLayout>
</layout>
```
En el archivo activity_main.xml, se invoca así:
> IMPORTANTE:
* Inyectar el toolbar utilizando el 'include'
```bash
<!--activity_main.xml-_>
<?xml version="1.0" encoding="utf-8"?>
<layout
...

    <androidx.drawerlayout.widget.DrawerLayout
        android:id="@+id/drawer_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
        <!--tools:openDrawer="start">-->

        <include
            android:id="@+id/contentToolbar"
            layout="@layout/toolbar" />

        <com.google.android.material.navigation.NavigationView
            android:id="@+id/nav_view"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_gravity="start"
            app:headerLayout="@layout/navigation_header"
            app:menu="@menu/navigation_menu"/>




    </androidx.drawerlayout.widget.DrawerLayout>
</layout>
```
Por ultimo, el archivo MainActivity.kt se configura así:
```bash
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
                    // Realizar acción
                    true
                }

                R.id.settingsMenu -> {
                    // Realizar otra acción
                    true
                } else -> false
            }
        }
    }
```
### NavigationDrawer:
En la carpeta 'layout' se crea un archivo header_drawer.xml. 
Consejo: 
* Usar LinearLayout
```bash
<!--header_drawer.xml-->
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/navigationHeader"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="150dp"
    android:padding="16dp"
    android:background="@color/black">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Sebastian Muñoz"
        android:paddingTop="16dp"
        android:textColor="@color/white"/>


</LinearLayout>
```
En el archivo activity_main.xml, se invoca DrawerLayout, y dentro de NavigationView se inyecta el headerLayout:
```bash
<!--activity_main.xml-_>
<?xml version="1.0" encoding="utf-8"?>
<layout
...

    <androidx.drawerlayout.widget.DrawerLayout
        android:id="@+id/drawer_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
        <!--tools:openDrawer="start">-->

        <include
            android:id="@+id/contentToolbar"
            layout="@layout/toolbar" />

        <com.google.android.material.navigation.NavigationView
            android:id="@+id/nav_view"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_gravity="start"
            app:headerLayout="@layout/navigation_header"
            app:menu="@menu/navigation_menu"/>




    </androidx.drawerlayout.widget.DrawerLayout>
</layout>
```
Por ultimo, el archivo MainActivity.kt se configura así:
```bash
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
    }

    private fun setupToolbar() {
        ...
        setupDrawer(toolbar)
        ...
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
                    startActivity(Intent(this, MainActivity2::class.java))
                    drawerLayout.closeDrawer(navView)
                    true
                }

                R.id.nav_iten2 -> {
                    Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show()
                    true
                } else -> false
            }
        }
    }
```
### Tabs:
En la carpeta 'layout' se crea un nuevo activity_main2.xml. 
```bash
<!--activity_main2.xml-->
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity2">

        <include
            android:id="@+id/contentToolbar"
            layout="@layout/toolbar"
            />

        <com.google.android.material.tabs.TabLayout
            android:id="@+id/tabLayout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:tabTextColor="@color/white"
            android:background="@color/black"
            app:layout_constraintTop_toBottomOf="@id/contentToolbar"/>

        <androidx.viewpager2.widget.ViewPager2
            android:id="@+id/viewPager"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layout_constraintTop_toBottomOf="@id/tabLayout"
            app:layout_constraintBottom_toTopOf="@id/buttonNavigation"
 />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
Se crea un nuevo archivo MainActivity2.kt
```bash
<!--MainActivity2.kt-->
package com.example.navigation_example_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.navigation_example_app.databinding.ActivityMain2Binding
import ViewPagerAdapter
import android.content.Intent
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        ...
        setupTabs()
        ...
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
    ...
}
```
En la carpeta de los archivos Kt (com.example.[nombre_app]), se crea un nuevo archivo, en este caso ViewPagerAdapter (el nombre puede ser diferente, pero debe coincidir con el invocado en la función setupTabs)
```bash
<!--ViewPagerAdapter.kt-->
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.navigation_example_app.Fragment1
import com.example.navigation_example_app.Fragment2

class ViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Fragment1()
            1 -> Fragment2()
            else -> Fragment1()
        }
    }
}
```
Vemos que aparecen dos 'Fragment', estos se crearon como BlankFragment, automaticamente se crea un .kt y un .xml, pero de momento no se profundiza en esto.
### BottomNavigation:
Dentro de activity_main2.xml, se escribe esta función. 
```bash
<!--activity_main2.xml-->
...
        <com.google.android.material.bottomnavigation.BottomNavigationView
            android:id="@+id/buttonNavigation"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:menu="@menu/menu_button_navigation"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            />
...
```
¿Como se crean las opciones que se muestran en pantalla? Se debe crear dentro de la carpeta menu un archivo así (notesé que esta siendo invocado desde app:menu)
> Esto sirve para agregar cualquier tipo de menu a un layout.
```bash
<!--menu_button_navigation.xml-->
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/button1"
        android:title="Home"/>

    <item
        android:id="@+id/button2"
        android:title="Favorite"/>

    <item
        android:id="@+id/button3"
        android:title="Music"/>

</menu>
```
En el archivo. kt, se muestra a traves de esta función.
```bash
<!--MainActivity2.kt-->
...
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ...
        setupButtonNavigation()
        ...
    }

    private fun setupButtonNavigation(){
        binding.buttonNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.button1 -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Has vuelto a la pagina prinicial", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.button2 -> {
                    Toast.makeText(this, "Favorito", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.button3 -> {
                    Toast.makeText(this, "Música", Toast.LENGTH_SHORT).show()
                    true
                } else -> false
            }
        }
    }
...
```
