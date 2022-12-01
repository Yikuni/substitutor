package com.yikuni.mc.subsitutor.command

import com.yikuni.mc.reflect.annotation.YikuniCommand
import com.yikuni.mc.rumiyalib.utils.sender
import com.yikuni.mc.subsitutor.Subsitutor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@YikuniCommand(value = "sstart", description = "开始游戏")
class StartCommand:CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player){
            if (!Subsitutor.isStarted()) {
                Subsitutor.start()
                sender.sender().success("游戏已开始")
            }else{
                sender.sender().warn("指令执行失败: 游戏已开始")
            }
            return true
        }
        return false
    }
}