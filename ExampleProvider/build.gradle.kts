import com.lagradost.cloudstream3.gradle.CloudstreamExtension

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.lagradost.cloudstream3.gradle")
}

cloudstream {
    setPackage("com.hcgn2005")
    authors = listOf("hcgn2005-ai")
    description = "Anikai.to Anime Plugin"
}
