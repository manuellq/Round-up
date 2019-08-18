package com.mlcorrea.roundup.framework.uuid

import java.util.*

/**
 * Created by manuel on 18/08/19
 */
class UuidUser : UuidService {

    override fun getUuid(): String {
        return UUID.randomUUID().toString()
    }
}