class PromotionHelperTests extends munit.FunSuite {
  

  //-----areCombinable-----

  test("areCombinable_ForBothNull_ReturnsFalse") {
    //Arrange
    val promo1 = null
    val promo2 = null
    //Act
    val result = PromotionHelper.areCombinable(promo1, promo2)

    //Assert
    assertEquals(result, false)
  }

  test("areCombinable_ForOneNull_ReturnsFalse") {
    //Arrange
    val promo1 = null
    val promo2 = Promotion("P2", Seq.empty)
    //Act
    val result = PromotionHelper.areCombinable(promo1, promo2)

    //Assert
    assertEquals(result, false)
  }

  test("areCombinable_ForSamePromotionCode_ReturnsFalse") {
    //Arrange
    val promo1 = Promotion("P1", Seq.empty)
    val promo2 = Promotion("P1", Seq.empty)
    //Act
    val result = PromotionHelper.areCombinable(promo1, promo2)

    //Assert
    assertEquals(result, false)
  }

  test("areCombinable_ForExcluded_ReturnsFalse") {
    //Arrange
    val promo1 = Promotion("P1", Seq.empty)
    val promo2 = Promotion("P2", Seq("P1"))
    //Act
    val result = PromotionHelper.areCombinable(promo1, promo2)

    //Assert
    assertEquals(result, false)
  }

  test("areCombinable_ForValid_ReturnsTrue") {
    //Arrange
    val promo1 = Promotion("P1", Seq.empty)
    val promo2 = Promotion("P2", Seq("P3"))
    //Act
    val result = PromotionHelper.areCombinable(promo1, promo2)

    //Assert
    assertEquals(result, true)
  }

  //-----combine-----
  
  test("combine_ForBothEmpty_ReturnsEmpty") {
    //Arrange
    val current = Seq.empty
    val promos = Seq.empty
    //Act
    val result = PromotionHelper.combine(current, promos)

    //Assert
    assertEquals(result.length, 0)
  }

  test("combine_ForPromosEmpty_ReturnsCombo") {
    //Arrange
    val current = Seq(Promotion("P1", Seq.empty),
                      Promotion("P2", Seq.empty))
    val promos = Seq.empty
    //Act
    val result = PromotionHelper.combine(current, promos)

    //Assert
    assertEquals(result.length, 1)
  }

  test("combine_ForPromosEmptyAndNoCurrentCombo_ReturnsEmpty") {
    //Arrange
    val current = Seq(Promotion("P1", Seq.empty))
    val promos = Seq.empty
    //Act
    val result = PromotionHelper.combine(current, promos)

    //Assert
    assertEquals(result.length, 0)
  }

  test("combine_ForCurrentEmpty_ReturnsCombos") {
    //Arrange
    val current = Seq.empty
    val promos = Seq(Promotion("P1", Seq.empty),
                      Promotion("P2", Seq.empty))
    //Act
    val result = PromotionHelper.combine(current, promos)

    //Assert
    assertEquals(result.length, 1)
  } 

  test("combine_ForCurrentEmptyAndNoCombos_ReturnsEmpty") {
    //Arrange
    val current = Seq.empty
    val promos = Seq(Promotion("P1", Seq.empty))
    //Act
    val result = PromotionHelper.combine(current, promos)

    //Assert
    assertEquals(result.length, 0)
  } 

  test("combine_ForExcludedPromos_ReturnsEmpty") {
    //Arrange
    val current = Seq.empty
    val promos = Seq(Promotion("P1", Seq("P2")),
                     Promotion("P2", Seq("P1")))
    //Act
    val result = PromotionHelper.combine(current, promos)

    //Assert
    assertEquals(result.length, 0)
  } 

  //-----maximizeCombos-----

  test("maximizeCombos_ForEmpty_ReturnsEmpty") {
    //Arrange
    val promos = Seq.empty
    //Act
    val result = PromotionHelper.maximizeCombos(promos)

    //Assert
    assertEquals(result.length, 0)
  }

  test("maximizeCombos_ForSingleList_ReturnsSingle") {
    //Arrange
    val pName1 = "P1"
    val pName2 = "P2"
    val promos = Seq(PromotionCombo(Seq(pName1, pName2)))
    //Act
    val result = PromotionHelper.maximizeCombos(promos)

    //Assert
    assertEquals(result.length, 1)
  }

  test("maximizeCombos_ForMultipleList_ReturnsMultiple") {
    //Arrange
    val pName1 = "P1"
    val pName2 = "P2"
    val pName3 = "P3"
    val promos = Seq(PromotionCombo(Seq(pName1, pName2)),
                     PromotionCombo(Seq(pName2, pName3)))
    //Act
    val result = PromotionHelper.maximizeCombos(promos)

    //Assert
    assertEquals(result.length, 2)
  }

  test("maximizeCombos_ForListWithSubset_ReturnsLongerList") {
    //Arrange
    val pName1 = "P1"
    val pName2 = "P2"
    val pName3 = "P3"
    val promos = Seq(PromotionCombo(Seq(pName1, pName2)),
                     PromotionCombo(Seq(pName1, pName2, pName3)))
    //Act
    val result = PromotionHelper.maximizeCombos(promos)

    //Assert
    assertEquals(result.length, 1)
    assertEquals(result(0).promotionCodes.length, 3)
  }
  
  //-----allCombinablePromotions-----

  test("allCombinablePromotions_ForEmptyList_ReturnsEmpty") {
    //Arrange
    val promos = Seq.empty
    //Act
    val result = PromotionHelper.allCombinablePromotions(promos)

    //Assert
    assertEquals(result.length, 0)
  }

  test("allCombinablePromotions_ForSingleList_ReturnsEmpty") {
    //Arrange
    val promos = Seq(Promotion("P1", Seq.empty))
    //Act
    val result = PromotionHelper.allCombinablePromotions(promos)

    //Assert
    assertEquals(result.length, 0)
  }

  test("allCombinablePromotions_ForCombinablePromos_ReturnsSingleCombination") {
    //Arrange
    val pName1 = "P1"
    val pName2 = "P2"
    val promos = Seq(Promotion(pName1, Seq.empty),
                     Promotion(pName2, Seq.empty))
    //Act
    val result = PromotionHelper.allCombinablePromotions(promos)

    //Assert
    assertEquals(result.length, 1)
    assertEquals(result(0).promotionCodes.contains(pName1), true)
    assertEquals(result(0).promotionCodes.contains(pName2), true)
  }

  //-----combinablePromotions-----

  test("combinablePromotions_ForBothEmpty_ReturnsEmpty") {
    //Arrange
    val startPromo = ""
    val promos = Seq.empty
    //Act
    val result = PromotionHelper.combinablePromotions(startPromo, promos)

    //Assert
    assertEquals(result.length, 0)
  }

  test("combinablePromotions_ForEmptyPromotions_ReturnsEmpty") {
    //Arrange
    val startPromo = "P1"
    val promos = Seq.empty
    //Act
    val result = PromotionHelper.combinablePromotions(startPromo, promos)

    //Assert
    assertEquals(result.length, 0)
  }

  test("combinablePromotions_ForEmptyStartPromo_ReturnsEmpty") {
    //Arrange
    val pName1 = "P1"
    val pName2 = "P2"
    val startPromo = ""
    val promos = Seq(Promotion(pName1, Seq.empty),
                     Promotion(pName2, Seq.empty))

    //Act
    val result = PromotionHelper.combinablePromotions(startPromo, promos)

    //Assert
    assertEquals(result.length, 0)
  }

  test("combinablePromotions_ForInvalidStartPromo_ReturnsEmpty") {
    //Arrange
    val pName1 = "P1"
    val pName2 = "P2"
    val startPromo = "P3"
    val promos = Seq(Promotion(pName1, Seq.empty),
                     Promotion(pName2, Seq.empty))

    //Act
    val result = PromotionHelper.combinablePromotions(startPromo, promos)

    //Assert
    assertEquals(result.length, 0)
  }

  test("combinablePromotions_ForValidStartPromo_ReturnsCombo") {
    //Arrange
    val pName1 = "P1"
    val pName2 = "P2"
    val startPromo = "P1"
    val promos = Seq(Promotion(pName1, Seq.empty),
                     Promotion(pName2, Seq.empty))

    //Act
    val result = PromotionHelper.combinablePromotions(startPromo, promos)

    //Assert
    assertEquals(result.length, 1)
  }
}
