package com.uramnoil.amc.acrobat.presenter

import com.uramnoil.amc.acrobat.application.StartAcrobatConnectionUseCase
import com.uramnoil.amc.acrobat.application.StopAcrobatConnectionUseCase

class ClientConnectionController(
    private val startAcrobatConnectionUseCase: StartAcrobatConnectionUseCase,
    private val stopAcrobatConnectionUseCase: StopAcrobatConnectionUseCase
) {
    suspend fun connect() {
        startAcrobatConnectionUseCase.execute()
    }

    fun disconnect() {
        stopAcrobatConnectionUseCase.execute()
    }
}