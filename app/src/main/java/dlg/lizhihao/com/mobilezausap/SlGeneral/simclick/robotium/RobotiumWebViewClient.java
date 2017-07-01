package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;

/**
 * Created by sogood on 16/11/2.
 */

public class RobotiumWebViewClient extends WebViewClient {

    private static final String TAG = RobotiumWebViewClient.class.getSimpleName();

    private Instrumentation inst;
    private WebViewClient robotiumWebClient;
    private WebViewClient originalWebViewClient = null;

    public RobotiumWebViewClient(Instrumentation inst){
        this.inst = inst;
        robotiumWebClient = this;
    }

    public void setRobotiumWebviewClient(List<WebView> webViews, WebViewClient originalWebViewClient) {
        this.originalWebViewClient = originalWebViewClient;
        for(final WebView webView : webViews){
            if(webView != null){
                inst.runOnMainSync(new Runnable() {
                    public void run() {
                        webView.setWebViewClient(robotiumWebClient);
                    }
                });
            }
        }
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onFormResubmission(view, dontResend, resend);
        }
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onLoadResource(view, url);
        }
    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        if (null != originalWebViewClient) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                originalWebViewClient.onPageCommitVisible(view, url);
            }
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (null != originalWebViewClient) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                originalWebViewClient.onReceivedClientCertRequest(view, request);
            }
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (null != originalWebViewClient) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                originalWebViewClient.onReceivedError(view, request, error);
            }
        }
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (null != originalWebViewClient) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                originalWebViewClient.onReceivedHttpError(view, request, errorResponse);
            }
        }
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onReceivedLoginRequest(view, realm, account, args);
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onReceivedSslError(view, handler, error);
        }
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onScaleChanged(view, oldScale, newScale);
        }
    }
/**
    @Override
    public void onUnhandledInputEvent(WebView view, InputEvent event) {
        if (null != originalWebViewClient) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                originalWebViewClient.onUnhandledInputEvent(view, event);
            }
        }
    }
 **/

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onTooManyRedirects(view, cancelMsg, continueMsg);
        }
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onUnhandledKeyEvent(view, event);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (null != originalWebViewClient) {
            originalWebViewClient.onPageFinished(view, url);
        }
        LogUtil.i(TAG, "onPageFinished: " + url);
    }
}
