package com.uramnoil.amc.acrobat.application

data class OnlinePlayer(val id: String, val name: String, val ping: Int)

interface AcrobatClientService {
    fun sendOnlinePlayers(onlinePlayers: List<OnlinePlayer>)
    suspend fun connect()
    fun disconnect()
}