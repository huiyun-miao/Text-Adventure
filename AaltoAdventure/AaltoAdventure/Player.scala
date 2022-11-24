package AaltoAdventure

import scala.collection.mutable.Buffer
import o1._


class Player(startingArea: Area) {

  private var currentLocation = startingArea
  private var quitCommandGiven = false
  private var possession = Buffer[(String, Item)]()


  def hasQuit = this.quitCommandGiven


  def location = this.currentLocation


  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + "."
  }


  def rest() = {
    "You rest for a while. Better get a move on, though."
  }


  def quit() = {
    this.quitCommandGiven = true
    ""
  }


  def has(itemName: String): Boolean = this.possession.map( _._1 ).contains(itemName)


  def use(itemName: String) = {
    if (this.has(itemName)) {
      itemName match {
        case "map"   => show(Pic("map.png"))
                        "You open the map."
        case "bag"   => "You open the bag and put everything inside. It is nice that you don't have to carry everything by hands."
        case "badge" => "You only need them when you at the destination."
        case other   => s"The ${itemName} is not really useful for completing the adventure."
      }
    } else
      s"You don't have ${itemName}."
  }


  def examine(itemName: String): String = {

    def getHiddenItem(item: Item) = {
      if (item.hiddenItems.nonEmpty) {
        val description = item.descriptionAfterExamine
        this.possession = this.possession ++ item.hiddenItems
        item.removedAll()
        description
      } else
        s"There is nothing hidden inside the ${item.name}."
    }

    val examinedItem = this.possession.map( _._2 ).find( item => item.name == itemName )

    examinedItem match {
      case Some(item) => getHiddenItem(item)
      case None => "If you want to examine something, you need to pick it up first."
    }

  }


  def get(itemName: String): String = {
    val removedItem = this.location.removeItem(itemName)
    removedItem match {
      case Some(item) => this.possession += itemName -> item
                         s"You pick up the ${item.name}."
      case None => s"There is no ${itemName} here to pick up."
    }
  }


  def countPossession = {
    val allItems = this.possession.map( _._1)
    val groupByName = allItems.groupBy( name => name )
     groupByName.map( pair => pair._1 -> pair._2.size )
  }


  def numberOfBadges = {
    val number = this.countPossession.get("badge")
    number match {
      case Some(value) => value
      case None => 0
    }
  }


  def inventory: String = {
    if (this.possession.nonEmpty) {
      "You are carrying:\n" + this.countPossession.map( pair => s"${pair._2} ${pair._1}(s)" ).mkString("\n")
    } else "You are empty-handed."
  }


  def help: String = {
    "Use \"go\" to go somewhere.\n" +
    "Use \"rest\" to rest for a while.\n" +
    "Use \"get\" to pick up items.\n" +
    "Use \"examine\" to examine the items you have.\n" +
    "Use \"use\" to use the items you have.\n" +
    "Use \"inventory\" to check the items you have.\n" +
    "Use \"quit\" to end the game.\n\n" +
    "Some items might be hidden in other items.\n" +
    "You can try to use all the items, but not all of them are useful."
  }


  override def toString = "Now at: " + this.location.name


}


