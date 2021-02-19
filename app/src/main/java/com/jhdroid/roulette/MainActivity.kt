package com.jhdroid.roulette

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jhdroid.roulette.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        val rouletteData = listOf("HELLO!", "HELLO@")

        binding.roulette.apply {
            setRouletteSize(2)
            setRouletteDataList(rouletteData)
        }
    }

    fun rotateRoulette() {
        val toDegrees = (2000..10000).random().toFloat()
        binding.roulette.rotateRoulette(toDegrees, 4000)
    }

    fun plusRouletteSize() {
        var rouletteSize = binding.roulette.getRouletteSize()

        if (rouletteSize == 8) return

        binding.roulette.setRouletteSize(++rouletteSize)
        binding.rouletteSizeTv.text = rouletteSize.toString()
    }

    fun minusRouletteSize() {
        var rouletteSize = binding.roulette.getRouletteSize()

        if (rouletteSize == 2) return

        binding.roulette.setRouletteSize(--rouletteSize)
        binding.rouletteSizeTv.text = rouletteSize.toString()
    }
}