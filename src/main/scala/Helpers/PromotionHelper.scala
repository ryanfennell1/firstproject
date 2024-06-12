object PromotionHelper {
    //are combinable if they both exist, are not the same, and do not exclude each other
    def areCombinable(promo1: Promotion, promo2: Promotion): Boolean = {
            promo1 != null && 
            promo2 != null &&
            promo1.code != promo2.code && 
            !promo1.notCombinableWith.exists(_ == promo2.code) && 
            !promo2.notCombinableWith.exists(_ == promo1.code)
    }
  
    //recursive combination
    def combine(currentList: Seq[Promotion], remainingList: Seq[Promotion]): Seq[PromotionCombo] = {
            //out of other elements to check, termination condition
            if (remainingList.isEmpty){
                //'Combinations' don't include single promotions or empty lists
                if (currentList.isEmpty || currentList.length == 1)
                    Seq.empty
                //We actually have a combination, return it in proper format (A PromotionCombo in a list, to be combined with others we generate)
                else
                    Seq(PromotionCombo(currentList.map(_.code)))
            }
            else {
                //run combine for skipping the next promotion
                val skippingNext = combine(currentList, remainingList.tail)

                //run combine with the next promotion in our list, if it is okay to combine with the others in our list
                //Otherwise, empty string here as we already handled skipping this promotion
                val withNext = if(currentList.forall(curr => areCombinable(curr, remainingList.head))){
                    combine(currentList :+ remainingList.head, remainingList.tail)
                }
                else {
                    Seq.empty
                }

                //Combine the possibilities of adding and skipping the next promotion
                skippingNext ++ withNext
            } 
    }

    def maximizeCombos(promoCombos: Seq[PromotionCombo]) : Seq[PromotionCombo] = {
        //filter our combos
        promoCombos.filter(combos => 
            //where all                       //are the same set
            promoCombos.forall(otherCombos => otherCombos.promotionCodes.toSet == combos.promotionCodes.toSet || 
                                //or are not subsets of any other lists we have
                                !combos.promotionCodes.forall(otherCombos.promotionCodes.contains)))
    }

    //Calling combine but formatting and maximizing our lists
    def allCombinablePromotions(allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {
        //start with an empty list to get all possible combinations
        maximizeCombos(combine(Seq.empty, allPromotions)).sortBy(combos => (combos.promotionCodes.head, combos.promotionCodes.length))
    }

    //Calling combine with a single starting promo code but formatting and maximizing our lists
    def combinablePromotions(promotionCode: String, allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {
        //start with a list containing the specified promotion, if it exists
        if (allPromotions.exists(_.code == promotionCode))
            maximizeCombos(combine(allPromotions.filter(_.code == promotionCode), allPromotions)).sortBy(combos => (combos.promotionCodes.head, combos.promotionCodes.length))
        else
            Seq.empty
    }

    //Encapsulating function used to run our main combination functions from user text input
    def getPromotionsFromInput(input: String, allPromotions: Seq[Promotion]): Seq[PromotionCombo] = {
        if (input.isBlank())
            Seq.empty
        if (input.toLowerCase().trim() == "all")
            allCombinablePromotions(allPromotions)
        else
            combinablePromotions(input, allPromotions)
    }
}
