package com.uramnoil.amc.acrobat.listener

import com.uramnoil.amc.acrobat.presenter.AcrobatController
import com.uramnoil.amc.acrobat.presenter.GetOnlinePlayersService
import org.spongepowered.api.event.EventListener
import org.spongepowered.api.event.network.ClientConnectionEvent

class PlayerJoinEventListener(private val controller: AcrobatController, private val service: GetOnlinePlayersService) :
    EventListener<ClientConnectionEvent.Join> {
    override fun handle(event: ClientConnectionEvent.Join) {
        controller.sendOnlinePlayersInfo(service.execute())
    }
}