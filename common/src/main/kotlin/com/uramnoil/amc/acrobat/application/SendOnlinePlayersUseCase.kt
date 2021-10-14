package com.uramnoil.amc.acrobat.application


fun interface SendOnlinePlayersUseCase {
    fun execute(onlinePlayers: List<OnlinePlayer>)
}