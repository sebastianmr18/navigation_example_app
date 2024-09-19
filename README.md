# 📱 navigation_example_app

> Esta es una aplicación sencilla donde se prueba funcionalidades de Kotlin tales como Toolbar, Navigation Drawer, Tabs con viewpager2 y botton Navigation

## Tabla de contenidos
1. [Intrucciones](##Instrucciones)
2. [Toolbar](###Toolbar)

---

## Instrucciones

A continuación, se explica como implementar las distintas funcionalidades.

### Toolbar:
En la carpeta 'layout' se crea un archivo tollbar.xml. 
IMPORTANTE: 
* Usar CoordinatorLayout
* Anidar la Toolbar así: CoordinatorLayout -> AppBarLayout -> Toolbar
```bash
<!--toolbar.xml-->
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <!-- Se agrega un AppBarLayout para la ToolBar -->
        <com.google.android.material.appbar.AppBarLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <!-- Se agrega una Toolbar-->
            <androidx.appcompat.widget.Toolbar
                android:id="@+id/toolbar"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                app:title="MI toolbal"
                app:menu="@menu/toolbar_menu">

            </androidx.appcompat.widget.Toolbar>


        </com.google.android.material.appbar.AppBarLayout>

    </androidx.coordinatorlayout.widget.CoordinatorLayout>
</layout>
```
En el archivo activity_main.xml, se invoca así:
IMPORTANTE:
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
### Navigation Drawer:
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
