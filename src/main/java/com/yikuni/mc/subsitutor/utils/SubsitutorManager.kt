package com.yikuni.mc.subsitutor.utils

import com.yikuni.mc.reflect.context.ApplicationContext
import com.yikuni.mc.rumiyalib.sender.ServerSender
import com.yikuni.mc.rumiyalib.utils.sender
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object SubsitutorManager {
    val subsitutorMap = mutableMapOf<Player, Player>()
    val substitutorNumMap = mutableMapOf<Player, Int>()

    private fun setSubsitutor(player: Player){
        val onlinePlayers = Bukkit.getOnlinePlayers()
        subsitutorMap.remove(player)
        if (onlinePlayers.size < 2){
            // 在线玩家不足, 不能找到替身
            player.sender().warn("在线玩家不足, 请等待其它玩家加入以开始游戏")
        }else{
            // 创建替身
            val substitutor = onlinePlayers.filter { it != player }.random()
            subsitutorMap[player] = substitutor
            player.sender().success("${substitutor.name}成为了您的替身")
            substitutor.sender().warn("您成为了${player.name}的替身")
            ApplicationContext.getContext(SubstitutorScoreboard::class.java)!!.updateSidebar()
            if (substitutorNumMap.contains(substitutor)){
                substitutorNumMap[substitutor] = substitutorNumMap[substitutor]!!.inc()
            }else{
                substitutorNumMap[substitutor] = 1
            }
        }
    }

    fun initAllSubsitutor(){
        Bukkit.getOnlinePlayers().forEach {
            if (!subsitutorMap.contains(it) || !subsitutorMap[it]!!.isOnline){
                setSubsitutor(it)
            }
        }
    }

    fun onSubsitutorFailure(player: Player){
        substitutorNumMap.remove(player)
        ServerSender.debug(subsitutorMap.size.toString())
        val change = subsitutorMap.filter { it.value == player }
        ServerSender.debug(change.size.toString())
        change.forEach { (t, _) -> setSubsitutor(t) }
    }

}