package com.jhdroid.roulette

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jhdroid.roulette.databinding.ActivityMainBinding
import com.jhdroid.view.RotateListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val rouletteData = listOf("JhDroid", "Android", "Blog", "IT", "Developer", "Kotlin", "Java", "Happy")

    private val rouletteListener = object : RotateListener {
        override fun onRotateStart() {
            binding.rotateResultTv.text = "Result : "
        }

        override fun onRotateEnd(result: String) {
            binding.rotateResultTv.text = "Result : $result"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupView()
    }

    private fun setupView() {
        binding.roulette.apply {
            setRouletteSize(8)
            binding.rouletteSizeTv.text = getRouletteSize().toString()
            setRouletteDataList(rouletteData)
        }

        binding.sizePlusBtn.setOnClickListener { plusRouletteSize() }
        binding.sizeMinusBtn.setOnClickListener { minusRouletteSize() }
        binding.rotateBtn.setOnClickListener { rotateRoulette() }
    }

    private fun rotateRoulette() {
        val toDegrees = (2000..10000).random().toFloat()
        binding.roulette.rotateRoulette(toDegrees, 4000, rouletteListener)
    }

    private fun plusRouletteSize() {
        var rouletteSize = binding.roulette.getRouletteSize()
        if (rouletteSize == 8) return

        binding.roulette.setRouletteSize(++rouletteSize)
        binding.rouletteSizeTv.text = rouletteSize.toString()
    }

    private fun minusRouletteSize() {
        var rouletteSize = binding.roulette.getRouletteSize()
        if (rouletteSize == 2) return

        binding.roulette.setRouletteSize(--rouletteSize)
        binding.rouletteSizeTv.text = rouletteSize.toString()
    }
}