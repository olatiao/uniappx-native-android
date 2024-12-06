@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER")
package uni.UNI4CAF968;
import io.dcloud.uniapp.*;
import io.dcloud.uniapp.extapi.*;
import io.dcloud.uniapp.framework.*;
import io.dcloud.uniapp.runtime.*;
import io.dcloud.uniapp.vue.*;
import io.dcloud.uniapp.vue.shared.*;
import io.dcloud.uts.*;
import io.dcloud.uts.Map;
import io.dcloud.uts.Set;
import io.dcloud.uts.UTSAndroid;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.async;
import io.dcloud.uniapp.extapi.navigateTo as uni_navigateTo;
open class GenPagesIndexIndex : BasePage {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndexIndex) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!;
            val _ctx = __ins.proxy as GenPagesIndexIndex;
            val _cache = __ins.renderCache;
            val title = ref("Hello");
            val goToPage2 = fun(){
                uni_navigateTo(NavigateToOptions(url = "/pages/index2/index2"));
            }
            ;
            return fun(): Any? {
                return createElementVNode("view", utsMapOf("class" to "content"), utsArrayOf(
                    createElementVNode("image", utsMapOf("class" to "logo", "src" to "/static/logo.png")),
                    createElementVNode("view", utsMapOf("class" to "text-area"), utsArrayOf(
                        createElementVNode("text", utsMapOf("class" to "title"), toDisplayString(unref(title)), 1)
                    )),
                    createElementVNode("button", utsMapOf("onClick" to goToPage2), "打开页面2")
                ));
            }
            ;
        }
        ;
        val styles: Map<String, Map<String, Map<String, Any>>>
            get() {
                return normalizeCssStyles(utsArrayOf(
                    styles0
                ), utsArrayOf(
                    GenApp.styles
                ));
            }
        val styles0: Map<String, Map<String, Map<String, Any>>>
            get() {
                return utsMapOf("content" to padStyleMapOf(utsMapOf("display" to "flex", "alignItems" to "center", "justifyContent" to "center")), "logo" to padStyleMapOf(utsMapOf("height" to "200rpx", "width" to "200rpx", "marginTop" to "200rpx", "marginBottom" to "50rpx")), "title" to padStyleMapOf(utsMapOf("fontSize" to "36rpx", "color" to "#8f8f94")));
            }
        var inheritAttrs = true;
        var inject: Map<String, Map<String, Any?>> = utsMapOf();
        var emits: Map<String, Any?> = utsMapOf();
        var props = normalizePropsOptions(utsMapOf());
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf();
        var components: Map<String, CreateVueComponent> = utsMapOf();
    }
}
