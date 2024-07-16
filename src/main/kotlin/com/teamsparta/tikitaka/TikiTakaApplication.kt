package com.teamsparta.tikitaka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class TikiTakaApplication

fun main(args: Array<String>) {
    runApplication<TikiTakaApplication>(*args)
}
