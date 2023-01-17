package com.jhdroid.roulette

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jhdroid.roulette.databinding.ActivityMainBinding
import com.jhdroid.view.RotateListener
import com.jhdroid.view.Roulette

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val rouletteData = listOf("JhDroid", "Android", "Blog", "IT", "Developer", "Kotlin", "Java", "Happy")

    @SuppressLint("SetTextI18n")
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
            rouletteSize = Roulette.ROULETTE_MAX_SIZE
            binding.rouletteSizeTv.text = rouletteSize.toString()
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
        var rouletteSize = binding.roulette.rouletteSize
        if (rouletteSize == Roulette.ROULETTE_MAX_SIZE) return

        binding.roulette.rouletteSize = ++rouletteSize
        binding.rouletteSizeTv.text = rouletteSize.toString()
    }

    private fun minusRouletteSize() {
        if (binding.roulette.isRotate) return

        var rouletteSize = binding.roulette.rouletteSize
        if (rouletteSize == Roulette.ROULETTE_MIN_SIZE) return

        binding.roulette.rouletteSize = --rouletteSize
        binding.rouletteSizeTv.text = rouletteSize.toString()
    }
}