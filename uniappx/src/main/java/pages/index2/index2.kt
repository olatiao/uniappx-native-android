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
import io.dcloud.uniapp.extapi.showModal as uni_showModal;
import uts.sdk.modules.kuxPip.start;
import uts.sdk.modules.kuxPip.StartOptions;
import uts.sdk.modules.kuxPip.StartSuccess;
import uts.sdk.modules.kuxPip.checkPermission;
import uts.sdk.modules.kuxPip.checkSupportPIP;
open class GenPagesIndex2Index2 : BasePage {
    constructor(__ins: ComponentInternalInstance) : super(__ins) {}
    companion object {
        @Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")
        var setup: (__props: GenPagesIndex2Index2) -> Any? = fun(__props): Any? {
            val __ins = getCurrentInstance()!!;
            val _ctx = __ins.proxy as GenPagesIndex2Index2;
            val _cache = __ins.renderCache;
            val videoRef = ref<UniVideoElement?>(null);
            val initHeight = ref(200);
            val height = ref(initHeight.value);
            fun genEnterPictureInPictureModeFn() {
                start(StartOptions(success = fun(res: StartSuccess) {
                    console.log(res, "成功回调");
                }
                , fail = fun(res: UniError) {
                    console.log(res, "失败回调");
                }
                , complete = fun(res: Any) {
                    console.log(res, "完成回调");
                }
                , stateChange = fun(res) {
                    if (res.height < initHeight.value) {
                        height.value = res.height;
                    } else {
                        height.value = initHeight.value;
                    }
                }
                ));
            }
            val enterPictureInPictureMode = ::genEnterPictureInPictureModeFn;
            val modal = fun(message: String){
                uni_showModal(ShowModalOptions(title = "提示", content = message, showCancel = false));
            }
            ;
            val onCheckPermission = fun(){
                try {
                    if (checkPermission()) {
                        modal("权限已获取");
                    } else {
                        modal("权限未获取");
                    }
                }
                 catch (err: UniError) {
                    modal("\u68C0\u67E5\u6743\u9650\u5931\u8D25\uFF1A" + err.errMsg);
                }
            }
            ;
            val onCheckSupportPIP = fun(){
                if (checkSupportPIP()) {
                    modal("支持画中画");
                } else {
                    modal("不支持画中画");
                }
            }
            ;
            return fun(): Any? {
                return createElementVNode("scroll-view", utsMapOf("style" to normalizeStyle(utsMapOf("flex" to "1"))), utsArrayOf(
                    if (unref(height) == unref(initHeight)) {
                        createElementVNode("view", utsMapOf("key" to 0, "class" to "status_bar"));
                    } else {
                        createCommentVNode("v-if", true);
                    }
                    ,
                    createElementVNode("view", null, utsArrayOf(
                        createElementVNode("video", utsMapOf("ref_key" to "videoRef", "ref" to videoRef, "style" to normalizeStyle(utsArrayOf(
                            utsMapOf("width" to "100%"),
                            utsMapOf("height" to (unref(height) + "px"))
                        )), "controls" to true, "src" to "http://www.runoob.com/try/demo_source/mov_bbb.mp4"), null, 4)
                    )),
                    createElementVNode("button", utsMapOf("onClick" to enterPictureInPictureMode), "开启画中画模式"),
                    createElementVNode("button", utsMapOf("onClick" to onCheckPermission), "检查权限是否被授予"),
                    createElementVNode("button", utsMapOf("onClick" to onCheckSupportPIP), "检查是否支持画中画.")
                ), 4);
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
                return utsMapOf("status_bar" to padStyleMapOf(utsMapOf("height" to CSS_VAR_STATUS_BAR_HEIGHT, "width" to "100%", "backgroundColor" to "#000000")));
            }
        var inheritAttrs = true;
        var inject: Map<String, Map<String, Any?>> = utsMapOf();
        var emits: Map<String, Any?> = utsMapOf();
        var props = normalizePropsOptions(utsMapOf());
        var propsNeedCastKeys: UTSArray<String> = utsArrayOf();
        var components: Map<String, CreateVueComponent> = utsMapOf();
    }
}
