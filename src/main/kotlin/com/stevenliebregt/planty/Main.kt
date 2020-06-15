package com.stevenliebregt.planty

import tornadofx.*

class Main : App(MainView::class)

fun main(args: Array<String>) {
    launch<Main>(args)
}