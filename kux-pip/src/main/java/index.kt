@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME", "UNUSED_ANONYMOUS_PARAMETER")
package uts.sdk.modules.kuxPip;
import android.app.PictureInPictureParams;
import android.os.Build;
import android.util.Rational;
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
open class StartSuccess (
    @JsonNotNull
    open var errCode: Number,
    @JsonNotNull
    open var errMsg: String,
) : UTSObject()
open class StateChange (
    @JsonNotNull
    open var width: Number,
    @JsonNotNull
    open var height: Number,
) : UTSObject()
open class StartOptions (
    open var numerator: Number? = null,
    open var denominator: Number? = null,
    open var success: ((res: StartSuccess) -> Unit)? = null,
    open var fail: ((res: UniError) -> Unit)? = null,
    open var complete: ((res: Any) -> Unit)? = null,
    open var stateChange: ((res: StateChange) -> Unit)? = null,
) : UTSObject()
typealias Start = (options: StartOptions) -> Unit;
typealias StartErrorCode = Number;
interface StartFail : IUniError {
    override var errCode: StartErrorCode
}
val UniErrorSubject = "kux-pip";
val UniErrors: Map<StartErrorCode, String> = Map(utsArrayOf(
    utsArrayOf(
        1001,
        "开启画中画失败"
    ),
    utsArrayOf(
        1002,
        "用户拒绝了画中画授权"
    ),
    utsArrayOf(
        1003,
        "当前系统版本不支持画中画"
    )
));
open class StartFailImpl : UniError, StartFail {
    constructor(errCode: StartErrorCode) : super() {
        this.errSubject = UniErrorSubject;
        this.errCode = errCode;
        this.errMsg = UniErrors[errCode] ?: "";
    }
}
fun enterPictureInPictureMode(numerator: Number, denominator: Number, options: StartOptions) {
    val aspectRatio = Rational(numerator.toInt(), denominator.toInt());
    val pipBuilder: PictureInPictureParams.Builder = PictureInPictureParams.Builder();
    pipBuilder.setAspectRatio(aspectRatio).build();
    UTSAndroid.getUniActivity()!!.enterPictureInPictureMode(pipBuilder.build());
    val res = StartSuccess(errCode = 0, errMsg = "start:ok");
    options.success?.invoke(res);
    options.complete?.invoke(res);
}
val permissionCheck = utsArrayOf(
    "android.permission.FOREGROUND_SERVICE",
    "android.permission.SYSTEM_ALERT_WINDOW"
);
fun checkSupportPIP(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
}
fun checkPermission(): Boolean {
    if (checkSupportPIP()) {
        return UTSAndroid.checkSystemPermissionGranted(UTSAndroid.getUniActivity()!!, permissionCheck);
    } else {
        val res = StartFailImpl(1003);
        throw res;
    }
}
fun start(options: StartOptions) {
    if (checkSupportPIP()) {
        val numerator = options?.numerator ?: 16;
        val denominator = options?.denominator ?: 9;
        try {
            UTSAndroid.requestSystemPermission(UTSAndroid.getUniActivity()!!, permissionCheck, fun(allRight: Boolean, grantedList: UTSArray<String>){
                if (allRight) {
                    enterPictureInPictureMode(numerator, denominator, options);
                } else {
                    enterPictureInPictureMode(numerator, denominator, options);
                }
            }, fun(doNotAskAgain: Boolean, grantedList: UTSArray<String>){
                if (doNotAskAgain) {
                    var res = StartFailImpl(1002);
                    res.cause = SourceError("用户拒绝了权限，并且选择不再询问");
                    options.fail?.invoke(res);
                    options.complete?.invoke(res);
                }
            });
        } catch (e: Throwable) {
            var res = StartFailImpl(1001);
            res.cause = SourceError(e.message ?: "");
            options.fail?.invoke(res);
            options.complete?.invoke(res);
        }
    } else {
        var res = StartFailImpl(1003);
        options.fail?.invoke(res);
        options.complete?.invoke(res);
    }
    UTSAndroid.onAppConfigChange(fun(res){
        val widthDp = res.get("screenWidthDp") as Number;
        val heightDp = res.get("screenHeightDp") as Number;
        val stateChange = StateChange(width = widthDp, height = heightDp);
        options.stateChange?.invoke(stateChange);
    }
    );
}
