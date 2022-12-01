package com.yikuni.mc.subsitutor.command

import com.yikuni.mc.reflect.annotation.YikuniCommand
import com.yikuni.mc.rumiyalib.utils.sender
import com.yikuni.mc.subsitutor.utils.SubsitutorManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

@YikuniCommand("stest")
class TestCommand: CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            if (args.isNotEmpty()){
                when(args[0]){
                    "drops" ->{
                        SubsitutorManager.substitutorNumMap[sender] = 2
                        sender.sender().info("设置倍数为2")
                    }
                    "sub" ->{
                        val msg = java.lang.StringBuilder()
                        SubsitutorManager.subsitutorMap.forEach { (t, u) ->
                            msg.append("${t.name} -> ${u.name}\n")
                        }
                        sender.sender().info(msg.toString())
                    }
                    "num" ->{
                        val msg = java.lang.StringBuilder()
                        SubsitutorManager.substitutorNumMap.forEach { (t, u) ->
                            msg.append("${t.name} -> ${u}\n")
                        }
                        sender.sender().info(msg.toString())
                    }
                }
            }
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        return if (args.size == 1){
            listOf("drops", "num", "sub").filter { it.startsWith(args[0]) }.toMutableList()
        }else{
            null
        }
    }
}