package com.affise.attribution.usecase

import com.affise.attribution.events.GDPREventRepository
import com.affise.attribution.events.predefined.GDPREvent
import com.affise.attribution.executors.ExecutorServiceProvider
import com.affise.attribution.network.CloudConfig
import com.affise.attribution.network.CloudRepository
import com.affise.attribution.parameters.factory.PostBackModelFactory

internal class SendGDPREventUseCaseImpl(
    private val repository: GDPREventRepository,
    private val serviceProvider: ExecutorServiceProvider,
    private val cloudRepository: CloudRepository,
    private val postBackModelFactory: PostBackModelFactory,
    private val eraseUserDataUseCase: EraseUserDataUseCaseImpl

) {
    fun registerForgetMeEvent(userData: String) {
        serviceProvider.provideExecutorService().execute {
            repository.runCatching { setEvent(GDPREvent(userData)) }
                .getOrNull()
                ?.also { sendForgetMeEvent() }
        }
    }

    // For each url in CloudConfig send postback with GDPREvent
    // if atleast one is succeed, erase user data
    fun sendForgetMeEvent() {
        serviceProvider.provideExecutorService().execute {
            val serializedEvent = repository.getEvent() ?: return@execute

            var isSucceed = false

            CloudConfig.getUrls().forEach {
                val postBackModel = postBackModelFactory.create(listOf(serializedEvent))
                try {
                    cloudRepository.send(listOf(postBackModel), it)
                    isSucceed = true
                } catch (e: Throwable) {
                    // do nothing if failed to sent
                }
            }

            if (isSucceed) {
                eraseUserDataUseCase.eraseUserData()
            }
        }
    }
}