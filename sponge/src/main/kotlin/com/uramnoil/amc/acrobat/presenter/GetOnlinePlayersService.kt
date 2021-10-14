package com.uramnoil.amc.acrobat.presenter

import com.uramnoil.amc.acrobat.application.OnlinePlayer
import org.spongepowered.api.Server

class GetOnlinePlayersService(private val server: Server) {
    fun execute(): List<OnlinePlayer> {
        return server.onlinePlayers.map {
            OnlinePlayer(
                it.identifier,
                it.name,
                it.connection.latency
            )
        }
    }
}