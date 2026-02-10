package eu.pw.messageboard.domian.mediaplayer

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import eu.pw.messageboard.R

class NewMessageSoundPlayer(
	context: Context
                           ) : INewMessageNotifier {

	private val appContext = context.applicationContext
	private val soundPool: SoundPool
	private val messageSoundId: Int

	init {
		val audioAttributes = AudioAttributes
			.Builder()
			.setUsage(AudioAttributes.USAGE_NOTIFICATION)
			.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
			.build()
		soundPool = SoundPool
			.Builder()
			.setMaxStreams(1)
			.setAudioAttributes(audioAttributes)
			.build()

		messageSoundId = soundPool.load(appContext, R.raw.message_sound, 1)
	}

	override fun alertNewMessage() {
		soundPool.play(
			messageSoundId,
			1.0f,
			1.0f,
			0,
			0,
			1f
					  )
	}

	override fun release(){
		soundPool.release()
	}
}