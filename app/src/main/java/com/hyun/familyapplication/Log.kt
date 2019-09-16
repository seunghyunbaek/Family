package com.hyun.familyapplication

import android.util.Log

interface Log {
    companion object {
        fun _W(tag: String, err: String) {
            Log.w(tag, err)
        }

        fun _D(tag: String, err: String) {
            Log.d(tag, err)
        }

        fun _E(tag: String, err: String) {
            Log.e(tag, err)
        }

        fun _I(tag: String, err: String) {
            Log.i(tag, err)
        }

        fun _V(tag: String, err: String) {
            Log.v(tag, err)
        }
    }

}