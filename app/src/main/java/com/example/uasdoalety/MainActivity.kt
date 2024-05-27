package com.example.uasdoalety
//Juan Pablo Jimenez
//Juan Esteban Sanchez
//Steven Naranjo Rojas
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uasdoalety.databinding.ActivityMainBinding
import com.example.uasdoalety.databinding.NavHeaderMainBinding
import com.example.uasdoalety.ui.cita.crearCita
import com.example.uasdoalety.ui.cita.iniciarSesion
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHeaderMainBinding: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener {
            val intent = Intent(this, crearCita::class.java)
            startActivity(intent)
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        
        val headerView = navView.getHeaderView(0)
        navHeaderMainBinding = NavHeaderMainBinding.bind(headerView)

        val imageButton: ImageButton = navHeaderMainBinding.imageView
        imageButton.setOnClickListener {
            val intent = Intent(this, iniciarSesion::class.java)
            startActivity(intent)
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}