package com.uramnoil.amc.acrobat.listener

import com.uramnoil.amc.acrobat.presenter.AcrobatController
import com.uramnoil.amc.acrobat.presenter.GetOnlinePlayersService
import org.spongepowered.api.event.EventListener
import org.spongepowered.api.event.network.ClientConnectionEvent

class ClientDisconnectEventListener(private val controller: AcrobatController, private val service: GetOnlinePlayersService) : EventListener<ClientConnectionEvent.Disconnect> {
    override fun handle(event: ClientConnectionEvent.Disconnect) {
        controller.sendOnlinePlayersInfo(service.execute())
    }
}