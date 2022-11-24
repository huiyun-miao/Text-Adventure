package AaltoAdventure


class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim


  def execute(actor: Player) = this.verb match {
    case "go"        => Some(actor.go(this.modifiers))
    case "rest"      => Some(actor.rest())
    case "quit"      => Some(actor.quit())
    case "inventory" => Some(actor.inventory)
    case "get"       => Some(actor.get(this.modifiers))
    case "examine"   => Some(actor.examine(this.modifiers))
    case "help"      => Some(actor.help)
    case "use"       => Some(actor.use(this.modifiers))
    case other       => None
  }


  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"


}

