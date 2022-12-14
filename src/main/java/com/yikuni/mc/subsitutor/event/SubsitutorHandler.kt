package com.yikuni.mc.subsitutor.event

import com.yikuni.mc.reflect.annotation.YikuniEvent
import com.yikuni.mc.rumiyalib.RumiyaLib
import com.yikuni.mc.rumiyalib.sender.PlayerSender
import com.yikuni.mc.rumiyalib.utils.actionBarSender
import com.yikuni.mc.rumiyalib.utils.sender
import com.yikuni.mc.subsitutor.Subsitutor
import com.yikuni.mc.subsitutor.utils.SubsitutorManager
import com.yikuni.mc.subsitutor.utils.isRegistered
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

@YikuniEvent
class SubsitutorHandler: Listener {
    @EventHandler
    fun onLogin(event: PlayerLoginEvent){
        if (!Subsitutor.isStarted()) return
        Bukkit.getScheduler().runTaskLater(RumiyaLib.getInstance(), Runnable {
            SubsitutorManager.initAllSubsitutor()
        }, 40)
    }
    @EventHandler
    fun onLostConnect(event: PlayerQuitEvent){
        if (!Subsitutor.isStarted()) return
        Bukkit.getScheduler().runTaskLater(RumiyaLib.getInstance(), Runnable {
            SubsitutorManager.subsitutorMap.remove(event.player)
            SubsitutorManager.substitutorNumMap.remove(event.player)
            SubsitutorManager.onSubsitutorFailure(event.player)
        }, 40)
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent){
        if (!Subsitutor.isStarted()) return
        val i = SubsitutorManager.substitutorNumMap[event.entity]?:0
        if (i > 1){
            event.drops.forEach {
                if (it.maxStackSize != 1){
                    it.amount *= i
                }
            }
        }
        SubsitutorManager.onSubsitutorFailure(event.entity)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun substitute(event: EntityDamageEvent){
        if (!Subsitutor.isStarted()) return
        val entity = event.entity
        if (entity is Player){
            if (event.damage < 0.1) return
            val substitutor = SubsitutorManager.subsitutorMap[entity]?:return
            val health = substitutor.health - event.finalDamage
            substitutor.health = if (health > 0) health else 0.0
            event.isCancelled = true
            entity.actionBarSender().primary("????????????${String.format("%.2f", event.finalDamage)}???????????????????????????${substitutor.name}")
            substitutor.actionBarSender().primary("??????????????????${entity.name}???${String.format("%.2f", event.finalDamage)}?????????")
        }
    }



}