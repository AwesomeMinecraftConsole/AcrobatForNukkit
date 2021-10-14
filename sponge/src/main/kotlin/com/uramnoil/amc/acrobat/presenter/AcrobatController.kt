package com.uramnoil.amc.acrobat.presenter

import com.uramnoil.amc.acrobat.application.OnlinePlayer
import com.uramnoil.amc.acrobat.application.SendOnlinePlayersUseCase

class AcrobatController(private val sendOnlinePlayersUseCase: SendOnlinePlayersUseCase) {
    fun sendOnlinePlayersInfo(onlinePlayers: List<OnlinePlayer>) {
        sendOnlinePlayersUseCase.execute(onlinePlayers)
    }
    fun connect() {

    }
    fun disconnect() {

    }
}