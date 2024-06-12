//Simple console app menu main function
@main def consoleApp() =
  var exit = false;
  while(!exit)
    println("Main Menu:")
    println("0 - Exit")
    println("1 - Best Group Prices")
    println("2 - Promotion Combinations")
    println
    val menuOption = scala.io.StdIn.readInt()
    println

    if (menuOption == 0)
      exit = true
    else if (menuOption == 1)
      println("Rates:")
      rates.foreach(println)
      println
      println("Cabin Prices:")
      prices.foreach(println)
      println
      println("Best Prices Per RateGroup:")
      RateHelper.getBestGroupPrices(rates, prices).foreach(println)
      println
    else if (menuOption == 2)
      println("Promos:")
      promos.foreach(println)
      println
      println("Please enter a promo code, or 'all' to view all combinations:")
      val promoOption = scala.io.StdIn.readLine().trim()
      PromotionHelper.getPromotionsFromInput(promoOption, promos).foreach(println)
      println

//Pre-defined data
val rates = Seq(Rate("M1", "Military"),
                Rate("M2", "Military"),
                Rate("S1", "Senior"),
                Rate("S2", "Senior"))

val prices = Seq(CabinPrice("CA", "M1", 200.00),
                 CabinPrice("CA", "M2", 250.00),
                 CabinPrice("CA", "S1", 225.00),
                 CabinPrice("CA", "S2", 260.00),
                 CabinPrice("CB", "M1", 230.00),
                 CabinPrice("CB", "M2", 260.00),
                 CabinPrice("CB", "S1", 245.00),
                 CabinPrice("CB", "S2", 270.00))

val promos = Seq(Promotion("P1", Seq("P3")),
                 Promotion("P2", Seq("P4", "P5")),
                 Promotion("P3", Seq("P1")),
                 Promotion("P4", Seq("P2")),
                 Promotion("P5", Seq("P2")))