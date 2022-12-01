package com.yikuni.mc.subsitutor.utils

import com.yikuni.mc.reflect.annotation.YikuniEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective

@YikuniEvent
class SubstitutorScoreboard: Listener {
    private val scoreboard = Bukkit.getScoreboardManager()!!.mainScoreboard
    private lateinit var sidebar: Objective

    fun initSidebar(){
        sidebar = scoreboard.getObjective("sidebar")?:scoreboard.registerNewObjective("sidebar", "dummy", "${ChatColor.GOLD}替身名单")
        sidebar.displaySlot = DisplaySlot.SIDEBAR
    }
    fun updateSidebar(){
        if (SubsitutorManager.subsitutorMap.isEmpty()){
            sidebar.getScore("暂时没有替身数据").score = 9
        }else{
            sidebar.getScore("玩家->替身").score = 9
            var i = 1
            SubsitutorManager.subsitutorMap.forEach { (t, u) ->
                val infoTeamName = "${t.name}-infoTeam"
                var infoTeam = scoreboard.getTeam(infoTeamName)
                if (infoTeam == null){
                    infoTeam = scoreboard.registerNewTeam(infoTeamName)
                    infoTeam.addEntry("${ChatColor.DARK_GREEN}${t.name}")
                }
                infoTeam.setSuffix("->${u.name}")
                sidebar.getScore("${ChatColor.DARK_GREEN}${t.name}").score = i++
            }
        }
    }

}