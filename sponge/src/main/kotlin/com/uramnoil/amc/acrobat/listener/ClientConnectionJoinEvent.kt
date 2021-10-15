package com.uramnoil.amc.acrobat.listener

import com.uramnoil.amc.acrobat.presenter.SendOnlinePlayersController
import com.uramnoil.amc.acrobat.presenter.GetOnlinePlayersService
import org.spongepowered.api.event.EventListener
import org.spongepowered.api.event.network.ClientConnectionEvent

class ClientConnectionJoinEvent(private val controller: SendOnlinePlayersController, private val service: GetOnlinePlayersService) :
    EventListener<ClientConnectionEvent.Join> {
    override fun handle(event: ClientConnectionEvent.Join) {
        controller.sendOnlinePlayersInfo(service.execute())
    }
}