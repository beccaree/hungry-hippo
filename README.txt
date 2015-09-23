# Prototype for VIDIVOX
SE206 2015 - Assignment 3 (Prototype for VIDIVOX)

VIDIVOX is a video editing platform that allows the users to add mp3 or audio over a video. 
In addition, it should run on Linux Main [L].

Follow these instructions to to run VIDIVOX prototype:

1. Boot the Linux main image (L) 
2. Open Up the terminal
3. Change Directories to where the jar file has been downloaded or stored through the terminal
	e.g>> cd Downloads (If it is in your downloads directory)
4. Once in the correct directory, run the jar file by typing the following into the terminal:

	>> java -jar VIDIVOX_izhu678_rlee291.jar
	
5. A prompt should appear for you to choose a video.



Explanation of Features:

Overlaying Audio with Video
This video player allows the user to add audio commentary onto top of the currently playing video.
To do this, the user can type out their commentary in the text area on the right of the screen under "Commentary here:"

[Speak]:
To preview what the commentary will sound like in the text area, clicking speak will play the audio.

[Stop]:
To stop hearing the commentary played by [Speak], [Stop] will stop the audio.
Note: It will take a small delay to stop the audio from "speaking".

[Save as MP3]:
This will save the commentary into the folder "MP3Files" which is in the current folder the jar file is in.
The user can choose to name this file.

[Merge With MP3]:
This will merge any .mp3 file chosen by the user and merge it with the currently playing video.
It will then prompt the user to choose if they want to play the newly merged video after it is made.



Further Options:
In the current folder, 'Overlaying Audio with Video' produces 3 intermediate files, all of which are hidden in MP3Files.

sound.wav: MP3Files
vidAudio.mp3: MP3Files
output.mp3: MP3Files

A visible file called output.avi is in the folder VideoFiles and is the most recently combined video with the chosen .mp3 from [Merge With MP3].

output.avi: VideoFiles

