package AaltoAdventure


class Adventure {

  val title = "Aalto Adventure"

  private val middle               = new Area("Empty Ground", "You are in the middle of an empty ground.\nThere are some buildings around you.")
  private val väre                 = new Area("Väre", "You are in Väre now, which is the School of Arts, Design and Architecture.\nPeople are passing by.")
  private val väre_1               = new Area("Väre Workshop", "Walking down a long stair, you see some students working in different workshops.")
  private val väre2                = new Area("Väre Second Floor", "Upstairs is the second floor with some study areas.")
  private val tuas                 = new Area("TUAS", "This building is shared by the School of Science and the School of Electrical Engineering.")
  private val tuas2                = new Area("TUAS Second Floor", "It's quiet on the second floor, you are wondering where are the students.")
  private val undergraduateCenter  = new Area("Undergraduate Center", "You are in the Undergraduate Center now. Don't get lost in here!")
  private val undergraduateCenter2 = new Area("Undergraduate Center Second Floor", "You see several lecture halls on the second floor.\nThere are some standing tables outside.")
  private val library              = new Area("Library", "Welcome to thr library, whhich is also called Harald Herlin Learning Centre.")
  private val täffä                = new Area("Täffä", "Something smell good here. It is almost time for lunch.\nAll you need is to collect 4 badges, and you will win a lunch ticket!")
  private val destination          = täffä


  middle.setNeighbors(Vector("east" -> undergraduateCenter, "south" -> library, "west" -> väre))

  väre.setNeighbors(Vector("north" -> tuas, "east" -> library, "upstairs" -> väre2, "downstairs" -> väre_1))
  väre_1.setNeighbor("upstairs", väre)
  väre2.setNeighbor("downstairs", väre)

  tuas.setNeighbors(Vector("east" -> undergraduateCenter, "south" -> väre, "upstairs" -> tuas2))
  tuas2.setNeighbor("downstairs", tuas)

  undergraduateCenter.setNeighbors(Vector("east" -> täffä, "south" -> library, "west" -> middle, "upstairs" -> undergraduateCenter2))
  undergraduateCenter2.setNeighbor("downstairs", undergraduateCenter)

  library.setNeighbors(Vector("north" -> undergraduateCenter, "west" -> väre))

  täffä.setNeighbors(Vector("west" -> undergraduateCenter))


  private val badge1     = new Item("badge", "There is a badge on top of the printer.")
  private val magazine   = new Item("magazine", "You see some Aalto magazines on the magazine rack next to the wall.")
  private val badge2     = new Item("badge", "Open the Aalto Magazine, you find a badge in between the pages.")
  private val bag        = new Item("bag", "You see a cloth bag on the chair. You can use this bag to carry all the stuff you have.")
  private val badge3     = new Item("badge", "Open the bag, you find a badge inside. What a surprise!")
  private val map        = new Item("map", "You look around, find a map on one the standing table.")
  private val badge4     = new Item("badge", "A badge is on the table next to the map.")
  private val dictionary = new Item("dictionary", "You see a Finnish-English dictionary here. \nIt might be useful, since you live in Finland and you don't speak Finnish.")
  private val badge5     = new Item("badge", "It is a normal dictionary. You don't really need to use it now, but you just flip over the pages.\nBetween the last two pages, you see a badge.")


  väre_1.addItem(badge1)
  väre2.addItem(magazine)
  magazine.addItem(badge2)

  tuas2.addItem(bag)
  bag.addItem(badge3)

  undergraduateCenter2.addItem(map)
  undergraduateCenter2.addItem(badge4)

  library.addItem(dictionary)
  dictionary.addItem(badge5)


  val player = new Player(middle)
  var turnCount = 0
  val timeLimit = 40


  def isComplete = this.player.location == this.destination && this.player.numberOfBadges >= 4


  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit


  def welcomeMessage = "You are a first-year student who participate an adventure game to explore Aalto University.\n" +
                       "You don't know much about Otaniemi because you are taking online courses the whole time.\n" +
                       "But don't worry, the adventure is not complicated.\n" +
                       "Find your way to Täffä, because it's almost time for lunch!"


  def goodbyeMessage = {
    if (this.isComplete) {
      val message1 = "Just in time for lunch! Here is your lunch ticket. Well done!\n"
      val message2 = if (this.player.numberOfBadges == 5) "You got all the badges! Free coffee for you as well!" else ""
      message1 + message2
    } else if (this.turnCount == this.timeLimit)
      "Oh no! Lunch time is over!\nGame over!"
    else
      "Quitter!"
  }


  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) {
      this.turnCount += 1
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }


}

