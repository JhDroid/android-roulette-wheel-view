package com.jhdroid.roulette

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jhdroid.roulette.databinding.ActivityMainBinding
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        val rouletteData = listOf("JhDroid", "Android", "Blog", "IT", "Developer", "Kotlin", "Java", "Happy")

        binding.roulette.apply {
            setRouletteSize(8)
            binding.rouletteSizeTv.text = getRouletteSize().toString()
            setRouletteDataList(rouletteData)
        }
    }

    fun rotateRoulette() {
        val rouletteListener = object : RotateListener {
            override fun onRotateStart() {
                binding.rotateResultTv.text = "Result : "
            }

            override fun onRotateEnd(result: String) {
                binding.rotateResultTv.text = "Result : $result"
            }
        }

        binding.roulette.rotateRoulette(3, 10, 4000, rouletteListener)
    }

    fun rotateRouletteRx() {
        binding.roulette
            .rotateRoulette(3, 10, 4000)
            .subscribe(object : SingleObserver<String> {
                override fun onSuccess(t: String) {
                    binding.rotateResultTv.text = "Result : $t"
                }

                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {}
            })
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