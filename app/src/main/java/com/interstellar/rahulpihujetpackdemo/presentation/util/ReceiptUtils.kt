package com.interstellar.rahulpihujetpackdemo.presentation.util


import android.content.Context
import android.content.Intent
import com.interstellar.rahulpihujetpackdemo.data.local.model.receipt.ReceiptData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ReceiptUtils {

    fun generateReceiptNumber(): String {
        val timestamp = System.currentTimeMillis()
        return "RCP${timestamp.toString().takeLast(8)}"
    }

    fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    fun shareReceipt(context: Context, receiptData: ReceiptData) {
        val receiptText = buildReceiptText(receiptData)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, receiptText)
            putExtra(Intent.EXTRA_SUBJECT, "Receipt - ${receiptData.receiptNumber}")
        }

        context.startActivity(
            Intent.createChooser(shareIntent, "Share Receipt")
        )
    }

    fun buildReceiptText(receiptData: ReceiptData): String {
        return buildString {
            appendLine("═══════════════════════════")
            appendLine("        RECEIPT")
            appendLine("═══════════════════════════")
            appendLine()
            appendLine(receiptData.storeName)
            appendLine(receiptData.storeAddress)
            appendLine(receiptData.storePhone)
            appendLine()
            appendLine("Receipt #: ${receiptData.receiptNumber}")
            appendLine("Date: ${receiptData.date}")
            appendLine("Items: ${receiptData.itemCount}")
            appendLine()
            appendLine("─── ITEMS ─────────────────")

            receiptData.items.forEach { item ->
                val itemTotal = item.priceValue * item.quantity
                appendLine(
                    "${item.name} (${item.quantity}x) - $${String.format("%.2f", itemTotal)}"
                )
            }

            appendLine()
            appendLine("─── TOTALS ────────────────")
            appendLine("Subtotal: $${String.format("%.2f", receiptData.subtotal)}")
            appendLine("Tax (8%): $${String.format("%.2f", receiptData.tax)}")
            appendLine("═══════════════════════════")
            appendLine("TOTAL: $${String.format("%.2f", receiptData.total)}")
            appendLine("═══════════════════════════")
            appendLine()
            appendLine("Thank you for your purchase!")
            appendLine("Visit us again soon!")
        }
    }
}
