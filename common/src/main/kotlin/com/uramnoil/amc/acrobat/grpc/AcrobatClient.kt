package com.uramnoil.amc.acrobat.grpc

import awesome_minecraft_console.endervision.AcrobatGrpcKt
import awesome_minecraft_console.endervision.Loyalwolf
import com.uramnoil.amc.acrobat.application.OnlinePlayer
import io.grpc.ManagedChannel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

interface AcrobatClient {
    suspend fun connect()
    suspend fun shutdown()
}

data class OnlinePlayer(val id: String, val name: String, val ping: Int)

fun List<OnlinePlayer>.build(): Loyalwolf.OnlinePlayersRequest {
    val onlinePlayers = this.map {
        Loyalwolf.OnlinePlayer.newBuilder().setId(it.id).setName(it.name).setPing(it.ping).build()
    }
    return Loyalwolf.OnlinePlayersRequest.newBuilder().addAllOnlinePlayers(onlinePlayers).build()
}

class AcrobatClientImpl(channel: ManagedChannel, private val onlinePlayersFlow: Flow<List<OnlinePlayer>>, context: CoroutineContext) :
    AcrobatClient,
    CoroutineScope by CoroutineScope(context + Job(context.job))
{
    private val stub = AcrobatGrpcKt.AcrobatCoroutineStub(channel).withWaitForReady()

    override suspend fun connect() {
        coroutineScope {
            launch {
                stub.onlinePlayers(onlinePlayersFlow.map { it.build() })
            }
        }.join()
    }

    override suspend fun shutdown() {

        coroutineContext.cancel()
    }
}