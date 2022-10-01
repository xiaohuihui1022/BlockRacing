package ml.windleaf.blockracing.listeners

import ml.windleaf.blockracing.BlockRacing.Companion.instance
import ml.windleaf.blockracing.BlockRacing.Companion.pluginManager
import ml.windleaf.blockracing.BlockRacing.Companion.teamManager
import ml.windleaf.blockracing.entity.goals.GoalBlock
import ml.windleaf.blockracing.events.PlayerCompleteGoalEvent
import ml.windleaf.blockracing.score.ScoreboardManager
import ml.windleaf.blockracing.team.Team
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.PlayerInventory

class ListenPlayerGetItem(private val player: Player): Runnable {
  override fun run() {
    val team: Team? = teamManager.getTeams().find { it.players.containsValue(player) }
    team?.getGoals()?.forEach {
      val inventory: PlayerInventory = player.inventory
      if (inventory.contains(it.material)) {
        val event = PlayerCompleteGoalEvent(player)
        Bukkit.getScheduler().runTask(instance) { _ -> pluginManager.callEvent(event) }
      }
    }
  }

  private fun Team.getGoals(): ArrayList<GoalBlock> {
    return ArrayList(ScoreboardManager.goals[this]!!)
  }
}