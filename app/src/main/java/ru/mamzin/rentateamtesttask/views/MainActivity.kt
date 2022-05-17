package ru.mamzin.rentateamtesttask.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.mamzin.rentateamtesttask.R
import ru.mamzin.rentateamtesttask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.user_list_menuitem -> {
                    loadFragment(it.itemId)
                }
                R.id.user_menuitem -> {
                    loadFragment(it.itemId)
                }
                R.id.about_menuitem -> {
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag) ?: when (itemId) {
            R.id.user_list_menuitem -> {
                UserListFragment.newInstance()
            }
            R.id.user_menuitem -> {
                UserFragment.newInstance()
            }
            else -> {
                null
            }
        }
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, fragment, tag)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bNav.selectedItemId = R.id.user_list_menuitem
    }
}