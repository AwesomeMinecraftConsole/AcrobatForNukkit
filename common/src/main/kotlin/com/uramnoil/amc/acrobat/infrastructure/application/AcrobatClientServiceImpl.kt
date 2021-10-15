package com.uramnoil.amc.acrobat.infrastructure.application

import com.uramnoil.amc.acrobat.application.AcrobatClientService
import com.uramnoil.amc.acrobat.application.OnlinePlayer
import com.uramnoil.amc.acrobat.grpc.AcrobatClient
import com.uramnoil.amc.acrobat.grpc.AcrobatClientImpl
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.CoroutineContext

class AcrobatClientServiceImpl(host: String, port: Short, context: CoroutineContext) :
    AcrobatClientService,
    CoroutineScope by CoroutineScope(context + Job(context.job)) {
    private val builder = NettyChannelBuilder.forAddress(host, port.toInt()).build()
    private val onlinePlayersFlow = MutableSharedFlow<List<OnlinePlayer>>()
    private var client: AcrobatClient? = null

    override fun sendOnlinePlayers(onlinePlayers: List<OnlinePlayer>) {
        launch {
            onlinePlayersFlow.emit(onlinePlayers)
        }
    }

    override suspend fun connect() {
        launch {
            val client = AcrobatClientImpl(builder, onlinePlayersFlow, coroutineContext)
            client.connect()
            this@AcrobatClientServiceImpl.client = client
        }.join()
    }

    override fun disconnect() {
        client?.shutdown()
        coroutineContext.cancelChildren()
    }
}