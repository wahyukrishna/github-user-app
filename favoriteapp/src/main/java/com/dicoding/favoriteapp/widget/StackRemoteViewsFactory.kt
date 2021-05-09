package com.dicoding.favoriteapp.widget

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.dicoding.favoriteapp.R
import com.dicoding.favoriteapp.adapter.MappingHelper
import com.dicoding.favoriteapp.db.DatabaseContract
import com.dicoding.favoriteapp.model.UserItems
import java.net.HttpURLConnection
import java.net.URL

internal class StackRemoteViewsFactory(private val context: Context): RemoteViewsService.RemoteViewsFactory {
    private var mWidgetItems = ArrayList<UserItems>()

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        val cursor = context.contentResolver.query(
            DatabaseContract.FavColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listMapping = MappingHelper.mapCursorToArrayList(cursor)
        mWidgetItems = (listMapping)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        val connect = URL(mWidgetItems[position].avatar).openConnection() as HttpURLConnection
        if (connect.responseCode == 200) {
            val bitmap = BitmapFactory.decodeStream(connect.inputStream)
            rv.setImageViewBitmap(R.id.imageView, bitmap)
        }

        val extras = bundleOf(
            FavoriteWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? =null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean =false
}