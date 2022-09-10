package com.example.myapplication

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable



import java.util.List

internal class Orders {
    internal inner class ex {
        var `in` = 0
    }




    internal inner class Example : Serializable
    {

        @SerializedName("shipments")
        @Expose
        val  shipments: List<String>?  = null;
        @SerializedName("drivers")
        @Expose
        val drivers: List<String>?  = null;


    }

    internal inner class Order : Serializable {
        @SerializedName("type")
        @Expose
        var type: String? = null


        @Expose
        var priceinEuro: Double = 0.0

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("ingredients")
        @Expose
        var ingredients: List<String>? = null

        @SerializedName("size")
        @Expose
        var size: String? = null

        @SerializedName("toppings")
        @Expose
        var toppings: List<String>? = null

        @SerializedName("sauce")
        @Expose
        var sauce: List<String>? = null

        @SerializedName("shape")
        @Expose
        var shape: String? = null

        @SerializedName("grain")
        @Expose
        var grain: String? = null

        @SerializedName("meat")
        @Expose
        var meat: String? = null

        @SerializedName("length")
        @Expose
        var length: Int? = null


    }
}