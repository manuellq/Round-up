package com.mlcorrea.roundup.data.extension

import com.mlcorrea.roundup.domain.model.FeedItem
import com.mlcorrea.roundup.domain.model.ViewModelData
import io.reactivex.Observable
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat


/**
 * Created by manuel on 18/08/19
 */

private val logger = LoggerFactory.getLogger("DoubleExt")

fun List<ViewModelData>.roundUpFeedItem(): Observable<BigDecimal> {
    return Observable.just(this)
        .concatMapIterable { t: List<ViewModelData> -> t }
        .filter { viewModel: ViewModelData -> viewModel is FeedItem }
        .map { viewModel: ViewModelData ->
            (viewModel as FeedItem).amount.minorUnits
        }
        .map { decimalString: String ->
            decimalString.toBigDecimalOrNull()?.setDecimalPoint()
        }
        .map { bigDecimal: BigDecimal ->
            val roundUp = bigDecimal.setScale(0, RoundingMode.UP)
            roundUp?.minus(bigDecimal)
            //logger.info(data.toString())
        }
        .toList()
        .toObservable()
        .map { bigDecimals ->
            bigDecimals.fold(BigDecimal.ZERO, BigDecimal::add)
        }
}

fun BigDecimal.setDecimalPoint(): BigDecimal {
    return this.divide(BigDecimal(100))
}

fun BigDecimal.removeDecimalSeparator(): String {
    val format = DecimalFormat()
    val decSep = format.decimalFormatSymbols.decimalSeparator
    val doubleDecimal = this.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
    return doubleDecimal.replace(decSep.toString(), "")
}