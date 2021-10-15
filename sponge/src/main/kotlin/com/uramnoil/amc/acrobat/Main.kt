package com.uramnoil.amc.acrobat

import com.google.inject.Inject
import com.uramnoil.amc.acrobat.application.AcrobatClientService
import com.uramnoil.amc.acrobat.application.SendOnlinePlayersUseCase
import com.uramnoil.amc.acrobat.infrastructure.application.AcrobatClientServiceImpl
import com.uramnoil.amc.acrobat.infrastructure.application.SendOnlinePlayersUseCaseImpl
import com.uramnoil.amc.acrobat.infrastructure.application.StartAcrobatConnectionUseCaseImpl
import com.uramnoil.amc.acrobat.infrastructure.application.StopAcrobatConnectionUseCaseImpl
import com.uramnoil.amc.acrobat.listener.ClientConnectionDisconnectEventListener
import com.uramnoil.amc.acrobat.listener.ClientConnectionJoinEvent
import com.uramnoil.amc.acrobat.presenter.ClientConnectionController
import com.uramnoil.amc.acrobat.presenter.GetOnlinePlayersService
import com.uramnoil.amc.acrobat.presenter.SendOnlinePlayersController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.spongepowered.api.Game
import org.spongepowered.api.Sponge
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.*
import org.spongepowered.api.plugin.Plugin
import java.util.concurrent.CancellationException

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

    private lateinit var acrobatClientService: AcrobatClientService
    private lateinit var clientConnectionController: ClientConnectionController

    @Listener
    fun onPreInitialization(event: GamePreInitializationEvent) {
        acrobatClientService = AcrobatClientServiceImpl(
            System.getenv().getOrDefault("ACROBAT_SERVER_HOST", "localhost"),
            System.getenv().getOrDefault("ACROBAT_SERVER_PORT", "50052").toShort(),
            coroutineContext
        )
        clientConnectionController = ClientConnectionController(
            StartAcrobatConnectionUseCaseImpl(acrobatClientService),
            StopAcrobatConnectionUseCaseImpl(acrobatClientService)
        )
    }

    @Listener
    fun onServerStarting(event: GameStartingServerEvent) = runBlocking {
        // connect
        clientConnectionController.connect()
        logger.info("Connected")

        val getOnlinePlayersService = GetOnlinePlayersService(game.server)
        val sendOnlinePlayersUseCase: SendOnlinePlayersUseCase = SendOnlinePlayersUseCaseImpl(acrobatClientService)
        val sendOnlinePlayersController = SendOnlinePlayersController(sendOnlinePlayersUseCase)

        // register events
        Sponge.getEventManager().run {
            registerListeners(this, ClientConnectionJoinEvent(sendOnlinePlayersController, getOnlinePlayersService))
            registerListeners(
                this,
                ClientConnectionDisconnectEventListener(sendOnlinePlayersController, getOnlinePlayersService)
            )
        }
    }

    @Listener
    fun onServerStopped(event: GameStoppedServerEvent) {
        clientConnectionController.disconnect()
        logger.info("Disconnected")
        coroutineContext.cancelChildren(CancellationException("Plugin stopped"))
    }
}