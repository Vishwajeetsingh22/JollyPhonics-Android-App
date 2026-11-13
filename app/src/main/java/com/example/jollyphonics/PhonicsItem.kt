package com.example.jollyphonics

data class PhonicsItem(
    val letter: String,
    val sound: String,
    val word: String,
    val description: String
) {
    companion object {
        fun getAllPhonics(): List<PhonicsItem> {
            return listOf(
                PhonicsItem("A", "/a/", "Apple", "Ants on your arm /a/ /a/ /a/"),
                PhonicsItem("B", "/b/", "Ball", "Bring your bat and ball /b/ /b/ /b/"),
                PhonicsItem("C", "/b, k/", "Cat", "We are clicking cards /k/ /k/ /k/"),
                PhonicsItem("D", "/d/", "Dog", "See me play on my drum /d/ /d/ /d/"),
                PhonicsItem("E", "/e/", "Elephant", "Eggs in the pan /e/ /e/ /e/"),
                PhonicsItem("F", "/f/", "Fish", "My dog barks /f/ /f/ /f/"),
                PhonicsItem("G", "/g/", "Goat", "Water gurgles down the drain /g/ /g/ /g/"),
                PhonicsItem("H", "/h/", "Hat", "Hear the horse panting /h/ /h/ /h/"),
                PhonicsItem("I", "/i/", "Igloo", "Inky the mouse is wet /i/ /i/ /i/"),
                PhonicsItem("J", "/j/", "Jug", "Jack in the box jumps up /j/ /j/ /j/"),
                PhonicsItem("K", "/k/", "Kite", "Kites flying in the sky /k/ /k/ /k/"),
                PhonicsItem("L", "/l/", "Lion", "Licking lollipops /l/ /l/ /l/"),
                PhonicsItem("M", "/m/", "Monkey", "Mmm, yummy food /m/ /m/ /m/"),
                PhonicsItem("N", "/n/", "Nest", "Hear the plane making a sound /n/ /n/ /n/"),
                PhonicsItem("O", "/o/", "Orange", "Turn on the light /o/ /o/ /o/"),
                PhonicsItem("P", "/p/", "Pig", "Puff out the candles /p/ /p/ /p/"),
                PhonicsItem("Q", "/qu/", "Queen", "Duck goes quack /qu/ /qu/ /qu/"),
                PhonicsItem("R", "/r/", "Rabbit", "Puppy tears the rag /r/ /r/ /r/"),
                PhonicsItem("S", "/s/", "Sun", "The snake goes in the grass /s/ /s/ /s/"),
                PhonicsItem("T", "/t/", "Train", "Tennis match going back and forth /t/ /t/ /t/"),
                PhonicsItem("U", "/u/", "Umbrella", "Up goes the umbrella /u/ /u/ /u/"),
                PhonicsItem("V", "/v/", "Van", "Drive the van /v/ /v/ /v/"),
                PhonicsItem("W", "/w/", "Watch", "The wind blows /w/ /w/ /w/"),
                PhonicsItem("X", "/ks/", "Xylophone", "X-ray picture /ks/ /ks/ /ks/"),
                PhonicsItem("Y", "/y/", "Yacht", "Eating yogurt /y/ /y/ /y/"),
                PhonicsItem("Z", "/z/", "Zebra", "Buzzy bees in the hive /z/ /z/ /z/")
            )
        }
    }
}