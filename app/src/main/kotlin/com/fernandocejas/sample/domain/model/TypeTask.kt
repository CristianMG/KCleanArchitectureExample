package com.fernandocejas.sample.domain.model

import com.fernandocejas.sample.R

enum class TypeTask(val idTask: Int, val resource: Int) {
    PRODUCT_SUPPLIER(1, R.string.product_supplier), COLLECTOR(2, R.string.collector), WRAPPER(3, R.string.wrapper), UNKNOWN(-1, 0);
}