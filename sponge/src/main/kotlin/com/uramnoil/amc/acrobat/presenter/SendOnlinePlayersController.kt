package com.uramnoil.amc.acrobat.presenter

import com.uramnoil.amc.acrobat.application.OnlinePlayer
import com.uramnoil.amc.acrobat.application.SendOnlinePlayersUseCase
import kotlinx.coroutines.CoroutineScope

class SendOnlinePlayersController(
    private val sendOnlinePlayersUseCase: SendOnlinePlayersUseCase,
) {
    fun sendOnlinePlayersInfo(onlinePlayers: List<OnlinePlayer>) {
        sendOnlinePlayersUseCase.execute(onlinePlayers)
    }
}