package com.example.musicmachine;

import org.jmusixmatch.MusixMatch;
import org.jmusixmatch.MusixMatchException;
import org.jmusixmatch.entity.track.Track;

import java.util.List;

public class LyricsSearch {
    String apiKey = "1471f2a32786006d6241f7c584b6a0bc";
    MusixMatch musixMatch = new MusixMatch(apiKey);

    public  LyricsSearch(){

    }

    public List<Track> MatchByLyrics(String lyrics) throws MusixMatchException {
        List<Track> tracks = musixMatch.searchTracks("", "Eminem", "", 10, 10, true);
        return tracks;
    }
}
