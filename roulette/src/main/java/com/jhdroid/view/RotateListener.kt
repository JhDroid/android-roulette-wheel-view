package com.jhdroid.view

/**
 * 룰렛 회전 애니메이션 시작과 종료 확인을 위한 리스너
 * Roulette rotate animation listener
 * */
interface RotateListener {
    fun onRotateStart()
    fun onRotateEnd(result: String)
}