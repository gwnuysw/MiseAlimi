package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.KeyEvent
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var myWebView: WebView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        myWebView = findViewById(R.id.webview)
        myWebView.webViewClient = MyWebViewClient()
        myWebView.settings.javaScriptEnabled = true
        myWebView.addJavascriptInterface(WebAppInterface(this), "Android")

        myWebView.loadUrl("http://115.86.172.10:3000/")
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

}
private class MyWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url == "http://115.86.172.10:3000/users/loginpage") {
            // This is my web site, so do not override; let my WebView load the page
            return false
        }
        else if(url == "http://115.86.172.10:3000/users/joinpage"){
            return false
        }
        return true
    }
}
class WebAppInterface(private val mContext: Context) {
    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(hey:String, hi:String, hello:String) {
        Toast.makeText(mContext, hey, Toast.LENGTH_SHORT).show()
        val intent = Intent(mContext, atmosphere::class.java)
        intent.putExtra("name", hey)
        intent.putExtra("age", hi)
        intent.putExtra("weight", hello)
        mContext.startActivity(intent)
    }
    @JavascriptInterface
    fun getUser(name:String, age:Int, weight:Int){
        Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show()
    }
}