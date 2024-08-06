package com.teamsparta.tikitaka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
class TikiTakaApplication

fun main(args: Array<String>) {
    runApplication<TikiTakaApplication>(*args)
}
