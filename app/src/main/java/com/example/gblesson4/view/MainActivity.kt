package com.example.gblesson4.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.gblesson4.R
import com.example.gblesson4.view.cities.CitiesListFragment
import com.example.gblesson4.view.history.HistoryListFragment
import com.example.gblesson4.view.map.MapsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, CitiesListFragment.newInstance())
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.history_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HistoryListFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }

            R.id.map_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MapsFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }

            R.id.contacts_menu_item -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ContactsFragment.newInstance())
                    .addToBackStack("")
                    .commit()
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }

        return true
    }
}