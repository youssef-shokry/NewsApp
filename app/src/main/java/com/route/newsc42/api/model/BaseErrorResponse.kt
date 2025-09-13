package com.route.newsc42.api.model

import com.google.gson.annotations.SerializedName

data class BaseErrorResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
