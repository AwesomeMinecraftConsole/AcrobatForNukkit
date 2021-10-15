package com.uramnoil.amc.acrobat.infrastructure.application

import com.uramnoil.amc.acrobat.application.AcrobatClientService
import com.uramnoil.amc.acrobat.application.StopAcrobatConnectionUseCase

class StopAcrobatConnectionUseCaseImpl(private val service: AcrobatClientService) : StopAcrobatConnectionUseCase {
    override fun execute() {
        service.disconnect()
    }
}