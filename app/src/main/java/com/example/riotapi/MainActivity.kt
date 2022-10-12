package com.example.riotapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

import com.github.kimcore.riot.RiotAPI
import com.github.kimcore.riot.enums.Platform
import com.github.kimcore.riot.enums.Region
import com.github.kimcore.riot.errors.RiotException
import com.github.kimcore.riot.routes.SummonerDTO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
* How to Run
*
* 참고자료 : https://github.com/kimcore/riot.kt
* settings.gradle -> dependencyResolutionManagement -> repositories -> maven { url 'https://jitpack.io' } 추가
* build.gradle -> dependencies -> implementation("com.github.kimcore:riot.kt:1.0") 추가
* AndroidManifest -> <uses-permission android:name="android.permission.INTERNET" /> 추가
* */

class MainActivity : AppCompatActivity() {

    private var nickname = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* RIOT API setting */
        //임시 api key setting 24시간마다 갱신해야됨!!!
        RiotAPI.setApiKey("RGAPI-a1a3f9f1-9e77-422a-b247-1f7830e6765d")
        // optional, defaults to KR, ASIA
        RiotAPI.setDefaultPlatform(Platform.KR)
        RiotAPI.setDefaultRegion(Region.ASIA)

        //nickname 저장
        var tierCheckBtn = findViewById<Button>(R.id.tierCheckBtn)
        tierCheckBtn.setOnClickListener {
            // nickname 받아오기
            nickname = findViewById<TextInputEditText>(R.id.nickname).text.toString()

            // tiercheck 함수 실행, suspend 함수라서 코루틴 사용해야함
            CoroutineScope(Dispatchers.Main).launch {
                val tierString = tierCheck(nickname)
                //textView의 문자열을 tier로 바꿔줌
                tier.text = tierString
            }
            Toast.makeText(this, "티어측정 성공!", Toast.LENGTH_SHORT).show()

        }

    }

    /* tierCheck 해서 문자열로 반환하는 함수 */
    suspend fun tierCheck(name: String): String {
        // 소환사가 없을 경우 NO SUMMONER 반환
        try {
            RiotAPI.summoner.getSummonerByName(name)
        } catch(e: RiotException) {
            Log.d("error",e.statusCode.toString()) // 404
            Log.d("error",e.statusMessage) // Data not found
            return "NO SUMMONER"
        }

        // 소환사 데이터 받아오기
        val summoner = RiotAPI.summoner.getSummonerByName(name)
        // 소환사 리그 데이터 받아오기
        val leagueEntries = RiotAPI.league.getLeagueEntriesBySummonerID(summoner.id)

        // 만약 유저가 unrank 이거나 배치고사를 보지 않아 솔로랭크티어가 없는 경우
        if (leagueEntries.toString() == "[]") {
            return "UNRANKED"
        // 솔로 랭크 티어가 있는 경우
        } else {
            val soloTier: String =
                leagueEntries.first { it.queueType == "RANKED_SOLO_5x5" }.tier
            return soloTier
        }

    }
}