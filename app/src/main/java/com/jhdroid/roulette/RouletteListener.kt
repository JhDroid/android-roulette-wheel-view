package com.jhdroid.roulette

interface RouletteListener {
    fun onRotateAnimationStart()
    fun onRotateAnimationEnd(result: String)
}