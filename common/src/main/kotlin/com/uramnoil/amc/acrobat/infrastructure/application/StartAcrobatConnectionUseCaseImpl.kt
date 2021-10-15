package com.uramnoil.amc.acrobat.infrastructure.application

import com.uramnoil.amc.acrobat.application.AcrobatClientService
import com.uramnoil.amc.acrobat.application.StartAcrobatConnectionUseCase

class StartAcrobatConnectionUseCaseImpl(private val service: AcrobatClientService) : StartAcrobatConnectionUseCase {
    override suspend fun execute() {
        service.connect()
    }
}