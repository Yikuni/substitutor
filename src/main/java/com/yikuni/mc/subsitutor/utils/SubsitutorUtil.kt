package com.yikuni.mc.subsitutor.utils

import org.bukkit.entity.Player

fun Player.isRegistered(): Boolean{
    return SubsitutorManager.subsitutorMap.containsKey(this)
}