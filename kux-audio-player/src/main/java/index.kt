@file:Suppress("UNCHECKED_CAST", "USELESS_CAST", "INAPPLICABLE_JVM_NAME")
package uts.sdk.modules.kuxAudioPlayer;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DeviceInfo;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.Commands;
import com.google.android.exoplayer2.Player.Events;
import com.google.android.exoplayer2.Player.PositionInfo;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.video.VideoSize;
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
import java.io.IOException;
import java.util.List;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.async;
typealias OnErrorCallback = (error: ApiFail) -> Unit;
typealias OnCommonCallback = () -> Unit;
typealias Loop = String;
typealias SrcMode = String;
interface IAudioPlayer {
    var src: String
    fun setPlaybackSrc(src: String)
    var startTime: Number
    fun setPlaybackStartTime(startTime: Number)
    var autoplay: Boolean
    fun setPlaybackAutoplay(autoplay: Boolean)
    var autonext: Boolean
    fun setPlaybackAutonext(autonext: Boolean)
    var loop: Loop
    fun setPlaybackLoop(loop: Loop)
    var obeyMuteSwitch: Boolean
    fun setPlaybackObeyMuteSwitch(obeyMuteSwitch: Boolean)
    var volume: Number
    fun setPlaybackVolume(volume: Number)
    var playbackRate: Number
    fun setPlaybackRates(rate: Number)
    val duration: Number
    var currentTime: Number
    fun setPlaybackCurrentTime(currentTime: Number)
    val paused: Boolean
    val buffered: Number
    var currentIndex: Number
    fun setPlaybackCurrentIndex(index: Number)
    var srcMode: SrcMode
    fun setPlaybackSrcMode(mode: SrcMode)
    fun play()
    fun pause()
    fun stop()
    fun seek(position: Number)
    fun destroy()
    fun onError(callback: OnErrorCallback)
    fun offError()
    fun onCanplay(callback: OnCommonCallback)
    fun offCanplay()
    fun onPlay(callback: OnCommonCallback)
    fun offPlay()
    fun onPause(callback: OnCommonCallback)
    fun offPause()
    fun onStop(callback: OnCommonCallback)
    fun offStop()
    fun onEnded(callback: OnCommonCallback)
    fun offEnded()
    fun onTimeUpdate(callback: OnCommonCallback)
    fun offTimeUpdate()
    fun onWaiting(callback: OnCommonCallback)
    fun offWaiting()
    fun onSeeking(callback: OnCommonCallback)
    fun offSeeking()
    fun onSeeked(callback: OnCommonCallback)
    fun offSeeked()
    fun onNext(callback: OnCommonCallback)
    fun offNext()
}
typealias ApiErrorCode = Number;
interface ApiFail : IUniError {
    override var errCode: ApiErrorCode
}
typealias OnStateChangeCallback = (playWhenReady: Boolean, playbackState: Int) -> Unit;
typealias OnErrorCallback1 = (error: PlaybackException) -> Unit;
typealias OnTimeUpdateCallback = (reason: Int) -> Unit;
typealias OnSeekingCallback = (reason: Int) -> Unit;
typealias OnMediaItemChangeCallback = (reason: Int) -> Unit;
@Suppress("DEPRECATION")
open class PlayerListener : Player.Listener {
    private var player: SimpleExoPlayer;
    private var onStateChangeCallback: OnStateChangeCallback?;
    private var onErrorCallback: OnErrorCallback1?;
    private var onTimeUpdateCallback: OnTimeUpdateCallback?;
    private var onSeekingCallback: OnSeekingCallback?;
    private var onSeekedCallback: OnCommonCallback?;
    private var onMediaItemChangeCallback: OnMediaItemChangeCallback?;
    constructor(player: SimpleExoPlayer) : super() {
        this.player = player;
        this.onStateChangeCallback = null;
        this.onErrorCallback = null;
        this.onTimeUpdateCallback = null;
        this.onSeekingCallback = null;
        this.onSeekedCallback = null;
        this.onMediaItemChangeCallback = null;
    }
    override fun onAudioAttributesChanged(audioAttributes: AudioAttributes): Unit {}
    override fun onAudioSessionIdChanged(audioSessionId: Int): Unit {}
    override fun onAvailableCommandsChanged(availableCommands: Commands): Unit {}
    override fun onDeviceInfoChanged(deviceInfo: DeviceInfo): Unit {}
    override fun onDeviceVolumeChanged(volume: Int, muted: Boolean): Unit {}
    override fun onEvents(player: Player, events: Events): Unit {}
    override fun onIsLoadingChanged(isLoading: Boolean): Unit {}
    override fun onIsPlayingChanged(isPlaying: Boolean): Unit {}
    override fun onLoadingChanged(isLoading: Boolean): Unit {}
    override fun onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs: Long): Unit {}
    public open fun onMediaItemChange(callback: OnMediaItemChangeCallback): Unit {
        this.onMediaItemChangeCallback = callback;
    }
    override fun onMediaItemTransition(mediaItem: com.google.android.exoplayer2.MediaItem?, reason: Int): Unit {
        this.onMediaItemChangeCallback?.invoke(reason);
    }
    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata): Unit {}
    override fun onMetadata(metadata: Metadata): Unit {}
    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int): Unit {}
    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters): Unit {}
    override fun onPlaybackStateChanged(playbackState: Int): Unit {}
    override fun onPlaybackSuppressionReasonChanged(playbackSuppressionReason: Int): Unit {}
    public open fun onError(callback: OnErrorCallback1): Unit {
        this.onErrorCallback = callback;
    }
    override fun onPlayerError(error: PlaybackException): Unit {
        this.onErrorCallback?.invoke(error);
    }
    override fun onPlayerErrorChanged(error: PlaybackException?): Unit {}
    public open fun onStateChange(callback: OnStateChangeCallback): Unit {
        this.onStateChangeCallback = callback;
    }
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int): Unit {
        this.onStateChangeCallback?.invoke(playWhenReady, playbackState);
    }
    override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata): Unit {}
    public open fun onSeeking(callback: OnSeekingCallback): Unit {
        this.onSeekingCallback = callback;
    }
    override fun onPositionDiscontinuity(reason: Int): Unit {
        this.onSeekingCallback?.invoke(reason);
    }
    public open fun onTimeUpdate(callback: OnTimeUpdateCallback): Unit {
        this.onTimeUpdateCallback = callback;
    }
    override fun onPositionDiscontinuity(oldPosition: PositionInfo, newPosition: PositionInfo, reason: Int): Unit {}
    override fun onRenderedFirstFrame(): Unit {}
    override fun onRepeatModeChanged(repeatMode: Int): Unit {}
    override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long): Unit {}
    override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long): Unit {}
    public open fun onSeeked(callback: OnCommonCallback): Unit {
        this.onSeekedCallback = callback;
    }
    override fun onSeekProcessed(): Unit {
        this.onSeekedCallback?.invoke();
    }
    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean): Unit {}
    override fun onSkipSilenceEnabledChanged(skipSilenceEnabled: Boolean): Unit {}
    override fun onSurfaceSizeChanged(width: Int, height: Int): Unit {}
    override fun onTimelineChanged(timeline: Timeline, reason: Int): Unit {}
    override fun onTrackSelectionParametersChanged(parameters: TrackSelectionParameters): Unit {}
    override fun onTracksChanged(tracks: Tracks): Unit {}
    override fun onVideoSizeChanged(videoSize: VideoSize): Unit {}
    override fun onVolumeChanged(volume: Float): Unit {}
    private fun handlePlayerError(error: PlaybackException): Unit {
        val exoError: ExoPlaybackException = error as ExoPlaybackException;
        if (exoError.type == ExoPlaybackException.TYPE_SOURCE) {
            val sourceException: IOException = exoError.getSourceException();
            if (sourceException is HttpDataSource.HttpDataSourceException) {
                val httpException: HttpDataSource.HttpDataSourceException = sourceException;
                console.log(httpException, " at uni_modules/kux-audio-player/utssdk/app-android/listener/playerListener.uts:170");
                console.log("网络异常", " at uni_modules/kux-audio-player/utssdk/app-android/listener/playerListener.uts:171");
            } else if (sourceException is IOException) {
                console.log("IO异常", " at uni_modules/kux-audio-player/utssdk/app-android/listener/playerListener.uts:173");
            }
        }
        console.log(error, ExoPlaybackException.TYPE_SOURCE, ExoPlaybackException.TYPE_RENDERER, ExoPlaybackException.TYPE_UNEXPECTED, ExoPlaybackException.TYPE_REMOTE, " at uni_modules/kux-audio-player/utssdk/app-android/listener/playerListener.uts:176");
    }
}
val UniErrorSubject = "kux-audio-player";
val APIErrors: Map<ApiErrorCode, String> = Map(utsArrayOf(
    utsArrayOf(
        10001,
        "系统错误"
    ),
    utsArrayOf(
        10002,
        "网络错误"
    ),
    utsArrayOf(
        10003,
        "文件错误"
    ),
    utsArrayOf(
        10004,
        "格式错误"
    ),
    utsArrayOf(
        -1,
        "未知错误"
    )
));
open class ApiFailImpl : UniError, ApiFail {
    constructor(errCode: ApiErrorCode) : super() {
        this.errSubject = UniErrorSubject;
        this.errCode = errCode;
        this.errMsg = APIErrors.get(errCode) ?: "";
    }
}
val getResourcePath = fun(path: String): String {
    return UTSAndroid.getResourcePath(path);
}
;
@Suppress("DEPRECATION")
open class AudioPlayerManager : IAudioPlayer {
    private var audioPlayer: SimpleExoPlayer;
    private var trackSelector: DefaultTrackSelector;
    private var loadControl: DefaultLoadControl;
    private var onErrorCallback: OnErrorCallback?;
    private var onCanplayCallback: OnCommonCallback?;
    private var onPlayCallback: OnCommonCallback?;
    private var onPauseCallback: OnCommonCallback?;
    private var onStopCallback: OnCommonCallback?;
    private var onEndedCallback: OnCommonCallback?;
    private var onTimeUpdateCallback: OnCommonCallback?;
    private var onWaitingCallback: OnCommonCallback?;
    private var onSeekingCallback: OnCommonCallback?;
    private var onSeekedCallback: OnCommonCallback?;
    private var onNextCallback: OnCommonCallback?;
    private var _duration: Number = 0;
    private var _src: String = "";
    private var _urls: UTSArray<String> = utsArrayOf();
    private var _autoplay: Boolean = false;
    private var _loop: Loop = "off";
    private var _playbackSpeed: Number = 1;
    private var initPlay: Boolean = false;
    private var _playCallback: Boolean = true;
    private var _seekCallback: Boolean = true;
    private var initSetMediaItems: Boolean = true;
    private var _buffered: Number = 0;
    private var _startTime: Number = 0;
    private var _autonext: Boolean = true;
    private var _volume: Number = 1.0;
    private var _currentIndex: Number = 0;
    private var _obeyMuteSwitch: Boolean = true;
    private var _srcMode: SrcMode = "push";
    private var _isSeeking: Boolean = false;
    constructor(){
        this.onErrorCallback = null;
        this.onCanplayCallback = null;
        this.onPlayCallback = null;
        this.onPauseCallback = null;
        this.onStopCallback = null;
        this.onEndedCallback = null;
        this.onTimeUpdateCallback = null;
        this.onWaitingCallback = null;
        this.onSeekingCallback = null;
        this.onSeekedCallback = null;
        this.onNextCallback = null;
        val context = UTSAndroid.getAppContext() as Context;
        this.trackSelector = DefaultTrackSelector(context);
        this.loadControl = DefaultLoadControl();
        this.audioPlayer = SimpleExoPlayer.Builder(context).setTrackSelector(this.trackSelector).build();
        val listener = PlayerListener(this.audioPlayer);
        val handler: Handler? = Handler(Looper.getMainLooper());
        var interval: Number? = null;
        val removeInterval = fun(){
            if (interval != null) {
                clearInterval(interval!!);
                interval = null;
            }
        }
        ;
        listener.onStateChange(fun(playWhenReady: Boolean, playbackState: Int){
            if (playbackState == Player.STATE_READY) {
                this._duration = this.audioPlayer.getDuration();
                this.onCanplayCallback?.invoke();
            } else if (playbackState == Player.STATE_BUFFERING) {
                if (!this._isSeeking) {
                    this.onWaitingCallback?.invoke();
                    val currentPositionUS = this.audioPlayer.getCurrentPosition();
                    val bufferedPositionUS = this.audioPlayer.getBufferedPosition();
                    this._buffered = UTSNumber.from((bufferedPositionUS - currentPositionUS) / 1000000);
                    removeInterval();
                }
            } else if (playbackState == Player.STATE_IDLE) {
                if (this.initPlay) {
                    this.onStopCallback?.invoke();
                    removeInterval();
                }
            } else if (playbackState == Player.STATE_ENDED) {
                removeInterval();
                if (this.initPlay) {
                    if (this._loop == "one") {
                        this._loopOne();
                    } else {
                        if (this.autonext && !this._isPlayEnding()) {
                            this.onNextCallback?.invoke();
                            this._playNext();
                        } else {
                            if (this._loop == "all") {
                                this._loopAll();
                            } else {
                                this.onEndedCallback?.invoke();
                                if (this.initSetMediaItems) {
                                    this._setMediaItems();
                                    this.initSetMediaItems = false;
                                }
                            }
                        }
                    }
                }
            }
            if (playWhenReady) {
                if (this._playCallback) {
                    this.onPlayCallback?.invoke();
                }
                if (playbackState == Player.STATE_READY) {
                    interval = setInterval(fun(){
                        this.onTimeUpdateCallback?.invoke();
                    }
                    , 1000);
                }
            } else {
                if (this.initPlay) {
                    this.onPauseCallback?.invoke();
                    removeInterval();
                }
            }
        }
        );
        listener.onError(fun(error: PlaybackException){
            if (error is ExoPlaybackException) {
                val exoError: ExoPlaybackException = error as ExoPlaybackException;
                if (exoError.type == ExoPlaybackException.TYPE_SOURCE) {
                    val sourceException: IOException = exoError.getSourceException();
                    if (sourceException is HttpDataSource.HttpDataSourceException) {
                        val httpException: HttpDataSource.HttpDataSourceException = sourceException;
                        this._handleException(10002, httpException.message ?: "");
                    } else if (sourceException is IOException) {
                        val message = sourceException.message as String;
                        if (message.includes("None of the available extractors")) {
                            this._handleException(10004, JSON.stringify(sourceException));
                        } else {
                            this._handleException(10003, JSON.stringify(sourceException));
                        }
                    }
                }
            }
        }
        );
        listener.onSeeking(fun(reason: Int){
            if (reason == Player.DISCONTINUITY_REASON_SEEK) {
                this._isSeeking = true;
                console.log(this._seekCallback, " at uni_modules/kux-audio-player/utssdk/app-android/audioPlayerManager.uts:195");
                if (this._seekCallback) {
                    this.onSeekingCallback?.invoke();
                }
            }
        }
        );
        listener.onSeeked(fun(){
            this._isSeeking = false;
            if (this._seekCallback) {
                this.onSeekedCallback?.invoke();
            }
        }
        );
        listener.onMediaItemChange(fun(reason: Int){
            if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO) {
                if (!this._autonext) {
                    this.audioPlayer.stop();
                } else {
                    this.onNextCallback?.invoke();
                }
            }
        }
        );
        this.audioPlayer.addListener(listener);
    }
    private fun _handleSystemException(error: UTSError) {
        this._handleException(10001, error.message ?: "系统错误");
    }
    private fun _setCurrentSrc() {
        this._src = this._urls[this._currentIndex];
    }
    private fun _playNext() {
        if (this._currentIndex >= this._urls.length) {
            this._currentIndex = 0;
        } else {
            this._currentIndex++;
        }
        this._setMediaItems();
        this.play();
    }
    private fun _isPlayEnding(): Boolean {
        return this._currentIndex >= this._urls.length - 1;
    }
    private fun _loopOne() {
        this._setMediaItems();
        this.play();
    }
    private fun _loopAll() {
        this._currentIndex = UTSNumber.from(this._urls.length);
        this._playNext();
    }
    override var startTime: Number
        get(): Number {
            return this._startTime;
        }
        set(value: Number) {
            this._startTime = value;
            this.seek(value);
        }
    override fun setPlaybackStartTime(value: Number) {}
    override var autoplay: Boolean
        get(): Boolean {
            return this._autoplay;
        }
        set(value: Boolean) {
            this._autoplay = value;
            if (this._src.length > 0 && this._autoplay) {
                this.play();
            }
        }
    override fun setPlaybackAutoplay(value: Boolean) {}
    override var autonext: Boolean
        get(): Boolean {
            return this._autonext;
        }
        set(value: Boolean) {
            this._autonext = value;
        }
    override fun setPlaybackAutonext(value: Boolean) {}
    override var loop: Loop
        get(): Loop {
            return this._loop;
        }
        set(value: Loop) {
            this._loop = value;
        }
    override fun setPlaybackLoop(value: Loop) {}
    override var volume: Number
        get(): Number {
            return this._volume;
        }
        set(value: Number) {
            this._volume = value;
            try {
                this.audioPlayer.setVolume(this._volume.toFloat());
            }
             catch (err: Throwable) {
                val error = err as UTSError;
                this._handleSystemException(error);
            }
        }
    override fun setPlaybackVolume(value: Number) {}
    override var src: String
        get(): String {
            return this._src;
        }
        set(value: String) {
            try {
                var src = "";
                if (value.indexOf("http") < 0) {
                    src = getResourcePath(value);
                } else {
                    src = value;
                }
                val index = this._urls.indexOf(src);
                if (index > -1) {
                    this._currentIndex = index;
                } else {
                    if (this._srcMode == "push") {
                        this._urls.push(src);
                    } else {
                        if (this._urls.length - 1 < this._currentIndex) {
                            this._urls.push(src);
                        } else {
                            this._urls[this._currentIndex] = src;
                        }
                    }
                }
                this._setMediaItems();
                if (this._autoplay) {
                    this.play();
                }
            }
             catch (err: Throwable) {
                console.log(err, " at uni_modules/kux-audio-player/utssdk/app-android/audioPlayerManager.uts:351");
                val error = err as UTSError;
                this._handleSystemException(error);
            }
        }
    override fun setPlaybackSrc(value: String) {}
    override val duration: Number
        get(): Number {
            try {
                return this.audioPlayer.getDuration() / 1000;
            }
             catch (err: Throwable) {
                val error = err as UTSError;
                this._handleSystemException(error);
                return 0;
            }
        }
    override var currentTime: Number
        get(): Number {
            try {
                return parseFloat((UTSNumber.from(this.audioPlayer.getCurrentPosition()) / 1000).toFixed(6));
            }
             catch (err: Throwable) {
                val error = err as UTSError;
                this._handleSystemException(error);
                return 0;
            }
        }
        set(value: Number) {
            this.seek(value);
        }
    override fun setPlaybackCurrentTime(value: Number) {}
    override val paused: Boolean
        get(): Boolean {
            try {
                val isPlaying = this.audioPlayer.getPlayWhenReady();
                val state = this.audioPlayer.getPlaybackState();
                if ((!isPlaying && state == Player.STATE_READY) || (state == Player.STATE_IDLE || state == Player.STATE_ENDED)) {
                    return true;
                }
                return false;
            }
             catch (err: Throwable) {
                val error = err as UTSError;
                this._handleSystemException(error);
                return false;
            }
        }
    override var playbackRate: Number
        get(): Number {
            return this._playbackSpeed;
        }
        set(value: Number) {
            try {
                if (value >= 0.5 && value <= 2.0) {
                    this._playbackSpeed = value;
                    this.audioPlayer.setPlaybackSpeed(value.toFloat());
                }
            }
             catch (err: Throwable) {
                val error = err as UTSError;
                this._handleSystemException(error);
            }
        }
    override fun setPlaybackRates(value: Number) {}
    override val buffered: Number
        get(): Number {
            return this._buffered;
        }
    override var currentIndex: Number
        get(): Number {
            return this._currentIndex;
        }
        set(index: Number) {
            if (index >= this._urls.length) {
                this._currentIndex = 0;
            } else if (index < 0) {
                this._currentIndex = 0;
            } else {
                this._currentIndex = index;
            }
            this._setMediaItems();
            if (this.autoplay) {
                this.play();
            }
        }
    override fun setPlaybackCurrentIndex(index: Number) {}
    override var srcMode: SrcMode
        get(): SrcMode {
            return this._srcMode;
        }
        set(mode: SrcMode) {
            this._srcMode = mode;
        }
    override fun setPlaybackSrcMode(mode: SrcMode) {
        this.srcMode = mode;
    }
    override var obeyMuteSwitch: Boolean
        get(): Boolean {
            return this._obeyMuteSwitch;
        }
        set(value: Boolean) {
            this._obeyMuteSwitch = value;
        }
    override fun setPlaybackObeyMuteSwitch(value: Boolean) {}
    private fun _setMediaItems() {
        this._setCurrentSrc();
        this.stop();
        this._enableSeekCallback();
        this.currentTime = 0;
        val mediaItem: MediaItem = MediaItem.Builder().setUri(this.src).build();
        this.audioPlayer.setMediaItems(List.of(mediaItem), false);
        this.audioPlayer.prepare();
    }
    private fun _handleException(code: Number, message: String) {
        val fail = ApiFailImpl(code);
        fail.cause = SourceError(message);
        this.onErrorCallback?.invoke(fail);
    }
    private fun _isAvailableExtractors(exception: IOException) {}
    private fun _disabledPlayCallback() {
        this._playCallback = false;
    }
    private fun _enablePlayCallback() {
        this._playCallback = true;
    }
    private fun _disableSeekCallback() {
        this._seekCallback = false;
    }
    private fun _enableSeekCallback() {
        this._seekCallback = true;
    }
    override fun play() {
        try {
            if (this.audioPlayer.getPlaybackState() == Player.STATE_IDLE) {
                this._setMediaItems();
            }
            this._enablePlayCallback();
            this.audioPlayer.setPlayWhenReady(true);
            this.initPlay = true;
        }
         catch (error: Throwable) {
            this._handleException(10001, error.message ?: "系统错误");
        }
    }
    override fun pause() {
        try {
            this._disabledPlayCallback();
            this.audioPlayer.setPlayWhenReady(false);
        }
         catch (error: Throwable) {
            this._handleException(10001, error.message ?: "系统错误");
        }
    }
    override fun stop() {
        try {
            this._disabledPlayCallback();
            this.audioPlayer.stop();
            this._disableSeekCallback();
            this.seek(0);
            this.onTimeUpdateCallback?.invoke();
        }
         catch (err: Throwable) {
            val error = err as UTSError;
            this._handleSystemException(error);
        }
    }
    override fun seek(position: Number) {
        try {
            val currentMediaItemIndex = this.audioPlayer.getCurrentMediaItemIndex();
            val seekPositionMs = position * 1000;
            this.audioPlayer.seekTo(currentMediaItemIndex, seekPositionMs.toLong(), Player.COMMAND_STOP, false);
            this._disabledPlayCallback();
        }
         catch (err: Throwable) {
            val error = err as UTSError;
            this._handleSystemException(error);
        }
    }
    override fun destroy() {
        this.offError();
        this.offCanplay();
        this.offPlay();
        this.offPause();
        this.offStop();
        this.offEnded();
        this.offTimeUpdate();
        this.offWaiting();
        this.offSeeking();
        this.offSeeked();
        this.offNext();
        this.audioPlayer.release();
    }
    override fun onError(callback: OnErrorCallback) {
        this.onErrorCallback = callback;
    }
    override fun offError() {
        this.onErrorCallback = null;
    }
    override fun onCanplay(callback: OnCommonCallback) {
        this.onCanplayCallback = callback;
    }
    override fun offCanplay() {
        this.onCanplayCallback = null;
    }
    override fun onPlay(callback: OnCommonCallback) {
        this.onPlayCallback = callback;
    }
    override fun offPlay() {
        this.onPlayCallback = null;
    }
    override fun onPause(callback: OnCommonCallback) {
        this.onPauseCallback = callback;
    }
    override fun offPause() {
        this.onPauseCallback = null;
    }
    override fun onStop(callback: OnCommonCallback) {
        this.onStopCallback = callback;
    }
    override fun offStop() {
        this.onStopCallback = null;
    }
    override fun onEnded(callback: OnCommonCallback) {
        this.onEndedCallback = callback;
    }
    override fun offEnded() {
        this.onEndedCallback = null;
    }
    override fun onTimeUpdate(callback: OnCommonCallback) {
        this.onTimeUpdateCallback = callback;
    }
    override fun offTimeUpdate() {
        this.onTimeUpdateCallback = null;
    }
    override fun onWaiting(callback: OnCommonCallback) {
        this.onWaitingCallback = callback;
    }
    override fun offWaiting() {
        this.onWaitingCallback = null;
    }
    override fun onSeeking(callback: OnCommonCallback) {
        this.onSeekingCallback = callback;
    }
    override fun offSeeking() {
        this.onSeekingCallback = null;
    }
    override fun onSeeked(callback: OnCommonCallback) {
        this.onSeekedCallback = callback;
    }
    override fun offSeeked() {
        this.onSeekedCallback = null;
    }
    override fun onNext(callback: OnCommonCallback) {
        this.onNextCallback = callback;
    }
    override fun offNext() {
        this.onNextCallback = null;
    }
}
val createAudioPlayer = fun(): IAudioPlayer {
    return AudioPlayerManager();
}
;
