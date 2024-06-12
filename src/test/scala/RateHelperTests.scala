class RateHelperTests extends munit.FunSuite {

  //Trying out a shared testing function
  def bestGroupPricesTestLength(
    name: String,
    rates: Seq[Rate],
    prices: Seq[CabinPrice],
    expectedLength: Int
  )(implicit loc: munit.Location): Unit = {
    test(name){
      //Act
      val result = RateHelper.getBestGroupPrices(rates, prices)
      //Assert
      assertEquals(result.length, expectedLength)
    }
  }

  bestGroupPricesTestLength(
    "getBestGroupPrices_ForBothEmpty_ReturnsEmpty",
    Seq.empty,
    Seq.empty,
    0
  )
  bestGroupPricesTestLength(
    "getBestGroupPrices_ForEmptyPrices_ReturnsEmpty",
    Seq(Rate("M1", "Military"),
        Rate("M2", "Military")),
    Seq.empty,
    0
  )
  bestGroupPricesTestLength(
    "getBestGroupPrices_ForEmptyRates_ReturnsEmpty",
    Seq.empty,
    Seq(CabinPrice("CA", "M1", 200.00),
        CabinPrice("CA", "M2", 250.00)),
    0
  )
  bestGroupPricesTestLength(
    "getBestGroupPrices_ForRateGroupDoesntExist_ReturnsEmpty",
    Seq(Rate("M1", "Military")),
    Seq(CabinPrice("CA", "S1", 200.00)),
    0
  )
  
  test("getBestGroupPrices_ForSameGroup_ReturnsLowerPriced") {
    //Arrange
    val lowerPrice : BigDecimal = 1.00

    val rates = Seq(Rate("M1", "Military"),
                    Rate("M2", "Military"))

    val prices = Seq(CabinPrice("CA", "M1", lowerPrice),
                     CabinPrice("CA", "M2", 250.00))

    //Act
    val result = RateHelper.getBestGroupPrices(rates, prices)

    //Assert
    assertEquals(result.length, 1)
    assertEquals(result(0).price, lowerPrice)
  }

  test("getBestGroupPrices_ForDifferentGroup_ReturnsBoth") {
    //Arrange
    val rates = Seq(Rate("M1", "Military"),
                    Rate("S1", "Senior"))

    val prices = Seq(CabinPrice("CA", "M1", 200.00),
                     CabinPrice("CA", "S1", 250.00))

    //Act
    val result = RateHelper.getBestGroupPrices(rates, prices)

    //Assert
    assertEquals(result.length, 2)
    assertNotEquals(result(0).rateGroup, result(1).rateGroup)
  }
}
