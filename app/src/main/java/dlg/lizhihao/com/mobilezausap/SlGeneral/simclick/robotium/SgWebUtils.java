package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium;

import android.content.Context;
import android.os.Handler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Contains web related methods. Examples are:
 * enterTextIntoWebElement(), getWebTexts(), getWebElements().
 *
 * @author Renas Reda, renas.reda@robotium.com
 *
 */

class SgWebUtils {

    private static final String TAG = WebUtils.class.getSimpleName();

    SgRobotiumWebClient robotiumWebCLient;
    SgRobotiumWebViewClient robotiumWebViewClient;
    WebElementCreator webElementCreator;
    WebChromeClient originalWebChromeClient = null;
    WebViewClient originalWebViewClient = null;
    WebView mWebView;
    private Solo.Config config;
    private Context mContext;
    private Handler mHandler;

    public SgWebUtils(Context context, WebView webView, Solo.Config config, Handler handler, Sleeper sleeper){
        this.config = config;
        webElementCreator = new WebElementCreator(sleeper);
        robotiumWebCLient = new SgRobotiumWebClient(handler, webElementCreator);
        robotiumWebViewClient = new SgRobotiumWebViewClient(handler);
        mWebView = webView;
        mContext = context;
        mHandler = handler;
    }



    /**
     * Returns {@code TextView} objects based on web elements shown in the present WebViews
     *
     * @param onlyFromVisibleWebViews true if only from visible WebViews
     * @return an {@code ArrayList} of {@code TextViews}s created from the present {@code WebView}s
     */

    public ArrayList<TextView> getTextViewsFromWebView(){
        boolean javaScriptWasExecuted = executeJavaScriptFunction("allTexts();");

        return createAndReturnTextViewsFromWebElements(javaScriptWasExecuted);
    }

    /**
     * Creates and returns TextView objects based on WebElements
     *
     * @return an ArrayList with TextViews
     */

    private ArrayList <TextView> createAndReturnTextViewsFromWebElements(boolean javaScriptWasExecuted){
        ArrayList<TextView> webElementsAsTextViews = new ArrayList<TextView>();

        if(javaScriptWasExecuted){
            for(WebElement webElement : webElementCreator.getWebElementsFromWebViews()){
                if(isWebElementSufficientlyShown(webElement)){
                    RobotiumTextView textView = new RobotiumTextView(mContext, webElement.getText(), webElement.getLocationX(), webElement.getLocationY());
                    webElementsAsTextViews.add(textView);
                }
            }
        }
        return webElementsAsTextViews;
    }

    /**
     * Returns an ArrayList of WebElements currently shown in the active WebView.
     *
     * @param onlySufficientlyVisible true if only sufficiently visible {@link WebElement} objects should be returned
     * @return an {@code ArrayList} of the {@link WebElement} objects shown in the active WebView
     */

    public ArrayList<WebElement> getWebElements(boolean onlySufficientlyVisible){
        boolean javaScriptWasExecuted = executeJavaScriptFunction("allWebElements();");

        return getWebElements(javaScriptWasExecuted, onlySufficientlyVisible);
    }

    /**
     * Returns an ArrayList of WebElements of the specified By object currently shown in the active WebView.
     *
     * @param by the By object. Examples are By.id("id") and By.name("name")
     * @param onlySufficientlyVisible true if only sufficiently visible {@link WebElement} objects should be returned
     * @return an {@code ArrayList} of the {@link WebElement} objects currently shown in the active WebView
     */

    public ArrayList<WebElement> getWebElements(final By by, boolean onlySufficientlyVisbile){
        boolean javaScriptWasExecuted = executeJavaScript(by, false);

        if(config.useJavaScriptToClickWebElements){
            if(!javaScriptWasExecuted){
                return new ArrayList<WebElement>();
            }
            return webElementCreator.getWebElementsFromWebViews();
        }

        return getWebElements(javaScriptWasExecuted, onlySufficientlyVisbile);
    }

    /**
     * Returns the sufficiently shown WebElements
     *
     * @param javaScriptWasExecuted true if JavaScript was executed
     * @param onlySufficientlyVisible true if only sufficiently visible {@link WebElement} objects should be returned
     * @return the sufficiently shown WebElements
     */

    private ArrayList<WebElement> getWebElements(boolean javaScriptWasExecuted, boolean onlySufficientlyVisbile){
        ArrayList<WebElement> webElements = new ArrayList<WebElement>();

        if(javaScriptWasExecuted){
            for(WebElement webElement : webElementCreator.getWebElementsFromWebViews()){
                if(!onlySufficientlyVisbile){
                    webElements.add(webElement);
                }
                else if(isWebElementSufficientlyShown(webElement)){
                    webElements.add(webElement);
                }
            }
        }
        return webElements;
    }

    /**
     * Prepares for start of JavaScript execution
     *
     * @return the JavaScript as a String
     */

    private String prepareForStartOfJavascriptExecution(List<WebView> webViews) {
        webElementCreator.prepareForStart();

        WebChromeClient currentWebChromeClient = getCurrentWebChromeClient();
        WebViewClient currentWebViewClient = getCurrentWebViewClient();
        if(currentWebChromeClient != null && !currentWebChromeClient.getClass().isAssignableFrom(SgRobotiumWebClient.class)){
            originalWebChromeClient = currentWebChromeClient;
        }
        if (currentWebViewClient != null && !currentWebViewClient.getClass().isAssignableFrom(SgRobotiumWebViewClient.class)){
            originalWebViewClient = currentWebViewClient;
        }

        robotiumWebCLient.enableJavascriptAndSetRobotiumWebClient(webViews, originalWebChromeClient);
//		robotiumWebViewClient.setRobotiumWebviewClient(webViews, originalWebViewClient);
        return getJavaScriptAsString();
    }

    /**
     * Returns the current WebChromeClient through reflection
     *
     * @return the current WebChromeClient
     *
     */

    private WebChromeClient getCurrentWebChromeClient(){
        WebChromeClient currentWebChromeClient = null;

        Object currentWebView = mWebView;

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            try{
                currentWebView = new Reflect(currentWebView).field("mProvider").out(Object.class);
            }catch(IllegalArgumentException ignored) {}
        }

        try{
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                Object mClientAdapter = new Reflect(currentWebView).field("mContentsClientAdapter").out(Object.class);
                currentWebChromeClient = new Reflect(mClientAdapter).field("mWebChromeClient").out(WebChromeClient.class);
            }
            else {
                Object mCallbackProxy = new Reflect(currentWebView).field("mCallbackProxy").out(Object.class);
                currentWebChromeClient = new Reflect(mCallbackProxy).field("mWebChromeClient").out(WebChromeClient.class);
            }
        }catch(Exception ignored){}

        return currentWebChromeClient;
    }

    private WebViewClient getCurrentWebViewClient() {

        WebViewClient currentWebViewClient = null;

        Object currentWebView = mWebView;

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            try{
                currentWebView = new Reflect(currentWebView).field("mProvider").out(Object.class);
            }catch(IllegalArgumentException ignored) {}
        }

        try{
            if (android.os.Build.VERSION.SDK_INT >= 19) {
                Object mClientAdapter = new Reflect(currentWebView).field("mContentsClientAdapter").out(Object.class);
                currentWebViewClient = new Reflect(mClientAdapter).field("mWebViewClient").out(WebViewClient.class);
            }
            else {
                Object mCallbackProxy = new Reflect(currentWebView).field("mCallbackProxy").out(Object.class);
                currentWebViewClient = new Reflect(mCallbackProxy).field("mWebViewClient").out(WebViewClient.class);
            }
        }catch(Exception ignored){}

        return currentWebViewClient;
    }

    /**
     * Enters text into a web element using the given By method
     *
     * @param by the By object e.g. By.id("id");
     * @param text the text to enter
     */

    public void enterTextIntoWebElement(final By by, final String text){
        if(by instanceof By.Id){
            executeJavaScriptFunction("enterTextById(\""+by.getValue()+"\", \""+text+"\");");
        }
        else if(by instanceof By.Xpath){
            executeJavaScriptFunction("enterTextByXpath(\""+by.getValue()+"\", \""+text+"\");");
        }
        else if(by instanceof By.CssSelector){
            executeJavaScriptFunction("enterTextByCssSelector(\""+by.getValue()+"\", \""+text+"\");");
        }
        else if(by instanceof By.Name){
            executeJavaScriptFunction("enterTextByName(\""+by.getValue()+"\", \""+text+"\");");
        }
        else if(by instanceof By.ClassName){
            executeJavaScriptFunction("enterTextByClassName(\""+by.getValue()+"\", \""+text+"\");");
        }
        else if(by instanceof By.Text){
            executeJavaScriptFunction("enterTextByTextContent(\""+by.getValue()+"\", \""+text+"\");");
        }
        else if(by instanceof By.TagName){
            executeJavaScriptFunction("enterTextByTagName(\""+by.getValue()+"\", \""+text+"\");");
        }
    }

    /**
     * Executes JavaScript determined by the given By object
     *
     * @param by the By object e.g. By.id("id");
     * @param shouldClick true if click should be performed
     * @return true if JavaScript function was executed
     */

    public boolean executeJavaScript(final By by, boolean shouldClick){
        if(by instanceof By.Id){
            return executeJavaScriptFunction("id(\""+by.getValue()+"\", \"" + String.valueOf(shouldClick) + "\");");
        }
        else if(by instanceof By.Xpath){
            return executeJavaScriptFunction("xpath(\""+by.getValue()+"\", \"" + String.valueOf(shouldClick) + "\");");
        }
        else if(by instanceof By.CssSelector){
            return executeJavaScriptFunction("cssSelector(\""+by.getValue()+"\", \"" + String.valueOf(shouldClick) + "\");");
        }
        else if(by instanceof By.Name){
            return executeJavaScriptFunction("name(\""+by.getValue()+"\", \"" + String.valueOf(shouldClick) + "\");");
        }
        else if(by instanceof By.ClassName){
            return executeJavaScriptFunction("className(\""+by.getValue()+"\", \"" + String.valueOf(shouldClick) + "\");");
        }
        else if(by instanceof By.Text){
            return executeJavaScriptFunction("textContent(\""+by.getValue()+"\", \"" + String.valueOf(shouldClick) + "\");");
        }
        else if(by instanceof By.TagName){
            return executeJavaScriptFunction("tagName(\""+by.getValue()+"\", \"" + String.valueOf(shouldClick) + "\");");
        }
        else if(by instanceof By.Index) {
            return executeJavaScriptFunction("byIndex(\""+by.getValue()+"\", \"" + String.valueOf(shouldClick) + "\");");
        }
        return false;
    }

    /**
     * Executes the given JavaScript function
     *
     * @param function the function as a String
     * @return true if JavaScript function was executed
     */

    private boolean executeJavaScriptFunction(final String function) {
//        List<WebView> webViews = viewFetcher.getCurrentViews(WebView.class, true);
        final WebView webView = mWebView;
        if(webView == null) {
            return false;
        }
        List<WebView> webViews = new ArrayList<>();
        webViews.add(mWebView);
        final String javaScript = setWebFrame(prepareForStartOfJavascriptExecution(webViews));
        mHandler.post(new Runnable() {
            public void run() {
                if(webView != null){
                    webView.loadUrl("javascript:" + javaScript + function);
                }
            }
        });
        return true;
    }

    private String setWebFrame(String javascript){
        String frame = config.webFrame;

        if(frame.isEmpty() || frame.equals("document")){
            return javascript;
        }
        javascript = javascript.replaceAll(Pattern.quote("document, "), "document.getElementById(\""+frame+"\").contentDocument, ");
        javascript = javascript.replaceAll(Pattern.quote("document.body, "), "document.getElementById(\""+frame+"\").contentDocument, ");
        return javascript;
    }

    /**
     * Returns true if the view is sufficiently shown
     *
     * @param view the view to check
     * @return true if the view is sufficiently shown
     */

    public final boolean isWebElementSufficientlyShown(WebElement webElement){
        final WebView webView = mWebView;
        final int[] xyWebView = new int[2];

        if(webView != null && webElement != null){
            webView.getLocationOnScreen(xyWebView);

            if(xyWebView[1] + webView.getHeight() > webElement.getLocationY())
                return true;
        }
        return false;
    }

    /**
     * Splits a name by upper case.
     *
     * @param name the name to split
     * @return a String with the split name
     *
     */

    public String splitNameByUpperCase(String name) {
        String [] texts = name.split("(?=\\p{Upper})");
        StringBuilder stringToReturn = new StringBuilder();

        for(String string : texts){

            if(stringToReturn.length() > 0) {
                stringToReturn.append(" " + string.toLowerCase());
            }
            else {
                stringToReturn.append(string.toLowerCase());
            }
        }
        return stringToReturn.toString();
    }

    /**
     * Returns the JavaScript file RobotiumWeb.js as a String
     *
     * @return the JavaScript file RobotiumWeb.js as a {@code String}
     */

    private String getJavaScriptAsString() {
        //update by sogood
        return "/**\n" +
                " * Used by the web methods.\n" +
                " * \n" +
                " * @author Renas Reda, renas.reda@robotium.com\n" +
                " * \n" +
                " */\n" +
                "\n" +
                "function allWebElements() {\n" +
                "\tfor (var key in document.all){\n" +
                "\t\ttry{\n" +
                "\t\t\tpromptElement(document.all[key]);\t\t\t\n" +
                "\t\t}catch(ignored){}\n" +
                "\t}\n" +
                "\tfinished();\n" +
                "}\n" +
                "\n" +
                "function allTexts() {\n" +
                "\tvar range = document.createRange();\n" +
                "\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_TEXT, null, false); \n" +
                "\twhile(n=walk.nextNode()){\n" +
                "\t\ttry{\n" +
                "\t\t\tpromptText(n, range);\n" +
                "\t\t}catch(ignored){}\n" +
                "\t} \n" +
                "\tfinished();\n" +
                "}\n" +
                "\n" +
                "function clickElement(element){\n" +
                "\tvar e = document.createEvent('MouseEvents');\n" +
                "\te.initMouseEvent('click', true, true, window, 1, 0, 0, 0, 0, false, false, false, false, 0, null);\n" +
                "\telement.dispatchEvent(e);\n" +
                "}\n" +
                "\n" +
                "function byIndex(index, click) {\n" +
                "    var i = 0;\n" +
                "    for (var key in document.all){\n" +
                "        try{\n" +
                "            var rect = document.all[key].getBoundingClientRect();\n" +
                "            var visible = false;\n" +
                "            if(rect.width > 0 && rect.height > 0 && rect.left >= 0 && rect.top >= 0){\n" +
                "                visible = true;\n" +
                "            } else {\n" +
                "                continue;\n" +
                "            }\n" +
                "            if (i == index) {\n" +
                "                if(click == 'true'){\n" +
                "                    clickElement(document.all[key]);\n" +
                "                }\n" +
                "                else{\n" +
                "                    promptElement(document.all[key]);\n" +
                "                }\n" +
                "            }\n" +
                "            if (visible) {\n" +
                "                i++;\n" +
                "            }\n" +
                "        }catch(ignored){}\n" +
                "\n" +
                "\n" +
                "    }\n" +
                "    finished();\n" +
                "}\n" +
                "\n" +
                "function id(id, click) {\n" +
                "\tvar element = document.getElementById(id);\n" +
                "\tif(element != null){ \n" +
                "\n" +
                "\t\tif(click == 'true'){\n" +
                "\t\t\tclickElement(element);\n" +
                "\t\t}\n" +
                "\t\telse{\n" +
                "\t\t\tpromptElement(element);\n" +
                "\t\t}\n" +
                "\t} \n" +
                "\telse {\n" +
                "\t\tfor (var key in document.all){\n" +
                "\t\t\ttry{\n" +
                "\t\t\t\telement = document.all[key];\n" +
                "\t\t\t\tif(element.id == id) {\n" +
                "\t\t\t\t\tif(click == 'true'){\n" +
                "\t\t\t\t\t\tclickElement(element);\n" +
                "\t\t\t\t\t\treturn;\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\telse{\n" +
                "\t\t\t\t\t\tpromptElement(element);\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t} catch(ignored){}\t\t\t\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\tfinished(); \n" +
                "}\n" +
                "\n" +
                "function xpath(xpath, click) {\n" +
                "\tvar elements = document.evaluate(xpath, document, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null); \n" +
                "\n" +
                "\tif (elements){\n" +
                "\t\tvar element = elements.iterateNext();\n" +
                "\t\twhile(element) {\n" +
                "\t\t\tif(click == 'true'){\n" +
                "\t\t\t\tclickElement(element);\n" +
                "\t\t\t\treturn;\n" +
                "\t\t\t}\n" +
                "\t\t\telse{\n" +
                "\t\t\t\tpromptElement(element);\n" +
                "\t\t\t\telement = elements.iterateNext();\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t\tfinished();\n" +
                "\t}\n" +
                "}\n" +
                "\n" +
                "function cssSelector(cssSelector, click) {\n" +
                "\tvar elements = document.querySelectorAll(cssSelector);\n" +
                "\tfor (var key in elements) {\n" +
                "\t\tif(elements != null){ \n" +
                "\t\t\ttry{\n" +
                "\t\t\t\tif(click == 'true'){\n" +
                "\t\t\t\t\tclickElement(elements[key]);\n" +
                "\t\t\t\t\treturn;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\telse{\n" +
                "\t\t\t\t\tpromptElement(elements[key]);\n" +
                "\t\t\t\t}\t\n" +
                "\t\t\t}catch(ignored){}  \n" +
                "\t\t}\n" +
                "\t}\n" +
                "\tfinished(); \n" +
                "}\n" +
                "\n" +
                "function name(name, click) {\n" +
                "\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, false); \n" +
                "\twhile(n=walk.nextNode()){\n" +
                "\t\ttry{\n" +
                "\t\t\tvar attributeName = n.getAttribute('name');\n" +
                "\t\t\tif(attributeName != null && attributeName.trim().length>0 && attributeName == name){\n" +
                "\t\t\t\tif(click == 'true'){\n" +
                "\t\t\t\t\tclickElement(n);\n" +
                "\t\t\t\t\treturn;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\telse{\n" +
                "\t\t\t\t\tpromptElement(n);\n" +
                "\t\t\t\t}\t\n" +
                "\t\t\t}\n" +
                "\t\t}catch(ignored){} \n" +
                "\t} \n" +
                "\tfinished();\n" +
                "}\n" +
                "\n" +
                "function className(nameOfClass, click) {\n" +
                "\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, false); \n" +
                "\twhile(n=walk.nextNode()){\n" +
                "\t\ttry{\n" +
                "\t\t\tvar className = n.className; \n" +
                "\t\t\tif(className != null && className.trim().length>0 && className == nameOfClass) {\n" +
                "\t\t\t\tif(click == 'true'){\n" +
                "\t\t\t\t\tclickElement(n);\n" +
                "\t\t\t\t\treturn;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\telse{\n" +
                "\t\t\t\t\tpromptElement(n);\n" +
                "\t\t\t\t}\t\n" +
                "\t\t\t}\n" +
                "\t\t}catch(ignored){} \n" +
                "\t} \n" +
                "\tfinished(); \n" +
                "}\n" +
                "\n" +
                "function textContent(text, click) {\n" +
                "\tvar range = document.createRange();\n" +
                "\tvar walk=document.createTreeWalker(document.body,NodeFilter.SHOW_TEXT,null,false); \n" +
                "\twhile(n=walk.nextNode()){ \n" +
                "\t\ttry{\n" +
                "\t\t\tvar textContent = n.textContent; \n" +
                "\t\t\tif(textContent.trim() == text.trim()){  \n" +
                "\t\t\t\tif(click == 'true'){\n" +
                "\t\t\t\t\tclickElement(n);\n" +
                "\t\t\t\t\treturn;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\telse{\n" +
                "\t\t\t\t\tpromptText(n, range);\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}catch(ignored){} \n" +
                "\t} \n" +
                "\tfinished();  \n" +
                "}\n" +
                "\n" +
                "function tagName(tagName, click) {\n" +
                "\tvar elements = document.getElementsByTagName(tagName);\n" +
                "\tfor (var key in elements) {\n" +
                "\t\tif(elements != null){ \n" +
                "\t\t\ttry{\n" +
                "\t\t\t\tif(click == 'true'){\n" +
                "\t\t\t\t\tclickElement(elements[key]);\n" +
                "\t\t\t\t\treturn;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\telse{\n" +
                "\t\t\t\t\tpromptElement(elements[key]);\n" +
                "\t\t\t\t}\t\n" +
                "\t\t\t}catch(ignored){}  \n" +
                "\t\t}\n" +
                "\t}\n" +
                "\tfinished();\n" +
                "}\n" +
                "\n" +
                "function enterTextById(id, text) {\n" +
                "\tvar element = document.getElementById(id);\n" +
                "\tif(element != null)\n" +
                "\t\telement.value = text;\n" +
                "\n" +
                "\tfinished(); \n" +
                "}\n" +
                "\n" +
                "function enterTextByXpath(xpath, text) {\n" +
                "\tvar element = document.evaluate(xpath, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null ).singleNodeValue;\n" +
                "\tif(element != null)\n" +
                "\t\telement.value = text;\n" +
                "\n" +
                "\tfinished(); \n" +
                "}\n" +
                "\n" +
                "function enterTextByCssSelector(cssSelector, text) {\n" +
                "\tvar element = document.querySelector(cssSelector);\n" +
                "\tif(element != null)\n" +
                "\t\telement.value = text;\n" +
                "\n" +
                "\tfinished(); \n" +
                "}\n" +
                "\n" +
                "function enterTextByName(name, text) {\n" +
                "\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, false); \n" +
                "\twhile(n=walk.nextNode()){\n" +
                "\t\tvar attributeName = n.getAttribute('name');\n" +
                "\t\tif(attributeName != null && attributeName.trim().length>0 && attributeName == name) \n" +
                "\t\t\tn.value=text;  \n" +
                "\t} \n" +
                "\tfinished();\n" +
                "}\n" +
                "\n" +
                "function enterTextByClassName(name, text) {\n" +
                "\tvar walk=document.createTreeWalker(document.body, NodeFilter.SHOW_ELEMENT, null, false); \n" +
                "\twhile(n=walk.nextNode()){\n" +
                "\t\tvar className = n.className; \n" +
                "\t\tif(className != null && className.trim().length>0 && className == name) \n" +
                "\t\t\tn.value=text;\n" +
                "\t}\n" +
                "\tfinished();\n" +
                "}\n" +
                "\n" +
                "function enterTextByTextContent(textContent, text) {\n" +
                "\tvar walk=document.createTreeWalker(document.body,NodeFilter.SHOW_TEXT, null, false); \n" +
                "\twhile(n=walk.nextNode()){ \n" +
                "\t\tvar textValue = n.textContent; \n" +
                "\t\tif(textValue == textContent) \n" +
                "\t\t\tn.parentNode.value = text; \n" +
                "\t}\n" +
                "\tfinished();\n" +
                "}\n" +
                "\n" +
                "function enterTextByTagName(tagName, text) {\n" +
                "\tvar elements = document.getElementsByTagName(tagName);\n" +
                "\tif(elements != null){\n" +
                "\t\telements[0].value = text;\n" +
                "\t}\n" +
                "\tfinished();\n" +
                "}\n" +
                "\n" +
                "function promptElement(element) {\n" +
                "\tvar id = element.id;\n" +
                "\tvar text = element.innerText;\n" +
                "\tif(text.trim().length == 0){\n" +
                "\t\ttext = element.value;\n" +
                "\t}\n" +
                "\tvar name = element.getAttribute('name');\n" +
                "\tvar className = element.className;\n" +
                "\tvar tagName = element.tagName;\n" +
                "\tvar attributes = \"\";\n" +
                "\tvar htmlAttributes = element.attributes;\n" +
                "\tfor (var i = 0, htmlAttribute; htmlAttribute = htmlAttributes[i]; i++){\n" +
                "\t\tattributes += htmlAttribute.name + \"::\" + htmlAttribute.value;\n" +
                "\t\tif (i + 1 < htmlAttributes.length) {\n" +
                "\t\t\tattributes += \"#$\";\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\n" +
                "\tvar rect = element.getBoundingClientRect();\n" +
                "\tif(rect.width > 0 && rect.height > 0 && rect.left >= 0 && rect.top >= 0){\n" +
                "\t\tprompt(id + ';,' + text + ';,' + name + \";,\" + className + \";,\" + tagName + \";,\" + rect.left + ';,' + rect.top + ';,' + rect.width + ';,' + rect.height + ';,' + attributes);\n" +
                "\t}\n" +
                "}\n" +
                "\n" +
                "function promptText(element, range) {\t\n" +
                "\tvar text = element.textContent;\n" +
                "\tif(text.trim().length>0) {\n" +
                "\t\trange.selectNodeContents(element);\n" +
                "\t\tvar rect = range.getBoundingClientRect();\n" +
                "\t\tif(rect.width > 0 && rect.height > 0 && rect.left >= 0 && rect.top >= 0){\n" +
                "\t\t\tvar id = element.parentNode.id;\n" +
                "\t\t\tvar name = element.parentNode.getAttribute('name');\n" +
                "\t\t\tvar className = element.parentNode.className;\n" +
                "\t\t\tvar tagName = element.parentNode.tagName;\n" +
                "\t\t\tprompt(id + ';,' + text + ';,' + name + \";,\" + className + \";,\" + tagName + \";,\" + rect.left + ';,' + rect.top + ';,' + rect.width + ';,' + rect.height);\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}\n" +
                "\n" +
                "function finished(){\n" +
                "\tprompt('robotium-finished');\n" +
                "}\n";
    }
}