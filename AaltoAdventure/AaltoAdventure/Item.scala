package AaltoAdventure

import scala.collection.mutable.Buffer


class Item(val name: String, val description: String) {

  private var hiddenObjects = Buffer[(String, Item)]()


  def hiddenItems = hiddenObjects


  def addItem(item: Item) = this.hiddenObjects += item.name -> item


  def contains(itemName: String) = this.hiddenObjects.map( _._1 ).contains(itemName)


  def removedAll() = this.hiddenObjects = Buffer[(String, Item)]()


  def descriptionAfterExamine = {
    this.hiddenObjects.map( pair => pair._2.description + "\nItem: " + pair._1 ).mkString("\n") +
    "\nYou get: " + this.hiddenObjects.map( _._1 ).mkString(" ")
  }


  override def toString = this.description + "\nItem: " + this.name


}