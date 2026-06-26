package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.data.local.RepairDatabase
import com.example.data.repository.RepairRepository
import com.example.ui.screens.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.RepairViewModel
import com.example.ui.viewmodel.RepairViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Initialize local Room Database
        val database = RepairDatabase.getDatabase(this)

        // 2. Instantiate Repository
        val repository = RepairRepository(database.repairDao())

        // 3. Instantiate ViewModel via Factory
        val factory = RepairViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[RepairViewModel::class.java]

        setContent {
            MyApplicationTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
