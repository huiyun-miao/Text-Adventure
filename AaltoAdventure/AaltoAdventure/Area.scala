package AaltoAdventure

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer


class Area(var name: String, var description: String) {

  private val neighbors = Map[String, Area]()
  private val items = Buffer[(String, Item)]()


  def neighbor(direction: String) = this.neighbors.get(direction)


  def setNeighbor(direction: String, neighbor: Area) = {
    this.neighbors += direction -> neighbor
  }


  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }


  def fullDescription = {
    val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
    val itemList = if (this.items.isEmpty) "" else "\n" + this.items.map( _._2 ).mkString("\n")
    this.description + itemList + exitList
  }


  def addItem(item: Item) = this.items += item.name -> item


  def contains(itemName: String) = this.items.map( _._1 ).contains(itemName)


  def removeItem(itemName: String) = {
    if (this.contains(itemName)) {
      val index = this.items.map( _._1 ).indexOf(itemName)
      val removed = this.items(index)
      this.items.remove(index)
      Some(removed._2)
    } else
      None
  }


  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)


}
