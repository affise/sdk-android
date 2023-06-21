package com.affise.attribution.module.status.usecase

import com.affise.attribution.modules.OnKeyValueCallback

interface CheckStatusUseCase {

    fun send(onComplete: OnKeyValueCallback)
}