package com.example.pokedex.main.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.databinding.MainLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // TODO use layer list or gradient to implement inner shadow
}