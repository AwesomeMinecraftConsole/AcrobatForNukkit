package com.uramnoil.amc.acrobat.infrastructure.application

import com.uramnoil.amc.acrobat.application.AcrobatClientService
import com.uramnoil.amc.acrobat.application.OnlinePlayer
import com.uramnoil.amc.acrobat.application.SendOnlinePlayersUseCase

class SendPlayersUseCaseImpl(private val acrobatClientService: AcrobatClientService) : SendOnlinePlayersUseCase {
    override fun execute(onlinePlayers: List<OnlinePlayer>) {
        acrobatClientService.sendOnlinePlayers(onlinePlayers)
    }
}