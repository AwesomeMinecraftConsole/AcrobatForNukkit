package com.uramnoil.amc.acrobat

import com.google.inject.Inject
import com.uramnoil.amc.acrobat.listener.ClientDisconnectEventListener
import com.uramnoil.amc.acrobat.listener.PlayerJoinEventListener
import com.uramnoil.amc.acrobat.presenter.AcrobatController
import com.uramnoil.amc.acrobat.presenter.GetOnlinePlayersService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.slf4j.Logger
import org.spongepowered.api.Game
import org.spongepowered.api.Server
import org.spongepowered.api.Sponge
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GamePreInitializationEvent
import org.spongepowered.api.event.game.state.GameStartedServerEvent
import org.spongepowered.api.event.game.state.GameStoppingEvent
import org.spongepowered.api.plugin.Plugin

@Plugin(
    id = "acrobat",
    name = "Acrobat",
    version = "0.1",
    description = "Send Server Info to LoyalWolf"
)
class Main : CoroutineScope by CoroutineScope(Dispatchers.Unconfined) {
    @Inject
    private lateinit var logger: Logger
    @Inject
    private lateinit var game: Game

    private lateinit var controller: AcrobatController

    @Listener
    fun onPreInitialization(event: GamePreInitializationEvent) {


        controller.connect()
        logger.info("connected")
    }

    @Listener
    fun onStart(event: GameStartedServerEvent) {
        val service = GetOnlinePlayersService(game.server)
        Sponge.getEventManager().run {
            registerListeners(this, PlayerJoinEventListener(controller, service))
            registerListeners(this, ClientDisconnectEventListener(controller, service))
        }
    }

    @Listener
    fun onPluginDisable(event: GameStoppingEvent) {
        controller.disconnect()
        logger.info("disconnected")
    }
}