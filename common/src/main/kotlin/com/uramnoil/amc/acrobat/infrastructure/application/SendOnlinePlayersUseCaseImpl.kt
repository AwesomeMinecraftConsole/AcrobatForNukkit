package com.uramnoil.amc.acrobat.infrastructure.application

import com.uramnoil.amc.acrobat.application.AcrobatClientService
import com.uramnoil.amc.acrobat.application.OnlinePlayer
import com.uramnoil.amc.acrobat.application.SendOnlinePlayersUseCase

class SendOnlinePlayersUseCaseImpl(private val service: AcrobatClientService) : SendOnlinePlayersUseCase {
    override fun execute(onlinePlayers: List<OnlinePlayer>) {
        service.sendOnlinePlayers(onlinePlayers)
    }
}