object RateHelper {
  def getBestGroupPrices(rates: Seq[Rate], prices: Seq[CabinPrice]): Seq[BestGroupPrice] = {
        //If either are empty, we can't group prices. Return empty
        if (rates.isEmpty || prices.isEmpty)
          return Seq.empty

        //Map function to easily get RateGroup from RateCode
        val rateCodeToGroup = rates.map(rate => rate.rateCode -> rate.rateGroup).toMap

        //Group cabins by rateGroup and cabinCode, filter out any where we find no RateGroup
        val cabinsGrouped = prices.groupBy(prices => (prices.cabinCode, rateCodeToGroup.getOrElse(prices.rateCode, ""))).filterNot(_._1._2.isBlank())
        
        //Get the lowest price in each rateGroup-cabinCode group, return a tuple with that CabinPrice and the RateGroup (Need to specify RateGroup for BestGroupPrice)
        val leastPerGroup = cabinsGrouped.map(group => (group._2.minBy(_.price), group._1._2)).toSeq

        //Map our tuple to BestGroupPrice objects
        val bestPrices = leastPerGroup.map(least => BestGroupPrice(least._1.cabinCode, least._1.rateCode, least._1.price, least._2)).sortBy(price => (price.cabinCode, price.rateCode))
        bestPrices
    }
}
