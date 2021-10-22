package com.example.demo

import io.split.client.SplitClient
import io.split.client.SplitClientConfig
import io.split.client.SplitFactoryBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SplitConfig {

    @Value("\${split.io.api.key}")
    private val splitApiKey: String? = null

    @Bean
    @Throws(Exception::class)
    fun splitClient(): SplitClient {
        val config = SplitClientConfig.builder()
            .setBlockUntilReadyTimeout(10000)
            .enableDebug()
            .build()
        val splitFactory = SplitFactoryBuilder.build(splitApiKey, config)
        val client = splitFactory.client()
        client.blockUntilReady()
        return client
    }

}
